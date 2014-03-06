package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ListJson extends List implements Json.Serializer<ListJson> {
	
	public ListJson(){
		super(new String[]{} ,Asset.skin);
	}
	
	public ListJson(String... items){
		super(items, Asset.skin);
		setItems(items);
	}

	@Override
	public ListJson read(Json json, JsonValue jv, Class arg2) {
		ListJson list = new ListJson(jv.getString("text").split(","));
		list.setName(jv.getString("name"));
		list.setX(jv.getFloat("x"));
		list.setY(jv.getFloat("y"));
		list.setWidth(jv.getFloat("width"));
		list.setHeight(jv.getFloat("height"));
		list.setColor(Color.valueOf(jv.getString("color")));
		return list;
	}

	@Override
	public void write(Json json, ListJson list, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", list.getClass().getName());
		json.writeValue("name", list.getName());
		json.writeValue("x", list.getX());
		json.writeValue("y", list.getY());
		json.writeValue("width", list.getWidth());
		json.writeValue("height", list.getHeight());
		String items = "";
		for(String s: list.getItems())
			items+=s+",";
		json.writeValue("text", items);
		json.writeValue("color", list.getColor().toString());
		json.writeObjectEnd();
	}

}
