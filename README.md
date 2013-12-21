Sink v0.75
===============
Sink is a Game Framework built on top of libGDX with all the batteries included. It has all configuration for assets,
sound, music, textures, animations already setup. You can directly start coding your game without any knowledge of 
writing error free setup code etc.
It consists of Scenes and has a SceneManager to control which scenes are to displayed when.
Main Idea is about live coding any changes done to the game must be directly shown in the Engine running
overlay in the Sink IDE.	
Also a Stage3d is being developed currently to support 3d games.
Stage3d class can be used to construct a Stage for Actor3d's  
You can use the Actor3d class to create Actors for the Stage  
Actor3d has almost identical properties to Actor  
You can perform actions on these actors using Action3d  

Demo
====
There are 3 demos
1. BasicDemo
2. TicTacToe
3. Stage3d

ScreenShots
============
<img src = "https://f.cloud.github.com/assets/1687946/1458794/a2b7de78-4399-11e3-8110-f2372f230ba8.PNG" width="480" height="320">  
<img src = "https://f.cloud.github.com/assets/1687946/1458798/a304382c-4399-11e3-9c9e-72f8b86e967d.PNG" width="480" height="320">  
<img src = "https://f.cloud.github.com/assets/1687946/1458797/a2fa3188-4399-11e3-9912-fae9745b8a1d.PNG" width="480" height="320">  
	
Project Structure
-----------------
><b>Important</b>  All asset files must be lowercase only.. otherwise it causes problems with android

1. All Assets are to be stored in the assets directory
2. Automatic Asset Loading the directory Structure should be like this
	
	assets/atlas/  --- all your Texture Atlas files .atlas and .png go here  
	assets/font/  --- all your BitmapFont files .fnt and .png go here  
	assets/music/  --- all your Music files .mp3 go here  
	assets/sound/  --- all your Music files .mp3 go here  
	assets/particle/  --- all your Particle files .part go here  
	assets/icon/  --- all your Game related icon files go here  
	assets/map/  --- all your TMX map files go here  
	
	assets/pack/  --- all your image files which are to be packed are to be stored here so
					  so that they are automatically packed by the texture packer and stored in
					  the atlas folder
					  
					  ex: assets/pack/glue/*.png  -- contains all your menu and glue images
					  	  assets/pack/char/*.png  -- contains all your game character images
					  	  
					  	  When you Invoke the Texture Packer in the IDE, it will pack all the files
					  	  in the glue folder to 
					  	  assets/atlas/glue.atlas and glue.png
					  	  
					  	  So now any changes is made to the images you can just invoke the texture packer
					  	  and those changes are automatically reflected in your code.
					  	  
					  	  					  	  
Using
-----
All assets are accessed this way,
First import what type of asset you wish to use as static,

```java
import static sink.core.Asset.anim;
import static sink.core.Asset.tex;
import static sink.core.Asset.musicPlay;
import static sink.core.Asset.soundPlay;
import static sink.core.Asset.font;
import sink.core.Scene;


//To load TextureRegion
TextureRegion cat = tex("cat");
	
//To load Animation
Animation catAnim = anim("cat");
	
//To load BitmapFont
BitmapFont font1 = font("font1");
	
//OfCourse Sink already has inbuilt MusicManager and SoundManager which you can use by invoking:
musicPlay("musicname");
soundPlay("soundname");
		
//To Log to the Console or the Logger Widget In Game you can use,
Scene.log("Some important data");
/This will display to your console and/or the loggerPane if enabled by using Config class in the engine

When exporting your game to jar Conig.isJar must be set so that
all your assets will get loaded automatically within the jar file
Config.isJar = true;
The asset functions can return null for Font, TextureRegion and Animation if the asset cannot be 
found so null checking has to be manually done.
	
Also There is a great Config class which already has basic things needed in a game,
like Config.isMusic to get if music is enabled
	
Currently Only Orthogonal Maps are supported

In the BasicDemo only level 1 Works Properly

// This is for Stage3d
import com.badlogix.gdx.scenes.scene3d.Actor3d;
import com.badlogix.gdx.scenes.scene3d.Group3d;
import com.badlogix.gdx.scenes.scene3d.Stage3d;
import com.badlogix.gdx.scenes.scene3d.actions.Actions;

stage3d = new Stage3d();
modelBuilder = new ModelBuilder();
model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                Usage.Position | Usage.Normal);
r = new Actor3d(model, 9f, 0f, 0f);

stage3d.addActor3d(r);

r.addAction3d(Actions.moveTo(7f, 0f, 0f, 1f));
r.addAction3d(Actions.rotateTo(59, 1f));
	
```
	
Todo
-----
1. Make Animations Working
2. Tutorials
3. Hex Map
4. Scene Transitions

Credits
--------
Thanks all these awesome frameworks  
[mtx](http://moribitotechx.blogspot.co.uk)  
[libgdx](libgdx.badlogicgames.com)  
[lwjgl](lwjgl.org)  ‎
[openal](kcat.strangesoft.net/openal.html)  

#Documentation
##Scene

Use this class to to create scenes or menus for your game. Just extend this class and override the
init() method all other things are done automatically like clearing the stage and populating it with the
actors of this group. A scene can be set using SceneManager.setCurrentScene("sceneName")
```java
public class MenuScene extends Scene{
	@Override
	public void init(){
		musicPlay("title");
		setBackground("title");
 		TextButton btn1 = new TextButton("Start", Asset.skin);
 		addActor(btn1, 45, 245);
 	}
 }
```

##SceneManager

Before you use your scenes use this class to register all your scenes using registerScene("sceneName", new SceneObject())
and then you can switch you scenes by using setCurrentScene method with the sceneName you registered your 
scene with
```java
//In your static void main, register your scenes
Sink.addListener(new CreateListener(){
			@Override
			public void onCreate() {
				SceneManager.registerScene("splash", new SplashScene());
				SceneManager.registerScene("menu", new MenuScene());
				SceneManager.registerScene("options", new OptionsScene());
				SceneManager.registerScene("credits", new CreditsScene());
				SceneManager.registerScene("login", new LoginScene());
				SceneManager.registerScene("level", new LevelScene());
				SceneManager.registerScene("game", new GameScene());
				SceneManager.setScene("splash");
			}
		});
```

##Sink

The Main Entry Point for the Sink Game is the Sink class
It consists of a single Stage and SceneCamera which are all initialized based on the Config class.
They can be accessed in a static way like Sink.stage Sink.camera.
It also has extra things like gameUptime, pauseState, CreateListeners, PauseListeners, ResumeListeners, 
DisposeListeners
```java
public class BasicDesktop extends MainDesktop{
	public static void main(String[] argc) {
		init();
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate() {
				SceneManager.registerScene("splash", new SplashScene());
				SceneManager.registerScene("menu", new MenuScene());
				SceneManager.registerScene("options", new OptionsScene());
				SceneManager.registerScene("credits", new CreditsScene());
				SceneManager.registerScene("login", new LoginScene());
				SceneManager.registerScene("level", new LevelScene());
				SceneManager.registerScene("game", new GameScene());
				SceneManager.setScene("splash");
			}
		});
		new LwjglApplication(new Sink(), cfg); 
	}
}
```