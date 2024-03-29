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

import static sink.core.Asset.musicPause;
import static sink.core.Asset.musicResume;
import static sink.core.Asset.soundStop;
import sink.event.ClickedListener;
import sink.event.DisposeListener;
import sink.event.DraggedListener;
import sink.event.PauseListener;
import sink.event.ResumeListener;
import sink.json.ButtonJson;
import sink.json.CheckBoxJson;
import sink.json.DialogJson;
import sink.json.ImageJson;
import sink.json.LabelJson;
import sink.json.ListJson;
import sink.json.SelectBoxJson;
import sink.json.SliderJson;
import sink.json.TableJson;
import sink.json.TextButtonJson;
import sink.json.TextFieldJson;
import sink.json.TouchpadJson;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.StringBuilder;

/** The Main Entry Point for the Sink Game is the Sink class
 * <p>
 * It consists of a single Stage and Camera which are all initialized based on the {@link Config} settings.
 * The stage can be accessed in a static way like Sink.getStage() and methods related to camera like moveTo, moveBy,
 * are also accessed the same way.<br>
 * It has extra things like gameUptime, pauseState, PauseListeners, ResumeListeners, AssetLoadedEvent
 * DisposeListeners.<br>
 * 
 * It has automatic asset unloading and disposing and you can use {@link #exit()} to quit your game safely
 * 
 * Note: Your TMX maps have to be unloaded manually as they can be huge resources needing to be freed early.
 * 
 * It has static methods which can be used for panning the camera using mouse, keyboard, drag.. etc.
 * It can also automatically follow a actor by using followActor(Actor actor)<br>
 * 
 * This class will register all your scenes based on your config.json file and then you can switch you scenes by using {@link #setScene}
 * method with the sceneName.<br>
 * 
 * Run the Desktop Game by using sink.core.Sink class as it contains the static main declaration.<br>
 * Your first sceneName in the config.json file gets shown first automatically and once and all your assets <br>
 * are loaded in the background(asynchronously) in the first scene and then automatically the next scene in the list is set.
 * <p>
 * @ex
 * <pre>
 * <code>
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
 </code>
 </pre>
 * @author pyros2097 */

public final class Sink implements ApplicationListener {
	public static String version = "1.01";
	private float startTime = System.nanoTime();
	public static float gameUptime = 0;
	private static Json json = new Json();
	public static JsonReader jsonReader = new JsonReader();
	public static JsonValue jsonValue = null;
	
	private static Stage stage;
	private static OrthographicCamera camera;
	private static LogPane logPane;
	private static Label fpsLabel;
	
	private static Object currentScene = null;
	private static String currentSceneName = "";
	private static int sceneIndex = 0;
	public static final Array<String> scenesList = new Array<String>();
	public static final Array<String> objectList = new Array<String>();
	private static final Array<String> serializerList = new Array<String>();
	private static Array<Actor> hudActors = new Array<Actor>();
	private static final Array<ClickedListener> clickedListeners = new Array<ClickedListener>();
	private static final Array<DraggedListener> draggedListeners = new Array<DraggedListener>();
	private static final Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static final Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	private static final Array<DisposeListener> disposeListeners = new Array<DisposeListener>();
	
	/*Important:
	 *  The Target Width  and Target Height refer to the nominal width and height of the game for the
	 *  graphics which are created  for this width and height, this allows for the Stage to scale this
	 *  graphics for all screen width and height. Therefore your game will work on all screen sizes 
	 *  but maybe blurred or look awkward on some devices.
	 *  ex:
	 *  My Game targetWidth = 800 targetHeight = 480
	 *  Then my game works perfectly for SCREEN_WIDTH = 800 SCREEN_HEIGHT = 480
	 *  and on others screen sizes it is just zoomed/scaled but works fine thats all
	 */
	public static float targetWidth = 800;
	public static float targetHeight  = 480;
	public static boolean pauseState = false;
	
	public static ClassLoader classLoader = null;
	private static boolean firstScene = true;
	private static boolean serializerlock = false;
	
	//Studio Related Stuff
	/* Selected Actor can never be null as when the stage is clicked it may return an actor or the root actor */
	public static Actor selectedActor = null;
	private ShapeRenderer shapeRenderer;
	public static boolean showGrid = false;
	public static boolean debug = false;
	
	public static int dots = 20;
	public static int xlines = (int)Sink.targetWidth/dots;
	public static int ylines = (int)Sink.targetHeight/dots;
	
	public static String sceneBackground = "";
	public static String sceneMusic = "";
	public static String sceneTransition = "";
	public static float sceneDuration = 0;
	public static Interpolation sceneInterpolation = Interpolation.linear;
	
	
	/** The Main Launcher for Sink Game
	 * <p>
	 * Just specify the Sink class as the Main file and when you export your game to jar add
	 * the manifest entry Main-Class: sink.core.Sink for it to work
	 */
	public static LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
	public static void main(String[] argc) {
		jsonValue = jsonReader.parse(Sink.class.getClassLoader().getResourceAsStream("config.json"));
		if(jsonValue.getBoolean("hasIcon"))
			cfg.addIcon("icon.png", FileType.Internal);
		cfg.width = jsonValue.getInt("screenWidth");
		cfg.height = jsonValue.getInt("screenHeight");
		cfg.x = jsonValue.getInt("x");
		cfg.y = jsonValue.getInt("y");
		cfg.resizable = jsonValue.getBoolean("resize");
		cfg.forceExit =  jsonValue.getBoolean("forceExit");
		cfg.fullscreen =  jsonValue.getBoolean("fullScreen");
		cfg.useGL20 = jsonValue.getBoolean("useGL20");
		cfg.vSyncEnabled = jsonValue.getBoolean("vSync");
		cfg.audioDeviceBufferCount = jsonValue.getInt("audioBufferCount");
		LwjglApplicationConfiguration.disableAudio = jsonValue.getBoolean("disableAudio");
		targetWidth = jsonValue.getInt("targetWidth");
		targetHeight = jsonValue.getInt("targetHeight");
		new LwjglApplication(new Sink(), cfg);
	}
	
	/*
	 * This is where the stage and camera are created and the splash scene in created
	 * dynamically and set as the first scene;
	*/
	@Override
	public final void create() {
		Sink.log("Sink: Created");
		Config.setup();
		stage = new Stage(cfg.width, cfg.height, jsonValue.getBoolean("keepAspectRatio"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, targetWidth, targetHeight);
		camera.position.set(targetWidth/2, targetHeight/2, 0);
		stage.setCamera(camera);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(stage);
 		stage.addListener(touchInput);
 		shapeRenderer = new ShapeRenderer();
 		for(String className: jsonValue.getString("scenes").split(","))
 			scenesList.add(className.trim()); // registering the scenes
 		setScene(scenesList.first());
 		selectedActor = stage.getRoot();
	}
	
	/*
	 * This is the main rendering call that updates the time, updates the stage
	 * and loads updates the camera and fps text
	 */
	@Override
	public final void render(){
		if (System.nanoTime() - startTime >= 1000000000) {
			gameUptime +=1 ;
			startTime = System.nanoTime();
		}
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT |GL20.GL_DEPTH_BUFFER_BIT);
		Asset.load();
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
		updateController();
		if(debug){
			drawGrid();
			drawSelection();
		}
		if (fpsLabel != null && jsonValue.getBoolean("showFps"))
			fpsLabel.setText("Fps: " + Gdx.graphics.getFramesPerSecond());
 	}
	
	void drawGrid(){
		if(showGrid){
	        shapeRenderer.begin(ShapeType.Point);
	        shapeRenderer.setColor(Color.BLACK);
	        for(int i = 0; i<xlines; i++)
	        	for(int j = 0; j<ylines; j++)
	        		shapeRenderer.point(i*dots, j*dots, 0);
	        shapeRenderer.end();
		}
	}
	
	void drawSelection(){
		if(selectedActor.getName() != null){
			shapeRenderer.begin(ShapeType.Line);
			shapeRenderer.setColor(Color.GREEN);
			shapeRenderer.rect(selectedActor.getX(), selectedActor.getY(), 
					selectedActor.getWidth(),selectedActor.getHeight());
			shapeRenderer.end();
		}
	}

	/*
	 * This will resize the stage accordingly to fit to your target width and height
	 */
	@Override
	public final void resize(int width, int height) {
		Sink.log("Sink: Resize");
		stage.setViewport(targetWidth, targetHeight, jsonValue.getBoolean("keepAspectRatio"));
	}

	/*
	 * This will pause any music and stop any sound being played
	 * and will fire the Pause event
	 */
	@Override
	public final void pause() {
		Sink.log("Sink: Pause");
		musicPause();
		soundStop();
		firePauseEvent();
	}

	/*
	 * This will resume any music currently being played
	 * and will fire the Resume event
	 */
	@Override
	public final void resume() {
		Sink.log("Sink: Resume");
		musicResume();
		fireResumeEvent();
	}

	/*
	 * When disposed is called
	 * It will automatically unload all your assets and dispose the stage
	 */
	@Override
	public final void dispose() {
		Sink.log("Sink: Disposing");
		fireDisposeEvent();
		stage.dispose();
		Asset.unloadAll();
		Config.writeTotalTime(gameUptime);
		Gdx.app.exit();
	}
	
	/*
	 * Use this to exit your game safely
	 * It will automatically unload all your assets and dispose the stage
	*/
	public static final void exit(){
		Sink.log("Sink: Disposing and Exiting");
		fireDisposeEvent();
		stage.dispose();
		Asset.unloadAll();
		Config.writeTotalTime(gameUptime);
		Gdx.app.exit();
	}

	public static void log(String log) {
		if(jsonValue.getBoolean("loggingEnabled")){
			Gdx.app.log("", log);
			if(logPane != null && jsonValue.getBoolean("showLogger"))
				logPane.update(log);
		}
	}
	
	public static Stage getStage(){
		return stage;
	}
	
	public static OrthographicCamera getCamera(){
		return camera;
	}
	
	public static void addListener(ClickedListener cl){
		clickedListeners.add(cl);
	}
	
	public static void addListener(DraggedListener dl){
		draggedListeners.add(dl);
	}
	
	public static void addListener(PauseListener pl){
		pauseListeners.add(pl);
	}
	
	public static void addListener(ResumeListener rl){
		resumeListeners.add(rl);
	}
	
	public static void addListener(DisposeListener dl){
		disposeListeners.add(dl);
	}
	
	public static void removeListener(ClickedListener cl){
		clickedListeners.removeValue(cl, true);
	}
	
	public static void removeListener(DraggedListener dl){
		draggedListeners.removeValue(dl, true);
	}
	
	public static void removeListener(PauseListener pl){
		pauseListeners.removeValue(pl, true);
	}
	
	public static void removeListener(ResumeListener rl){
		resumeListeners.removeValue(rl, true);
	}
	
	public static void removeListener(DisposeListener dl){
		disposeListeners.removeValue(dl, true);
	}
	
	public static void clearAllListeners(){
		clickedListeners.clear();
		draggedListeners.clear();
		pauseListeners.clear();
		resumeListeners.clear();
		disposeListeners.clear();
		hudActors.clear();
	}
	
	/**
	 * Manually Fire a Pause Event
	 * */
	public static void firePauseEvent(){
		pauseState = true;
		for(PauseListener pl: pauseListeners) pl.onPause();
	}
	
	/**
	 * Manually Fire a Resume Event
	 * */
	public static void fireResumeEvent(){
		pauseState = false;
		for(ResumeListener rl: resumeListeners) rl.onResume();
	}
	
	private static void fireDisposeEvent(){
		for(DisposeListener dl: disposeListeners) dl.onDispose();
	}
	
	/**
	 * Get screen time from start in format of HH:MM:SS. It is calculated from
	 * "secondsTime" parameter.
	 * */
	public static String toScreenTime(float secondstime) {
		int seconds = (int)(secondstime % 60);
		int minutes = (int)((secondstime / 60) % 60);
		int hours =  (int)((secondstime / 3600) % 24);
		return new String(addZero(hours) + ":" + addZero(minutes) + ":" + addZero(seconds));
	}
	
	private static String addZero(int value){
		String str = "";
		if(value < 10)
			 str = "0" + value;
		else
			str = "" + value;
		return str;
	}
	
/***********************************************************************************************************
* 					Scene Related Functions											   		   	           *
************************************************************************************************************/	
	/**
	 * Set the current scene to be displayed
	 * @param className The registered scene's name
	 **/
	public static void setScene(String className){
		if(scenesList.contains(className, false)){
			Sink.log("Current Scene :"+className);
			camera.position.set(targetWidth/2, targetHeight/2, 0);
			clearAllListeners();
			stage.clear();
			stage.addListener(touchInput);
			stage.getRoot().setPosition(0, 0);
			stage.getRoot().setSize(targetWidth, targetHeight);
			stage.getRoot().setBounds(0,0,targetWidth,targetHeight);
			try {
				currentSceneName = className;
				load();
				if(classLoader == null)
					currentScene = Class.forName(className).newInstance();
				else
					currentScene = classLoader.loadClass(className).newInstance();
			} catch (InstantiationException e) {
				Sink.log("Sink: Scene cannot be created , Check if scene class can be found");
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if (fpsLabel != null && jsonValue.getBoolean("showFps")){
				registerSceneHud(fpsLabel);
				fpsLabel.setPosition(targetWidth - 80, targetHeight - 20);
				stage.addActor(fpsLabel);
			}
			if (logPane != null && jsonValue.getBoolean("showLogger")){
				registerSceneHud(logPane);
				logPane.setPosition(0, 0);
				stage.addActor(logPane);
			}
		}
		else{
			Sink.log(className+": Scene Does not Exist");
		}
	}
	
	/**
	 * Returns the current scene being Displayed on stage
	 **/
	public static Object getScene(){
		return currentScene;
	}
	
	/**
	 * Changes to the next scene in the scnesList
	 **/
	public static void nextScene(){
		if(sceneIndex <= scenesList.size)
			sceneIndex++;
		setScene(scenesList.get(sceneIndex));
	}
	
	/**
	 * Changes to the previous scene in the scnesList
	 **/
	public static void prevScene(){
		if(sceneIndex >= 0)
			sceneIndex--;
		setScene(scenesList.get(sceneIndex));
	}
	
	/**
	 * This loads the fonts for fps and logPane from the skin file. This is called by Asset once the
	 * assets are done loading
	 * */
	static void setup(){
		fpsLabel = new Label("", Asset.skin);
		logPane = new LogPane(Asset.skin);
	}
	
	/**
	 * This loads the fonts for fps and logPane from a BitmapFont. This is called by Asset once the
	 * assets are done loading
	 * */
	static void setup(BitmapFont font){
		if(font != null){
			LabelStyle ls = new LabelStyle();
			ls.font = font;
			fpsLabel = new Label("", ls);
			logPane = new LogPane(font);
		}
	}
	
	/* If you want to make any elements/actors to move along with the camera like HUD's add them using
	 * this method
	 */
	public static void registerSceneHud(Actor actor){
		if(!hudActors.contains(actor, false))
			hudActors.add(actor);
	}
	
	/* If you want to stop any elements/actors from moving along with the camera like HUD's you can stop them
	 * by using this method
	 */
	public static void unregisterSceneHud(Actor actor){
		hudActors.removeValue(actor, false);
	}
	 
	public static void clearSceneHud(){
		hudActors.clear();
	}
	
	public static void addActor(Actor actor){
		stage.addActor(actor);
	}
	
	public static void addActor(Actor actor, float x, float y){
		actor.setPosition(x, y);
		stage.addActor(actor);
	}
	
	public static boolean removeActor(Actor actor){
		return stage.getRoot().removeActor(actor);
	}
	
	public static void addAction(Action action) {
		stage.addAction(action);
	}
	
	public static void removeAction(Action action) {
		stage.getRoot().removeAction(action);
	}
	
	public static Actor findActor(String actorName){
		return stage.getRoot().findActor(actorName);
	}
	
	public static SnapshotArray<Actor> getChildren(){
		return stage.getRoot().getChildren();
	}
	
	public static Actor hit(float x, float y){
		return stage.getRoot().hit(x, y, true);
	}
	
	private static Image imgbg = null;
	public static void setBackground(String texName) {
		if(imgbg != null)
			removeBackground();
		if(Asset.tex(texName) != null){
			imgbg = new Image(new TextureRegionDrawable(Asset.tex(texName)), Scaling.stretch);
			imgbg.setFillParent(true);
			stage.addActor(imgbg);
			imgbg.toBack();
			Sink.log("Sink: Background Image Set "+texName);
		}
	}
	
	public static void removeBackground() {
		stage.getRoot().removeActor(imgbg);
	}
	
	private static void load(){
		Sink.log("Loading");
		if(firstScene){
			firstScene = false; 
			// First time Splash Scene do not load serializers as assets are yet to be loaded
			return;
		}
		if(!serializerlock)
			initSerializers(); 
		FileHandle fh = Gdx.files.internal(Asset.basePath+"scene/"+currentSceneName+".json");
		if(fh.exists()){
			String[] lines = fh.readString("UTF-8").split("\n");
			for(String line: lines){
				if(line.trim().isEmpty())
					continue;
				JsonValue jv = jsonReader.parse(line);
				deserialize(jv.get("class").asString(), line);
			}
		}
		Sink.log("Loaded");
	}
	
	/* This is used by the studio so dont use this */
	public static void load(String sceneName){
		Sink.log("Loading");
		currentSceneName = sceneName;
		FileHandle fh = Gdx.files.internal(Asset.basePath+"scene/"+currentSceneName+".json");
		if(fh.exists()){
			String[] lines = fh.readString("UTF-8").split("\n");
			for(String line: lines){
				if(line.trim().isEmpty())
					continue;
				JsonValue jv = jsonReader.parse(line);
				deserialize(jv.get("class").asString(), line);
			}
		}
		Sink.log("Loaded");
	}
	
	/* This is used by the studio so dont use this */
	public static void save(){
		Sink.log("Saving");
		StringBuilder sb = new StringBuilder();
		sb.append("{class:SceneJson,");
		sb.append("background:\""+sceneBackground+"\",");
		sb.append("music:\""+sceneMusic+"\",");
		sb.append("transition:\""+sceneTransition+"\",");
		sb.append("duration:"+sceneDuration+",");
		for(int i=0;i<interpolationsValue.length;i++){
			if(sceneInterpolation.equals(interpolationsValue[i])){
				sb.append("interpolation:"+Interpolations.values()[i].toString()+"}");
				break;
			}
		}
		sb.append("\n");
		for(Actor actor: getChildren()){
			if(actor.getName() != null){
				sb.append(json.toJson(actor));
				sb.append("\n");
			}
		}
		FileHandle fh = Gdx.files.local(Asset.basePath+"scene/"+currentSceneName+".json");
		if(fh.exists())
			fh.writeString(sb.toString(), false, "UTF-8");
		Sink.log("Saved");
	}
	
	public static enum Transitions{
		None, leftToRight, rightToLeft, upToDown, downToUp ,FadeIn, FadeOut, ScaleIn, ScaleOut
	};
	
	public static enum Interpolations{
		Bounce, BounceIn, BounceOut, Circle, CircleIn, CircleOut, 
		Elastic, ElasticIn, ElasticOut, Exp10, Exp10In, Exp10Out, 
		Exp5, Exp5In, Exp5Out, Linear, Fade,
		Pow2, Pow2In, Pow2Out, Pow3, Pow3In, Pow3Out,
		Pow4, Pow4In, pow4Out, Pow5, Pow5In, Pow5Out, 
		Sine, SineIn, SineOut, Swing, SwingIn, SwingOut
	};
	
	public static Interpolation[] interpolationsValue = {
		Interpolation.bounce, Interpolation.bounceIn,  Interpolation.bounceOut,  
		Interpolation.circle,  Interpolation.circleIn,  Interpolation.circleOut, 
		Interpolation.elastic,  Interpolation.elasticIn,  Interpolation.elasticOut, 
		Interpolation.exp10,  Interpolation.exp10In,  Interpolation.exp10Out, 
		Interpolation.exp5,  Interpolation.exp5In,  Interpolation.exp5Out, 
		Interpolation.linear,  Interpolation.fade,
		Interpolation.pow2,  Interpolation.pow2In,  Interpolation.pow2Out, 
		Interpolation.pow3,  Interpolation.pow3In,  Interpolation.pow3Out, 
		Interpolation.pow4,  Interpolation.pow4In,  Interpolation.pow4Out, 
		Interpolation.pow5,  Interpolation.pow5In,  Interpolation.pow5Out, 
		Interpolation.sine,  Interpolation.sineIn,  Interpolation.sineOut, 
		Interpolation.swing,  Interpolation.swingIn,  Interpolation.swingOut, 
	};
	
	public static void deserialize(String className, String value){
		if(className.equals("SceneJson")){
			JsonValue jv = jsonReader.parse(value);
			sceneBackground = jv.getString("background");
			sceneMusic = jv.getString("music");
			sceneTransition = jv.getString("transition");
			sceneDuration = jv.getFloat("duration");
			Sink.setBackground(sceneBackground);
			Asset.musicPlay(sceneMusic);
			Interpolations interp = Interpolations.valueOf(jv.getString("interpolation"));
			int i = 0;
			for(Interpolations interpolation: Interpolations.values()){
				if(interp.equals(interpolation)){
					sceneInterpolation = interpolationsValue[i];
					break;
				}
				i++;
			}
			switch(Transitions.valueOf(sceneTransition)){
				case None: break;
				case leftToRight: transitionLeftToRight();break;
				case rightToLeft: transitionRightToLeft();break;
				case upToDown: transitionUpToDown();break;
				case downToUp: transitionDownToUp();break;
				case FadeIn: transitionFadeIn();break;
				case FadeOut: transitionFadeOut();break;
				case ScaleIn: transitionScaleIn();break;
				case ScaleOut: transitionScaleOut();break;
			}
		}
		else{
			try {
				Class cc = Class.forName(className);
				addActor((Actor)json.fromJson(cc, value));
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void registerSerializer(Class clazz, Json.Serializer serializer){
		json.setSerializer(clazz, serializer);
	}
	
	public static void initSerializers(){
		/*registerSerializer(ActorJson.class, new ActorJson());*/
		registerSerializer(ImageJson.class, new ImageJson());
		registerSerializer(LabelJson.class, new LabelJson());
		registerSerializer(ButtonJson.class, new ButtonJson());
		registerSerializer(TextButtonJson.class, new TextButtonJson());
		registerSerializer(TableJson.class, new TableJson());
		registerSerializer(CheckBoxJson.class, new CheckBoxJson());
		registerSerializer(SelectBoxJson.class, new SelectBoxJson());
		registerSerializer(ListJson.class, new ListJson());
		registerSerializer(SliderJson.class, new SliderJson());
		registerSerializer(TextFieldJson.class, new TextFieldJson());
		registerSerializer(DialogJson.class, new DialogJson());
		registerSerializer(TouchpadJson.class, new TouchpadJson());
		serializerlock = true;
	}
    
/***********************************************************************************************************
* 					Camera Related Functions											   		   	       *
************************************************************************************************************/	
    private static float duration;
    private static float time;
	private static Interpolation interpolation;
    private static boolean complete;
    private static float lastPercent;
    private static float panSpeedX, panSpeedY;
    private static final Vector3 mousePos = new Vector3();
	
    // try to re-implement this with statetime
    private void updateController(){
    	float delta = Gdx.graphics.getDeltaTime();
    	if(!complete)
    		moveByAction(delta);
    	if(hasControl){
			if(Config.usePan) panCameraWithMouse();
			if(Config.useKeyboard) panCameraWithKeyboard();
		}
		if(followedActor != null)
			follow();
    }
    
    public static void moveTo(Actor actor) {
		camera.position.x = actor.getX();
		camera.position.y = actor.getY();
		for(Actor hudactor: Sink.hudActors) 
			hudactor.setPosition(camera.position.x + hudactor.getWidth()/12 - targetWidth/2, 
					camera.position.y + hudactor.getHeight()/2 - targetHeight/2);
	}
	
	public void moveTo(float x, float y) {
		camera.position.x = x;
		camera.position.y = y;
		for(Actor hudactor: Sink.hudActors) 
			hudactor.setPosition(x- targetWidth/2, y - targetHeight/2);
	}
     
     /** Moves the actor instantly. */
    public static void moveBy (float amountX, float amountY) {
         moveBy(amountX, amountY, 0, null);
    }

    public static void moveBy (float amountX, float amountY, float duration) {
         moveBy(amountX, amountY, duration, null);
    }

    public static void moveBy (float amountX, float amountY, float dur, Interpolation interp) {
    	duration = dur;
     	interpolation = interp;
     	panSpeedX = amountX;
     	panSpeedY = amountY;
     	lastPercent = 0;
     	restart();
    }
    
    private void moveByAction(float delta){
        time += delta;
        complete = time >= duration;
        float percent;
        if (complete)
                percent = 1;
        else {
            percent = time / duration;
            if (interpolation != null) percent = interpolation.apply(percent);
        }
        updateMoveBy(percent);
        if (complete) end();
    }

    private void updateMoveBy (float percent) {
        updateRelativeMoveBy(percent - lastPercent);
        lastPercent = percent;
    }

    private void updateRelativeMoveBy (float percentDelta){
    	camera.translate(panSpeedX * percentDelta, panSpeedY * percentDelta, 0);
    	for(Actor actor: Sink.hudActors) 
    		actor.setPosition(actor.getX()+panSpeedX * percentDelta, actor.getY()+panSpeedY * percentDelta);
    }

    private static void restart () {
        time = 0;
        complete = false;
    }

    private void reset () {
        interpolation = null;
    }
    
    private void end () {
    	reset();
    }
    
    public static float getCameraWidth(){
    	return camera.viewportWidth;
    }
    
    public static float getCameraHeight(){
    	return camera.viewportHeight;
    }
    
    public static float getCameraX(){
    	return camera.position.x;
    }
    
    public static float getCameraY(){
    	return camera.position.y;
    }
    
/***********************************************************************************************************
* 					Controller Related Functions												   	       *
************************************************************************************************************/	
    private static boolean hasControl = false;
    
    public static void enablePanning(){
    	hasControl = true;
    }
    	
    public static void disablePanning(){
    	hasControl = false;
    }
    
	public static void followActor(Actor actor){
	    followedActor = actor;
	}
    
	private static Actor followedActor;
	private float followSpeed = 3; 
	private float followTopOffset = 60;
	private float followLeftOffset = 10;
	private float followBotOffset = 70;
	private float followRightOffset = 10;
	
    public void setFollowActorOffset(float top, float left, float bot, float right){
    	followTopOffset = top;
    	followLeftOffset = left;
    	followBotOffset = bot;
    	followRightOffset = right;
    }
    
    public void setFollowSpeed(float speed){
    	followSpeed = speed;
    }
	
    private void follow(){
    	if(camera.position.x < followedActor.getX() - followLeftOffset) moveBy(followSpeed, 0);
		else if(camera.position.x > followedActor.getX() + followRightOffset) moveBy(-followSpeed, 0);
		else if(camera.position.y < followedActor.getY() - followBotOffset) moveBy(0, followSpeed);
		else if(camera.position.y > followedActor.getY() - followTopOffset) moveBy(0, -followSpeed);
		else followedActor = null;
    }
    
    private float panSpeed = 5f;
    private float panXLeftOffset = 100;
	private float panXRightOffset = cfg.width - 100;
	private float panYUpOffset = 70;
	private float panYDownOffset = cfg.height - 70;
	public static float camOffsetX = 160f;
	public static float camOffsetYTop = 110f;
	public static float camOffsetYBot = 65f;
	public static float mapOffsetX = 0;
	public static float mapOffsetY = 0;
	
	public void setPanSpeed(float speed){
		panSpeed = speed;
	}
	
    public void setPanOffset(float xLeft, float xRight, float yUp, float dDown){
    	panXLeftOffset = xLeft;
    	panXRightOffset = xRight;
    	panYUpOffset = yUp;
    	panYDownOffset = dDown;
    }
    
    public void setCamOffset(float xOffset, float yOffsetTop, float yOffsetBot){
    	camOffsetX = xOffset;
    	camOffsetYTop = yOffsetTop;
    	camOffsetYBot = yOffsetBot;
    }
    
    private void panCameraWithMouse(){
    	mousePos.x = Gdx.input.getX();
    	mousePos.y = Gdx.input.getY();
    	if(mousePos.x > panXRightOffset && camera.position.x < mapOffsetX - 5) moveBy(panSpeed, 0);
    	else if(mousePos.x < panXLeftOffset && camera.position.x > camOffsetX +5)  moveBy(-panSpeed, 0);
    	else if(mousePos.y < panYUpOffset && camera.position.y < mapOffsetY -5) moveBy(0, panSpeed);
    	else if(mousePos.y > panYDownOffset && camera.position.y > camOffsetYBot +5) moveBy(0, -panSpeed);
    }
    	
    private void panCameraWithKeyboard(){
    	if(Gdx.input.isKeyPressed(Keys.LEFT))
    		//if(camera.position.x > camOffsetX +5)
    			moveBy(-panSpeed, 0);
    	else if(Gdx.input.isKeyPressed(Keys.RIGHT))
    		//if(camera.position.x < mapOffsetX - 5)
    			moveBy(panSpeed, 0);
    	else if(Gdx.input.isKeyPressed(Keys.UP))
    		//if(camera.position.y < mapOffsetY -5)
    			moveBy(0, panSpeed);
    	else if(Gdx.input.isKeyPressed(Keys.DOWN))
    		//if(camera.position.y > camOffsetYBot +5)
    			moveBy(0, -panSpeed);
    }
    	
	private final static Vector3 curr = new Vector3();
	private final static Vector3 last = new Vector3(-1, -1, -1);
	private final static Vector3 delta = new Vector3();
	private static float deltaCamX = 0;
	private static float deltaCamY = 0;
	
	private static void dragCam(int x, int y){
		camera.unproject(curr.set(x, y, 0));
    	if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
    		camera.unproject(delta.set(last.x, last.y, 0));
    		delta.sub(curr);
    		deltaCamX = delta.x + camera.position.x;
    		deltaCamY = delta.y + camera.position.y;
    		if(deltaCamX > camOffsetX && deltaCamX < mapOffsetX)
    			moveBy(delta.x, 0);
    		if(deltaCamY > camOffsetYBot && deltaCamY < mapOffsetY)
    			moveBy(0, delta.y);		
    	}
    	last.set(x, y, 0);
    }
    
    private final static ClickListener touchInput = new ClickListener(){
    	@Override
    	public void clicked(InputEvent event, float x, float y){
    		super.clicked(event, x, y);
    		selectedActor = hit(x,y);
    		if(selectedActor != null)
    			for(ClickedListener cl: clickedListeners) 
    				cl.onClicked();
    	}
    	
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			super.touchDown(event, x, y, pointer, button);
			return true;
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			super.touchDragged(event, x, y, pointer);
			for(DraggedListener dl: draggedListeners)
				dl.onDragged();
			if(hasControl)
				if(Config.useDrag) dragCam((int)x, (int)-y);
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button){
			super.touchUp(event, x, y, pointer, button);
			
			if(hasControl)
				last.set(-1, -1, -1);
		}
	};
	
	public void touchPad(float xPercent, float yPercent){
	}
	
	
/***********************************************************************************************************
* 					Transition Related Functions												   	       *
************************************************************************************************************/		
	
	public static void transitionLeftToRight(){
		stage.getRoot().setPosition(-999, 0);
 		addAction(Actions.moveTo(0, 0, sceneDuration, sceneInterpolation));
	}
	
	public static void transitionRightToLeft(){
		stage.getRoot().setPosition(999, 0);
 		addAction(Actions.moveTo(0, 0, sceneDuration, sceneInterpolation));
	}
	
	public static void transitionUpToDown(){
		stage.getRoot().setPosition(0, 999);
 		addAction(Actions.moveTo(0, 0, sceneDuration, sceneInterpolation));
	}
	
	public static void transitionDownToUp(){
		stage.getRoot().setPosition(0, -999);
 		addAction(Actions.moveTo(0, 0, sceneDuration, sceneInterpolation));
	}
	
	public static void transitionFadeIn(){
		Color color = stage.getRoot().getColor();
		color.a = 0f;
		stage.getRoot().setColor(color);
 		addAction(Actions.fadeIn(sceneDuration, sceneInterpolation));
	}
	
	public static void transitionFadeOut(){
		Action action2 = new Action(){
			@Override 
			public boolean act(float delta){
				Color color = stage.getRoot().getColor();
				color.a = 1f;
				stage.getRoot().setColor(color);
				return true;
			}
		};
		addAction(Actions.sequence(Actions.fadeOut(sceneDuration, sceneInterpolation), action2));
	}
	
	public static void transitionScaleIn(){
		Sink.stage.getRoot().setScale(0, 0);
 		addAction(Actions.scaleTo(1, 1, sceneDuration, sceneInterpolation));
	}
	
	public static void transitionScaleOut(){
		Action action2 = new Action(){
			@Override 
			public boolean act(float delta){
				stage.getRoot().scale(1f);
				return true;
			}
		};
		addAction(Actions.sequence(Actions.scaleTo(1, 1, sceneDuration, sceneInterpolation), action2));
	}
}

/*
 * This class is used to display the sink logs on the game screen itself so it
 * becomes easier to track game states can be disabled in the options
*/
class LogPane extends SceneGroup{
	Label logLabel;
	ScrollPane scroll;
	
	public LogPane(Skin skin){
		setSize(300, 100);
		logLabel = new Label("", skin);
		scroll = new ScrollPane(logLabel);
		scroll.setPosition(0,0);
		scroll.setSize(300, 100);
		scroll.setBounds(0, 0, 300, 100);
		addActor(scroll);
	}
	
	public LogPane(BitmapFont font){
		setSize(300, 100);
		LabelStyle ls = new LabelStyle();
		ls.font = font;
		logLabel = new Label("", ls);
		scroll = new ScrollPane(logLabel);
		scroll.setPosition(0,0);
		scroll.setSize(300, 100);
		scroll.setBounds(0, 0, 300, 100);
		addActor(scroll);
	}
	
	public void update(String text){
		logLabel.setText(logLabel.getText() + "\n" +text);
		scroll.setScrollPercentY(100);
	}
}