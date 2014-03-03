package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SplitPaneJson extends SplitPane implements Json.Serializer<SplitPaneJson> {
	
	public SplitPaneJson(){
		super(null, null, false, Asset.skin);
	}

	@Override
	public SplitPaneJson read(Json json, JsonValue jv, Class arg2) {
		SplitPaneJson label = new SplitPaneJson();
		label.setName(jv.getString("name"));
		label.setX(jv.getFloat("x"));
		label.setY(jv.getFloat("y"));
		label.setWidth(jv.getFloat("width"));
		label.setHeight(jv.getFloat("height"));
		label.setColor(Color.valueOf(jv.getString("color")));
		return label;
	}

	@Override
	public void write(Json json, SplitPaneJson label, Class arg2) {
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
