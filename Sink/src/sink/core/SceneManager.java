package sink.core;

import com.badlogic.gdx.utils.ArrayMap;

public final class SceneManager {
	private static final ArrayMap<String , Scene> sceneMap = new ArrayMap<String, Scene>();
	private static Scene currentScene = null;
	
	/**
	 * You Must Register the scene with a sceneName so that it can be cached and change scenes
	 * using sceneName's
	 * */
	public static void registerScene(String sceneName, Scene scene){
		sceneMap.put(sceneName, scene);
	}
	
	public static void setCurrentScene(String sceneName){
		if(sceneMap.containsKey(sceneName))
			currentScene = sceneMap.get(sceneName);//scene.init();
		else
			Scene.log(sceneName+": Scene Does not Exist");
	}
	
	public static Scene getCurrentScene(){
		return currentScene;
	}
	
	public static Scene getScene(String sceneName){
		if(sceneMap.containsKey(sceneName))
			return sceneMap.get(sceneName);
		return null;
	}
	
	public static boolean contains(String sceneName){
		return sceneMap.containsKey(sceneName);
	}

	public static void sceneGraph(){
		
	}
	
	public static void nextScene(){
		
	}
	
	public static void prevScene(){
		
	}
	
	/**
	 * This update method is called each frame and calls the update method of the current scene
	 *  so can be used to add soem calculations/ game logic to the current scene
	 *  ex:	check collisions, check game state ..etc
	 * */
	public static void update(){
		if(currentScene != null)
			currentScene.update();
	}
	
}
