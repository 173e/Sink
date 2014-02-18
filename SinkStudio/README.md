SinkStudio v0.05
=======
SkyCode is an IDE for creating awesome games using libGdx. It is written in Java using Swing ,libGdx,
WebLookandfeel and RSyntaxTextArea. It has similar features of Xcode. It has all the features of libGdx
built-in so you can easily,start creating games with it. Tools like Hiero Font Editor, Particle Editor,
Texture Packer, GDXBuilder, TMXBuilder, ImagingTools are already built into it. 
It also has a powerful Game Framework based on libGDX called Sink which allows the game coder 
to concentrate on the logic of the game and not be bothered about setting up assets or configuration. 
It has Automatic Asset Loading including Atlas, TextureRegions, BitmapFonts, Music, Sound.
It has the Latest Nightly Version of libGdx inside it so you don't need to download the libGdx at all,
when exporting to jar for desktop it automatically loads these libraries into it.

In case you want to update the libGdx built in the IDE, you can just open the SkyCode.jar file
and replace the jars in the libs/libdgx folder with the latest version.

Sink
=========
Checkout:https://github.com/pyros2097/Sink

Features
---------
#1. Automatic Build System
	Uses an incremental Builder system like eclipse, so on the fly building so you can instantly
	run or debug your application. Using the famous Eclipse Java Compiler(ECJ).
#2. Ant Build System
	Since ant is very famous java build system it is directly integrated into the IDE, all projects have
	a build file already configured for all different platforms and the source is compiled based on
	current project target.
#3. Automatic File Saving
	All Files are automatically save when you switch tabs or change views. No more wasting time pressing
	CTRL+S or clicking the save button (inspired by Xcode)
#4. Target System
	When Building your libGDX game you can set the target for which you want to build and run and it
	automatically compiles for the target and runs it.
	ex: Android, iOS, Desktop, HTML
#5. Source Target Independent
	All your Source code for all platforms  will be stored under the same project folder but under 
	different folder names.
	ex: Core/Main game source is stored under /src
		All source code for desktop platform is stored under /src_dektop
		All source code for android platform is stored under /src_android
		All source code for iOS platform is stored under /src_ios
	
Project Structure
-----------------
#Important -- > All asset files must be lowercase only.. otherwise it causes problems
	
assets/pack/  --- all your image files which are to be packed are to be stored here so
				  so that they are automatically packed by the texture packer and stored in
				  the atlas folder
					  
				  ex: assets/pack/glue/*.png  -- contains all your menu and glue images
					  assets/pack/char/*.png  -- contains all your game character images
					  	  
When you Invoke the Texture Packer in the IDE, it will pack all the files in the glue folder to 
	assets/atlas/glue.atlas and glue.png
So now any changes is made to the images you can just invoke the texture packer and those changes
are automatically reflected in your code.
					  	  					  	  					  	
Todo
-----
1. Make Animations Working
2. GDXBuilder
3. TMXBuilder
4. Automatic Building
5. Automatic Updates
		