package sink.core;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SceneSprite extends SceneActor {
	
	private Animation animation;
	private TextureRegion keyFrame;
	
	private boolean isAnimationActive = false;
	private boolean isAnimationLooping = true;
	
	public SceneSprite(Animation animation){
		this.animation = animation;
	}
	
	public SceneSprite(Animation animation, boolean looping){
		this(animation);
		isAnimationLooping = looping;
	}
	
	public SceneSprite(Animation animation, boolean looping, boolean active){
		this(animation, looping);
		isAnimationActive = active;
	}
	
	/*@Override
	public void act(float delta){
		super.act(delta);
	}*/
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if (isAnimationActive) {
			keyFrame = animation.getKeyFrame(stateTime, isAnimationLooping);
			batch.draw(keyFrame, getX(), getY(), getWidth(), getHeight());
		}
	}

}
