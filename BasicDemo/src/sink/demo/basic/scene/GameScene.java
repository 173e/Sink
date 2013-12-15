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

package sink.demo.basic.scene;


import static sink.core.Asset.unloadTmx;
import sink.core.Asset;
import sink.core.Config;
import sink.core.Scene;
import sink.core.SceneManager;
import sink.core.Sink;

import sink.event.PauseListener;
import sink.event.ResumeListener;
import sink.map.Map;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Base64Coder;

public final class GameScene extends Scene implements PauseListener, ResumeListener {
	Image pauseImage;
	TextButton pauseBtn;
	
	/* Game Constants */
	private GameState gameState = GameState.GAME_RUNNING;
	public static int currentLevel = 0;
	Map map;
	
	@Override
	public void init() {
		musicPlay("level1");
		map = new Map(Asset.loadTmx(currentLevel+1), 24);
		map.loadLayer(0);
		map.loadLayer(1);
		addActor(map);
		pauseImage = new Image(Asset.skin.getRegion("default"));
		pauseBtn = new TextButton("Pause", Asset.skin);
		pauseBtn.setPosition(300, 300);
		pauseBtn.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!Sink.pauseState)
					Sink.firePauseEvent();
				else
					Sink.fireResumeEvent();
 			}
 		});
		addActor(pauseBtn);
		Sink.camera.addHudActor(SceneManager.fpsLabel);
		Sink.camera.addHudActor(SceneManager.logPane);
		Sink.camera.addHudActor(pauseBtn);
		Sink.addListener((PauseListener)this);
		Sink.addListener((ResumeListener)this);
		Sink.camera.enablePanning();
	}

	@Override
	public void onPause() {
		pauseBtn.setText("Resume");
		pauseImage.setFillParent(true);
		pauseImage.setTouchable(Touchable.disabled);
		pauseImage.setColor(1, 1, 1, 0);
		pauseImage.addAction(Actions.alpha(0.6f, 0.7f, Interpolation.linear));
		pauseImage.setPosition(Sink.camera.position.x - Config.TARGET_WIDTH/2,
				Sink.camera.position.y - Config.TARGET_HEIGHT/2);
		addActor(pauseImage);
		Sink.camera.disablePanning();
	}

	@Override
	public void onResume() {
		pauseBtn.setText("Pause");
		Action hideAction = Actions.alpha(0f, 0.6f, Interpolation.linear);
		Action over = new Action() {
			@Override
			public boolean act(final float it) {
				removeActor(pauseImage);
				return true;
			}
		};
		SequenceAction sequence = Actions.sequence(hideAction, over);
		pauseImage.addAction(sequence);
		Sink.camera.enablePanning();
	}
	
	public void save() {
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bb = Tea.encrypt("zzd", "aa");
		//Config.writeSaveData(bb.toString());//.writeBytes(bb, false);
		Config.writeSaveData(Base64Coder.encodeString("faa"));
		Scene.log("Save: "+Config.readSaveData());
	}
	
	public void load(){
		String data = Config.readSaveData();
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bytes = Tea.decrypt(data, "aa");
		Scene.log("Load: "+Base64Coder.decodeString(data));
	}

	public void back() {
		Sink.camera.disablePanning();
		unloadTmx(currentLevel+1);
		Sink.removeListener((PauseListener)this);
		Sink.removeListener((ResumeListener)this);
		Sink.camera.removeHudActor(SceneManager.fpsLabel);
		Sink.camera.removeHudActor(SceneManager.logPane);
		Sink.camera.removeHudActor(pauseBtn);
	}
	
	public void setState(GameState ss){
		gameState = ss;
		Scene.log("Game State: " + gameState.toString());
	}
}

enum GameState {
	GAME_RUNNING,
	GAME_OVER,
	GAME_WIN,
	GAME_LEVELWIN
}