package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class CheckBoxJson extends CheckBox  implements Json.Serializer<CheckBoxJson> {
	
	public CheckBoxJson(){
		super("",Asset.skin);
	}
	
	public CheckBoxJson(String text){
		super(text,Asset.skin);
	}

	@Override
	public CheckBoxJson read(Json json, JsonValue jv, Class arg2) {
		CheckBoxJson check = new CheckBoxJson(jv.getString("text"));
		check.setName(jv.getString("name"));
		check.setX(jv.getFloat("x"));
		check.setY(jv.getFloat("y"));
		check.setWidth(jv.getFloat("width"));
		check.setHeight(jv.getFloat("height"));
		check.setColor(Color.valueOf(jv.getString("color")));
		return check;
	}

	@Override
	public void write(Json json, CheckBoxJson check, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", check.getClass().getName());
		json.writeValue("name", check.getName());
		json.writeValue("x", check.getX());
		json.writeValue("y", check.getY());
		json.writeValue("width", check.getWidth());
		json.writeValue("height", check.getHeight());
		json.writeValue("text", check.getText().toString());
		json.writeValue("color", check.getColor().toString());
		json.writeObjectEnd();
	}

}
