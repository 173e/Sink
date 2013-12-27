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

package sink.demo.tictactoe;

import sink.core.Config;
import sink.core.Sink;
import sink.event.CreateListener;
import sink.demo.tictactoe.scene.GameScene;
import sink.demo.tictactoe.scene.MenuScene;
import sink.demo.tictactoe.scene.SplashScene;
import sink.main.MainDesktop;

public class TicTacToeDesktop extends MainDesktop {
	public static void main(String[] argc) {
		Config.isJar = false; // set to true when exporting to jar
		Config.title = "TicTacToe"; // your game title name
		Config.showIcon = true; // whether you want to use an icon for your game
		Config.iconLocation = "icon/icon_32.png"; // specify the location of your icon
		Config.TARGET_WIDTH = 800; // your game's target width it will automatically scale to other sizes
		Config.TARGET_HEIGHT = 480; // your game's target height it will automatically scale to other sizes
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate(){
				Sink.registerScene("splash", new SplashScene());
				Sink.registerScene("menu", new MenuScene());
				Sink.registerScene("game", new GameScene());
				Sink.setScene("splash");
			}
		});
		init();
		run();
	}
}