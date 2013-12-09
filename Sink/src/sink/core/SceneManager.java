package sink.core;

import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ArrayMap;

/** The Singleton Class for managing Scenes
 * <p>
 * Use this class to register all your scenes and then you can switch you scenes by using {@link #setCurrentScene}
 *  method with the sceneName you registered your scene with.
 *  You can define a ScenePath and use the {@link #nextScene}, {@link #prevScene} to walk the path.
 * <p>
 * @author pyros2097 */

public final class SceneManager {
	private static final ArrayMap<String , Scene> sceneMap = new ArrayMap<String, Scene>();
	private static Scene currentScene = null;
	
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
	public static void setCurrentScene(String sceneName){
		if(sceneMap.containsKey(sceneName)){
			currentScene = sceneMap.get(sceneName);
			clearScene();
			currentScene.init();
			showScene();
		}
		else{
			Scene.log(sceneName+": Scene Does not Exist");
			return;
		}
	}
	
	/**
	 * Returns the current scene being Displayed on stage
	 **/
	public static Scene getCurrentScene(){
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
	
	/**
	 * Returns whether the current scene is registered in the SceneManager
	 * @param sceneName The registered scene's name
	 **/
	public static boolean contains(String sceneName){
		return sceneMap.containsKey(sceneName);
	}

	/**
	 * Define the scenePath so that scenes can be automatically switched using
	 * nextScene() and prevScene()
	 **/
	public static void scenePath(){
		
	}
	
	/**
	 * Display the next scene in the ScenePath
	 **/
	public static void nextScene(){
		
	}
	
	/**
	 * Display the previous scene in the ScenePath
	 * Useful for implementing back functionality
	 **/
	public static void prevScene(){
		
	}
	
	/**
	 * This update method is called each frame and calls the update method of the current scene
	 *  so can be used to add some calculations/ game logic to the current scene
	 *  ex:	check collisions, check game state ..etc
	 **/
	public static void update(){
		if(currentScene != null)
			currentScene.update();
	}
	
	private static void clearScene(){
		Stage.camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		//Stage.clear();
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
	}
	

	private static void showScene(){
		//Stage.addActor(this);
		if (Scene.fpsLabel != null && Config.fpsVisible){
			Scene.fpsLabel.setPosition(Config.TARGET_WIDTH - 80, Config.TARGET_HEIGHT - 20);
			//Stage.addActor(fpsLabel);
		}
		if (Scene.logPane != null && Config.loggerVisible){
			Scene.logPane.setPosition(0, 0);
			//Stage.addActor(logPane);
		}
	}
	
}
