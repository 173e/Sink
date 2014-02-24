SinkStudio v0.15
================
SinkStudio is a tool for creating awesome games using libGdx. It is written in Java using Swing, libgdx,
WebLookandfeel and RSyntaxTextArea. It has similar features of Xcode. It has all the features of libgdx
built-in so you can easily,start creating games with it. Tools like Hiero Font Editor, Particle Editor,
Texture Packer, GDXBuilder, TMXBuilder, ImagingTools are already built into it. 
It also has a powerful Game Framework based on libGDX called Sink which allows the game coder 
to concentrate on the logic of the game and not be bothered about setting up assets or configuration. 
It has Automatic Asset Loading including Atlas, TextureRegions, BitmapFonts, Music, Sound.
It has the Latest Nightly Version of libGdx inside it so you don't need to download the libGdx at all,
when exporting to jar for desktop it automatically loads these libraries into it.


Checkout: [Sink](https://github.com/pyros2097/Sink)

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
ex: Android, iOS, Desktop
#5. Target Independent
Write Once Deploy Everywhere. You only need to write you game logic all other cross compiling for different
platforms and loading is done automatically.
					  	  					  	  					  	
Todo
-----
1. Make Animations Working
3. TMXBuilder
4. Automatic Building
5. Automatic Updates

Credits
--------
Thanks to all these awesome frameworks  
[Libgdx](libgdx.badlogicgames.com)  
[WebLookAndFeel](weblookandfeel.com)  
[RSyntaxTextArea](fifesoft.com/rsyntaxtextarea)  
		