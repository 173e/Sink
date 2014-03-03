package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class DialogJson extends Dialog implements Json.Serializer<DialogJson> {
	public DialogJson(){
		super("", Asset.skin);
	}
	
	public DialogJson(String title){
		super(title, Asset.skin);
	}

	@Override
	public DialogJson read(Json json, JsonValue jv, Class arg2) {
		DialogJson dialog = new DialogJson(jv.getString("title"));
		dialog.setName(jv.getString("name"));
		dialog.setX(jv.getFloat("x"));
		dialog.setY(jv.getFloat("y"));
		dialog.setWidth(jv.getFloat("width"));
		dialog.setHeight(jv.getFloat("height"));
		dialog.setColor(Color.valueOf(jv.getString("color")));
		dialog.setModal(jv.getBoolean("modal"));
		dialog.setMovable(jv.getBoolean("move"));
		dialog.setResizable(jv.getBoolean("resize"));
		return dialog;
	}

	@Override
	public void write(Json json, DialogJson dialog, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", dialog.getClass().getName());
		json.writeValue("name", dialog.getName());
		json.writeValue("x", dialog.getX());
		json.writeValue("y", dialog.getY());
		json.writeValue("width", dialog.getWidth());
		json.writeValue("height", dialog.getHeight());
		json.writeValue("title", dialog.getTitle());
		json.writeValue("modal", dialog.isModal());
		json.writeValue("move", dialog.isMovable());
		json.writeValue("resize", dialog.isResizable());
		json.writeValue("color", dialog.getColor().toString());
		json.writeObjectEnd();
	}

}
