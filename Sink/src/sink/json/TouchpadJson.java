package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TouchpadJson extends Touchpad implements Json.Serializer<TouchpadJson> {
	private float dr = 5;
	
	public TouchpadJson(){
		super(5, Asset.skin);
	}
	
	public TouchpadJson(float deadZoneRadius){
		super(deadZoneRadius, Asset.skin);
		dr = deadZoneRadius;
	}

	@Override
	public TouchpadJson read(Json json, JsonValue jv, Class arg2) {
		TouchpadJson tp = new TouchpadJson();
		tp.setName(jv.getString("name"));
		tp.setX(jv.getFloat("x"));
		tp.setY(jv.getFloat("y"));
		tp.setWidth(jv.getFloat("width"));
		tp.setHeight(jv.getFloat("height"));
		tp.setColor(Color.valueOf(jv.getString("color")));
		tp.setDeadzone(jv.getFloat("deadzoneRadius"));
		return tp;
	}

	@Override
	public void write(Json json, TouchpadJson tp, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", tp.getClass().getName());
		json.writeValue("name", tp.getName());
		json.writeValue("x", tp.getX());
		json.writeValue("y", tp.getY());
		json.writeValue("width", tp.getWidth());
		json.writeValue("height", tp.getHeight());
		json.writeValue("deadzoneRadius", dr);
		json.writeValue("color", tp.getColor().toString());
		json.writeObjectEnd();
	}
	
	@Override
	public void setDeadzone(float value){
		super.setDeadzone(value);
		dr = value;
	}
	
	public float getDeadzone(){
		return dr;
	}
}
