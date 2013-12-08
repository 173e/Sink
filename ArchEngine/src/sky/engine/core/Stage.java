package sky.engine.core;

import static sky.engine.core.Asset.$musicPause;
import static sky.engine.core.Asset.$musicResume;
import static sky.engine.core.Asset.$soundStop;
import sky.engine.input.PauseListener;
import sky.engine.input.ResumeListener;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Array;

public final class Stage extends com.badlogic.gdx.scenes.scene2d.Stage implements ApplicationListener {
	private float startTime = System.nanoTime();
	public static float gameUptime = 0;
	
	public static SmoothCamera camera;
	public Scene splashPanel = null;
	
	private static Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	
	public Stage(){
		super(Config.SCREEN_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
		camera = new SmoothCamera();
		camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		setCamera(camera);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(this);
 		Scene.$log("TotalTime: "+getScreenTime(Config.readTotalTime()));
	}
	
	@Override
	public void create() {
		Scene.$log("Stage: Created");
	}

	/**
	 * Get screen time from start in format of HH:MM:SS. It is calculated from
	 * "secondsTime" parameter.
	 * */
	public static String getScreenTime(float secondstime) {
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
	
	private void updateTime(float delta) {
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
		act(delta);
		draw();
	}
	
	@Override
	public void render(){
		float delta = Gdx.graphics.getDeltaTime();
		updateTime(delta);
		if(splashPanel != null)
			splashPanel.update();
	  	//GameMan.update(delta);
 	}

	@Override
	public void resize(int width, int height) {
		Scene.$log("Engine: Resize");
		setViewport(Config.TARGET_WIDTH, Config.TARGET_HEIGHT, Config.keepAspectRatio);
	}

	@Override
	public void pause() {
		Scene.$log("Engine: Pause");
		$musicPause();
		$soundStop();
		firePauseEvent();
	}

	@Override
	public void resume() {
		Scene.$log("Engine: Resume");
		$musicResume();
		fireResumeEvent();
	}

	@Override
	public void dispose() {
		Scene.$log("Engine: Disposing");
		Config.writeTotalTime(gameUptime);
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
	
	public static void firePauseEvent(){
		for(PauseListener pl: pauseListeners) pl.onPause();
	}
	
	public static void fireResumeEvent(){
		for(ResumeListener rl: resumeListeners) rl.onResume();
	}

}
