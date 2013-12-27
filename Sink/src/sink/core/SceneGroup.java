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
package sink.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SceneGroup extends Group{
	
	public static void musicPlay(String filename){
		Asset.musicPlay(filename);
	}
	public static void musicPause(){
		Asset.musicPause();
	}
	
	public static void musicResume(){
		Asset.musicResume();
	}
	
	public static void musicStop(){
		Asset.musicStop();
	}
	
	public static void musicDispose(){
		Asset.musicDispose();
	}
	
	public static void soundPlay(String filename){
		Asset.soundPlay(filename);
	}
	
	public static void soundClick(){
		Asset.soundClick();
	}
	
	public static void soundPause(){
		Asset.soundPause();
	}
	
	public static void soundResume(){
		Asset.soundResume();
	}
	
	public static void soundStop(){
		Asset.soundStop();
	}
	
	public static void soundDispose(){
		Asset.soundDispose();
	}
	
	public static BitmapFont font(String fontName){
		return Asset.font(fontName);
	}
	
	public static TextureRegion tex(String textureregionName){
		return Asset.tex(textureregionName);
	}
	
	public static Animation anim(String animationBaseName){
		return Asset.anim(animationBaseName);
	}
}
