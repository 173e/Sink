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

package sky.engine.core;

import java.io.File;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/** Automatic Assets Loading for the libGdx Game
 * <p>
 *  This class automatically loads all the assets in the prescribed folders into the appropriate 
 *  class types. All This can be accessed using an neat api
 * 
 *	#Important 
 *	All asset files must be lowercase only.. otherwise it causes problems with android
 *
 *	1. All Assets are to be stored in the assets directory
 *	2. Automatic Asset Loading the directory Structure should be like this
 *	
 *	assets/atlas/  --- all your Texture Atlas files .atlas and .png go here  
 *	assets/font/  --- all your BitmapFont files .fnt and .png go here  
 *	assets/music/  --- all your Music files .mp3 go here  
 *	assets/sound/  --- all your Music files .mp3 go here  
 *	assets/particle/  --- all your Particle files .part go here  
 *	assets/icon/  --- all your Game related icon files go here  
 *	assets/map/  --- all your TMX map files go here  
 *	assets/pack/  --- all your image files which are to be packed are to be stored here so
 *					  so that they are automatically packed by the texture packer and stored in
 *					  the atlas folder
 *	  	    	  					  	  
 *	All assets are accessed this way,
 *	First import what type of asset you wish to use as static,
 *
 *	import static sky.engine.core.Asset.$anim;
 *	import static sky.engine.core.Asset.$tex;
 *	import static sky.engine.core.Asset.$musicPlay;
 *	import static sky.engine.core.Asset.$soundPlay;
 *	import static sky.engine.core.Asset.$font;
 *	import static sky.engine.core.Panel.$log;
 *
 *
 *	To load TextureRegion
 *	TextureRegion cat = $tex("cat");
 *	
 *	To load Animation
 *	Animation catAnim = $anim("cat");
 *	
 *	To load BitmapFont
 *	BitmapFont font1 = $font("font1");
 *
 *  OfCourse SkyEngine already has inbuilt MusicManager and SoundManager which you can use by invoking:
 *	$musicPlay("musicname");
 *	$soundPlay("soundname");
 *		
 *  To Log to the Console or the Logger Widget In Game you can use,
 *  This will display to your console and/or the loggerPanel in the engine
 *	$log("Some important data");
 *	
 *
 *  When exporting your game to jar Conig.isJar must be set so that
 *  all your assets will get loaded automatically within the jar file
 *	Config.isJar = true;
 *	
 *  The asset functions can return null for Font, TextureRegion and Animation if the asset cannot be found
 * 
 * <p>
 * @author pyros2097 */

public final class Asset {
	public static AssetManager assetMan = new AssetManager();
	public static Skin skin;
	
	private static FileHandle[] musicFiles;
	private static FileHandle[] soundFiles;
	private static FileHandle[] fontFiles;
	private static FileHandle[] atlasFiles;
	private static Array<String> musicJarFiles = new Array<String>();
	private static Array<String> soundJarFiles = new Array<String>();
	private static Array<String> fontJarFiles = new Array<String>();
	private static Array<String> atlasJarFiles = new Array<String>();
	private final static ArrayMap<String, Music> musicMap = new ArrayMap<String, Music>();
	private final static ArrayMap<String, Sound> soundMap = new ArrayMap<String, Sound>();
	private final static ArrayMap<String, BitmapFont> fontMap = new ArrayMap<String, BitmapFont>();
	private final static ArrayMap<String, TextureRegion> texMap = new ArrayMap<String, TextureRegion>();
	private final static ArrayMap<String, Animation> animMap = new ArrayMap<String, Animation>();
	
	private static Music currentMusic = null;
	private static String currentMusicName = ""; //Only file name no prefix or suffix
	private static Sound currentSound = null;
	
	
	
	public static boolean update(){
		return assetMan.update();
	}
	
	public static void loadData(){
		if (Gdx.app.getType() == ApplicationType.Android){
			Scene.$log("Loading Music Files");
			musicFiles = Gdx.files.internal("music").list(); //adroid works
			Scene.$log("Loading Sound Files");
			soundFiles = Gdx.files.internal("sound").list();
			Scene.$log("Loading Font Files");
			fontFiles = Gdx.files.internal("font").list();
			Scene.$log("Loading Atlas Files");
			atlasFiles = Gdx.files.internal("atlas").list();
		}
		else if(Gdx.app.getType() == ApplicationType.Desktop){
			if(Config.isJar)
				loadFromJar();
			else{
				Scene.$log("Loading Music Files");
				musicFiles = Gdx.files.internal("./bin/music").list();
				Scene.$log("Loading Sound Files");
				soundFiles = Gdx.files.internal("./bin/sound").list();
				Scene.$log("Loading Font Files");
				fontFiles = Gdx.files.internal("./bin/font").list();
				Scene.$log("Loading Atlas Files");
				atlasFiles = Gdx.files.internal("./bin/atlas").list();
				for(FileHandle f: musicFiles)
					assetMan.load("music/"+f.name(), Music.class);
				for(FileHandle f: soundFiles)
					assetMan.load("sound/"+f.name(), Sound.class);
				for(FileHandle f: fontFiles){
					if(f.extension().equals("fnt"))
						assetMan.load("font/"+f.name(), BitmapFont.class);
				}
				for(FileHandle f: atlasFiles){
					if(f.extension().equals("atlas"))
						assetMan.load("atlas/"+f.name(), TextureAtlas.class);
				}
				
			}
				
		}
		
	}
	
	private static void loadFromJar(){
		try{
			File jarName = new File(Asset.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			ZipFile zf=new ZipFile(jarName.getAbsoluteFile());
		    Enumeration<? extends ZipEntry> e=zf.entries();
		    while (e.hasMoreElements()) 
		     {
		          ZipEntry ze=(ZipEntry)e.nextElement();
		          String entryName = ze.getName();
				  if(entryName.startsWith("music") &&  entryName.endsWith(".mp3")) {
					  musicJarFiles.add(entryName);
					  Scene.$log(ze.getName());
				  }
				  else if(entryName.startsWith("sound") &&  entryName.endsWith(".mp3")){
					  soundJarFiles.add(entryName);
					  Scene.$log(ze.getName());
				  }
				  else if(entryName.startsWith("font") &&  entryName.endsWith(".fnt")){
					  fontJarFiles.add(entryName);
					  Scene.$log(ze.getName());
				  }
				  else if(entryName.startsWith("atlas") &&  entryName.endsWith(".atlas")){
					  atlasJarFiles.add(entryName);
					  Scene.$log(ze.getName());
				  }
		     }
		     zf.close();
		}
		catch (Exception e){e.printStackTrace();}
		for(String f: musicJarFiles)
			assetMan.load(f, Music.class);
		for(String f: soundJarFiles)
			assetMan.load(f, Sound.class);
		for(String f: fontJarFiles)
			assetMan.load(f, BitmapFont.class);
		for(String f: atlasJarFiles)
			assetMan.load(f, TextureAtlas.class);
	}

	public static void setUp(){
		assetMan.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
		loadTextureRegions();
		//loadAnimations();
		loadMusics();
		loadSounds();
		loadFonts();
	}
	
	
	
/***********************************************************************************************************
* 								Music Related Global Functions											   *
************************************************************************************************************/
	private static void loadMusics(){
		if(Config.isJar){
			for(String f: musicJarFiles){
				Music m = assetMan.get(f, Music.class);
				String name = f.replace("music/", "");
				musicMap.put(name.replace(".mp3", ""),m);
			}
		}
		else{
			for(FileHandle f: musicFiles){
				Music m = assetMan.get("music/"+f.name(), Music.class);
				musicMap.put(f.nameWithoutExtension(),m);
			}
		}
	}
	
	/** Plays the music file which was dynamically loaded if it is present otherwise logs the name
	 *  @param filename The Music File name only
	 *  @ex <code>$music("title")</code>
	 *  */
	public static void $musicPlay(String filename){
		if(Config.isMusic){
			if(currentMusic != null)
				if(currentMusic.isPlaying())
					if(currentMusicName == filename)
						return;
					else
						$musicStop();
			if(musicMap.containsKey(filename)){
				Scene.$log("Music: playing "+filename);
				currentMusic = musicMap.get(filename);//Gdx.audio.newMusic(Gdx.files.internal("music/"+filename));
				currentMusic.setVolume(Config.volMusic);
				currentMusic.setLooping(true);
				currentMusic.play();
				currentMusicName = filename;
			}
			else{
				Scene.$log("Music File Not Found: "+filename);
			}
		}
	}
	
	/** Pauses the current music file being played */
	public static void $musicPause(){
		if(currentMusic != null)
			if(currentMusic.isPlaying()){
				Scene.$log("Music: pausing "+currentMusicName);
				currentMusic.pause();
			}
	}
	
	/** Resumes the current music file being played */
	public static void $musicResume(){
		if(currentMusic != null)
			if(!currentMusic.isPlaying()){
				Scene.$log("Music: resuming "+currentMusicName);
				currentMusic.play();
			}
		else
			$musicPlay(currentMusicName);
	}
	
	/** Stops the current music file being played */
	public static void $musicStop(){
		if(currentMusic != null){
			Scene.$log("Music: stoping "+currentMusicName);
			currentMusic.stop();
			currentMusic = null;
		}
	}
	
	/** Sets the volume music file being played */
	public static void $musicVolume(){
		if(currentMusic != null);
			currentMusic.setVolume(Config.volMusic);
	}
	
	/** Disoposes the current music file being played */
	public static void $musicDispose(){
		if(currentMusic != null);
			currentMusic.dispose();
	}
	
/***********************************************************************************************************
* 								Sound Related Global Functions							   				   *
************************************************************************************************************/
	private static void loadSounds(){
		if(Config.isJar){
			for(String f: soundJarFiles){
				Sound m = assetMan.get(f, Sound.class);
				String name = f.replace("sound/", "");
				soundMap.put(name.replace(".mp3", ""),m);
			}
		}
		else{
			for(FileHandle f: soundFiles){
				Sound m = assetMan.get("sound/"+f.name(), Sound.class);
				soundMap.put(f.nameWithoutExtension(),m);
			}
		}
	}
	
	/** Plays the sound file which was dynamically loaded if it is present otherwise logs the name
	 *  @param filename The Sound File name only
	 *  @ex <code>$sound("bamg")</code>
	 *  */
	public static void $soundPlay(String filename){
		if(Config.isSound){
			if(soundMap.containsKey(filename)){
				currentSound = soundMap.get(filename);
				long id = currentSound.play(Config.volSound);
				currentSound.setLooping(id, false);
				currentSound.setPriority(id, 99);
				Scene.$log("Sound:"+"Play "+ filename);
			}
			else{
				Scene.$log("Music File Not Found: "+filename);
			}
		}
	}
	
	/** Plays the sound file "click" */
	public static void $soundClick(){
		if(Config.isSound){
	        currentSound = soundMap.get("click");
			long id = currentSound.play(Config.volSound);
			currentSound.setLooping(id, false);
			currentSound.setPriority(id, 99);
			Scene.$log("Sound:"+"Play "+ "click");
		}
	}
	
	/** Pauses the current sound file being played */
	public static void $soundPause(){
		Scene.$log("Sound:"+"Pausing");
		if(currentSound != null)
			currentSound.pause();
	}
	
	/** Resumes the current sound file being played */
	public static void $soundResume(){
		Scene.$log("Sound:"+"Resuming");
		if(currentSound != null)
			currentSound.resume();
	}
	
	/** Stops the current sound file being played */
	public static void $soundStop(){
		Scene.$log("Sound:"+"Stopping");
		if(currentSound != null)
			currentSound.stop();
	}
	
	/** Disposes the current sound file being played */
	public static void $soundDispose(){
		Scene.$log("Sound:"+"Disposing Sound");
		if(currentSound != null)
			currentSound.dispose();
	}
	
	
/***********************************************************************************************************
* 								BitmapFont Related Functions							   				   *
************************************************************************************************************/
	private static void loadFonts(){
		if(Config.isJar){
			for(String f: fontJarFiles){
				BitmapFont m = assetMan.get(f, BitmapFont.class);
				String name = f.replace("font/", "");
				fontMap.put(name.replace(".fnt", ""),m);
			}
		}
		else{
			for(FileHandle f: fontFiles){
				if(f.extension().equals("fnt")){
					BitmapFont m = assetMan.get("font/"+f.name(), BitmapFont.class);
					fontMap.put(f.nameWithoutExtension(),m);
				}
			}
		}
	}
	
	/** If key is present returns the BitmapFont that was dynamically loaded
	 *  else returns null
	 *  @param fontname The BitmapFont name
	 *  @return BitmapFont or null
	 *  @ex $font("font1") or $font("arial")
	 *  */
	public static BitmapFont $font(String fontname){
		if(fontMap.containsKey(fontname)){
			return fontMap.get(fontname);
		}
		else{
			Scene.$log("Font File Not Found: "+fontname);
			return null;
		}
	}
	
/***********************************************************************************************************
* 								Texture Related Functions							   				   	   *
************************************************************************************************************/
	private static void loadTextureRegions(){
		if(Config.isJar){
			for(String f: atlasJarFiles){
				TextureAtlas atlas = assetMan.get(f, TextureAtlas.class);
				Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
				for(TextureAtlas.AtlasRegion ar: regions){
					texMap.put(ar.name, ar);
				}
			}
		}
		else{
			for(FileHandle f: atlasFiles){
				if(f.extension().equals("atlas")){
						TextureAtlas atlas = assetMan.get("atlas/"+f.name(), TextureAtlas.class);
						Array<TextureAtlas.AtlasRegion> regions = atlas.getRegions();
						for(TextureAtlas.AtlasRegion ar: regions){
							texMap.put(ar.name, ar);
						}
					}
				}
			}
		}
	
	/** If key is present returns the TextureRegion that was loaded from all the atlases
	 *  else returns null
	 *  @param textureregionName The TextureRegion name
	 *  @return TextureRegion or null
	 *  */
	public static TextureRegion $tex(String textureregionName){
		if(texMap.containsKey(textureregionName)){
			return texMap.get(textureregionName);
		}
		else{
			Scene.$log("TextureRegion Not Found: "+textureregionName);
			return null;
		}
	}
	
/***********************************************************************************************************
* 								Animation Related Functions							   				   	   *
************************************************************************************************************/
	
	/**1.TextureRegion Names with numbers like slug00 slug01 are automatically classified as animations
	 * 2.TextureRegion Name with "_row" is treated as row animations so don't use _row as filename
	 *  @param animationBaseName The Animation Base name
	 *  @return Animation or null
	 *  @ex	<code>$anim("slug")</code>
	 * */
	public static Animation $anim(String animationBaseName){
		if(animMap.containsKey(animationBaseName)){
			return animMap.get(animationBaseName);
		}
		else{
			Scene.$log("Animation Not Found: "+animationBaseName);
			return null;
		}
	}
	
	/**
	 * Warning: Image Name a TextureRegion Name should not  with numbers
	 * Warning: Image Name a TextureRegion Name with "_row" is treated as row animations
	 * 			for automatic animation loading so dont use _row as filename
	 * 
	 * */
	private static void loadAnimations() {
		Array<TextureRegion> keyFrames = new Array<TextureRegion>();
		Array<String> animsLoaded = new Array<String>();
		String baseAnimName = "";
		Array<String> texRegionsName = texMap.keys().toArray();
		for(String name: texRegionsName) {
			if(name.matches(".*\\d.*") && !name.contains("_row")){
				baseAnimName = name.replaceAll("[0-9]","");
				Scene.$log(baseAnimName);
				if(!animsLoaded.contains(baseAnimName, false)){
					for(int i=0; i < 100; i++){
						if(i<10 && texRegionsName.contains(baseAnimName+"0"+i, false)){
							Scene.$log("Contains: "+baseAnimName+"0"+i);
							keyFrames.add(texMap.get(baseAnimName+"0"+i));
						}
						else if(texRegionsName.contains(baseAnimName+i, false)){
							Scene.$log("Contains: "+baseAnimName+"0"+i);
							keyFrames.add(texMap.get(baseAnimName+i));
						}
						else{
							//$log("KeyFrames: "+keyFrames.toString());
							animsLoaded.add(baseAnimName);
							animMap.put(baseAnimName, new Animation(1/keyFrames.size, keyFrames));
							keyFrames.clear();
							break;
						}
					}
				}
			}
			// Row Single Png Image Texture Animations
			else if(name.contains("_row")){
				Scene.$log("Loading Row Animation: "+name);
				TextureRegion textureRegion = texMap.get(name);
				String[] sep = name.split("_row");
				//$log(sep[1]);
				int noOfFrames = Integer.parseInt(sep[1]);
				TextureRegion[] rowFrames = new TextureRegion[noOfFrames];

				// Set key frames (each comes from the single texture)
				for (int i = 0; i < noOfFrames; i++) {
					rowFrames[i] = new TextureRegion(textureRegion,
							(textureRegion.getRegionWidth() / noOfFrames) * i, 0,
							textureRegion.getRegionWidth() / noOfFrames,
							textureRegion.getRegionHeight());
				}
				/*TextureRegion[] rowFrames = textureRegion.split(textureRegion.getRegionWidth()/noOfFrames, 
						textureRegion.getRegionHeight())[0];*/
				animMap.put(sep[0] ,new Animation(1/noOfFrames, rowFrames));
			}
		}
	}

	/**
	 * Get animation from texture atlas (Based on TexturePacker). There is only
	 * single texture which contains all frames (It is like a single png which
	 * has all the frames). Each frames' width should be same for proper results
	 * <p>
	 * 
	 * @param textureAtlas
	 *            atlas which contains the single animation texture
	 * @param animationName
	 *            name of the animation in atlas
	 * @param numberOfFrames
	 *            number of frames of the animation
	 * @param frameDuration
	 *            each frame duration on play
	 * @return animation created
	 * 
	 * */
	private static Animation getAnimationFromSingleTexture(TextureAtlas textureAtlas, String animationName, int numberOfFrames, float frameDuration) {
		// Get animation texture (single texture)
		TextureRegion textureRegion = textureAtlas.findRegion(animationName);

		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];

		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++) {
			keyFrames[i] = new TextureRegion(textureRegion,
					(textureRegion.getRegionWidth() / numberOfFrames) * i, 0,
					textureRegion.getRegionWidth() / numberOfFrames,
					textureRegion.getRegionHeight());
		}

		//
		Animation animation = new Animation(frameDuration, keyFrames);
		return animation;
	}

	/**
	 * Get animation from texture atlas (Based on TexturePacker). There is only
	 * single texture which contains all frames (It is like a single png which
	 * has all the frames). Each frames' width should be same for proper results
	 * <p>
	 * 
	 * @param textureAtlas
	 *            atlas which contains the single animation texture
	 * @param animationName
	 *            name of the animation in atlas
	 * @param numberOfFrames
	 *            number of frames of the animation
	 * @param numberOfMaximumFramesInTheSheet
	 *            maximum number of frame in a row in the sheet
	 * @param numberOfRows
	 *            number of rows that the sheet contains
	 * @param indexOfAnimationRow
	 *            the row index (starts from 0) that desired animation exists
	 * @param frameDuration
	 *            each frame duration on play
	 * @return animation created
	 * 
	 * */
	private static Animation getAnimationFromSingleTextureMultiRows(TextureAtlas textureAtlas, String animationName, int numberOfFrames, int numberOfMaximumFramesInTheSheet,
			int numberOfRows, int indexOfAnimationRow, float frameDuration) {
		// Get animation texture (single texture)
		TextureRegion textureRegion = textureAtlas.findRegion(animationName);

		// Key frames list
		TextureRegion[] keyFrames = new TextureRegion[numberOfFrames];

		// Set key frames (each comes from the single texture)
		for (int i = 0; i < numberOfFrames; i++) {
			keyFrames[i] = new TextureRegion(
					textureRegion,
					(textureRegion.getRegionWidth() / numberOfMaximumFramesInTheSheet)
							* i, textureRegion.getRegionHeight() / numberOfRows
							* indexOfAnimationRow,
					textureRegion.getRegionWidth()
							/ numberOfMaximumFramesInTheSheet,
					textureRegion.getRegionHeight() / numberOfRows);
		}

		//
		Animation animation = new Animation(frameDuration, keyFrames);
		return animation;
	}
	
/***********************************************************************************************************
	* 								TMX MAP Related Functions							   				   *
************************************************************************************************************/
	public static TiledMap $loadTmx(int i){
		assetMan.load(Config.getBasePath()+"map/level"+i+".tmx", TiledMap.class);
		assetMan.finishLoading();
		return assetMan.get(Config.getBasePath()+"map/level"+i+".tmx", TiledMap.class);
	}
	
	public static void $unloadTmx(int i){
		assetMan.unload(Config.getBasePath()+"map/level"+i+".tmx");
	}
	
/***********************************************************************************************************
* 								DEBUG Related Functions							   				   	   	   *
************************************************************************************************************/
	public static void debugAssets(){
		debugTextures();
		debugAnimations();
		debugFonts();
		debugSounds();
		debugMusics();
	}
	
	public static void debugTextures(){
		Scene.$log("Debugging Textures------------------");
		for(String na: texMap.keys)
			Scene.$log(na);
		Scene.$log("END Debugging Textures------------------");
	}
	
	public static void debugAnimations(){
		Scene.$log("Debugging Animations------------------");
		for(String na: animMap.keys)
			Scene.$log(na);
		Scene.$log("END Debugging Animations------------------");
	}
	
	public static void debugFonts(){
		Scene.$log("Debugging Fonts------------------");
		for(String na: fontMap.keys)
			Scene.$log(na);
		Scene.$log("END Debugging Fonts------------------");
	}
	
	public static void debugSounds(){
		Scene.$log("Debugging Sounds------------------");
		for(String na: soundMap.keys)
			Scene.$log(na);
		Scene.$log("END Debugging Sounds------------------");
	}
	
	public static void debugMusics(){
		Scene.$log("Debugging Musics------------------");
		for(String na: musicMap.keys)
			Scene.$log(na);
		Scene.$log("END Debugging Musics------------------");
	}
	
	public static void unloadAll(){
		assetMan.dispose();
	}
}