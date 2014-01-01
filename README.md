Sink v0.77
===============
Sink is a Game Framework built on top of libGDX with all the batteries included. It has all configuration for assets,
sound, music, textures, animations already setup. You can directly start coding your game without any knowledge of 
writing error free setup code etc.
It consists of Scenes and has a SceneManager to control which scenes are to displayed when.
It also has Scene, SceneGroup, SceneActor classes which make it easier to add resources without many 
manual importing.  

##Stage3d
Also a Stage3d is being developed currently to support 3d games  
Stage3d class can be used to construct a Stage for Actor3d's  
You can use the Actor3d class to create Actors for the Stage  
Actor3d has almost identical properties to Actor  
You can perform actions on these actors using Action3d  

Demo
====
There are 3 demos  
1. [BasicDemo](https://github.com/pyros2097/Sink/releases/download/v0.77/BasicDemo.jar)  
2. [TicTacToe](https://github.com/pyros2097/Sink/releases/download/v0.77/TicTacToe.jar)
3. [Stage3d](https://github.com/pyros2097/Sink/releases/download/v0.77/Stage3d.jar)

ScreenShots
============
<img src = "https://f.cloud.github.com/assets/1687946/1814935/cd0ea04a-6f08-11e3-80b6-c7c1af54fa0d.png" width="480" height="320">  
<img src = "https://f.cloud.github.com/assets/1687946/1814936/cd133326-6f08-11e3-8851-13cec6ee471f.png" width="480" height="320">  
<img src = "https://f.cloud.github.com/assets/1687946/1814937/cd1d8416-6f08-11e3-8763-d5c377236d36.png" width="480" height="320">
<img src = "https://f.cloud.github.com/assets/1687946/1814938/cf039586-6f08-11e3-9c5d-95651046f89d.png" width="480" height="320">  

<img src = "https://f.cloud.github.com/assets/1687946/1814960/7f7d1fea-6f09-11e3-80fa-882c67f57ac8.png" width="480" height="320">
<img src = "https://f.cloud.github.com/assets/1687946/1814961/83e96854-6f09-11e3-81fc-d3b8eb52eb7d.png" width="480" height="320">
<img src = "https://f.cloud.github.com/assets/1687946/1814977/1cc0408e-6f0a-11e3-87ea-9d1f61f5c07f.png" width="480" height="320">  
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

// these methods are directly built into Scene, SceneGroup, SceneActor so you need not import it from Asset class
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
1. Automatic animation loading
2. Hex Map

Credits
--------
Thanks all these awesome frameworks  
[mtx](http://moribitotechx.blogspot.co.uk)  
[libgdx](libgdx.badlogicgames.com)  
[lwjgl](lwjgl.org)  
[openal](kcat.strangesoft.net/openal.html)  

Download
--------
[Sink Framework](https://github.com/pyros2097/Sink/releases/download/v0.77/sink.jar)

#Documentation
##Scene
Use this class to to create scenes or menus for your game. Just extend this class and override the
onInit() method all other things are done automatically like clearing the stage and populating it with the
actors of this group. A scene can be set using SceneManager.setScene("sceneName")
All the Asset methods are directly built into it
```java
public class MenuScene extends Scene{
	@Override
	public void onInit(){
		transitionLeftToRight();
		musicPlay("title"); 
		setBackground("title");
 		TextButton btn1 = new TextButton("Start", Asset.skin);
 		addActor(btn1, 45, 245);
 	}
 }
```

##SceneActor
This extends the Actor class and all the Asset methods are directly built into it

##SceneGroup
This extends the Group class and all the Asset methods are directly built into it

##Sink

The Main Entry Point for the Sink Game is the Sink class
It consists of a single Stage and SceneCamera which are all initialized based on the Config class.
They can be accessed in a static way like Sink.stage Sink.camera.
It also has extra things like gameUptime, pauseState, CreateListeners, PauseListeners, ResumeListeners, 
DisposeListeners
It also has static methods which can be used for panning the camera using mouse, keyboard, drag.. etc.
It can also automatically follow a actor by using followActor(Actor actor)
Before you use your scenes use this class to register all your scenes using registerScene("sceneName", new SceneObject())
and then you can switch you scenes by using setScene method with the sceneName you registered your scene with
```java
public class BasicDesktop extends MainDesktop{
	public static void main(String[] argc) {
		Config.isJar = false; // set to true when exporting to jar
		Config.title = "Sink"; // your game title name
		Config.showIcon = true; // whether you want to use an icon for your game
		Config.iconLocation = "icon/myicon.png"; // specify the location of your icon
		Config.TARGET_WIDTH = 800; // your game's target width it will automatically scale to other sizes
		Config.TARGET_HEIGHT = 480; // your game's target height it will automatically scale to other sizes
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate(){
				Sink.registerScene("splash", new SplashScene());
				Sink.registerScene("menu", new MenuScene());
				Sink.registerScene("options", new OptionsScene());
				Sink.registerScene("credits", new CreditsScene());
				Sink.registerScene("game", new GameScene());
				Sink.setScene("splash");// In splash load your assets and setup Sink
			}
		});
		init(); // this will set the configuration
		run(); // this will create the lwjgl application
	}
}
```
##Map

The Map is a SceneGroup which automatically loads all the tiles and arranges them accordingly it is
highly recommended that you override the loadLayer method and customize the map
```java
map = new Map(Asset.loadTmx(currentLevel+1), 24);
map.loadLayer(0); // used to load layer 0 and fill the scene group with static tiles
map.loadLayer(1); // used to load layer 1 and fill the scene group with static tiles
addActor(map); // add actor to scene to display map
```

##MapActor
The MapActor is a SceneActor that can be used as a static tile, animated tile, animated actor or as a plain  
actor.  
1.For using it as a Static Tile use:  
	```java MapActor(TextureRegion region, int row, int col, int id, int tileSize) ```  
2.For using it as a Animated Tile/Actor use:  
	```java MapActor(Animation a, int row, int col, int id, int tileSize)  ```  
3.For using it as a plain Actor use:  
	```java MapActor(int row, int col, int tileSize)  ```  
It has many important methods like moveTo, moveBy, collides, intersects, getCenterX, getCenterY  
><b>Note:</b> Only Use setPositionXY and SetPositionRC on this actor do not use the Actor's setPosition method  
as it causes problems  

#Faq
1. Performance?  
2. Feature Requests?  
