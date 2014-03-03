package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ScrollPaneJson extends ScrollPane implements Json.Serializer<ScrollPaneJson> {
	
	public ScrollPaneJson(){
		super(null);
	}

	@Override
	public ScrollPaneJson read(Json json, JsonValue jv, Class arg2) {
		ScrollPaneJson label = new ScrollPaneJson();
		label.setName(jv.getString("name"));
		label.setX(jv.getFloat("x"));
		label.setY(jv.getFloat("y"));
		label.setWidth(jv.getFloat("width"));
		label.setHeight(jv.getFloat("height"));
		label.setColor(Color.valueOf(jv.getString("color")));
		return label;
	}

	@Override
	public void write(Json json, ScrollPaneJson label, Class arg2) {
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
