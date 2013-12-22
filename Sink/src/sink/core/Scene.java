/*******************************************************************************
 * Copyright 2014 pyros2097
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
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

/** The base class for creating Scenes
 * <p>
 * Use this class to to create screens or menus for your game. Just extend this class and override the
 * {@link #onInit} method all other things are done automatically like clearing the stage and populating it with the
 * actors of this group and override act method which is called in the main render method for updating your
 * scene logic such as splash scene, timers, delays etc...
 * It also contains a Table which can be used as a box layout for ui components.
 * A scene can be set using {@link SceneManager.setScene}
 * 
 * Scene also supports transitions. You can start a scene transition by calling the method at he beginning of
 * the init method ex: transitionRightToLeft(); will transit the scene from right to left when 
 * it is shown.
 * <p>
 * @author pyros2097 */


public abstract class Scene extends Group {
	protected Table grid;
	public float xoffset, yoffset; // can be used for manual placing of widgets
	public float xcenter, ycenter;
	protected Image imgbg;
	
	public Scene(){}
	
	@Override
	public void act(float delta){
		super.act(delta);
	}
	
	protected void addActor(Actor a, float x, float y){
		a.setPosition(x, y);
		addActor(a);
	}
	
	public void setBackground(String texName) {
		if(tex(texName) != null){
			Drawable tBg = new TextureRegionDrawable(tex(texName));
			imgbg = new Image(tBg, Scaling.stretch);
			imgbg.setFillParent(true);
			Sink.stage.addActor(imgbg);
			Sink.log("SCENE BG IMAGE SET");
		}
	}
	
	public void removeBackground() {
		Sink.stage.getRoot().removeActor(imgbg);
	}
	
	public static void musicPlay(String filename){
		Asset.musicPlay(filename);
	}
	
	public static void musicPause(){
		Asset.musicPause();
	}
	
	public static void musicResume(){
		Asset.musicResume();
	}
	
	public static void musicStop(){
		Asset.musicStop();
	}
	
	public static void musicDispose(){
		Asset.musicDispose();
	}

	public static void soundPlay(String filename){
		Asset.soundPlay(filename);
	}
	
	public static void soundClick(){
		Asset.soundClick();
	}
	
	public static void soundPause(){
		Asset.soundPause();
	}
	
	public static void soundResume(){
		Asset.soundResume();
	}
	
	public static void soundStop(){
		Asset.soundStop();
	}
	
	public static void soundDispose(){
		Asset.soundDispose();
	}
	
	public BitmapFont font(String fontName){
		return Asset.font(fontName);
	}
	
	public TextureRegion tex(String textureregionName){
		return Asset.tex(textureregionName);
	}
	
	public Animation anim(String animationBaseName){
		return Asset.anim(animationBaseName);
	}
	
	public void transitionLeftToRight(){
		setPosition(-999, 0);
 		addAction(Actions.moveTo(0,  0, 0.5f));
	}
	
	public void transitionLeftToRight(float duration){
		setPosition(-999, 0);
 		addAction(Actions.moveTo(0, 0, duration));
	}
	
	public void transitionLeftToRight(Interpolation inter){
		setPosition(-999, 0);
 		addAction(Actions.moveTo(0,  0, 0.5f, inter));
	}
	
	public void transitionLeftToRight(float duration, Interpolation inter){
		setPosition(-999, 0);
 		addAction(Actions.moveTo(0, 0, duration, inter));
	}
	
	public void transitionRightToLeft(){
		setPosition(999, 0);
 		addAction(Actions.moveTo(0,  0, 0.5f));
	}
	
	public void transitionRightToLeft(float duration){
		setPosition(999, 0);
 		addAction(Actions.moveTo(0, 0, duration));
	}
	
	public void transitionRightToLeft(Interpolation inter){
		setPosition(999, 0);
 		addAction(Actions.moveTo(0,  0, 0.5f, inter));
	}
	
	public void transitionRightToLeft(float duration, Interpolation inter){
		setPosition(999, 0);
 		addAction(Actions.moveTo(0, 0, duration, inter));
	}
	
	public void transitionUpToDown(){
		setPosition(0, 999);
 		addAction(Actions.moveTo(0,  0, 0.5f));
	}
	
	public void transitionUpToDown(float duration){
		setPosition(0, 999);
 		addAction(Actions.moveTo(0, 0, duration));
	}
	
	public void transitionUpToDown(Interpolation inter){
		setPosition(0, 999);
 		addAction(Actions.moveTo(0,  0, 0.5f, inter));
	}
	
	public void transitionUpToDown(float duration, Interpolation inter){
		setPosition(0, 999);
 		addAction(Actions.moveTo(0, 0, duration, inter));
	}
	
	public void transitionDownToUp(){
		setPosition(0, -999);
 		addAction(Actions.moveTo(0,  0, 0.5f));
	}
	
	public void transitionDownToUp(float duration){
		setPosition(0, -999);
 		addAction(Actions.moveTo(0, 0, duration));
	}
	
	public void transitionDownToUp(Interpolation inter){
		setPosition(0, -999);
 		addAction(Actions.moveTo(0,  0, 0.5f, inter));
	}
	
	public void transitionDownToUp(float duration, Interpolation inter){
		setPosition(0, -999);
 		addAction(Actions.moveTo(0, 0, duration, inter));
	}
	
	public abstract void onInit();
}
