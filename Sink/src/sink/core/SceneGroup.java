package sink.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Group;

public class SceneGroup extends Group{
	
	public static void $musicPlay(String filename){
		Asset.$musicPlay(filename);
	}
	public static void $soundPlay(String filename){
		Asset.$soundPlay(filename);
	}
	
	public static void $soundClick(){
		Asset.$soundClick();
	}
	
	public static void $soundPause(){
		Asset.$soundPause();
	}
	
	public static void $soundResume(){
		Asset.$soundResume();
	}
	
	public static void $soundStop(){
		Asset.$soundStop();
	}
	
	public static void $soundDispose(){
		Asset.$soundDispose();
	}
	
	public static BitmapFont $font(String fontName){
		return Asset.$font(fontName);
	}
	
	public static TextureRegion $tex(String textureregionName){
		return Asset.$tex(textureregionName);
	}
	
	public static Animation $anim(String animationBaseName){
		return Asset.$anim(animationBaseName);
	}
}
