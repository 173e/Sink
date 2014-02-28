package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ImageJson extends Image implements Json.Serializer<ImageJson> {
	private String texName = "";
	
	public ImageJson(){
		
	}
	
	public ImageJson(String texName){
		super(Asset.tex(texName));
		this.texName = texName;
	}

	@Override
	public ImageJson read(Json arg0, JsonValue jv, Class arg2) {
		ImageJson image = new ImageJson(jv.getString("texName"));
		image.setName(jv.getString("name"));
		image.setX(jv.getFloat("x"));
		image.setY(jv.getFloat("y"));
		image.setWidth(jv.getFloat("width"));
		image.setHeight(jv.getFloat("height"));
		return image;
	}

	@Override
	public void write(Json json, ImageJson image, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", image.getClass().getName());
		json.writeValue("name", image.getName());
		json.writeValue("x", image.getX());
		json.writeValue("y", image.getY());
		json.writeValue("width", image.getWidth());
		json.writeValue("height", image.getHeight());
		json.writeValue("texName", image.getTexName());
		json.writeObjectEnd();
	}
	
	public String getTexName(){
		return texName;
	}
}
