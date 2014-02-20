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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/** The Configuration/Settings for the libGdx Game
 * <p>
 * The Config class contains all the necessary options for all different platforms into one class.
 * Here you can save all the data of the game that is required to be persistent. The Screen 
 * Width/Height and the Target Width/Height are all declared here.<br>
 * 
 * All the Configuration is loaded from the config.json file present within the project or jar <br>
 * You don't need to use Custom configuration for each platform all is declared here.<br>
 * @author pyros2097 */

public final class Config {
	public static String title = "Sink";
	public static boolean keepAspectRatio = false;
	public static boolean showFps = true;
	public static boolean showLogger = false;
	public static boolean loggingEnabled= true;
	/*When exporting your game to jar Conif.isJar must be set so that
	* all your assets will get loaded automatically within the jar file
	*/
	public static boolean isJar = false;
	
	/*Important:
	 *  The Target Width  and Target Height refer to the nominal width and height of the game for the
	 *  graphics which are created  for this width and height, this allows for the Stage to scale this
	 *  graphics for all screen width and height. Therefore your game will work on all screen sizes 
	 *  but maybe blurred on some.
	 *  ex:
	 *  My Game targetWidth = 800 targetHeight = 480
	 *  Then my game works perfectly for SCREEN_WIDTH = 800 SCREEN_HEIGHT = 480
	 *  and on others screen sizes it is just zoomed/scaled but works fine thats all
	 */
	public static float targetWidth = 0;
	public static float targetHeight  = 0;
	
	public static String firstSceneClassName = "";
	
    static final String MUSIC = "music";
    static final String SOUND = "sound";
    static final String VOLUME_MUSIC = "volumeMusic";
	static final String VOLUME_SOUND = "volumeSOUND";
    static final String VIBRATION = "vibration";
    static final String BATTLE_ANIMATION = "battleanimation";
    static final String SEMI_AUTOMATIC = "semiautomatic";
    static final String FIRST_LAUNCH = "firstLaunch";
    static final String LEVELS = "levels";
    static final String CURRENT_LEVEL = "currentlevel";
    static final String SAVEDATA= "savedata";
    static final String TOTAL_TIME= "totaltime";
    static final String PANSCROLL = "panscroll";
    static final String DRAGSCROLL = "dragscroll";
    static final String PANSPEED = "panspeed";
    static final String DRAGSPEED = "dragspeed";
    static final String KEYBOARD = "keyboard";
    static final String SCORE = "score";

    static Preferences prefs;
    
    public static boolean isMusic;
    public static boolean isSound;
   
    public static float volMusic;
    public static float volSound;
    
    public static boolean usePan;
    public static boolean useDrag;
    public static boolean useKeyboard;
    
    public static float speedPan;
    public static float speedDrag;
    
    private static int score;
    
    static void setup(){
       prefs =Gdx.app.getPreferences(title);
       isMusic = prefs.getBoolean(MUSIC, true);
       isSound = prefs.getBoolean(SOUND, true);
       
       volMusic = prefs.getFloat(VOLUME_MUSIC, 1f);
       volSound = prefs.getFloat(VOLUME_SOUND, 1f);
        
       usePan = prefs.getBoolean(PANSCROLL, true);
       useDrag = prefs.getBoolean(DRAGSCROLL, true);
       useKeyboard = prefs.getBoolean(KEYBOARD, true);
        
       speedPan = prefs.getFloat(PANSPEED, 5f);
       speedDrag = prefs.getFloat(DRAGSPEED, 5f);
       
       score = prefs.getInteger(SCORE, 0);
    }
   
    
    
    public static String readSaveData(){
        return prefs.getString(SAVEDATA, "nodata");
    }
 
    public static void writeSaveData(String ue){
        prefs.putString(SAVEDATA, ue);
        prefs.flush();
    }
    
    public static float readTotalTime(){
        return prefs.getFloat(TOTAL_TIME, 0.0f);
    }
 
    public static void writeTotalTime(float secondstime){
        float curr = readTotalTime();
        prefs.putFloat(TOTAL_TIME, secondstime+curr);
        prefs.flush();
    }
    
    static String addZero(int value){
		String str = "";
		if(value < 10)
			 str = "0" + value;
		else
			str = "" + value;
		return str;
	}
    
    public static int levels(){
        return prefs.getInteger(LEVELS, 20);
    }
 
    public static void setLevels(int ue){
        prefs.putInteger(LEVELS, ue);
        prefs.flush();
    }
    
    public static int currentLevel(){
        return prefs.getInteger(CURRENT_LEVEL, 0);
    }
 
    public static void setCurrentLevel(int ue){
        prefs.putInteger(CURRENT_LEVEL, ue);
        prefs.flush();
    }
    
    public static boolean isBattleEnabled(){
        return prefs.getBoolean(BATTLE_ANIMATION, true);
    }
 
    public static void setBattle(boolean ue){
        prefs.putBoolean(BATTLE_ANIMATION, ue);
        prefs.flush();
    }
    
    public static boolean isSemiAutomatic(){
        return prefs.getBoolean(SEMI_AUTOMATIC, false);
    }
 
    public static void setSemiAutomatic(boolean ue){
        prefs.putBoolean(SEMI_AUTOMATIC, ue);
        prefs.flush();
    }
 
 
    public static void setPanScroll(boolean ue){
        prefs.putBoolean(PANSCROLL, ue);
        prefs.flush();
        usePan = ue ;
    }
 
    public static void setDragScroll(boolean ue){
        prefs.putBoolean(DRAGSCROLL, ue);
        prefs.flush();
        useDrag = ue;
    }
 
    public static void setPanSpeed(float ue){
        prefs.putFloat(PANSPEED, ue);
        prefs.flush();
        speedPan = ue;
    }
    
    public static void setDragSpeed(float ue){
        prefs.putFloat(VOLUME_SOUND, ue);
        prefs.flush();
        speedDrag = ue;
    }
    
    public static void setKeyboard(boolean ue){
        prefs.putBoolean(KEYBOARD, ue);
        prefs.flush();
        useKeyboard = ue;
    }
    
    public static void setSound(boolean ue){
        prefs.putBoolean(SOUND, ue);
        prefs.flush();
        isSound = ue ;
        
    }
 
    public static void setMusic(boolean ue){
        prefs.putBoolean(MUSIC, ue);
        prefs.flush();
        isMusic = ue;
    }
 
    public static void setMusicVolume(float ue){
        prefs.putFloat(VOLUME_MUSIC, ue);
        prefs.flush();
        volMusic = ue;
        Asset.musicVolume();
    }
    
    public static void setSoundVolume(float ue){
        prefs.putFloat(VOLUME_SOUND, ue);
        prefs.flush();
        volSound = ue;
    }
    
    public static void setScore(int value){
        prefs.putInteger(SCORE, value);
        prefs.flush();
        score = value;
    }
    
    public static int getScore(){
        return score;
    }
    
    public static void setVibration(boolean ue){
		prefs.putBoolean(VIBRATION, ue);
		prefs.flush();
	}

    public static boolean getVibration(){
		return prefs.getBoolean(VIBRATION, true);
	}
	
	public static void setFirstLaunchDone(boolean ue){
		prefs.putBoolean(FIRST_LAUNCH, ue);
		prefs.flush();
		
	}
	
	public static boolean isFirstLaunch(){
		return prefs.getBoolean(FIRST_LAUNCH, false);
	}
}
