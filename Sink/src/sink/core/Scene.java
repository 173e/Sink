/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import static sink.core.Asset.$tex;

/** The base class for creating Scenes
 * <p>
 * Use this class to to create screens or menus for your game. Just extend this class and override the
 * {@link #init} method all other things are done automatically like clearing the stage and populating it with the
 * actors of this group and use {@link #update} method which is called in the main render method for updating your
 * scene logic such as splash scene, timers, delays etc...
 * It also contains a Table which can be used as a box layout for ui components.
 * A scene can be set using {@link SceneManager.setCurrentScene}
 * <p>
 * @author pyros2097 */


public abstract class Scene extends Group {
	protected Table grid;
	public float xoffset, yoffset; // can be used for manual placing of widgets
	public float xcenter, ycenter;
	protected Image imgbg;
	protected boolean bgSet = false; // mutex
	
	protected void addActor(Actor a, float x, float y){
		a.setPosition(x, y);
		addActor(a);
	}
	
	public void setBackground(String texName) {
		if($tex(texName) != null){
			Drawable tBg = new TextureRegionDrawable($tex(texName));
			imgbg = new Image(tBg, Scaling.stretch);
			imgbg.setFillParent(true);
			if(!bgSet){
				Stage.addActor(imgbg);
				bgSet = true;
			}
			log("SCREEN BG IMAGE SET");
		}
	}
	
	public void removeBackground() {
		if(bgSet){
			Stage.removeActor(imgbg);
			bgSet = false;
		}
		log("SCREEN BG IMAGE REMOVED");
	}
	
	public static void log(String log) {
		if(Config.loggingEnabled){
			Gdx.app.log("", log);
			if(SceneManager.logPane != null && Config.loggerVisible)
				SceneManager.logPane.update(log);
		}
	}
	protected abstract void init();
	protected abstract void update();
}
