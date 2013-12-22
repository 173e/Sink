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

import sink.core.Sink;
import sink.event.CreateListener;
import sink.demo.tictactoe.scene.GameScene;
import sink.demo.tictactoe.scene.MenuScene;
import sink.demo.tictactoe.scene.SplashScene;
import sink.main.MainDesktop;

public class TicTacToeDesktop extends MainDesktop {
	public static void main(String[] argc) {
		init();
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate(){
				Sink.registerScene("splash", new SplashScene());
				Sink.registerScene("menu", new MenuScene());
				Sink.registerScene("game", new GameScene());
				Sink.setScene("splash");
			}
		});
		run();
	}
}