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
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent.Type;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener.FocusEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Pool.Poolable;
import com.badlogic.gdx.utils.Pools;
import com.badlogic.gdx.utils.SnapshotArray;

/** The Main Entry Point for the Sink Game is the Singleton Stage
 * <p>
 * 
 * <p>
 * @author pyros2097 */

public class Stage implements ApplicationListener {
	private float startTime = System.nanoTime();
	public static float gameUptime = 0;
	public static StageCamera camera;
	private static final Array<PauseListener> pauseListeners = new Array<PauseListener>();
	private static final Array<ResumeListener> resumeListeners = new Array<ResumeListener>();
	private static final Array<DisposeListener> disposeListeners = new Array<DisposeListener>();
	
	@Override
	public void create() {
		Scene.log("Stage: Created");
		batch = new SpriteBatch();
		width = Config.SCREEN_WIDTH;
		height = Config.SCREEN_HEIGHT;
		camera = new StageCamera();
		setViewport(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT, Config.keepAspectRatio);
		camera.setToOrtho(false, Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		Gdx.input.setCatchBackKey(true);
 		Gdx.input.setCatchMenuKey(true);
 		Gdx.input.setInputProcessor(inputAdapter);
 		//Scene.$log("TotalTime: "+toScreenTime(Config.readTotalTime()));
 		//Config.writeTotalTime(gameUptime);
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
		clear();
		if (ownsBatch) batch.dispose();
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
	
	
	/*
	 * 
	 * 
	 */
	static private final Vector2 actorCoords = new Vector2();
	static private final Vector3 cameraCoords = new Vector3();

	private static float viewportX, viewportY, viewportWidth, viewportHeight;
	private static float width, height;
	private static float gutterWidth, gutterHeight;
	public static Batch batch ;
	public static boolean ownsBatch;
	public static Group root = new Group();
	private static final Vector2 stageCoords = new Vector2();
	private static final Actor[] pointerOverActors = new Actor[20];
	private static final boolean[] pointerTouched = new boolean[20];
	private static final int[] pointerScreenX = new int[20];
	private static final int[] pointerScreenY = new int[20];
	private static int mouseScreenX, mouseScreenY;
	private static Actor mouseOverActor;
	private static Actor keyboardFocus, scrollFocus;
	private static final SnapshotArray<TouchFocus> touchFocuses = new SnapshotArray<TouchFocus>(true, 4, TouchFocus.class);

	/** Sets up the stage size using a viewport that fills the entire screen without keeping the aspect ratio.
	 * @see #setViewport(float, float, boolean, float, float, float, float) */
	public static void setViewport (float width, float height) {
		setViewport(width, height, false, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/** Sets up the stage size using a viewport that fills the entire screen.
	 * @see #setViewport(float, float, boolean, float, float, float, float) */
	public static void setViewport (float width, float height, boolean keepAspectRatio) {
		setViewport(width, height, keepAspectRatio, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	/** Sets up the stage size and viewport. The viewport is the glViewport position and size, which is the portion of the screen
	 * used by the stage. The stage size determines the units used within the stage, depending on keepAspectRatio:
	 * <p>
	 * If keepAspectRatio is false, the stage is stretched to fill the viewport, which may distort the aspect ratio.
	 * <p>
	 * If keepAspectRatio is true, the stage is first scaled to fit the viewport in the longest dimension. Next the shorter
	 * dimension is lengthened to fill the viewport, which keeps the aspect ratio from changing. The {@link #getGutterWidth()} and
	 * {@link #getGutterHeight()} provide access to the amount that was lengthened.
	 * @param viewportX The top left corner of the viewport in glViewport coordinates (the origin is bottom left).
	 * @param viewportY The top left corner of the viewport in glViewport coordinates (the origin is bottom left).
	 * @param viewportWidth The width of the viewport in pixels.
	 * @param viewportHeight The height of the viewport in pixels. */
	public static void setViewport (float stageWidth, float stageHeight, boolean keepAspectRatio, float vX, float vY,
		float vW, float vH) {
		viewportX = vX;
		viewportY = vY;
		viewportWidth = vW;
		viewportHeight = vH;
		if (keepAspectRatio) {
			if (viewportHeight / viewportWidth < stageHeight / stageWidth) {
				float toViewportSpace = viewportHeight / stageHeight;
				float toStageSpace = stageHeight / viewportHeight;
				float deviceWidth = stageWidth * toViewportSpace;
				float lengthen = (viewportWidth - deviceWidth) * toStageSpace;
				width = stageWidth + lengthen;
				height = stageHeight;
				gutterWidth = lengthen / 2;
				gutterHeight = 0;
			} else {
				float toViewportSpace = viewportWidth / stageWidth;
				float toStageSpace = stageWidth / viewportWidth;
				float deviceHeight = stageHeight * toViewportSpace;
				float lengthen = (viewportHeight - deviceHeight) * toStageSpace;
				height = stageHeight + lengthen;
				width = stageWidth;
				gutterWidth = 0;
				gutterHeight = lengthen / 2;
			}
		} else {
			width = stageWidth;
			height = stageHeight;
			gutterWidth = 0;
			gutterHeight = 0;
		}

		float centerX = width / 2;
		float centerY = height / 2;
		camera.position.set(centerX, centerY, 0);
		camera.viewportWidth = width;
		camera.viewportHeight = height;
	}

	public static void draw () {
		camera.update();
		if (!root.isVisible()) return;
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		root.draw(batch, 1);
		batch.end();
	}

	/** Calls {@link #act(float)} with {@link Graphics#getDeltaTime()}. */
	public static void act () {
		act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	}

	/** Calls the {@link Actor#act(float)} method on each actor in the stage. Typically called each frame. This method also fires
	 * enter and exit events.
	 * @param delta Time in seconds since the last frame. */
	public static void act (float delta) {
		// Update over actors. Done in act() because actors may change position, which can fire enter/exit without an input event.
		for (int pointer = 0, n = pointerOverActors.length; pointer < n; pointer++) {
			Actor overLast = pointerOverActors[pointer];
			// Check if pointer is gone.
			if (!pointerTouched[pointer]) {
				if (overLast != null) {
					pointerOverActors[pointer] = null;
					screenToStageCoordinates(stageCoords.set(pointerScreenX[pointer], pointerScreenY[pointer]));
					// Exit over last.
					InputEvent event = Pools.obtain(InputEvent.class);
					event.setType(InputEvent.Type.exit);
					event.setStageX(stageCoords.x);
					event.setStageY(stageCoords.y);
					event.setRelatedActor(overLast);
					event.setPointer(pointer);
					overLast.fire(event);
					Pools.free(event);
				}
				continue;
			}
			// Update over actor for the pointer.
			pointerOverActors[pointer] = fireEnterAndExit(overLast, pointerScreenX[pointer], pointerScreenY[pointer], pointer);
		}
		// Update over actor for the mouse on the desktop.
		ApplicationType type = Gdx.app.getType();
		if (type == ApplicationType.Desktop || type == ApplicationType.Applet || type == ApplicationType.WebGL)
			mouseOverActor = fireEnterAndExit(mouseOverActor, mouseScreenX, mouseScreenY, -1);

		root.act(delta);
	}

	private static Actor fireEnterAndExit (Actor overLast, int screenX, int screenY, int pointer) {
		// Find the actor under the point.
		screenToStageCoordinates(stageCoords.set(screenX, screenY));
		Actor over = hit(stageCoords.x, stageCoords.y, true);
		if (over == overLast) return overLast;

		InputEvent event = Pools.obtain(InputEvent.class);
		event.setStageX(stageCoords.x);
		event.setStageY(stageCoords.y);
		event.setPointer(pointer);
		// Exit overLast.
		if (overLast != null) {
			event.setType(InputEvent.Type.exit);
			event.setRelatedActor(over);
			overLast.fire(event);
		}
		// Enter over.
		if (over != null) {
			event.setType(InputEvent.Type.enter);
			event.setRelatedActor(overLast);
			over.fire(event);
		}
		Pools.free(event);
		return over;
	}
	
	public InputAdapter inputAdapter = new InputAdapter(){
		/** Applies a touch down event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the event. */
		public boolean touchDown (int screenX, int screenY, int pointer, int button) {
			if (screenX < viewportX || screenX >= viewportX + viewportWidth) return false;
			if (screenY < viewportY || screenY >= viewportY + viewportHeight) return false;

			pointerTouched[pointer] = true;
			pointerScreenX[pointer] = screenX;
			pointerScreenY[pointer] = screenY;

			screenToStageCoordinates(stageCoords.set(screenX, screenY));

			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(Type.touchDown);
			event.setStageX(stageCoords.x);
			event.setStageY(stageCoords.y);
			event.setPointer(pointer);
			event.setButton(button);

			Actor target = hit(stageCoords.x, stageCoords.y, true);
			if (target == null) target = root;

			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a touch moved event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the event.
		 * Only {@link InputListener listeners} that returned true for touchDown will receive this event. */
		public boolean touchDragged (int screenX, int screenY, int pointer) {
			pointerScreenX[pointer] = screenX;
			pointerScreenY[pointer] = screenY;
			mouseScreenX = screenX;
			mouseScreenY = screenY;

			if (touchFocuses.size == 0) return false;

			screenToStageCoordinates(stageCoords.set(screenX, screenY));

			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(Type.touchDragged);
			event.setStageX(stageCoords.x);
			event.setStageY(stageCoords.y);
			event.setPointer(pointer);

			SnapshotArray<TouchFocus> touchFocuses = Stage.touchFocuses;
			TouchFocus[] focuses = touchFocuses.begin();
			for (int i = 0, n = touchFocuses.size; i < n; i++) {
				TouchFocus focus = focuses[i];
				if (focus.pointer != pointer) continue;
				event.setTarget(focus.target);
				event.setListenerActor(focus.listenerActor);
				if (focus.listener.handle(event)) event.handle();
			}
			touchFocuses.end();

			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a touch up event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the event.
		 * Only {@link InputListener listeners} that returned true for touchDown will receive this event. */
		public boolean touchUp (int screenX, int screenY, int pointer, int button) {
			pointerTouched[pointer] = false;
			pointerScreenX[pointer] = screenX;
			pointerScreenY[pointer] = screenY;

			if (touchFocuses.size == 0) return false;

			screenToStageCoordinates(stageCoords.set(screenX, screenY));

			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(Type.touchUp);
			event.setStageX(stageCoords.x);
			event.setStageY(stageCoords.y);
			event.setPointer(pointer);
			event.setButton(button);

			SnapshotArray<TouchFocus> touchFocuses = Stage.touchFocuses;
			TouchFocus[] focuses = touchFocuses.begin();
			for (int i = 0, n = touchFocuses.size; i < n; i++) {
				TouchFocus focus = focuses[i];
				if (focus.pointer != pointer || focus.button != button) continue;
				if (!touchFocuses.removeValue(focus, true)) continue; // Touch focus already gone.
				event.setTarget(focus.target);
				event.setListenerActor(focus.listenerActor);
				if (focus.listener.handle(event)) event.handle();
				Pools.free(focus);
			}
			touchFocuses.end();

			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a mouse moved event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the event.
		 * This event only occurs on the desktop. */
		public boolean mouseMoved (int screenX, int screenY) {
			if (screenX < viewportX || screenX >= viewportX + viewportWidth) return false;
			if (screenY < viewportY || screenY >= viewportY + viewportHeight) return false;

			mouseScreenX = screenX;
			mouseScreenY = screenY;

			screenToStageCoordinates(stageCoords.set(screenX, screenY));

			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(Type.mouseMoved);
			event.setStageX(stageCoords.x);
			event.setStageY(stageCoords.y);

			Actor target = hit(stageCoords.x, stageCoords.y, true);
			if (target == null) target = root;

			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a mouse scroll event to the stage and returns true if an actor in the scene {@link Event#handle() handled} the
		 * event. This event only occurs on the desktop. */
		public boolean scrolled (int amount) {
			Actor target = scrollFocus == null ? root : scrollFocus;

			screenToStageCoordinates(stageCoords.set(mouseScreenX, mouseScreenY));

			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(InputEvent.Type.scrolled);
			event.setScrollAmount(amount);
			event.setStageX(stageCoords.x);
			event.setStageY(stageCoords.y);
			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a key down event to the actor that has {@link Stage#setKeyboardFocus(Actor) keyboard focus}, if any, and returns
		 * true if the event was {@link Event#handle() handled}. */
		public boolean keyDown (int keyCode) {
			Actor target = keyboardFocus == null ? root : keyboardFocus;
			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(InputEvent.Type.keyDown);
			event.setKeyCode(keyCode);
			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a key up event to the actor that has {@link Stage#setKeyboardFocus(Actor) keyboard focus}, if any, and returns true
		 * if the event was {@link Event#handle() handled}. */
		public boolean keyUp (int keyCode) {
			Actor target = keyboardFocus == null ? root : keyboardFocus;
			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(InputEvent.Type.keyUp);
			event.setKeyCode(keyCode);
			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}

		/** Applies a key typed event to the actor that has {@link Stage#setKeyboardFocus(Actor) keyboard focus}, if any, and returns
		 * true if the event was {@link Event#handle() handled}. */
		public boolean keyTyped (char character) {
			Actor target = keyboardFocus == null ? root : keyboardFocus;
			InputEvent event = Pools.obtain(InputEvent.class);
			event.setType(InputEvent.Type.keyTyped);
			event.setCharacter(character);
			target.fire(event);
			boolean handled = event.isHandled();
			Pools.free(event);
			return handled;
		}
	};

	

	/** Adds the listener to be notified for all touchDragged and touchUp events for the specified pointer and button. The actor
	 * will be used as the {@link Event#getListenerActor() listener actor} and {@link Event#getTarget() target}. */
	public static void addTouchFocus (EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
		TouchFocus focus = Pools.obtain(TouchFocus.class);
		focus.listenerActor = listenerActor;
		focus.target = target;
		focus.listener = listener;
		focus.pointer = pointer;
		focus.button = button;
		touchFocuses.add(focus);
	}

	/** Removes the listener from being notified for all touchDragged and touchUp events for the specified pointer and button. Note
	 * the listener may never receive a touchUp event if this method is used. */
	public static void removeTouchFocus (EventListener listener, Actor listenerActor, Actor target, int pointer, int button) {
		SnapshotArray<TouchFocus> touchFocuses = Stage.touchFocuses;
		for (int i = touchFocuses.size - 1; i >= 0; i--) {
			TouchFocus focus = touchFocuses.get(i);
			if (focus.listener == listener && focus.listenerActor == listenerActor && focus.target == target
				&& focus.pointer == pointer && focus.button == button) {
				touchFocuses.removeIndex(i);
				Pools.free(focus);
			}
		}
	}

	/** Sends a touchUp event to all listeners that are registered to receive touchDragged and touchUp events and removes their
	 * touch focus. This method removes all touch focus listeners, but sends a touchUp event so that the state of the listeners
	 * remains consistent (listeners typically expect to receive touchUp eventually). The location of the touchUp is
	 * {@link Integer#MIN_VALUE}. Listeners can use {@link InputEvent#isTouchFocusCancel()} to ignore this event if needed. */
	public static void cancelTouchFocus () {
		cancelTouchFocus(null, null);
	}

	/** Cancels touch focus for all listeners except the specified listener.
	 * @see #cancelTouchFocus() */
	public static void cancelTouchFocus (EventListener listener, Actor actor) {
		InputEvent event = Pools.obtain(InputEvent.class);
		event.setType(InputEvent.Type.touchUp);
		event.setStageX(Integer.MIN_VALUE);
		event.setStageY(Integer.MIN_VALUE);

		// Cancel all current touch focuses except for the specified listener, allowing for concurrent modification, and never
		// cancel the same focus twice.
		SnapshotArray<TouchFocus> touchFocuses = Stage.touchFocuses;
		TouchFocus[] items = touchFocuses.begin();
		for (int i = 0, n = touchFocuses.size; i < n; i++) {
			TouchFocus focus = items[i];
			if (focus.listener == listener && focus.listenerActor == actor) continue;
			if (!touchFocuses.removeValue(focus, true)) continue; // Touch focus already gone.
			event.setTarget(focus.target);
			event.setListenerActor(focus.listenerActor);
			event.setPointer(focus.pointer);
			event.setButton(focus.button);
			focus.listener.handle(event);
			// Cannot return TouchFocus to pool, as it may still be in use (eg if cancelTouchFocus is called from touchDragged).
		}
		touchFocuses.end();

		Pools.free(event);
	}

	/** Adds an actor to the root of the stage.
	 * @see Group#addActor(Actor)
	 * @see Actor#remove() */
	public static void addActor (Actor actor) {
		root.addActor(actor);
	}

	/** Adds an action to the root of the stage.
	 * @see Group#addAction(Action) */
	public static void addAction (Action action) {
		root.addAction(action);
	}

	/** Returns the root's child actors.
	 * @see Group#getChildren() */
	public static Array<Actor> getActors () {
		return root.getChildren();
	}

	/** Adds a listener to the root.
	 * @see Actor#addListener(EventListener) */
	public static boolean addListener (EventListener listener) {
		return root.addListener(listener);
	}

	/** Removes a listener from the root.
	 * @see Actor#removeListener(EventListener) */
	public static boolean removeListener (EventListener listener) {
		return root.removeListener(listener);
	}

	/** Adds a capture listener to the root.
	 * @see Actor#addCaptureListener(EventListener) */
	public static boolean addCaptureListener (EventListener listener) {
		return root.addCaptureListener(listener);
	}

	/** Removes a listener from the root.
	 * @see Actor#removeCaptureListener(EventListener) */
	public static boolean removeCaptureListener (EventListener listener) {
		return root.removeCaptureListener(listener);
	}
	
	public static void removeActor(Actor actor){
		root.removeActor(actor);
	}

	/** Removes the root's children, actions, and listeners. */
	public static void clear () {
		unfocusAll();
		root.clear();
	}

	/** Removes the touch, keyboard, and scroll focused actors. */
	public static void unfocusAll () {
		scrollFocus = null;
		keyboardFocus = null;
		cancelTouchFocus();
	}

	/** Removes the touch, keyboard, and scroll focus for the specified actor and any descendants. */
	public static void unfocus (Actor actor) {
		if (scrollFocus != null && scrollFocus.isDescendantOf(actor)) scrollFocus = null;
		if (keyboardFocus != null && keyboardFocus.isDescendantOf(actor)) keyboardFocus = null;
	}

	/** Sets the actor that will receive key events.
	 * @param actor May be null. */
	public static void setKeyboardFocus (Actor actor) {
		if (keyboardFocus == actor) return;
		FocusEvent event = Pools.obtain(FocusEvent.class);
		event.setType(FocusEvent.Type.keyboard);
		Actor oldKeyboardFocus = keyboardFocus;
		if (oldKeyboardFocus != null) {
			event.setFocused(false);
			event.setRelatedActor(actor);
			oldKeyboardFocus.fire(event);
		}
		if (!event.isCancelled()) {
			keyboardFocus = actor;
			if (actor != null) {
				event.setFocused(true);
				event.setRelatedActor(oldKeyboardFocus);
				actor.fire(event);
				if (event.isCancelled()) setKeyboardFocus(oldKeyboardFocus);
			}
		}
		Pools.free(event);
	}

	/** Gets the actor that will receive key events.
	 * @return May be null. */
	public static Actor getKeyboardFocus () {
		return keyboardFocus;
	}

	/** Sets the actor that will receive scroll events.
	 * @param actor May be null. */
	public static void setScrollFocus (Actor actor) {
		if (scrollFocus == actor) return;
		FocusEvent event = Pools.obtain(FocusEvent.class);
		event.setType(FocusEvent.Type.scroll);
		Actor oldScrollFocus = keyboardFocus;
		if (oldScrollFocus != null) {
			event.setFocused(false);
			event.setRelatedActor(actor);
			oldScrollFocus.fire(event);
		}
		if (!event.isCancelled()) {
			scrollFocus = actor;
			if (actor != null) {
				event.setFocused(true);
				event.setRelatedActor(oldScrollFocus);
				actor.fire(event);
				if (event.isCancelled()) setScrollFocus(oldScrollFocus);
			}
		}
		Pools.free(event);
	}

	/** Gets the actor that will receive scroll events.
	 * @return May be null. */
	public static Actor getScrollFocus () {
		return scrollFocus;
	}



	/** Returns the {@link Actor} at the specified location in stage coordinates. Hit testing is performed in the order the actors
	 * were inserted into the stage, last inserted actors being tested first. To get stage coordinates from screen coordinates, use
	 * {@link #screenToStageCoordinates(Vector2)}.
	 * @param touchable If true, the hit detection will respect the {@link Actor#setTouchable(Touchable) touchability}.
	 * @return May be null if no actor was hit. */
	public static Actor hit (float stageX, float stageY, boolean touchable) {
		root.parentToLocalCoordinates(actorCoords.set(stageX, stageY));
		return root.hit(actorCoords.x, actorCoords.y, touchable);
	}

	/** Transforms the screen coordinates to stage coordinates.
	 * @param screenCoords Input screen coordinates and output for resulting stage coordinates. */
	public static Vector2 screenToStageCoordinates (Vector2 screenCoords) {
		camera.unproject(cameraCoords.set(screenCoords.x, screenCoords.y, 0), viewportX, viewportY, viewportWidth, viewportHeight);
		screenCoords.x = cameraCoords.x;
		screenCoords.y = cameraCoords.y;
		return screenCoords;
	}

	/** Transforms the stage coordinates to screen coordinates.
	 * @param stageCoords Input stage coordinates and output for resulting screen coordinates. */
	public static Vector2 stageToScreenCoordinates (Vector2 stageCoords) {
		camera.project(cameraCoords.set(stageCoords.x, stageCoords.y, 0), viewportX, viewportY, viewportWidth, viewportHeight);
		stageCoords.x = cameraCoords.x;
		stageCoords.y = viewportHeight - cameraCoords.y;
		return stageCoords;
	}

	/** Transforms the coordinates to screen coordinates. The coordinates can be anywhere in the stage since the transform matrix
	 * describes how to convert them. The transform matrix is typically obtained from {@link Batch#getTransformMatrix()} during
	 * {@link Actor#draw(Batch, float)}.
	 * @see Actor#localToStageCoordinates(Vector2) */
	public static Vector2 toScreenCoordinates (Vector2 coords, Matrix4 transformMatrix) {
		ScissorStack.toWindowCoordinates(camera, transformMatrix, coords);
		return coords;
	}

	public static void calculateScissors (Rectangle area, Rectangle scissor) {
		ScissorStack.calculateScissors(camera, viewportX, viewportY, viewportWidth, viewportHeight, batch.getTransformMatrix(),
			area, scissor);
	}

	/** Internal class for managing touch focus. Public only for GWT.
	 * @author Nathan Sweet */
	public static final class TouchFocus implements Poolable {
		EventListener listener;
		Actor listenerActor, target;
		int pointer, button;

		public void reset () {
			listenerActor = null;
			listener = null;
		}
	}
}