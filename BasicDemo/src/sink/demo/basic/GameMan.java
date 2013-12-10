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

package sink.demo.basic;

import static sink.core.Asset.$musicPlay;
import static sink.core.Asset.$unloadTmx;
import sink.core.Asset;
import sink.core.Config;
import sink.core.Scene;
import sink.core.Sink;
import sink.map.Map;

import com.badlogic.gdx.utils.Base64Coder;

public class GameMan {
	/* Game Constants */
	private static GameState gameState = GameState.GAME_MENU;
	public static int $CURRENT_LEVEL = 0;
	static World world;
	static Map map;
	static int currentTurn = 1;
	
	public static void startLevel() {
		Sink.stage.clear();
		map = new Map(Asset.$loadTmx(GameMan.$CURRENT_LEVEL+1), 24);
		map.loadLayer(0);
		map.loadLayer(1);
		world = new World();
		Sink.stage.addActor(map);
		Sink.stage.addActor(world);
		Sink.camera.enablePanning();
		$musicPlay("level1");
		GameMan.$setState(GameState.GAME_RUNNING);
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

	public static void back() {
		Sink.camera.disablePanning();
		$unloadTmx(GameMan.$CURRENT_LEVEL+1);
		GameMan.$setState(GameState.GAME_MENU);
	}
	
	public static void addCamX(float x){
		Sink.camera.moveBy(x, 0, 0.003f);
	}
	
	public static void addCamY(float y){
		Sink.camera.moveBy(0, y, 0.003f);
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
		Scene.log("Game State: " + gameState.toString());
	}

}