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

import sky.engine.core.Config;

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
		new LwjglApplication(new BasicEngine(), cfg); 
	}
}