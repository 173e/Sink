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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import sink.core.Config;
import sink.core.Sink;

import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
/** The Main Launcher for Sink Game
 * <p>
 * Just specify the MainDesktop class as the Main file and when you export your game to jar add
 * the manifest entry Main-Class: sink.main.MainDesktop for it to work
 */
public final class MainDesktop {
	public static LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	public static void main(String[] argc) {
		String text = "";
		try {
			InputStream input = MainDesktop.class.getClassLoader().getResourceAsStream("config.json");
			if(input != null){
				BufferedReader br = new BufferedReader(new InputStreamReader(input, "UTF-8"));
				String line;
				while((line = br.readLine()) != null)
					text+= line;
				br.close();
				input.close();
			}
					
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		if(!text.isEmpty()){
			JsonReader jr = new JsonReader();
			JsonValue jv = jr.parse(text);
			Config.title = jv.get("title").asString();
			cfg.title = Config.title;
			if(jv.get("showIcon").asBoolean())
				cfg.addIcon(jv.get("iconLocation").asString(), FileType.Internal);
			Config.targetWidth = jv.get("targetWidth").asInt();
			Config.targetHeight = jv.get("targetHeight").asInt();
			cfg.width = jv.get("screenWidth").asInt();
			cfg.height = jv.get("screenHeight").asInt();
			cfg.x = jv.get("x").asInt();
			cfg.y = jv.get("y").asInt();
			cfg.resizable = jv.get("resize").asBoolean();
			cfg.forceExit =  jv.get("forceExit").asBoolean();
			cfg.fullscreen =  jv.get("fullScreen").asBoolean();
			cfg.useGL20 = jv.get("useGL20").asBoolean();
			cfg.vSyncEnabled = jv.get("vSync").asBoolean();
			LwjglApplicationConfiguration.disableAudio = jv.get("disableAudio").asBoolean();
			cfg.audioDeviceBufferCount = jv.get("audioBufferCount").asInt();
			Config.isJar = jv.get("isJar").asBoolean();
			Config.keepAspectRatio = jv.get("keepAspectRatio").asBoolean();
			Config.showFps = jv.get("showFps").asBoolean();
			Config.showLogger = jv.get("showLogger").asBoolean();
			Config.loggingEnabled = jv.get("loggingEnabled").asBoolean();
			Config.firstScene = jv.get("firstScene").asString();
			Config.firstSceneClassName = jv.get("firstSceneClassName").asString();
			new LwjglApplication(new Sink(), cfg);
		}
	}
}