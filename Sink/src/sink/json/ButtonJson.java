package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class ButtonJson extends Button implements Json.Serializer<ButtonJson> {
	private String upTexName = null;
	private String downTexName = null;
	private boolean useSkin = false;
	
	public ButtonJson(){
	}
	
	public ButtonJson(boolean useSkin){
		super(Asset.skin);
		this.useSkin = useSkin;
		setWidth(100);
		setHeight(70);
	}
	
	public ButtonJson(String upTexName, String downTexName){
		super(new TextureRegionDrawable(Asset.tex(upTexName)), new TextureRegionDrawable(Asset.tex(downTexName)));
		this.upTexName = upTexName;
		this.downTexName = downTexName;
		setWidth(100);
		setHeight(70);
	}

	@Override
	public ButtonJson read(Json json, JsonValue jv, Class arg2) {
		ButtonJson btn = new ButtonJson(true);
		btn.setName(jv.getString("name"));
		btn.setX(jv.getFloat("x"));
		btn.setY(jv.getFloat("y"));
		btn.setWidth(jv.getFloat("width"));
		btn.setHeight(jv.getFloat("height"));
		btn.setColor(Color.valueOf(jv.getString("color")));
		return btn;
	}

	@Override
	public void write(Json json, ButtonJson btn, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", btn.getClass().getName());
		json.writeValue("name", btn.getName());
		json.writeValue("x", btn.getX());
		json.writeValue("y", btn.getY());
		json.writeValue("width", btn.getWidth());
		json.writeValue("height", btn.getHeight());
		if(upTexName != null && !useSkin)
			json.writeValue("upTexName", upTexName);
		if(downTexName != null && !useSkin)
			json.writeValue("downTexName", downTexName);
		if(useSkin)
			json.writeValue("skin", true);
		json.writeValue("color", btn.getColor().toString());
		json.writeObjectEnd();
	}

}
