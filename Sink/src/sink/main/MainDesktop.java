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

package sink.main;

import sink.core.Config;
import sink.core.Sink;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class MainDesktop{
	public static LwjglApplicationConfiguration cfg;
	public static void init() {
		cfg = new LwjglApplicationConfiguration();
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
		if(Config.showIcon)
			cfg.addIcon(Config.iconLocation, FileType.Internal);
		//Integer apiVersion = 1;
		//StackMob client = new StackMob(apiVersion, "1b5090a2-970b-4f13-ba19-ce5a06ea72f1");
		//Task myTask = new Task("Learn more about StackMob", new Date());
		//myTask.save();
	}
	
	public static void run(){
		new LwjglApplication(new Sink(), cfg); 
	}
}