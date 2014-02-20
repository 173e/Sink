package sink.studio.panel;

import sink.core.Asset;
import sink.core.Scene;
import sink.core.Sink;
import sink.studio.bar.RightSideBar;
import sink.studio.bar.StatusBar;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class IntroScene extends Scene {
	
	boolean active = false;
	Vector2 mouse = new Vector2();
	Vector2 vec = new Vector2();
	
	static Actor selectedActor = null;
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	
	final ClickListener clicked = new ClickListener(){
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			StatusBar.updateXY(x, y);
			mouse.set(x, y);
			Actor acc = IntroScene.this.hit(x, y, true);
			if(acc != null && acc != IntroScene.this){			
				selectedActor = acc;
				RightSideBar.selectActor(acc.getName());
				StatusBar.updateSelected(acc.getName());
			}
			else{
				selectedActor = null;
				StatusBar.updateSelected("None");
			}
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			super.touchDragged(event, x, y, pointer);
			StatusBar.updateXY(x, y);
			if(selectedActor != null)
				selectedActor.setPosition(x, y);
		}
		
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			super.touchDown(event, x, y, pointer, button);
			mouse.set(x, y);
			StatusBar.updateXY(x, y);
			return true;
		}
			
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button){
			super.touchUp(event, x, y, pointer, button);
			mouse.set(x, y);
			StatusBar.updateXY(x, y);
		}
	};
	
	
	@Override
	public void onInit() {
		Asset.loadData();
		Texture bg1 = new Texture("model/ship.png");
		final Image imgbg1 = new Image(bg1);
		imgbg1.setFillParent(true);
		addActor(imgbg1);
		addListener(clicked);
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
			active = true;
			AssetPanel.updateAsset();
		}
		if(selectedActor != null){
			
		}
	}
	
	public void createLabel(String fontName){
		Label.LabelStyle ls = new Label.LabelStyle();
		ls.font = font(fontName);
		Label info = new Label("Text", ls);
		//Sink.stage.screenToStageCoordinates(mouse);
		addActor(info, mouse.x, mouse.y);
	}
	
	public void createTexture(String texName){
		//mouse.set(Gdx.input.getX(),Gdx.input.getY());
		//Sink.stage.screenToStageCoordinates(mouse);
		addActor(new Image(tex(texName)), mouse.x, mouse.y);
	}
	
	@Override
    public void draw(Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
		if(selectedActor != null){
			vec.set(selectedActor.getX(), selectedActor.getY());
			selectedActor.localToStageCoordinates(vec);
	        batch.end();
	        shapeRenderer.begin(ShapeType.Line);
	        shapeRenderer.setColor(Color.GREEN);
	        //shapeRenderer.rect(vec.x, vec.y, selectedActor.getWidth(), selectedActor.getHeight());
	        shapeRenderer.rect(selectedActor.getX(), selectedActor.getY(), selectedActor.getWidth(),
	        		selectedActor.getHeight());
	        shapeRenderer.end();
	        batch.begin();
		}
    }
	
	public static void setSelectedActor(String actorName){
		selectedActor = Sink.getScene().findActor(actorName);
		StatusBar.updateSelected(actorName);
	}
}

/*
URL url = Menu.class.getProtectionDomain().getCodeSource().getLocation();
File file = DataUtilities.urlToFile(url);
JarFile jar = null;
try {
    jar = new JarFile(file);
    Manifest manifest = jar.getManifest();
    Attributes attributes = manifest.getMainAttributes();
    return attributes.getValue("Built-By");
} finally {
    jar.close();
}
*/