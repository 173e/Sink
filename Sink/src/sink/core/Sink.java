/*******************************************************************************
 * Copyright 2014 pyros2097
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
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/** The Main Entry Point for the Sink Game is the Sink class
 * <p>
 * It consists of a single Stage and SceneCamera which are all initialized based on the {@link Config} settings.
 * They can be accessed in a static way like Sink.stage Sink.camera.
 * It also has extra things like gameUptime, pauseState, CreateListeners,PauseListeners, ResumeListeners, 
 * DisposeListeners.
 * Use this class to register all your scenes and then you can switch you scenes by using {@link #setScene}
 * method with the sceneName you registered your scene with.
 * You Must setup the Sink framework in your splash/menu or first scene after you have loaded all your
 * assets if you want the logPane and fps to display.
 * @ex
 * <code>
 * public class BasicDesktop extends MainDesktop{
	public static void main(String[] argc) {
		init();
		Sink.addListener(new CreateListener(){
			@Override
			public void onCreate(){
				Sink.registerScene("splash", new SplashScene());
				Sink.registerScene("menu", new MenuScene());
				Sink.registerScene("options", new OptionsScene());
				Sink.registerScene("credits", new CreditsScene());
				Sink.registerScene("game", new GameScene());
				Sink.setScene("splash");
			}
		});
		run();
	}
} </code>
 * <p>
 * @author pyros2097 */

public final class Sink implements ApplicationListener {
	public static float gameUptime = 0;
	public static Stage stage;
	public static SceneCamera camera;
	
	private float startTime = System.nanoTime();
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
	 * You Must setup the Sink framework in your splash/menu scene after you have loaded all your
	 * assets if you want the logPane and fps to display.
	 * */
	public static void setup(){
		fpsLabel = new Label("", Asset.skin);
		logPane = new LogPane();
	}
	
	@Override
	public final void create() {
		Sink.log("Sink: Created");
		Config.setup();
		stage = new Stage(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Config.keepAspectRatio);
		camera = new SceneCamera();
		camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		stage.setCamera(camera);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(stage);
 		fireCreateEvent();
 		//Scene.$log("TotalTime: "+toScreenTime(Config.readTotalTime()));
 		//Config.writeTotalTime(gameUptime);
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
}

class LogPane extends SceneGroup{
	Label logLabel;
	ScrollPane scroll;
	
	public LogPane(){
		setSize(300, 100);
		logLabel = new Label("", Asset.skin);
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