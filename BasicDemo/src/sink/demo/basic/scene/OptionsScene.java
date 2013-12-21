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
package sink.demo.basic.scene;

import sink.core.Asset;
import sink.core.Config;
import sink.core.Scene;
import sink.core.SceneManager;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class OptionsScene extends Scene{
	@Override
	public void onInit(){
		setBackground("title");
		setPosition(0, -999);
		addAction(Actions.moveTo(0,  0, 0.3f));
		xoffset = 100;
		yoffset = Config.TARGET_HEIGHT - 80;

		/*	________________________
		 * |_______Options__________|
		 * |		  |				|
		 * |		  |				|
		 * |		  |				|
		 * |__________|_____________|
		 * |_________Back___________|
		*/
		TextButton title = new TextButton("Options", Asset.skin);
		title.setSize(200, 75);
		TextButton back = new TextButton("Back", Asset.skin);
		back.setSize(200, 75);
		back.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				SceneManager.setScene("menu");
 			}
 		});
		
		CheckBox soundbtn = new CheckBox("  Sound ", Asset.skin);
		soundbtn.setChecked(Config.isSound);
		
		CheckBox musicbtn = new CheckBox("  Music ", Asset.skin);
		musicbtn.setChecked(Config.isMusic);
		
		CheckBox battbtn = new CheckBox("  Battle ", Asset.skin);
		battbtn.setChecked(Config.isBattleEnabled());
		
		CheckBox semibtn = new CheckBox(" Semi-Auto ", Asset.skin);
		semibtn.setChecked(Config.isSemiAutomatic());
        
        
		CheckBox panbtn = new CheckBox("  PanScroll ", Asset.skin);
		panbtn.setChecked(Config.usePan);
		
		CheckBox dragbtn = new CheckBox("  DragScroll ", Asset.skin);
		dragbtn.setChecked(Config.useDrag);
		
		CheckBox keybtn = new CheckBox("  UseKeyboard ", Asset.skin);
		keybtn.setChecked(Config.useKeyboard);
        
		Slider musicSlider = new Slider(0f, 1f, 0.1f, false, Asset.skin);
		Slider soundSlider = new Slider(0f, 1f, 0.1f, false, Asset.skin);
		
        addActor(title, xcenter - title.getWidth() + 30, yoffset);
        addActor(musicbtn, xcenter - musicbtn.getWidth(), yoffset - 60);
        addActor(musicSlider, xcenter+30, yoffset - 60);
        addActor(soundbtn, xcenter - soundbtn.getWidth() ,yoffset - 90);
        addActor(soundSlider, xcenter+30, yoffset - 90);
        
        addActor(battbtn, xcenter - battbtn.getWidth(), yoffset - 120);
        addActor(panbtn, xcenter + 30, yoffset - 120);
        
        addActor(semibtn, xcenter - semibtn.getWidth(), yoffset - 150);
        addActor(dragbtn, xcenter + 30, yoffset - 150);
        addActor(dragbtn, xcenter + 30, yoffset - 180);
        
        addActor(back, xcenter - back.getWidth() + 30, 20);
	}
	
	void addLeft(Actor a){
		grid.add(a).padTop(10).expand().fill().left();
	}
	
	void addRight(Actor a){
		grid.add(a).expand().fill().right().padTop(10).row();
	}

}
