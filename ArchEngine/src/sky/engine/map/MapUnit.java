/*******************************************************************************
 * Copyright 2013 pyros2097
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

package sky.engine.map;

import sky.engine.input.IKey;
import sky.engine.input.ITouch;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Touchable;

public abstract class MapUnit extends MapActor implements ITouch, IKey{
	public boolean isSelected = false;
	public boolean isFocussed = false;
  
  	protected Animation animation;
  	protected boolean isAnimationActive = true;
  	protected boolean isAnimationLooping = true;
	protected TextureRegion keyFrame;

	// Particle
	protected ParticleEffect particleEffect;
	protected float particlePosX = 0.0f;
	protected float particlePosY = 0.0f;
	protected boolean isParticleEffectActive;
	
	
	// Animation timer
	protected float stateTime = 0;
	protected float startTime = System.nanoTime();
	protected float secondsTime = 0;
	
	public MapUnit(Animation a, int tileSize) {
		super(0,0, tileSize);
		animation = a;
		setTouchable(Touchable.disabled);
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
		if (System.nanoTime() - startTime >= 1000000000) {
			secondsTime++;
			startTime = System.nanoTime();
		}
	}
	
	@Override
	public void draw(SpriteBatch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(getColor());
		if (isAnimationActive) {
				keyFrame = animation.getKeyFrame(stateTime, isAnimationLooping);
				batch.draw(keyFrame, getX(), getY(), getWidth(), getHeight());
		}
		drawParticleEffect(batch);
	}
	
	
	void drawParticleEffect(SpriteBatch batch){
		if (isParticleEffectActive) {
			particleEffect.draw(batch, Gdx.graphics.getDeltaTime());
			particleEffect.setPosition(getX() + particlePosX, getY()+ particlePosY);
		}
	}

	/**
	 * Set particle for this actor, centerPosition is used to center the
	 * particle on this actor sizes
	 * */
	public void setParticleEffect(ParticleEffect particleEffect,
			boolean isParticleEffectActive, boolean isStart,
			boolean centerPosition) {
		this.particleEffect = particleEffect;
		this.isParticleEffectActive = isParticleEffectActive;
		if (!centerPosition) {
			this.particleEffect.setPosition(getX(), getY());
		} else {
			particlePosX = getWidth() / 2.0f;
			particlePosY = getHeight() / 2.0f;
			this.particleEffect.setPosition(getX() + particlePosX, getY()
					+ particlePosY);
		}

		if (isStart) {
			this.particleEffect.start();
		}
	}

	/**
	 * Set particle position
	 * */
	public void setParticlePositionXY(float x, float y) {
		particlePosX = x;
		particlePosY = y;
	}

	/**
	 * Check if particle active
	 * */
	public boolean isParticleEffectActive() {
		return isParticleEffectActive;
	}

	/**
	 * Set particle active to draw or not
	 * */
	public void setParticleEffectActive(boolean isParticleEffectActive) {
		this.isParticleEffectActive = isParticleEffectActive;
	}
	
	public float getStateTime() {
		return stateTime;
	}
	public void setStateTime(float stateTime) {
		this.stateTime = stateTime;
	}
	public float getSecondsTime() {
		return secondsTime;
	}
	public void setSecondsTime(float secondsTime) {
		this.secondsTime = secondsTime;
	}
	
	public void setSelected(){
		isSelected = true;
	}
	
	public void setUnSelected(){
		isSelected = false;
	}
	
	public void setFocus(){
		isFocussed = true;
	}
	
	public void setUnFocus(){
		isFocussed = false;
	}
}