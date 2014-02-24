package sink.studio.panel;

import sink.core.Asset;
import sink.core.Config;
import sink.core.Scene;
import sink.core.Sink;
import sink.studio.core.Content;
import sink.studio.core.Export;
import sink.studio.core.SinkStudio;
import sink.studio.core.StatusBar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;

public class StudioScene extends Scene {
	
	//JsonWriter jsonWriter = new JsonWriter();
	
	Json json = new Json();
	
	boolean active = false;
	Vector2 mouse = new Vector2();
	Vector2 vec = new Vector2();
	
	static Actor selectedActor = null;
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	boolean showGrid = true;
	
	int nxlines = 25;
	int nylines = 25;
	
	int xlines = (int)Config.targetWidth/nxlines;
	int ylines = (int)Config.targetHeight/nylines;
	Animation an;
	
	
	final ClickListener clicked = new ClickListener(){
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			StatusBar.updateXY(x, y);
			mouse.set(x, y);
			Actor acc = StudioScene.this.hit(x, y, true);
			if(acc != null && acc != StudioScene.this){			
				selectedActor = acc;
				ActorPanel.selectActor(acc.getName());
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
		addListener(clicked);
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if(Asset.update() && !active ){
			Asset.setup();
			AssetPanel.updateAsset();
			an = new Animation(0.5f, tex("porgarett"), tex("porsabra"));
			addActor(new SceneSprite(an,100, 100));
			Asset.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
			active = true;
		}
		if(selectedActor != null){
			
		}
	}
	
	public void setName(Actor actor){
		actor.setName("Actor"+getChildren().size);
		ActorPanel.addActor(actor.getName());
	}
	
	public void createLabel(String fontName){
		Label.LabelStyle ls = new Label.LabelStyle();
		ls.font = font(fontName);
		Label info = new Label("Text", ls);
		setName(info);
		//Sink.stage.screenToStageCoordinates(mouse);
		/*String text = json.toJson(new ActorJson(actor), ActorJson.class);
		SinkStudio.log(text);
		ActorJson person2 = json.fromJson(ActorJson.class, text);
		SinkStudio.log(json.prettyPrint(person2));*/
		
		addActor(info, mouse.x, mouse.y);
	}
	
	public void createTexture(String texName){
		Image image = new Image(tex(texName));
		setName(image);
		String text = json.toJson(new ImageJson(image, texName), ImageJson.class);
		SinkStudio.log(text);
		ImageJson person2 = json.fromJson(ImageJson.class, text);
		SinkStudio.log(json.prettyPrint(person2));
		addActor(image, mouse.x, mouse.y);
	}
	
	public void createButton(){
		Button button = new Button(Asset.skin);
		addActor(button, mouse.x, mouse.y);
	}
	
	public void createTextButton(){
		addActor(new TextButton("TextButton",Asset.skin), mouse.x, mouse.y);
	}
	
	void drawBG(){
		shapeRenderer.begin(ShapeType.Filled);
		shapeRenderer.setColor(Color.BLACK);
		shapeRenderer.rect(0, 0, getWidth(), getHeight());
		shapeRenderer.end();
	}
	
	void drawGrid(){
		if(showGrid){
	        shapeRenderer.begin(ShapeType.Point);
	        shapeRenderer.setColor(Color.BLACK);
	        for(int i = 0; i<= xlines+1; i++)
	        	for(int j = 0; j<= ylines+1; j++)
	        		shapeRenderer.point(10+(i*xlines), j*ylines, 0);
	        /*for(int i = 0; i<= xlines; i++)
	        	shapeRenderer.line(i*xlines, 0, i*xlines, Config.targetHeight);
	        for(int i = 0; i<= ylines; i++)
	        	shapeRenderer.line(0, i*ylines, Config.targetWidth, i*ylines);*/
	        shapeRenderer.end();
		}
	}
	
	void drawSelection(){
		if(selectedActor != null){
			vec.set(selectedActor.getX(), selectedActor.getY());
			selectedActor.localToStageCoordinates(vec);
	        
	        shapeRenderer.begin(ShapeType.Line);
	        shapeRenderer.setColor(Color.GREEN);
	        //shapeRenderer.rect(vec.x, vec.y, selectedActor.getWidth(), selectedActor.getHeight());
	        shapeRenderer.rect(selectedActor.getX(), selectedActor.getY(), selectedActor.getWidth(),
	        		selectedActor.getHeight());
	        shapeRenderer.end();
		}
	}
	
	@Override
    public void draw(Batch batch, float parentAlpha) {
		batch.end();
		//drawBG();
		drawGrid();
        batch.begin();
		super.draw(batch, parentAlpha);
		batch.end();
		drawSelection();
		batch.begin();
    }
	
	public static void setSelectedActor(String actorName){
		selectedActor = Sink.getScene().findActor(actorName);
		StatusBar.updateSelected(actorName);
	}
	
	public void load(){
		String text = Export.readFile("source/"+Content.getFile()+".java");
		text = text.replaceAll("[\r]", "");
		//ActorPanel.clear();
		String[] lines = text.split("\n");
		SinkStudio.log("Scene Size: "+lines.length);
		for(int i =0; i< lines.length; i++){
			if(lines[i].contains("//begin")){
				SinkStudio.log("Beginning Scene: "+Content.getFile());
			}
			else if(lines[i].contains("//end")){
				SinkStudio.log("Ending Scene: "+Content.getFile());
				//break;
			}
			if(lines[i].contains("Label")){
				createLabel("normal");
			}
			if(lines[i].contains("Image")){
				createTexture("porgarett");
			}
		}
	}
	
	public void save(){
		
	}
}

class ActorJson {
	   public String name;
	   public float x;
	   public float y;
	   public float w;
	   public float h;
	   
	   ActorJson(){
	   }
	   
	   ActorJson(Actor actor){
		   name = actor.getName();
		   x = actor.getX();
		   y = actor.getY();
		   w = actor.getWidth();
		   h = actor.getHeight();
	   }
}

class ImageJson {
	   public String name;
	   public float x;
	   public float y;
	   public float w;
	   public float h;
	   public String texName;
	   
	   ImageJson(){
		   
	   }
	   
	   ImageJson(Image image, String texName){
		   name = image.getName();
		   x = ((Actor)image).getX();
		   y = image.getY();
		   w = image.getWidth();
		   h = image.getHeight();
		   this.texName = texName;
	   }
}