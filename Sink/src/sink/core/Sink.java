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
import sink.event.CreateListener;
import sink.event.DisposeListener;
import sink.event.PauseListener;
import sink.event.ResumeListener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/** The Main Entry Point for the Sink Game is the Sink class
 * <p>
 * It consists of a single Stage and Camera which are all initialized based on the {@link Config} settings.
 * The stage can be accessed in a static way like Sink.stage and methods related to camera like moveTo, moveBy,
 * are also accessed the same way.<br>
 * It has extra things like gameUptime, pauseState, CreateListeners, PauseListeners, ResumeListeners, 
 * DisposeListeners.<br>
 * 
 * It has static methods which can be used for panning the camera using mouse, keyboard, drag.. etc.
 * It can also automatically follow a actor by using followActor(Actor actor)<br>
 * 
 * Use this class to register all your scenes and then you can switch you scenes by using {@link #setScene}
 * method with the sceneName you registered your scene with.<br>
 * 
 * You Must setup the Sink framework in your splash/menu or first scene after you have loaded all your
 * assets if you want the logPane and fps to display by calling {@link #setup()}<br>
 * 
 * <p>
 * @ex
 * <pre>
 * <code>
 * public class BasicDesktop extends MainDesktop{
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
				Sink.setScene("splash"); // In splash load your assets and setup Sink
			}
		});
		init(); // this will set the configuration
		run(); // this will create the lwjgl application
	}
} </code>
 </pre>
 * @author pyros2097 */

public final class Sink implements ApplicationListener {
	private float startTime = System.nanoTime();
	public static float gameUptime = 0;
	
	public static Stage stage;
	private static OrthographicCamera camera;
	private static LogPane logPane;
	private static Label fpsLabel;
	static Scene currentScene = null;
	static final ArrayMap<String , Scene> sceneMap = new ArrayMap<String, Scene>();
	public static Array<Actor> hudActors = new Array<Actor>();
	public static boolean pauseState = false;
	private static final Array<CreateListener> createListeners = new Array<CreateListener>();
	private static final Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static final Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	private static final Array<DisposeListener> disposeListeners = new Array<DisposeListener>();
	
	/**
	 * You Must call setup the Sink framework in your splash/menu scene after you have loaded all your
	 * assets if you want the logPane and fps to display.
	 * This loads the fonts for fps and logPane from the skin file. You can use Asset.skin
	 * @param font - The Skin to set the Fps and logPane text font.
	 * */
	public static void setup(Skin skin){
		if(skin != null){
			fpsLabel = new Label("", skin);
			logPane = new LogPane(skin);
		}
	}
	
	/**
	 * You Must call setup the Sink framework in your splash/menu scene after you have loaded all your
	 * assets if you want the logPane and fps to display.
	 * This loads the fonts for fps and logPane from the specified BitmapFont font;
	 * @param font - The BitmapFont to set the Fps and logPane text font.
	 * */
	public static void setup(BitmapFont font){
		if(font != null){
			LabelStyle ls = new LabelStyle();
			ls.font = font;
			fpsLabel = new Label("", ls);
			logPane = new LogPane(font);
		}
	}
	
	@Override
	public final void create() {
		Sink.log("Sink: Created");
		Config.setup();
		stage = new Stage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Config.keepAspectRatio);
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		stage.setCamera(camera);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(stage);
 		Sink.stage.addListener(touchInput);
 		fireCreateEvent();
 		log("TotalTime: "+toScreenTime(Config.readTotalTime()));
	}
	
	@Override
	public final void render(){
		if (System.nanoTime() - startTime >= 1000000000) {
			gameUptime +=1 ;
			startTime = System.nanoTime();
		}
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stage.act(Gdx.graphics.getDeltaTime());
		updateController();
		stage.draw();
		if (fpsLabel != null && Config.fpsVisible)
			fpsLabel.setText("Fps: " + Gdx.graphics.getFramesPerSecond());
 	}

	@Override
	public final void resize(int width, int height) {
		Sink.log("Sink: Resize");
		stage.setViewport(Config.TARGET_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
	}

	@Override
	public final void pause() {
		Sink.log("Sink: Pause");
		musicPause();
		soundStop();
		firePauseEvent();
	}

	@Override
	public final void resume() {
		Sink.log("Sink: Resume");
		musicResume();
		fireResumeEvent();
	}

	@Override
	public final void dispose() {
		Sink.log("Sink: Disposing");
		fireDisposeEvent();
		stage.dispose();
		Asset.unloadAll();
		Config.writeTotalTime(gameUptime);
		Gdx.app.exit();
	}

	public static void log(String log) {
		if(Config.loggingEnabled){
			Gdx.app.log("", log);
			if(logPane != null && Config.loggerVisible)
				logPane.update(log);
		}
	}

	public static void addListener(CreateListener cl){
		createListeners.add(cl);
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
	
	public static void removeListener(CreateListener cl){
		createListeners.removeValue(cl, true);
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
		pauseListeners.clear();
		resumeListeners.clear();
		disposeListeners.clear();
	}
	
	private static void fireCreateEvent(){
		for(CreateListener cl: createListeners) cl.onCreate();
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
	 * You Must Register the scene with a sceneName so that it can be cached and change scenes
	 * using sceneName's
	 * @param sceneName The name/key to be associated with the scene
	 * @param scene The Scene for caching and easy switching
	 * */
	public static void registerScene(String sceneName, Scene scene){
		sceneMap.put(sceneName, scene);
	}

	/**
	 * Set the current scene to be displayed
	 * @param sceneName The registered scene's name
	 **/
	public static void setScene(String sceneName){
		if(sceneMap.containsKey(sceneName)){
			Sink.log("Current Scene :"+sceneName);
			currentScene = sceneMap.get(sceneName);
			Sink.clearScene();
			currentScene.onInit();
			Sink.showScene();
		}
		else
			Sink.log(sceneName+": Scene Does not Exist");
	}
	
	/**
	 * Returns the current scene being Displayed on stage
	 **/
	public static Scene getScene(){
		return currentScene;
	}
	
	/**
	 * Returns whether scene if it is registered in the SceneManager
	 * else null
	 * @param sceneName The registered scene's name
	 **/
	public static Scene getScene(String sceneName){
		if(sceneMap.containsKey(sceneName))
			return sceneMap.get(sceneName);
		return null;
	}
	
	private static void showScene(){
		stage.addActor(currentScene);
		if (fpsLabel != null && Config.fpsVisible){
			registerSceneHud(fpsLabel);
			fpsLabel.setPosition(Config.TARGET_WIDTH - 80, Config.TARGET_HEIGHT - 20);
			stage.addActor(fpsLabel);
		}
		if (logPane != null && Config.loggerVisible){
			registerSceneHud(logPane);
			logPane.setPosition(0, 0);
			stage.addActor(logPane);
		}
	}

	private static void clearScene(){
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		stage.clear();
		currentScene.clear();
		currentScene.setPosition(0, 0);
		currentScene.setSize(Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		currentScene.setBounds(0,0,Config.TARGET_WIDTH,Config.TARGET_HEIGHT);
		currentScene.grid = new Table();
		currentScene.grid.setSize(Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		currentScene.grid.setFillParent(true);
		currentScene.grid.setPosition(0, 0);
		currentScene.grid.top().left();
		currentScene.xcenter = currentScene.getWidth()/2;
		currentScene.ycenter = currentScene.getHeight()/2;
		clearSceneHud();
	}
	
	/* If you want to make any elements/actors to move along with the camera like HUD's add them using
	 * this method
	 */
	public static void registerSceneHud(Actor actor){
		if(!hudActors.contains(actor, true))
			hudActors.add(actor);
	}
	
	/* If you want to stop any elements/actors from moving along with the camera like HUD's you can stop them
	 * by using this method
	 */
	public static void unregisterSceneHud(Actor actor){
		hudActors.removeValue(actor, true);
	}
	 
	public static void clearSceneHud(){
		hudActors.clear();
	}
    
/***********************************************************************************************************
* 					Camera Related Functions											   		   	       *
************************************************************************************************************/	
    private float duration, time;
	private Interpolation interpolation;
    private boolean complete;
    private float lastPercent;
    private float panSpeedX, panSpeedY;
    private final Vector3 mousePos = new Vector3();
	
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
			hudactor.setPosition(camera.position.x + hudactor.getWidth()/12 - Config.TARGET_WIDTH/2, 
					camera.position.y + hudactor.getHeight()/2 - Config.TARGET_HEIGHT/2);
	}
	
	public void moveTo(float x, float y) {
		camera.position.x = x;
		camera.position.y = y;
		for(Actor hudactor: Sink.hudActors) 
			hudactor.setPosition(x- Config.TARGET_WIDTH/2, y - Config.TARGET_HEIGHT/2);
	}
     
     /** Moves the actor instantly. */
    public void moveBy (float amountX, float amountY) {
         moveBy(amountX, amountY, 0, null);
    }

    public void moveBy (float amountX, float amountY, float duration) {
         moveBy(amountX, amountY, duration, null);
    }

    public void moveBy (float amountX, float amountY, float duration, Interpolation interpolation) {
    	this.duration = duration;
     	this.interpolation = interpolation;
     	this.panSpeedX = amountX;
     	this.panSpeedY = amountY;
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

    private void restart () {
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
	private float panXRightOffset = Config.SCREEN_WIDTH - 100;
	private float panYUpOffset = 70;
	private float panYDownOffset = Config.SCREEN_HEIGHT - 70;
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
    	
	private final Vector3 curr = new Vector3();
	private final Vector3 last = new Vector3(-1, -1, -1);
	private final Vector3 delta = new Vector3();
	private float deltaCamX = 0;
	private float deltaCamY = 0;
	
	private void dragCam(int x, int y){
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
    
    private final InputListener touchInput = new InputListener(){
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			super.touchDown(event, x, y, pointer, button);
			return true;
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			super.touchDragged(event, x, y, pointer);
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
}

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