package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class LabelJson extends Label implements Json.Serializer<LabelJson> {
	private String fontName = "";
	
	public LabelJson(){
		super("", Asset.skin);
	}
	
	public LabelJson(String text, String fontName){
		super(text, new LabelStyle(Asset.font(fontName), Color.WHITE));
		this.fontName = fontName;
	}

	@Override
	public LabelJson read(Json json, JsonValue jv, Class arg2) {
		LabelJson label = new LabelJson(jv.getString("text"),jv.getString("fontName"));
		label.setName(jv.getString("name"));
		label.setX(jv.getFloat("x"));
		label.setY(jv.getFloat("y"));
		label.setWidth(jv.getFloat("width"));
		label.setHeight(jv.getFloat("height"));
		label.setColor(Color.valueOf(jv.getString("color")));
		return label;
	}

	@Override
	public void write(Json json, LabelJson label, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", label.getClass().getName());
		json.writeValue("name", label.getName());
		json.writeValue("x", label.getX());
		json.writeValue("y", label.getY());
		json.writeValue("width", label.getWidth());
		json.writeValue("height", label.getHeight());
		json.writeValue("text", label.getText().toString());
		json.writeValue("fontName", label.getFontName());
		json.writeValue("color", label.getColor().toString());
		json.writeObjectEnd();
	}

	public String getFontName(){
		return fontName;
	}
}
