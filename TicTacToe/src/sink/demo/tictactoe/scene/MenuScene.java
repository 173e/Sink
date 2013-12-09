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
package sink.demo.tictactoe.scene;

import sink.core.Asset;
import sink.core.Scene;
import sky.demo.tictactoe.GameMode;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MenuScene extends Scene{
	@Override
	public void init(){
		setBackground("title");
		grid.setPosition(-999, 0);
 		grid.addAction(Actions.moveTo(0,  0, 0.5f));
 		grid.center();
 		/*	________________________
		 * |_______Warsong__________|
		 * |________Start___________|
		 * |_______Options__________|
		 * |_______Credits__________|
		 * |_________Exit___________|
		*/
 		TextButton btn1 = new TextButton("Play VS Computer", Asset.skin);
		TextButton btn3 = new TextButton("SinglePlayer", Asset.skin);
		TextButton btn4 = new TextButton("Exit", Asset.skin);
		grid.add(btn1).size(200, 75).padTop(15).row();
		grid.add(btn3).size(200, 75).padTop(15).row();
		if(Gdx.app.getType() != ApplicationType.Android || Gdx.app.getType() != ApplicationType.iOS)
			grid.add(btn4).size(200, 75).padTop(15).row();
		btn1.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameScene.setMode(GameMode.SINGLE_PLAYER_VS_COMPUTER);
				GameScene.startLevel();
 			}
 		});
		btn3.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameScene.setMode(GameMode.SINGLE_PLAYER);
				GameScene.startLevel();
 			}
 		});
		btn4.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Gdx.app.exit();
 			}
 		});
		/*backBtn.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				new MenuPanel();
 			}
 		});*/
		addActor(grid);
	}

	@Override
	protected void update() {
		
	}
}
