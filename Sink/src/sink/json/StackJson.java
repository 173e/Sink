package sink.json;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class StackJson extends Stack implements Json.Serializer<StackJson> {
	
	public StackJson(){
	}

	@Override
	public StackJson read(Json json, JsonValue jv, Class arg2) {
		StackJson label = new StackJson();
		label.setName(jv.getString("name"));
		label.setX(jv.getFloat("x"));
		label.setY(jv.getFloat("y"));
		label.setWidth(jv.getFloat("width"));
		label.setHeight(jv.getFloat("height"));
		label.setColor(Color.valueOf(jv.getString("color")));
		return label;
	}

	@Override
	public void write(Json json, StackJson label, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", label.getClass().getName());
		json.writeValue("name", label.getName());
		json.writeValue("x", label.getX());
		json.writeValue("y", label.getY());
		json.writeValue("width", label.getWidth());
		json.writeValue("height", label.getHeight());
		json.writeValue("color", label.getColor().toString());
		json.writeObjectEnd();
	}
}
