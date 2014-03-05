/*******************************************************************************
 * Copyright 2013 pyros2097
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * sa
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import sink.core.Asset;
import sink.core.Sink;

/** A Basic SplashScreen/SplashMenu/SplashPanel for the Game
 * <p>
 * The SplashPanel loads all the splash assets to be shown directly from the file system and displays them 
 * without using the awesome AssetManager class, so all the loaded assets has be disposed manually because 
 * mostly we are going to use them only once throughout the game.
 * <p>
 * @author pyros2097 */
public class Splash implements Disposable {
	Texture bg1, bg2;
	LoadingText loadingText;
	
	public Splash() {
		Asset.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		bg1 = new Texture("splash/libgdx.png");
		bg2 = new  Texture("splash/libgdx.png");
		
		final Image imgbg1 = new Image(bg1);
		final Image imgbg2 = new Image(bg1);
		imgbg1.setFillParent(true);
		imgbg2.setFillParent(true);
		loadingText = new LoadingText();
		Sink.addActor(imgbg1);
		Sink.addActor(loadingText, Sink.targetWidth/2, 23);
	}

	@Override
	public void dispose() {
		bg1.dispose();
		bg2.dispose();
	}
}

class LoadingText extends Group {
	String text;
	Label loading;
    float stateTime;
    float startTime = System.nanoTime();
    int textlen = 7;

    public LoadingText() {
    	text = "Loading";
    	loading = new Label(text, Asset.skin);
    	loading.setColor(Color.BLACK);
    	loading.setFontScale(3f);
    	addActor(loading);
    }
    	
    @Override
    public void act(float delta) {
    	super.act(delta);
    	if(System.nanoTime() - startTime >= 300000000)
    	{
    		if(text.length() == textlen)
    		{
    			if(textlen == 11)
    			{
    				text = "Loading";
    				loading.setText(text);
    				textlen = 7;
    				return;
    			}
    			loading.setText(text);
    			text+= ".";
    			textlen += 1;
    		}
    		startTime = System.nanoTime();
    	}
       stateTime += delta;  
    }
}