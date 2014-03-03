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

package sink.core;

import sink.json.ButtonJson;
import sink.json.CheckBoxJson;
import sink.json.DialogJson;
import sink.json.ImageJson;
import sink.json.LabelJson;
import sink.json.ListJson;
import sink.json.SelectBoxJson;
import sink.json.SliderJson;
import sink.json.TableJson;
import sink.json.TextButtonJson;
import sink.json.TextFieldJson;
import sink.json.TouchpadJson;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.Scaling;

/** The base class for creating Scenes
 * <p>
 * Use this class to to create screens or menus for your game. Just extend this class and override the
 * {@link #onInit} method all other things are done automatically like clearing the stage and populating it 
 * with the actors of this group and override {@link #act} method which is called in the main render method for 
 * updating your scene logic such as splash scene, timers, delays etc...
 * <p>
 * It also contains a Table which can be used as a box layout for ui components.
 * A scene can be set using {@link Sink.setScene}
 * 
 * <p>
 * Scene also supports transitions. You can start a scene transition by calling the method at he beginning of
 * the init method ex: transitionRightToLeft(); will transit the scene from right to left when 
 * it is shown.
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
	
	public static void log(String log){
		Sink.log(log);
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
	
	public void load(String sceneName){
			FileHandle file = Gdx.files.internal("scene/"+sceneName+".json");
			String text = file.readString();
			String[] lines = text.split("\n");
			for(String line: lines){
				if(line.trim().isEmpty())
					continue;
				JsonValue jv = Sink.jsonReader.parse(line);
				switch(jv.get("class").asString()){
					case "sink.json.ImageJson":addActor(Sink.json.fromJson(ImageJson.class, line));break;
					case "sink.json.LabelJson":addActor(Sink.json.fromJson(LabelJson.class, line));break;
					case "sink.json.ButtonJson":addActor(Sink.json.fromJson(ButtonJson.class, line));break;
					case "sink.json.TextButtonJson":addActor(Sink.json.fromJson(TextButtonJson.class, line));break;	
					case "sink.json.TableJson":addActor(Sink.json.fromJson(TableJson.class, line));break;	
					case "sink.json.CheckBoxJson":addActor(Sink.json.fromJson(CheckBoxJson.class, line));break;	
					case "sink.json.SelectBoxJson":addActor(Sink.json.fromJson(SelectBoxJson.class, line));break;
					case "sink.json.ListJson":addActor(Sink.json.fromJson(ListJson.class, line));break;
					case "sink.json.SliderJson":addActor(Sink.json.fromJson(SliderJson.class, line));break;
					case "sink.json.TextFieldJson":addActor(Sink.json.fromJson(TextFieldJson.class, line));break;
					case "sink.json.DialogJson":addActor(Sink.json.fromJson(DialogJson.class, line));break;
					case "sink.json.TouchpadJson":addActor(Sink.json.fromJson(TouchpadJson.class, line));break;
				}
			}
	}
	
	public abstract void onInit();
}
