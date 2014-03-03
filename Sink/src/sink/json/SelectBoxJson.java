package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SelectBoxJson extends SelectBox<String>  implements Json.Serializer<SelectBoxJson> {
	
	public SelectBoxJson(){
		super(Asset.skin);
	}
	
	public SelectBoxJson(String... items){
		super(Asset.skin);
		setItems(items);
	}

	@Override
	public SelectBoxJson read(Json json, JsonValue jv, Class arg2) {
		SelectBoxJson select = new SelectBoxJson(jv.getString("text").split(","));
		select.setName(jv.getString("name"));
		select.setX(jv.getFloat("x"));
		select.setY(jv.getFloat("y"));
		select.setWidth(jv.getFloat("width"));
		select.setHeight(jv.getFloat("height"));
		select.setColor(Color.valueOf(jv.getString("color")));
		return select;
	}

	@Override
	public void write(Json json, SelectBoxJson select, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", select.getClass().getName());
		json.writeValue("name", select.getName());
		json.writeValue("x", select.getX());
		json.writeValue("y", select.getY());
		json.writeValue("width", select.getWidth());
		json.writeValue("height", select.getHeight());
		String items = "";
		for(String s: select.getItems())
			items+=s+",";
		json.writeValue("text", items);
		json.writeValue("color", select.getColor().toString());
		json.writeObjectEnd();
	}

}
