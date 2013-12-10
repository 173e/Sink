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

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelScene extends Scene{
	public final static int maxLevel = 20;
	@Override
	public void init(){
		grid.setPosition(-999, 0);
		grid.addAction(Actions.moveTo(0, 0, 0.3f));
		grid.top().left().pad(10, 10, 10, 10);
		/*	_____________________
		 * | [1] [2] [3] [4] [5] |
		 * |		  			 | 
		 * |		  			 |
		 * |		 			 |
		 * |_____________________|
		*/
		
		for(int i=0; i<maxLevel; i++){
			final int index = i;
			TextButton btn = new TextButton(""+(i+1), Asset.skin);
			if(i > Config.levels() - 0)btn.setVisible(false);
			if(i % 5 == 0)grid.row();
			grid.add(btn).size(100, 100).pad(5,0,0,0).expandX();
			btn.addListener(new ClickListener(){
	 			@Override
				public void clicked(InputEvent event, float x, float y) {
					super.clicked(event, x, y);
					GameScene.currentLevel = index;
					SceneManager.setCurrentScene("game");
	 			}
	 		});
		}
		addActor(grid);
	}
}
