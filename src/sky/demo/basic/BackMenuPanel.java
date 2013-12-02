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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sky.engine.core.Asset;
import sky.engine.core.Config;

public class BackMenuPanel extends Table {
	
	public BackMenuPanel(){
		setSize(Config.TARGET_WIDTH, Config.TARGET_HEIGHT);
		this.top().left();
		TextButton btn1 = new TextButton("Winning Condition", Asset.skin);
		TextButton btn2 = new TextButton("Tips", Asset.skin);
		TextButton btn3 = new TextButton("Help", Asset.skin);
		TextButton btn4 = new TextButton("End Turn", Asset.skin);
		TextButton btn5 = new TextButton("Back To MainMenu", Asset.skin);
		add(btn1).left().padTop(20).row();
		add(btn2).left().padTop(10).row();
		add(btn3).left().padTop(10).row();
		add(btn4).left().padTop(10).row();
		add(btn5).left().padTop(10).row();
		btn5.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				GameMan.back();
				new MenuPanel();
 			}
 		});
	}
}
