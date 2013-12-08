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

import sky.engine.core.Config;
import sky.engine.core.Engine;
import sky.engine.core.Scene;

import com.badlogic.gdx.utils.Base64Coder;

public class GameMan {
	/* Game Constants */
	private static GameState gameState = GameState.GAME_MENU;
	public static boolean $IS_GAME_SCREEN = false;
	
	public static void startLevel() {
		GameMan.$setState(GameState.GAME_RUNNING);
		GameMan.$IS_GAME_SCREEN = true;
	}

	public static void update(float delta){
	}
	
	static void checkGameCondition() {
	}

	public void save() {
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bb = Tea.encrypt("zzd", "aa");
		//Config.writeSaveData(bb.toString());//.writeBytes(bb, false);
		Config.writeSaveData(Base64Coder.encodeString("faa"));
		Scene.$log("Save: "+Config.readSaveData());
	}
	
	public void load(){
		String data = Config.readSaveData();
		//FileHandle file = Gdx.files.internal("data/savedata.bin");
		//byte[] bytes = Tea.decrypt(data, "aa");
		Scene.$log("Load: "+Base64Coder.decodeString(data));
	}

	public static void back() {
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
		Scene.$log("Game State: " + gameState.toString());
	}

}