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

public class Utils {
	
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
		return Gdx.graphics.getHeight() / Config.targetHeight;
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
		return Gdx.graphics.getWidth() / Config.targetHeight;
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
		return Gdx.graphics.getHeight() / Config.targetHeight;
	}
	
	/*
	 * Capitalizes the First Letter of a String
	 */
	public static String capitalize(String line){
		  if(line != null && line != "")
			  return Character.toUpperCase(line.charAt(0)) + line.substring(1);
		  else
			  return "";
	}
	
	/*
	 * XXTEA Encryption and Decryption
	 */
	    private static final int delta = 0x9E3779B9;
	    private static final int MX(int sum, int y, int z, int p, int e, int[] k) {
	        return (z >>> 5 ^ y << 2) + (y >>> 3 ^ z << 4) ^ (sum ^ y) + (k[p & 3 ^ e] ^ z);
	    }
	    
	    public static final byte[] encrypt(String data, String key){
	    	return encrypt(data.getBytes(), key.getBytes());
	    }
	    
	    public static final byte[] decrypt(String data, String key){
	    	return decrypt(data.getBytes(), key.getBytes());
	    }

	    public static final byte[] encrypt(byte[] data, byte[] key) {
	        if (data.length == 0) 
	            return data;
	        return toByteArray(encrypt(toIntArray(data, true), toIntArray(key, false)), false);
	    }

	    
	    public static final byte[] decrypt(byte[] data, byte[] key) 
	    {
	        if (data.length == 0)
	            return data;
	        return toByteArray(decrypt(toIntArray(data, false), toIntArray(key, false)), true);
	    }

	    /**
	     * Encrypt data with key.
	     *
	     * @param v
	     * @param k
	     * @return
	     */
	    private static final int[] encrypt(int[] v, int[] k) {
	        int n = v.length - 1;
	        if (n < 1) {
	            return v;
	        }
	        if (k.length < 4) {
	            int[] key = new int[4];

	            System.arraycopy(k, 0, key, 0, k.length);
	            k = key;
	        }
	        int z = v[n], y = v[0], sum = 0, e;
	        int p, q = 6 + 52 / (n + 1);

	        while (q-- > 0) {
	            sum = sum + delta;
	            e = sum >>> 2 & 3;
	            for (p = 0; p < n; p++) {
	                y = v[p + 1];
	                z = v[p] += MX(sum, y, z, p, e, k);
	            }
	            y = v[0];
	            z = v[n] += MX(sum, y, z, p, e, k);
	        }
	        return v;
	    }

	    /**
	     * Decrypt data with key.
	     *
	     * @param v
	     * @param k
	     * @return
	     */
	    private static final int[] decrypt(int[] v, int[] k) 
	    {
	        int n = v.length - 1;

	        if (n < 1) {
	            return v;
	        }
	        if (k.length < 4) {
	            int[] key = new int[4];

	            System.arraycopy(k, 0, key, 0, k.length);
	            k = key;
	        }
	        int z = v[n], y = v[0], sum, e;
	        int p, q = 6 + 52 / (n + 1);

	        sum = q * delta;
	        while (sum != 0) {
	            e = sum >>> 2 & 3;
	            for (p = n; p > 0; p--) {
	                z = v[p - 1];
	                y = v[p] -= MX(sum, y, z, p, e, k);
	            }
	            z = v[n];
	            y = v[0] -= MX(sum, y, z, p, e, k);
	            sum = sum - delta;
	        }
	        return v;
	    }

	    /**
	     * Convert byte array to int array.
	     *
	     * @param data
	     * @param includeLength
	     * @return
	     */
	    private static final int[] toIntArray(byte[] data, boolean includeLength) 
	    {
	        int n = (((data.length & 3) == 0)
	                ? (data.length >>> 2)
	                : ((data.length >>> 2) + 1));
	        int[] result;

	        if (includeLength) {
	            result = new int[n + 1];
	            result[n] = data.length;
	        }
	        else {
	            result = new int[n];
	        }
	        n = data.length;
	        for (int i = 0; i < n; i++) {
	            result[i >>> 2] |= (0x000000ff & data[i]) << ((i & 3) << 3);
	        }
	        return result;
	    }

	    /**
	     * Convert int array to byte array.
	     *
	     * @param data
	     * @param includeLength
	     * @return
	     */
	    private static final byte[] toByteArray(int[] data, boolean includeLength) 
	    {
	        int n = data.length << 2;

	        if (includeLength) {
	            int m = data[data.length - 1];

	            if (m > n) {
	                return null;
	            }
	            else {
	                n = m;
	            }
	        }
	        byte[] result = new byte[n];

	        for (int i = 0; i < n; i++) {
	            result[i] = (byte) ((data[i >>> 2] >>> ((i & 3) << 3)) & 0xff);
	        }
	        return result;
	    }
}
