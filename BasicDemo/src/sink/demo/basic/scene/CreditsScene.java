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
import sink.core.Sink;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class CreditsScene extends Scene{

	@Override
	public void onInit(){
		setBackground("title");
		Table grid = new Table();
		grid.setSize(Config.targetWidth, Config.targetHeight);
		grid.setFillParent(true);
		grid.setPosition(0, 0);
		grid.top().left();
		grid.center();
		grid.setPosition(0, -250);
 		grid.addAction(Actions.moveTo(0,  110, 2.0f));
 		grid.center();
 		/*	________________________
		 * |_______Credits__________|
		 * |		  |				|
		 * |		  |				|
		 * |		  |				|
		 * |__________|_____________|
		 * |_________Back___________|
		*/
		TextButton title = new TextButton("Credits", Asset.skin);
		TextButton back = new TextButton("Back", Asset.skin);
		back.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Sink.setScene("menu");
 			}
		});
		grid.add(title).size(200, 75);
		grid.row();
		grid.add(back).size(200, 75).center();
		addActor(grid);
	}
}
