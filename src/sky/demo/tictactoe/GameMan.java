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

package sky.demo.tictactoe;

import sky.engine.core.Config;
import sky.engine.core.Panel;

import com.badlogic.gdx.utils.Base64Coder;

public class GameMan {
	/* Game Constants */
	private static GameState gameState = GameState.GAME_MENU;
	private static GameMode gameMode = GameMode.SINGLE_PLAYER_VS_COMPUTER;
	
	public static boolean $IS_GAME_SCREEN = false;
	static String playerMusic;
	static String enemyMusic;
	
	public static Turn currentTurn = Turn.Player;
	static GamePanel gamePanel;
	
	public static void startLevel() {
		gamePanel = new GamePanel();
		GameMan.$setState(GameState.GAME_RUNNING);
		GameMan.$IS_GAME_SCREEN = true;
	}

	public static void update(float delta){
		if($IS_GAME_SCREEN){
			if ($state(GameState.GAME_RUNNING)){
				gamePanel.update();
			}
		}
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
		GameMan.$setState(GameState.GAME_MENU);
		GameMan.$IS_GAME_SCREEN = false;
	}
	
	/* Check if state is current return true */
	public static boolean $mode(GameMode gm){
		if(gameMode == gm)
			return true;
		else
			return false;
	}
	
	public static void $setMode(GameMode gm){
		gameMode = gm;
		Panel.$log("Game State: " + gameState.toString());
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