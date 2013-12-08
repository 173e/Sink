/*******************************************************************************
 * Copyright 2013 pyros2097
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package sky.engine.core;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

import static sky.engine.core.Asset.$musicPause;
import static sky.engine.core.Asset.$musicResume;
import static sky.engine.core.Asset.$soundStop;

/** The Main EntryPoint of a libGdx game.
 * <p>
 * The Engine contains a stage and a orthographic camera. The Engine controls and handles the splashscreens,
 * the game screens/menus/panels and how the game is rendered. This is where the assets are loaded during
 * the splash screen.
 * <p>
 * @author pyros2097 */

public abstract class Engine implements ApplicationListener {
	private float startTime = System.nanoTime();
	public static float SECONDS_TIME = 0;
	
	public static SmoothCamera $camera;
	public static Stage $stage;
	public Scene splashPanel = null;
	
	@Override
	public void create() {
		Scene.$log("Engine: Created");
		$stage = new Stage(Config.SCREEN_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
		$camera = new SmoothCamera();
		$camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		$camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		$stage.setCamera($camera);
 		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor($stage);
 		Scene.$log("TotalTime: "+getScreenTime(Config.readTotalTime()));
	}

	/**
	 * Get screen time from start in format of HH:MM:SS. It is calculated from
	 * "secondsTime" parameter.
	 * */
	public static String getScreenTime(float secondstime) {
		int seconds = (int)(secondstime % 60);
		int minutes = (int)((secondstime / 60) % 60);
		int hours =  (int)((secondstime / 3600) % 24);
		return new String(addZero(hours) + ":" + addZero(minutes) + ":" + addZero(seconds));
	}
	
	private static String addZero(int value){
		String str = "";
		if(value < 10)
			 str = "0" + value;
		else
			str = "" + value;
		return str;
	}
	
	private void updateTime(float delta) {
		// Update screen clock (1 second tick)
		// ############################################################
		if (System.nanoTime() - startTime >= 1000000000) {
			SECONDS_TIME +=1 ;
			startTime = System.nanoTime();
		}

		// Update animation times
		// ############################################################
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update stage/actors logic (update() method in previous games)
		// ############################################################
		$stage.act(delta);
		// Render drawings (draw()/render() methods in previous games)
		// ############################################################
		$stage.draw();
	}
	
	@Override
	public void render(){
		float delta = Gdx.graphics.getDeltaTime();
		updateTime(delta);
		if(splashPanel != null)
			splashPanel.update();
	  	//GameMan.update(delta);
 	}

	@Override
	public void resize(int width, int height) {
		Scene.$log("Engine: Resize");
		$stage.setViewport(Config.TARGET_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
	}

	@Override
	public void pause() {
		Scene.$log("Engine: Pause");
		$musicPause();
		$soundStop();
		//if(GameMan.$state(GameState.GAME_RUNNING)){
		//	GameMan.$setState(GameState.GAME_PAUSED);
		//}	
	}

	@Override
	public void resume() {
		Scene.$log("Engine: Resume");
		$musicResume();
	}

	@Override
	public void dispose() {
		Scene.$log("Engine: Disposing");
		Config.writeTotalTime(SECONDS_TIME);
		$stage.dispose();
		Asset.unloadAll();
		Gdx.app.exit();
	}
}