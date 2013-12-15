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

import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;

public abstract class MapActor extends SceneActor {
	private int row;
	private int col;
	protected int tileSize;

	// When tiles coords row and column are directly specified
	public MapActor(int row, int col, int tileSize){
		this.row = row;
		this.col = col;
		this.tileSize = tileSize;
		setSize(tileSize, tileSize); // All map units are 24x24
		super.setPosition(row*tileSize, col*tileSize);
	}
	
	
	public void setPosition(int row, int col){
		this.row = row;
		this.col = col;
		super.setPosition(row*tileSize, col*tileSize);
	}
	
	@Override
	public void setPosition(float x, float y){
		this.row = (int)x/tileSize;
		this.col =(int)y/tileSize;
		super.setPosition(x, y);
	}
	
	public int getRow(){
		return row;
	}
	
	public int getCol(){
		return col;
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
