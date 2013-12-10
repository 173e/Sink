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

package sink.demo.basic;


import sink.core.Asset;
import sink.core.Config;
import sink.core.SceneManager;
import sink.core.Sink;




import sink.event.PauseListener;
import sink.event.ResumeListener;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public final class World extends Group implements PauseListener, ResumeListener {
	Image pauseImage;
	TextButton pauseBtn;
	boolean pauseState = false; //mutex to prevent continuous actions and hiding and showing
	
	public World() {
		setSize(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
		pauseImage = new Image(Asset.skin.getRegion("default"));
		pauseBtn = new TextButton("Pause", Asset.skin);
		pauseBtn.setPosition(300, 300);
		pauseBtn.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!pauseState)
					Sink.firePauseEvent();
				else
					Sink.fireResumeEvent();
 			}
 		});
		addActor(SceneManager.fpsLabel);
		addActor(SceneManager.logPane);
		addActor(pauseBtn);
		Sink.addListener((PauseListener)this);
		Sink.addListener((ResumeListener)this);
	}

	@Override
	public void onPause() {
		pauseBtn.setText("Resume");
		pauseImage.setFillParent(true);
		pauseImage.setTouchable(Touchable.disabled);
		pauseImage.setColor(1, 1, 1, 0);
		pauseImage.addAction(Actions.alpha(0.6f, 0.7f, Interpolation.linear));
		addActor(pauseImage);
		pauseState = true;
		Sink.camera.disablePanning();
	}

	@Override
	public void onResume() {
		pauseBtn.setText("Pause");
		Action hideAction = Actions.alpha(0f, 0.6f, Interpolation.linear);
		Action over = new Action() {
			@Override
			public boolean act(final float it) {
				removeActor(pauseImage);
				return true;
			}
		};
		SequenceAction sequence = Actions.sequence(hideAction, over);
		pauseImage.addAction(sequence);
		pauseState = false;
		Sink.camera.enablePanning();
	}
}