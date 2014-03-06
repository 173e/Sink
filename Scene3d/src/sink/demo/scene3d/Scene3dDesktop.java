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

package sink.demo.scene3d;

import sink.scene3d.Actor3d;
import sink.scene3d.Group3d;
import sink.scene3d.Stage3d;
import sink.scene3d.actions.Actions3d;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Scene3dDesktop implements ApplicationListener {
	Stage3d stage3d;
	Stage stage2d;
	Skin skin;
	ModelBuilder modelBuilder;
	CameraInputController camController;
	Model model;
	Actor3d r;
	Group3d group3d;
	Label fpsText;
	
	public static void main(String[] argc) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.audioDeviceBufferCount = 20;
		cfg.title = "Stage3d Test";
		cfg.useGL20 = false;
		cfg.width = 852;
		cfg.height = 480;
		new LwjglApplication(new Scene3dDesktop(), cfg);
	}
	
	// Must implement a better test
     
    @Override
    public void create () {
    	//2d stuff
    	stage2d = new Stage();
    	skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    	fpsText = new Label("ff", skin);
    	fpsText.setPosition(Gdx.graphics.getWidth() - 80, Gdx.graphics.getHeight()-40);
    	stage2d.addActor(fpsText);
    	
    	//3dstuff
    	stage3d = new Stage3d();
    	modelBuilder = new ModelBuilder();
    	model = modelBuilder.createBox(5f, 5f, 5f, new Material(ColorAttribute.createDiffuse(Color.WHITE)),
                Usage.Position | Usage.Normal);
    	r = new Actor3d(model, 9f, 0f, 0f);
    	camController = new CameraInputController(stage3d.getCamera());
        Gdx.input.setInputProcessor(camController);
        
    	testActor3d();
    	//testGroup3d();
    	testStage3d();
    }
    
    void testActor3d(){
    	stage3d.addActor3d(r);
        stage3d.addActor3d(new Actor3d(model));
    	//r.scale(1.5f);
    	//r.addAction3d(Actions.scaleTo(2f, 2f, 2f, 1f));
    	//r.addAction3d(Actions.scaleBy(0.3f, 0.3f, 0.3f, 1f));
    	
       // r.addAction3d(Actions.moveTo(7f, 0f, 0f, 1f));
        //r.addAction3d(Actions.moveBy(7f, 0f, 0f, 1f));
       // r.setRotation(59);
        // r.setRotation(59);
        //r.addAction3d(Actions.rotateTo(59, 1f));
        //r.addAction3d(Actions.rotateBy(59, 1f));
    }
    
    void testGroup3d(){
    	group3d = new Group3d();
    	group3d.setPosition(0f, 0f, 0f);
    	group3d.addActor(r);
    	group3d.addActor(new Actor3d(model, 7f, 0f, 0f));
    	stage3d.addActor3d(group3d);
    	//group3d.addAction3d(Actions.moveTo(-7f, 0f, 0f, 1f));
    }
    
    void testStage3d(){
    	stage3d.addAction3d(Actions3d.moveTo(-7f, 0f, 0f, 1f));
    }
 
    @Override
    public void render () {
    	//Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        stage2d.act();
    	stage2d.draw();
        stage3d.act();
    	stage3d.draw();
    	camController.update();
    	fpsText.setText("Fps: " + Gdx.graphics.getFramesPerSecond());
    }
     
    @Override
    public void dispose () {
    	stage3d.dispose();
    }

	@Override
	public void pause() {
	}

	@Override
	public void resize(int arg0, int arg1) {
	}

	@Override
	public void resume() {
	}
}