package sink.core;

import static sink.core.Asset.$musicPause;
import static sink.core.Asset.$musicResume;
import static sink.core.Asset.$soundStop;
import sink.event.DisposeListener;
import sink.event.PauseListener;
import sink.event.ResumeListener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public final class Stage extends com.badlogic.gdx.scenes.scene2d.Stage implements ApplicationListener {
	private float startTime = System.nanoTime();
	public static float gameUptime = 0;
	
	public static SmoothCamera camera;
	private static final Group root = new Group();
	
	private static final Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static final Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	private static final Array<DisposeListener> disposeListeners = new Array<DisposeListener>();
	
	public Stage(){
		super(Config.SCREEN_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
		camera = new SmoothCamera();
		camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		setCamera(camera);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(this);
 		//Scene.$log("TotalTime: "+toScreenTime(Config.readTotalTime()));
 		//Config.writeTotalTime(gameUptime);
	}
	
	@Override
	public final void create() {
		Scene.log("Stage: Created");
	}
	
	@Override
	public final void render(){
		// Update screen clock (1 second tick)
		// ############################################################
		if (System.nanoTime() - startTime >= 1000000000) {
			gameUptime +=1 ;
			startTime = System.nanoTime();
		}
		// Update animation times
		// ############################################################
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// Update stage/actors logic (update() method in previous games)
		// ############################################################
		act(Gdx.graphics.getDeltaTime());
		SceneManager.update();
		draw();
 	}

	@Override
	public final void resize(int width, int height) {
		Scene.log("Engine: Resize");
		setViewport(Config.TARGET_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
	}

	@Override
	public final void pause() {
		Scene.log("Engine: Pause");
		$musicPause();
		$soundStop();
		firePauseEvent();
	}

	@Override
	public final void resume() {
		Scene.log("Engine: Resume");
		$musicResume();
		fireResumeEvent();
	}

	@Override
	public final void dispose() {
		Scene.log("Engine: Disposing");
		fireDisposeEvent();
		dispose();
		Asset.unloadAll();
		Gdx.app.exit();
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
	
	/**
	 * Manually Fire a Pause Event
	 * */
	public static void firePauseEvent(){
		for(PauseListener pl: pauseListeners) pl.onPause();
	}
	
	/**
	 * Manually Fire a Resume Event
	 * */
	public static void fireResumeEvent(){
		for(ResumeListener rl: resumeListeners) rl.onResume();
	}
	
	private static void fireDisposeEvent(){
		for(DisposeListener dl: disposeListeners) dl.onDispose();
	}
	
	/**
	 * Camera Related methods
	 * "secondsTime" parameter.
	 * */
	
	public static void setCamX(float x){
		camera.position.x  = x;
	}
	
	public static void setCamY(float y){
		camera.position.y = y;
	}
	
	public static void addCamX(float x){
		camera.position.x += x;
	}
	
	public static void addCamY(float y){
		camera.position.y += y;
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
