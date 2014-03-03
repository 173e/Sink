package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Tree;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TreeJson extends Tree implements Json.Serializer<TreeJson> {
	
	public TreeJson(){
		super(Asset.skin);
	}

	@Override
	public TreeJson read(Json json, JsonValue jv, Class arg2) {
		TreeJson label = new TreeJson();
		label.setName(jv.getString("name"));
		label.setX(jv.getFloat("x"));
		label.setY(jv.getFloat("y"));
		label.setWidth(jv.getFloat("width"));
		label.setHeight(jv.getFloat("height"));
		label.setColor(Color.valueOf(jv.getString("color")));
		return label;
	}

	@Override
	public void write(Json json, TreeJson label, Class arg2) {
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
