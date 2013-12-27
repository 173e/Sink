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

package sink.map;

import sink.core.SceneActor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

/** The MapActor Class
 * <p>
 * The MapActor is a SceneActor that can be used as a static tile, animated tile, animated actor or as a plain
 * actor.
 * 1.For using it as a Static Tile use:
 *  	MapActor(TextureRegion region, int row, int col, int id, int tileSize)
 * 2.For using it as a Animated Tile/Actor use:
 * 	 	MapActor(Animation a, int row, int col, int id, int tileSize)
 * 3.For using it as a plain Actor use:
 * 	 	MapActor(int row, int col, int tileSize)
 * 
 * It has many important methods like moveTo, moveBy, collides, intersects, getCenterX, getCenterY
 * <p>
 * @author pyros2097 */
public class MapActor extends SceneActor {
	private int row;
	private int col;
	private int tileSize;
	
	public TextureRegion tileImage;
	public int index;
	
  	protected Animation animation;
  	protected boolean isAnimationActive = false;
  	protected boolean isAnimationLooping = true;
	protected TextureRegion keyFrame;

	// Particle
	public ParticleEffect particleEffect;
	public float particlePosX = 0.0f;
	public float particlePosY = 0.0f;
	public boolean isParticleEffectActive;
		
	// Animation timer
	protected float stateTime = 0;

	// When tiles coords row and column are directly specified
	public MapActor(int row, int col, int tileSize){
		setTileSize(tileSize);
		setPositionRC(row, col);
		setSize(tileSize, tileSize); // All map units are 24x24
	}
	
	public MapActor(TextureRegion region, int row, int col, int id, int tileSize){
		this(row, col, tileSize);
		index = id;
		if(region != null)
			tileImage = region;
	}
	
	
	public MapActor(Animation a, int row, int col, int id, int tileSize) {
		this(row, col, tileSize);
		index = id;
		if(a != null){
			animation = a;
			isAnimationActive = true;
		}
		
	}
	
	@Override
	public void act(float delta) {
		super.act(delta);
		stateTime += delta;
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		batch.setColor(getColor());
		if(tileImage != null)
			batch.draw(tileImage, getX(), getY(), tileSize, tileSize);
		//font("normal").draw(batch, ""+index, getX(), getY()+tileSize);
		if (isAnimationActive && animation != null) {
			keyFrame = animation.getKeyFrame(stateTime, isAnimationLooping);
			batch.draw(keyFrame, getX(), getY(), tileSize, tileSize);
		}
		drawParticleEffect(batch);
	}
	
	public void drawParticleEffect(Batch batch){
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
	
	
	public void setTileSize(int tsize){
		tileSize = tsize;
	}
	
	public int getTileSize(){
		return tileSize;
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
	}
	
	public void setPositionRC(int row, int col){
		this.row = row;
		this.col = col;
		setPosition(row*tileSize, col*tileSize);
	}
	
	public void setPositionXY(float x, float y){
		setPosition(x, y);
		this.row = (int)x/tileSize;
		this.col =(int)y/tileSize;
	}
	
	public void actionMoveTo(float x, float y, float duration) {
		// Move to a specific position by time
		MoveToAction action = new MoveToAction();
		action.setPosition(x, y);
		if (duration > 0.0f) {
			action.setDuration(duration);
		}
		addAction(action);
	}

	public void actionMoveBy(float x, float y, float duration) {
		// Move towards a direction during given time (NON-STOP)
		MoveByAction action = new MoveByAction();
		action.setAmount(x, y);
		if (duration > 0.0f) {
			action.setDuration(duration);
		}
		addAction(action);
	}
	
	/**
	 * Translate actor in a direction of speed without stopping. Actor moves in
	 * constants speed set without acceleration
	 * 
	 * @param speedX
	 *            axis-X speed
	 * @param speedY
	 *            axis-Y speed
	 * @param delta
	 *            the delta time for accurate speed
	 * */
	public void translateWithoutAcc(float speedX, float speedY, float delta) {
		setPosition(getX() + (speedX * delta), getY() + (speedY * delta));
	}
	
	/**
	 * Get center x point of an object
	 * <p>
	 * EXAMPLE<br>
	 * Object's width 200, and we touched the screen in 400 in position X, and
	 * we want to center the object according to our touch position. (200 / 2 =
	 * 100 then 400 - 100), so 300 our center position
	 * 
	 * */
	public static float getCenterX(float eventX, float objectWidth) {
		return eventX - (objectWidth / 2);
	}

	/**
	 * @see getCenterX()
	 * */
	public static float getCenterY(float eventY, float objectHeight) {
		return eventY - (objectHeight / 2);
	}
	
	public boolean intersects(MapActor other){
		//$log(""+row+" : "+other.getRow());
		if(row == other.getRow() && col == other.getCol())
			return true;
		return false;
	}
	
	/**
	 * Get the rectangle of an actor from its current POSITION and SIZE
	 * */
	public Rectangle getBoundingBox() {
		float posX = this.getX();
		float posY = this.getY();
		float width = this.getWidth();
		float height = this.getHeight();
		return new Rectangle(posX, posY, width, height);
	}
	
	public boolean collides(float x, float y) {
		Rectangle rectA1 = getBoundingBox();
		Rectangle rectA2 = new Rectangle(x, y, 5, 5);
		// Check if rectangles collides
		if (Intersector.overlaps(rectA1, rectA2)) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean collides(MapActor other) {
		Rectangle rectA1 = getBoundingBox();
		Rectangle rectA2 = other.getBoundingBox();
		// Check if rectangles collides
		if (Intersector.overlaps(rectA1, rectA2)) {
			return true;
		} else {
			return false;
		}
	}
}
