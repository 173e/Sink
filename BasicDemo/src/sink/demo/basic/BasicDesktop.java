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
import sink.core.SceneManager;
import sink.core.Sink;
import sink.demo.basic.scene.CreditsScene;
import sink.demo.basic.scene.GameScene;
import sink.demo.basic.scene.LevelScene;
import sink.demo.basic.scene.LoginScene;
import sink.demo.basic.scene.MenuScene;
import sink.demo.basic.scene.OptionsScene;
import sink.demo.basic.scene.SplashScene;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class BasicDesktop{
	public static void main(String[] argc) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.audioDeviceBufferCount = 20;
		cfg.title = Config.title;
		cfg.useGL20 = Config.useGL20;
		LwjglApplicationConfiguration.disableAudio = Config.diableAudio;
		cfg.width = Config.SCREEN_WIDTH;
		cfg.height = Config.SCREEN_HEIGHT;
		cfg.forceExit = Config.forceExit;
		cfg.fullscreen = Config.fullscreen;
		cfg.resizable = Config.resizable;
		cfg.vSyncEnabled = Config.vSyncEnabled;
		cfg.x = Config.SCREEN_X;
		cfg.y = Config.SCREEN_Y;
		cfg.addIcon(Config.icon, FileType.Internal);
		new LwjglApplication(new Sink(){
			@Override
			public void create(){
				super.create();
				SceneManager.registerScene("splash", new SplashScene());
				SceneManager.registerScene("menu", new MenuScene());
				SceneManager.registerScene("options", new OptionsScene());
				SceneManager.registerScene("credits", new CreditsScene());
				SceneManager.registerScene("login", new LoginScene());
				SceneManager.registerScene("level", new LevelScene());
				SceneManager.registerScene("game", new GameScene());
				SceneManager.setCurrentScene("splash");
			}
		}, cfg); 
	}
}