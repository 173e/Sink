package sink.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SmoothCamera extends OrthographicCamera {
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
    
    public void follow(Actor actor){
    	if(position.x < actor.getX() - followLeftOffset) position.x += followSpeed;
		else if(position.x > actor.getX() + followRightOffset) position.x -= followSpeed;
		else if(position.y < actor.getY() - followBotOffset) position.y += followSpeed;
		else if(position.y > actor.getY() - followTopOffset) position.y -= followSpeed;
		else actor = null;
    }
    
	public void panCameraWithMouse(){
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		if(mousePos.x > panXRightOffset && position.x < mapOffsetX - 5) position.x += panSpeed;
		else if(mousePos.x < panXLeftOffset && position.x > camOffsetX +5)  position.x -= panSpeed;
		else if(mousePos.y < panYUpOffset && position.y < mapOffsetY -5) position.y += panSpeed;
		else if(mousePos.y > panYDownOffset && position.y > camOffsetYBot +5) position.y -= panSpeed;
	}
	
	public void panCameraWithKeyboard(){
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			if(position.x > camOffsetX +5)
				position.x -= panSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			if(position.x < mapOffsetX - 5)
				position.x += panSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.UP)){
			if(position.y < mapOffsetY -5)
				position.y += panSpeed;
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			if(position.y > camOffsetYBot +5)
				position.y -= panSpeed;
		}	
	}
	
	void dragCam(int x, int y){
		unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			deltaCamX = delta.x + position.x;
			deltaCamY = delta.y + position.y;
			if(deltaCamX > camOffsetX && deltaCamX < mapOffsetX)
				position.x += delta.x;
			if(deltaCamY > camOffsetYBot && deltaCamY < mapOffsetY)
				position.y += delta.y;		
		}
		last.set(x, y, 0);
	}
    
    
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
