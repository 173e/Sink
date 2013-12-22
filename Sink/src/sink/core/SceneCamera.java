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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.utils.Array;

/** The Camera class with all batteries included like panning, following, smoothing
 * <p>
 * This is a singleton class which can be used for panning the camera using mouse, keyboard, drag.. etc.
 * It can also automatically follow a actor by using {@link #followActor}
 * <p>
 * @author pyros2097 */

public class SceneCamera extends OrthographicCamera {
	private float duration, time;
	private Interpolation interpolation;
    private boolean complete;
    
    /* MoveBy variables */
    private float lastPercent;
    private float panSpeedX, panSpeedY;
    private Actor actor;
    
    public float followSpeed = 3; 
    public float followTopOffset = 60;
    public float followLeftOffset = 10;
    public float followBotOffset = 70;
    public float followRightOffset = 10;
    
    private final Vector3 mousePos = new Vector3();
    public float panSpeed = 5f;
    private final float panXLeftOffset = 100;
	private final float panXRightOffset = Config.SCREEN_WIDTH - 100;
	private final float panYUpOffset = 70;
	private final float panYDownOffset = Config.SCREEN_HEIGHT - 70;
	
	public final float camOffsetX = 160f;
	public final float camOffsetYTop = 110f;
	public final float camOffsetYBot = 65f;
	public float mapOffsetX = 0;
	public float mapOffsetY = 0;
	
	private final Vector3 curr = new Vector3();
	private final Vector3 last = new Vector3(-1, -1, -1);
	private final Vector3 delta = new Vector3();
	
	private float deltaCamX = 0;
	private float deltaCamY = 0;
	private static Actor followedActor;
	
	//private boolean hudPanning = false;
	private Array<Actor> hudActors = new Array<Actor>();
	
	SceneCamera(){
		Sink.stage.addListener(touchInput);
	}
	
	/* If you want to make any elements/actors to move along with the camera like HUD's add them using
	 * this method
	 */
	public void registerSceneHud(Actor actor){
		hudActors.add(actor);
	}
	
	/* If you want to stop any elements/actors from moving along with the camera like HUD's you can stop them
	 * by using this method
	 */
	public void unregisterSceneHud(Actor actor){
		hudActors.removeValue(actor, true);
	}
	 
	public void clearSceneHud(){
		hudActors.clear();
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
    
    @Override
    public void update () {
    	super.update();
    	float delta = Gdx.graphics.getDeltaTime();
    	if(!complete)
    		moveByAction(delta);
    	if(hasControl){
			if(Config.usePan) Sink.camera.panCameraWithMouse();
			if(Config.useKeyboard) Sink.camera.panCameraWithKeyboard();
		}
		if(followedActor != null)
			follow();
    }
    
    void moveByAction(float delta){
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

    void updateMoveBy (float percent) {
        updateRelativeMoveBy(percent - lastPercent);
        lastPercent = percent;
    }

    void updateRelativeMoveBy (float percentDelta){
    	this.translate(panSpeedX * percentDelta, panSpeedY * percentDelta, 0);
    	if(actor != null) actor.translate(panSpeedX * percentDelta, panSpeedY * percentDelta);
    }
    
    /** Skips to the end of the transition. */
    void finish () {
        time = duration;
    }

    void restart () {
        time = 0;
        complete = false;
    }

    void reset () {
        interpolation = null;
    }
    
    void end () {
    	reset();
    }
    
/***********************************************************************************************************
* 					Controller Related Functions												   	       *
************************************************************************************************************/	
    boolean hasControl = false;
    	
    public void enablePanning(){
    	if(hasControl)
    		return;
    	hasControl = true;
    }
    	
    public void disablePanning(){
    	if(!hasControl)
    		return;
    	hasControl = false;
    }
    	
    public void touchPad(float xPercent, float yPercent){
    }
    
	public void followActor(Actor actor){
	    followedActor = actor;
	}
    
    private void follow(){
    	if(position.x < followedActor.getX() - followLeftOffset) translateX(followSpeed);
		else if(position.x > followedActor.getX() + followRightOffset) translateX(-followSpeed);
		else if(position.y < followedActor.getY() - followBotOffset) translateY(followSpeed);
		else if(position.y > followedActor.getY() - followTopOffset) translateY(-followSpeed);
		//else actor = null;
    }
    
    public void panCameraWithMouse(){
    	mousePos.x = Gdx.input.getX();
    	mousePos.y = Gdx.input.getY();
    	if(mousePos.x > panXRightOffset && position.x < mapOffsetX - 5) translateX(panSpeed);
    	else if(mousePos.x < panXLeftOffset && position.x > camOffsetX +5)  translateX(-panSpeed);
    	else if(mousePos.y < panYUpOffset && position.y < mapOffsetY -5) translateY(panSpeed);
    	else if(mousePos.y > panYDownOffset && position.y > camOffsetYBot +5) translateY(-panSpeed);
    }
    	
    public void panCameraWithKeyboard(){
    	if(Gdx.input.isKeyPressed(Keys.LEFT))
    		if(position.x > camOffsetX +5)
    			translateX(-panSpeed);
    	else if(Gdx.input.isKeyPressed(Keys.RIGHT))
    		if(position.x < mapOffsetX - 5)
    			translateX(panSpeed);
    	else if(Gdx.input.isKeyPressed(Keys.UP))
    		if(position.y < mapOffsetY -5)
    			translateY(panSpeed);
    	else if(Gdx.input.isKeyPressed(Keys.DOWN))
    		if(position.y > camOffsetYBot +5)
    			translateY(-panSpeed);
    }
    	
    public void dragCam(int x, int y){
    	unproject(curr.set(x, y, 0));
    	if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
    		unproject(delta.set(last.x, last.y, 0));
    		delta.sub(curr);
    		deltaCamX = delta.x + position.x;
    		deltaCamY = delta.y + position.y;
    		if(deltaCamX > camOffsetX && deltaCamX < mapOffsetX)
    			translateX(delta.x);
    		if(deltaCamY > camOffsetYBot && deltaCamY < mapOffsetY)
    			translateY(delta.y);		
    	}
    	last.set(x, y, 0);
    }
    
    final InputListener touchInput = new InputListener(){
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
	
	private void translateX(float x){
		//if(hudPanning)
			for(Actor actor: hudActors) actor.translate(x, 0);
		translate(x, 0);
	}
	
	private void translateY(float y){
		//if(hudPanning)
			for(Actor actor: hudActors) actor.translate(0, y);
		translate(0, y);
	}
}
