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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

import static sink.core.Asset.$tex;

/** The base class for creating easy Menus/Screens/Panels/Scenes
 * <p>
 * Use this class to to create screens or menus for your game. Just extend this class and override the
 * {@link #init} method all other things are done automatically like clearing the stage and populating it with the
 * actors of this group.
 * It also contains a Table which can be used a a box layout for layouting the gui components.
 * <p>
 * @author pyros2097 */


public abstract class Scene extends Group {
	protected Table grid;
	protected float xoffset, yoffset; // can be used for manual placing of widgets
	protected float xcenter, ycenter;
	protected Image imgbg;
	protected boolean bgSet = false; // mutex
	
	public static Label fpsLabel;
	public static LogPane logPane; 
	protected static TextButton backBtn;
	
	public Scene(){
		Stage.camera.position.set(Config.TARGET_WIDTH/2, Config.TARGET_HEIGHT/2, 0);
		Stage.clear();
		removeBackground();
		setPosition(0, 0);
		setSize(Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		setBounds(0,0,Config.TARGET_WIDTH,Config.TARGET_HEIGHT);
		grid = new Table();
		grid.setSize(Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		grid.setFillParent(true);
		grid.setPosition(0, 0);
		grid.top().left();
		xcenter = getWidth()/2;
		ycenter = getHeight()/2;
		init();
		showScene();
	}
	
	protected abstract void init();
	
	private void showScene(){
		Stage.addActor(this);
		if (fpsLabel != null && Config.fpsVisible){
			fpsLabel.setPosition(Config.TARGET_WIDTH - 80, Config.TARGET_HEIGHT - 20);
			Stage.addActor(fpsLabel);
		}
		if (logPane != null && Config.loggerVisible){
			logPane.setPosition(0, 0);
			Stage.addActor(logPane);
		}
	}
	
	protected void addActor(Actor a, float x, float y){
		a.setPosition(x, y);
		addActor(a);
	}
	
	public void setBackground(String bgname) {
		if($tex(bgname) != null){
		Drawable tBg = new TextureRegionDrawable($tex(bgname));
		imgbg = new Image(tBg, Scaling.stretch);
		imgbg.setFillParent(true);
		if(!bgSet){
			Stage.addActor(imgbg);
			bgSet = true;
		}
		log("SCREEN BG IMAGE SET");
		}
	}
	
	private void removeBackground() {
		if(bgSet){
			Stage.getRoot().removeActor(imgbg);
			bgSet = false;
		}
		log("SCREEN BG IMAGE REMOVED");
	}
	
	public static void log(String log) {
		if(Config.loggingEnabled){
			Gdx.app.log("", log);
			if(logPane != null && Config.loggerVisible)
				logPane.update(log);
		}
	}
	
	public void update(){}
}
