package sink.core;

/** The base class for creating Splash Scenes
 * <p>
 * This class is the entry Scene to the sink framework . Just extend this class and override the
 * {@link #onInit} method and manually load some asset and display your splash while your assets are loading
 * in the background. When the assets are done loading the overridden method {@link #onAssetsLoaded} is called
 * so that you can change your scene or play with your assets etc..
 * Note: The first scene in the Sink Framework must always be a splash scene as the assets are yet to be loaded
 * 		 by the framework.
 * <p>
 * 
 * A scene can be set using {@link Sink.setScene}
 * 
 * <p>
 * Scene also supports transitions. You can start a scene transition by calling the method at he beginning of
 * the init method ex: transitionRightToLeft(); will transit the scene from right to left when 
 * it is shown.
 * @author pyros2097 */
public abstract class SplashScene extends Scene {
	abstract public void onAssetsLoaded();
	private boolean finsihedLoading = false;
	
	@Override
	public void act(float delta){
		if(Asset.loadNonBlocking() && !finsihedLoading){
			onAssetsLoaded();
			finsihedLoading = true;
		}
	}
}