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

package sink.utils;

import sink.core.Config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MathUtils {
	
	public static double getDistance(Actor a, Actor b){
		float dx, dy;
		if(a.getX() > b.getX())
			dx = a.getX() - b.getX();
		else	
			dx = b.getX() - a.getX();
		if(a.getY() > b.getY())
			dy = a.getY() - b.getY();
		else
			dy = b.getY()- a.getY();
		return Math.sqrt(dx*dx + dy*dy); 
	}
	
	static double getAngle(float inputX, float inputY, float objX, float objY) {
		 double dx = inputX - objX;
		// Minus to correct for coord re-mapping
		 double dy = inputY -  objY;
		//
		 double inRads = Math.atan2(dy, dx);

		// We need to map to coord system when 0 degree is at 3 O'clock, 270 at
		// 12 O'clock
		if (inRads < 0) {
			inRads = Math.abs(inRads);
		} else {
			inRads = 2 * Math.PI - inRads;
		}

		 double finalDegree = Math.toDegrees(inRads);
		return finalDegree;
	}
	
	public static float $posX(float x){
		return x * xRatio();
	}
	
	public static float $posY(float y){
		return y * yRatio();
	}
	
	public static float $scale(float s){
		return s * getWorldSizeRatio();
	}
	
	static boolean $isOrientationPortrait() {
		if (Gdx.graphics.getWidth() <= Gdx.graphics.getHeight()) 
			return true;
		else 
			return false;
	}
	
	/**
	 * eg: Target: 800x480
	 *	   ScreenSize: 1280x720
	 * 
	 *     1280/480 = 6/4  ---------> new width = 480*6/4 =  720;
	 * 	
	 * */
	public static float getWorldSizeRatio() {
		return Gdx.graphics.getHeight() / Config.TARGET_HEIGHT;
	}

	/**
	 * Get position X ratio to re-position actors (only for DIPactive true)
	 * EXAMPLE:
	 * if WORLD_TARGET_WIDTH = 480, and we set x position 20 for actor. We
	 * designed this for 480 WORLD_WIDTH, but a device with 960 width,
	 * WORLD_WIDTH will be 960, so new position should be 40 in this world, so
	 * position ratio is 2.0f
	 * made:changes
	 * @return 
	 * */
	public static float xRatio() {
		return Gdx.graphics.getWidth() / Config.TARGET_WIDTH;
	}

	/**
	 * Get position Y ratio to re-position actors (only for DIPactive true)
	 * <p>
	 * EXAMPLE: <br>
	 * if WORLD_TARGET_HEIGHT = 480, and we set y position 20 for actor. We
	 * designed this for 480 WORLD_HEIGHT, but a device with 960 height,
	 * WORLD_HEIGHT will be 960, so new position should be 40 in this world, so
	 * position ratio is 2.0f
	 * made:changes
	 * @return 
	 * */
	public static float yRatio() {
		return Gdx.graphics.getHeight() / Config.TARGET_HEIGHT;
	}
}
