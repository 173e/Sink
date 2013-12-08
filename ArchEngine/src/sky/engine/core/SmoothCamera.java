package sky.engine.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SmoothCamera extends OrthographicCamera {
	private float duration, time;
	private Interpolation interpolation;
    private boolean complete;
    
    /* MoveBy variables */
    private float lastPercent;
    private float panSpeedX, panSpeedY;
    private Actor actor;
    
    
    public void setActor(Actor actor){
    	this.actor = actor;
    }
    
    public Actor getActor(){
    	return actor;
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
    	if (!complete){
    		moveByAction(delta);
    	}
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
}
