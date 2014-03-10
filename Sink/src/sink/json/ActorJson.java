package sink.json;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ActorJson extends Actor implements Json.Serializer<ActorJson> {
	
	public ActorJson(){
	}

	@Override
	public ActorJson read(Json json, JsonValue jv, Class arg2) {
		ActorJson btn = new ActorJson();
		btn.setName(jv.getString("name"));
		btn.setX(jv.getFloat("x"));
		btn.setY(jv.getFloat("y"));
		btn.setWidth(jv.getFloat("width"));
		btn.setHeight(jv.getFloat("height"));
		btn.setColor(Color.valueOf(jv.getString("color")));
		return btn;
	}

	@Override
	public void write(Json json, ActorJson btn, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", btn.getClass().getName());
		json.writeValue("name", btn.getName());
		json.writeValue("x", btn.getX());
		json.writeValue("y", btn.getY());
		json.writeValue("width", btn.getWidth());
		json.writeValue("height", btn.getHeight());
		json.writeValue("color", btn.getColor().toString());
		json.writeObjectEnd();
	}

}
