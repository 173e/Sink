package sky.engine.core;

import com.badlogic.gdx.utils.ArrayMap;

public final class SceneManager {
	private static ArrayMap<String , Scene> sceneMap = new ArrayMap<String, Scene>();
	
	public static void setScene(String sceneName){
		if(sceneMap.containsKey(sceneName))
			sceneMap.get(sceneName);//scene.init();
		else
			Scene.$log(sceneName+": Scene Does not Exist");
	}
	
	public static void registerScene(String sceneName, Scene scene){
		sceneMap.put(sceneName, scene);
	}
	
	public static void sceneGraph(){
		
	}
	
	public static void nextScene(){
		
	}
	
	public static void prevScene(){
		
	}
}
