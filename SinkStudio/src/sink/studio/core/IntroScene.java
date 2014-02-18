package sink.studio.core;


import sink.core.Asset;
import sink.core.Scene;
import sink.studio.bar.LeftSideBar;
import sink.studio.bar.RightSideBar;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

public class IntroScene extends Scene {
	
	boolean active = false;
	
	@Override
	public void onInit() {
		Asset.loadData();
		Texture bg1 = new Texture("model/ship.png");
		final Image imgbg1 = new Image(bg1);
		imgbg1.setFillParent(true);
		addActor(imgbg1);
	}
	
	@Override
	public void addActor(Actor actor){
		super.addActor(actor);
		actor.setName("Actor"+getChildren().size);
		RightSideBar.addActor(actor.getName());
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if(Asset.update() && !active ){
			Asset.setup();
			Label.LabelStyle ls = new Label.LabelStyle();
			ls.font = font("font1");
			Label info = new Label("Create a \n New Scene", ls);
			addActor(info, 0, getHeight()/2);
			active = true;
			LeftSideBar.updateAsset();
		}
	}
}