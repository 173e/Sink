Sink v1.01
==========
Sink is a Game Framework built on top of libGDX with all the batteries included. It has all configuration for assets,
sound, music, textures, animations already setup. You can directly start coding your game without any knowledge of 
writing error free setup code etc.
It consists of Scenes and has a SceneManager to control which scenes are to displayed when.
It also has Scene, SceneGroup, SceneActor classes which make it easier to add resources without many 
manual importing.  
Be sure to see the demo's source as it can help you a lot in understanding the framework

Checkout: [SinkStudio](https://github.com/pyros2097/SinkStudio)  

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
=================
><b>Important</b>  All asset files must be lowercase only.. otherwise it causes problems with android

1. All Assets are to be stored in the assets directory
2. Automatic Asset Loading the directory Structure should be like this
	
	assets/icon.png - your game icon which is loaded by the framework
	assets/atlas/  --- all your Texture Atlas files .atlas and .png go here  
	assets/font/  --- all your BitmapFont files .fnt and .png go here  
	assets/music/  --- all your Music files .mp3 go here  
	assets/sound/  --- all your Music files .mp3 go here  
	assets/particle/  --- all your Particle files .part go here  
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
=====
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
Sink.log("Some important data");
/This will display to your console and/or the loggerPane if enabled by using Config class in the engine

The asset functions can return null for Font, TextureRegion and Animation if the asset cannot be 
found so null checking has to be manually done.
	
Also there is a great Config class which already has basic things needed in a game,
like Config.isMusic to get if music is enabled
Currently Only Orthogonal Maps are supported

    //This is our first Scene and it shows the libgdx logo until all the assets are loaded 
    //then it automatically switches to the Menu scene
    public class  Splash {
		
		public Splash() {
			final Texture bg1 = new Texture("splash/libgdx.png");
			final Image imgbg1 = new Image(bg1);
			imgbg1.setFillParent(true);
			Sink.addActor(imgbg1);
	    } 
   }
   
    //This is Scene gets called once the assets are loaded
    public class  Menu {
    
		public Menu() {
			//create some actors
			// if you used sink studio and create a scene like Menu.json then
			// it will automatically call load("Menu") it will populate your scene after parsing the json file
			
			//you can access these objects like this
			TextButton btn = (TextButton) Sink.findActor("TextButton1");
			Image img = (Image) Sink.findActor("Image5");
			
			// these actors are loaded from the json file and are give names which allows
			// easy access to them
		}
	}
	
	//In config.json
	"scenes": "Splash,Menu"
```



Download
--------
[Sink Framework](https://github.com/pyros2097/Sink/releases/download/v0.92/sink.jar)

Running
--------
Include the sink-1.00.jar in your game classpath it contains all the libgdx files also, 
so you dont need to include them.      
Run the Desktop Game by using sink.core.Sink class as it contains the static main declaration.  
Specify the all your scenes in your config.json file.
Note:
	All your scenes must be located in the default package otherwise you must give your scene's
	full class name in the scenes List in the config.json file.

Todo
-----
1. Automatic animation loading still not done
2. Hexagonal Map
3. Isometric Map
  
Credits
--------
Thanks to all these awesome frameworks  
[mtx](http://moribitotechx.blogspot.co.uk)  
[libgdx](http://libgdx.badlogicgames.com)  
[lwjgl](http://lwjgl.org)  
[openal](http://kcat.strangesoft.net/openal.html)  


#Documentation
##config.json
Create a config.json file in your src folder and add these lines to it
```json
{
   "title": "Sink",     // your game title name  
   "hasIcon": true,     // your icon must be in the classpath as icon.png
   "targetWidth": 320,  //your game's target width it will automatically scale to other sizes
   "targetHeight": 224, //your game's target height it will automatically scale to other sizes  
   "screenWidth": 800,  //your game's screen Width
   "screenHeight": 480, //your game's screen Height
   "x": 280,			//x position of your game window  
   "y": 100,			//y position of your game window  
   "resize": false,		//whether you can resize the window  
   "forceExit": false,
   "fullScreen": false,
   "useGL20": false,
   "vSync": false,
   "disableAudio": false,
   "audioBufferCount": 20,
   "keepAspectRatio": false,
   
   "useAccelerometer": false,
   "useCompass": false,
   "hideStatusBar": true,
   "useWakelock": true,
   "useCloud": true,
   
   "showFps": true,				//Whether you want to display the fps on the game screen  
   "showLogger": false,			//Whether you want to display the logging pane on the game screen  
   "loggingEnabled": true,		//To Sink logging
   
   "scenes": "Splash,Menu,Options"
   
   //Note use if scenes are in a specific package
   "scenes": "com.mygame.Splash,com.mygame.Menu"
   //and to change a scene
   //setScene("com.mygame.Menu");
}
```
##SceneActor
This extends the Actor class and all the Asset methods are directly built into it

##SceneSprite
This extends the SceneActor and has animation built into it so you can directly display animations in your game

##SceneGroup
This extends the Group class and all the Asset methods are directly built into it

##Sink
The Main Entry Point for the Sink Game is the Sink class
It consists of a single Stage and Camera which are all initialized based on the Config settings.
The stage can be accessed in a static way like Sink.getStage() and methods related to camera like moveTo, moveBy,
are also accessed the same way.<br>
It has extra things like gameUptime, pauseState, PauseListeners, ResumeListeners, 
DisposeListeners.<br>
It has static methods which can be used for panning the camera using mouse, keyboard, drag.. etc.
It can also automatically follow a actor by using followActor(Actor actor)<br>

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

#Faq
1. Performance?  
2. Feature Requests?  

##Stage3d
Also a Stage3d is being developed currently to support 3d games  
Stage3d class can be used to construct a Stage for Actor3d's  
You can use the Actor3d class to create Actors for the Stage  
Actor3d has almost identical properties to Actor  
You can perform actions on these actors using Action3d  