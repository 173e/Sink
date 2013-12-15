package sink.core;

import static sink.core.Asset.$musicPause;
import static sink.core.Asset.$musicResume;
import static sink.core.Asset.$soundStop;
import sink.event.CreateListener;
import sink.event.DisposeListener;
import sink.event.PauseListener;
import sink.event.ResumeListener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;

/** The Main Entry Point for the Sink Game is the Sink class
 * <p>
 * It consists of a single Stage and SceneCamera which are all initialized based on the {@link Config} settings.
 * They can be accessed in a static way like Sink.stage Sink.camera.
 * It also has extra things like gameUptime, pauseState, PauseListeners, ResumeListeners, DisposeListeners.
 * An instance object is usually created and the {@link #create} method overrided to initialize all the
 * scenes for your game  during creation of LWJGLApplication creation. 
 * ex:
 * <code>
 * public class BasicDesktop extends MainDesktop{
	public static void main(String[] argc) {
		init();
		new LwjglApplication(new Sink(){
			@Override
			public void create(){
				super.create();
				SceneManager.registerScene("splash", new SplashScene());
				SceneManager.registerScene("menu", new MenuScene());
				SceneManager.registerScene("options", new OptionsScene());
				SceneManager.registerScene("credits", new CreditsScene());
				SceneManager.registerScene("game", new GameScene());
				SceneManager.setCurrentScene("splash");
			}
		}, cfg); 
	}
} </code>
 * <p>
 * @author pyros2097 */

public final class Sink implements ApplicationListener {
	public static float gameUptime = 0;
	public static Stage stage;
	public static SceneCamera camera;
	
	private float startTime = System.nanoTime();
	public static boolean pauseState = false;
	private static final Array<CreateListener> createListeners = new Array<CreateListener>();
	private static final Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static final Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	private static final Array<DisposeListener> disposeListeners = new Array<DisposeListener>();
	
	@Override
	public void create() {
		Scene.log("Sink: Created");
		Config.init();
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
 	}

	@Override
	public final void resize(int width, int height) {
		Scene.log("Sink: Resize");
		stage.setViewport(Config.TARGET_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
	}

	@Override
	public final void pause() {
		Scene.log("Sink: Pause");
		$musicPause();
		$soundStop();
		firePauseEvent();
	}

	@Override
	public final void resume() {
		Scene.log("Sink: Resume");
		$musicResume();
		fireResumeEvent();
	}

	@Override
	public final void dispose() {
		Scene.log("Sink: Disposing");
		fireDisposeEvent();
		stage.dispose();
		Asset.unloadAll();
		Gdx.app.exit();
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
}