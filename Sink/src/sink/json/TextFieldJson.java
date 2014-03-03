package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TextFieldJson extends TextField implements Json.Serializer<TextFieldJson> {
	private boolean pass = false;
	
	
	public TextFieldJson(){
		super("", Asset.skin);
	}
	
	public TextFieldJson(String text){
		super(text, Asset.skin);
		setMessageText("");
	}

	@Override
	public TextFieldJson read(Json json, JsonValue jv, Class arg2) {
		TextFieldJson tf = new TextFieldJson();
		tf.setName(jv.getString("name"));
		tf.setX(jv.getFloat("x"));
		tf.setY(jv.getFloat("y"));
		tf.setWidth(jv.getFloat("width"));
		tf.setHeight(jv.getFloat("height"));
		tf.setText(jv.getString("text"));
		//tf.setMessageText(jv.getString("msgtext"));
		////tf.setPasswordMode(jv.getBoolean("password"));
		tf.setColor(Color.valueOf(jv.getString("color")));
		return tf;
	}

	@Override
	public void write(Json json, TextFieldJson tf, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", tf.getClass().getName());
		json.writeValue("name", tf.getName());
		json.writeValue("x", tf.getX());
		json.writeValue("y", tf.getY());
		json.writeValue("width", tf.getWidth());
		json.writeValue("height", tf.getHeight());
		json.writeValue("text", tf.getText().toString());
		//json.writeValue("msgtext", tf.getMessageText());
		//json.writeValue("password", pass);
		json.writeValue("color", tf.getColor().toString());
		json.writeObjectEnd();
	}
	
	public boolean getPasswordMode(){
		return pass;
	}
	
	@Override
	public void setPasswordMode(boolean passwordMode){
		super.setPasswordMode(passwordMode);
		this.pass = passwordMode;
	}
}
