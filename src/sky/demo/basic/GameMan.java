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

package sky.demo.basic;

import static sky.engine.core.Asset.$musicPlay;
import static sky.engine.core.Asset.$unloadTmx;
import sky.engine.core.Asset;
import sky.engine.core.Config;
import sky.engine.core.Engine;
import sky.engine.core.Panel;
import sky.engine.map.Map;

import com.badlogic.gdx.utils.Base64Coder;

public class GameMan {
	/* Game Constants */
	private static GameState gameState = GameState.GAME_MENU;
	public static boolean $IS_GAME_SCREEN = false;
	public static int $CURRENT_LEVEL = 0;
	
	static World world;
	static Map map;
	static String playerMusic;
	static String enemyMusic;
	static int currentTurn = 1;
	
	public static void startLevel() {
		Engine.$stage.clear();
		map = new Map(Asset.$loadTmx(GameMan.$CURRENT_LEVEL+1), 24);
		map.loadLayer(0);
		map.loadLayer(1);
		world = new World();
		Engine.$camera.setActor(world);
		$musicPlay("level1");
		GameMan.$setState(GameState.GAME_RUNNING);
		GameMan.$IS_GAME_SCREEN = true;
	}

	public static void update(float delta){
		if($IS_GAME_SCREEN){
			if ($state(GameState.GAME_RUNNING)){
				map.act(delta);
				//checkGameCondition();
				//if (touchpad.isTouched()) map.touchPad(touchpad.getKnobPercentX(), touchpad.getKnobPercentY());
				world.hidePause();
			}
			else if($state(GameState.GAME_PAUSED)){
				world.showPause();
			}
			else if($state(GameState.GAME_LEVELWIN)){
				back();
			}
			else if($state(GameState.GAME_OVER)){
				back();
			}
			else if($state(GameState.GAME_WIN)){
				//final credits
			}
		}
	}
	
	static void checkGameCondition() {
	}

	

	public void save() {
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bb = Tea.encrypt("zzd", "aa");
		//Config.writeSaveData(bb.toString());//.writeBytes(bb, false);
		Config.writeSaveData(Base64Coder.encodeString("faa"));
		Panel.$log("Save: "+Config.readSaveData());
	}
	
	public void load(){
		String data = Config.readSaveData();
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bytes = Tea.decrypt(data, "aa");
		Panel.$log("Load: "+Base64Coder.decodeString(data));
	}

	public static void back() {
		Map.removeController();
		$unloadTmx(GameMan.$CURRENT_LEVEL+1);
		GameMan.$setState(GameState.GAME_MENU);
		GameMan.$IS_GAME_SCREEN = false;
	}
	
	public static void addCamX(float x){
		Engine.$camera.moveBy(x, 0, 0.003f);
	}
	
	public static void addCamY(float y){
		Engine.$camera.moveBy(0, y, 0.003f);
	}
	
	/* Check if state is current return true */
	public static boolean $state(GameState ss){
		if(gameState == ss)
			return true;
		else
			return false;
	}
	
	public static void $setState(GameState ss){
		gameState = ss;
		Panel.$log("Game State: " + gameState.toString());
	}

}