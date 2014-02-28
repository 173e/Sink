package sink.studio.panel;

import sink.core.Asset;
import sink.core.Config;
import sink.core.Scene;
import sink.core.SceneSprite;
import sink.core.Sink;
import sink.json.ButtonJson;
import sink.json.ImageJson;
import sink.json.LabelJson;
import sink.json.TextButtonJson;
import sink.studio.core.Content;
import sink.studio.core.Export;
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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class StudioScene extends Scene {
	static Json json = new Json();
	static JsonReader reader = new JsonReader();
	static boolean isDirty = false;
	
	Vector2 mouse = new Vector2();
	Vector2 vec = new Vector2();
	
	ShapeRenderer shapeRenderer = new ShapeRenderer();
	boolean showGrid = true;
	
	int nxlines = 25;
	int nylines = 25;
	
	int xlines = (int)Config.targetWidth/nxlines;
	int ylines = (int)Config.targetHeight/nylines;

	final ClickListener clicked = new ClickListener(){
		@Override
		public void clicked(InputEvent event, float x, float y) {
			super.clicked(event, x, y);
			StatusBar.updateXY(x, y);
			mouse.set(x, y);
			ActorPanel.setSelectedActor(StudioScene.this.hit(x, y, true));
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			super.touchDragged(event, x, y, pointer);
			ActorPanel.updateSelectedActor(x, y);
			isDirty = true;
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
		Asset.assetMan.finishLoading();
		Asset.setup();
		AssetPanel.updateAsset();
		Asset.skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
		Sink.setup(Asset.skin);
		addListener(clicked);
		initSerializers();
		load();
	}
	
	public void initSerializers(){
		json.setSerializer(ImageJson.class, new ImageJson());
		json.setSerializer(LabelJson.class, new LabelJson());
		json.setSerializer(ButtonJson.class, new ButtonJson());
		json.setSerializer(TextButtonJson.class, new TextButtonJson());
	}
	
	public void load(){
		if(Content.sceneExists()){
			String file = Export.readFile("scene/"+Content.getScene()+".json");
			String[] lines = file.split("\n");
			for(String line: lines){
				if(line.trim().isEmpty())
					continue;
				JsonValue jv = reader.parse(line);
				switch(jv.get("class").asString()){
					case "sink.json.ImageJson":addActor(json.fromJson(ImageJson.class, line));break;
					case "sink.json.LabelJson":addActor(json.fromJson(LabelJson.class, line));break;
					case "sink.json.ButtonJson":addActor(json.fromJson(ButtonJson.class, line));break;
					case "sink.json.TextButtonJson":addActor(json.fromJson(TextButtonJson.class, line));break;	
				}
			}
		}
	}
	
	public static void save(){
		if(isDirty){
			String list = "";
			for(Actor actor: Sink.getScene().getChildren())
				list+=json.toJson(actor)+"\n";
			Export.writeFile("scene/"+Content.getScene()+".json", list);
			isDirty = false;
		}
	}
	
	@Override
	public void addActor(Actor actor){
		super.addActor(actor);
		ActorPanel.addActor(actor.getName());
	}
	
	public void setName(Actor actor){
		if(findActor("Actor"+getChildren().size) == null)
			actor.setName("Actor"+getChildren().size);
		else
			actor.setName("Actor"+getChildren().size+"_1");
	}
	
	public void rename(Actor actor){
		
	}
	
	public void createLabel(String fontName){
		//Sink.stage.screenToStageCoordinates(mouse);
		LabelJson label = new LabelJson("Text", fontName);
		setName(label);
		label.setX(mouse.x);
		label.setY(mouse.y);
		addActor(label);
		isDirty = true;
	}
	
	public void createImage(String texName){
		ImageJson image = new ImageJson(texName);
		image.setX(mouse.x);
		image.setY(mouse.y);
		setName(image);
		addActor(image);
		isDirty = true;
	}
	
	public void createSceneSprite(){
		Animation an = new Animation(0.5f, tex("porgarett"), tex("porsabra"));
		SceneSprite sceneSprite = new SceneSprite(an, 100, 100);
		setName(sceneSprite);
		addActor(sceneSprite);
		isDirty = true;
	}
	
	public void createButton(){
		ButtonJson button = new ButtonJson(true);
		button.setX(mouse.x);
		button.setY(mouse.y);
		button.setWidth(100);
		button.setHeight(70);
		setName(button);
		addActor(button);
		isDirty = true;
	}
	
	public void createTextButton(){
		TextButtonJson button = new TextButtonJson("TextButton");
		button.setX(mouse.x);
		button.setY(mouse.y);
		setName(button);
		addActor(button);
		isDirty = true;
	}
	
	void drawGrid(){
		if(showGrid){
	        shapeRenderer.begin(ShapeType.Point);
	        shapeRenderer.setColor(Color.BLACK);
	        for(int i = 0; i<= xlines+1; i++)
	        	for(int j = 0; j<= ylines+1; j++)
	        		shapeRenderer.point(10+(i*xlines), j*ylines, 0);
	        shapeRenderer.end();
		}
	}
	
	void drawSelection(){
		if(ActorPanel.selectedActor != null){
			vec.set(ActorPanel.selectedActor.getX(), ActorPanel.selectedActor.getY());
			ActorPanel.selectedActor.localToStageCoordinates(vec);
	        shapeRenderer.begin(ShapeType.Line);
	        shapeRenderer.setColor(Color.GREEN);
	        //shapeRenderer.rect(vec.x, vec.y, selectedActor.getWidth(), selectedActor.getHeight());
	        shapeRenderer.rect(ActorPanel.selectedActor.getX(), ActorPanel.selectedActor.getY(), ActorPanel.selectedActor.getWidth(),
	        		ActorPanel.selectedActor.getHeight());
	        shapeRenderer.end();
		}
	}
	
	@Override
    public void draw(Batch batch, float parentAlpha) {
		batch.end();
		drawGrid();
        batch.begin();
		super.draw(batch, parentAlpha);
		batch.end();
		drawSelection();
		batch.begin();
    }
}