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

import sky.engine.core.Asset;
import sky.engine.core.Panel;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class CreditsPanel extends Panel{

	@Override
	public void init(){
		setBackground("title");
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
		grid.add(title).size(200, 75);
		grid.row();
		grid.add(backBtn).size(200, 75).center();
		addActor(grid);
	}
}
