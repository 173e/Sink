package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TextButtonJson extends TextButton implements Json.Serializer<TextButtonJson> {
	
	public TextButtonJson(){
		super("", Asset.skin);
	}
	
	public TextButtonJson(String text){
		super(text, Asset.skin);
	}

	@Override
	public TextButtonJson read(Json json, JsonValue jv, Class arg2) {
		TextButtonJson btn = new TextButtonJson(jv.getString("text"));
		btn.setName(jv.getString("name"));
		btn.setX(jv.getFloat("x"));
		btn.setY(jv.getFloat("y"));
		btn.setWidth(jv.getFloat("width"));
		btn.setHeight(jv.getFloat("height"));
		btn.setColor(Color.valueOf(jv.getString("color")));
		return btn;
	}

	@Override
	public void write(Json json, TextButtonJson btn, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", btn.getClass().getName());
		json.writeValue("name", btn.getName());
		json.writeValue("x", btn.getX());
		json.writeValue("y", btn.getY());
		json.writeValue("width", btn.getWidth());
		json.writeValue("height", btn.getHeight());
		json.writeValue("text", btn.getText().toString());
		json.writeValue("color", btn.getColor().toString());
		json.writeObjectEnd();
	}
}

