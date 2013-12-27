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

import sink.core.Config;
import sink.core.Sink;
import sink.demo.basic.scene.CreditsScene;
import sink.demo.basic.scene.GameScene;
import sink.demo.basic.scene.LevelScene;
import sink.demo.basic.scene.LoginScene;
import sink.demo.basic.scene.MenuScene;
import sink.demo.basic.scene.OptionsScene;
import sink.demo.basic.scene.SplashScene;
import sink.event.CreateListener;
import sink.main.MainDesktop;

public class BasicDesktop extends MainDesktop{
	public static void main(String[] argc) {
		Config.isJar = false; // set to true when exporting to jar
		Config.title = "BasicDemo"; // your game title name
		Config.showIcon = true; // whether you want to use an icon for your game
		Config.iconLocation = "icon/icon_32.png"; // specify the location of your icon
		Config.TARGET_WIDTH = 800; // your game's target width it will automatically scale to other sizes
		Config.TARGET_HEIGHT = 480; // your game's target height it will automatically scale to other sizes
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate() {
				Sink.registerScene("splash", new SplashScene());
				Sink.registerScene("menu", new MenuScene());
				Sink.registerScene("options", new OptionsScene());
				Sink.registerScene("credits", new CreditsScene());
				Sink.registerScene("login", new LoginScene());
				Sink.registerScene("level", new LevelScene());
				Sink.registerScene("game", new GameScene());
				Sink.setScene("splash");
			}
		});
		init();
		run();
	}
}