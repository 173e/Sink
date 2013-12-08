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

package sky.demo.basic;


import sink.core.Engine;
import sink.core.Scene;
import sink.map.Map;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public final class World extends Group {
	Image imagePause;
	boolean pauseState = false; //mutex to prevent continuous actions and hiding and showing
	
	public World() {
		addActor(Scene.fpsLabel);
		addActor(Scene.logPane);
		Engine.$stage.addActor(this);
	}
	
	public void showPause(){
		if(pauseState == false){
		imagePause.setFillParent(true);
		imagePause.setTouchable(Touchable.disabled);
		imagePause.setColor(1, 1, 1, 0);
		imagePause.addAction(Actions.alpha(0.6f, 0.7f, Interpolation.linear));
		addActor(imagePause);
		pauseState = true;
		Map.removeController();
		}
	}
	
	public void hidePause(){
		if(pauseState == true){
		Action hideAction = Actions.alpha(0f, 0.6f, Interpolation.linear);
		Action over = new Action() {
	          @Override
	          public boolean act(final float it) {
	        	  removeActor(imagePause);
	            return true;
	          }
	    };
	    SequenceAction sequence = Actions.sequence(hideAction, over);
		imagePause.addAction(sequence);
		pauseState = false;
		Map.addController();
		}
	}
}