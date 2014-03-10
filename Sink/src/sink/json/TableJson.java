package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class TableJson extends Table implements Json.Serializer<TableJson> {
	private String bgTexName = "";
	private boolean useSkin = false;
	
	public TableJson(){
	}
	
	public TableJson(boolean useSkin){
		super(Asset.skin);
		this.useSkin = useSkin;
		setSize(200, 200);
	}
	
	public TableJson(String bgTexName){
		setBackground(new TextureRegionDrawable(Asset.tex(bgTexName)));
		this.bgTexName = bgTexName;
		setSize(200, 200);
	}

	@Override
	public TableJson read(Json json, JsonValue jv, Class arg2) {
		TableJson table = new TableJson(true);
		table.setName(jv.getString("name"));
		table.setX(jv.getFloat("x"));
		table.setY(jv.getFloat("y"));
		table.setWidth(jv.getFloat("width"));
		table.setHeight(jv.getFloat("height"));
		table.setColor(Color.valueOf(jv.getString("color")));
		return table;
	}

	@Override
	public void write(Json json, TableJson table, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", table.getClass().getName());
		json.writeValue("name", table.getName());
		json.writeValue("x", table.getX());
		json.writeValue("y", table.getY());
		json.writeValue("width", table.getWidth());
		json.writeValue("height", table.getHeight());
		if(bgTexName != null && !useSkin)
			json.writeValue("bgTexName", bgTexName);
		if(useSkin)
			json.writeValue("skin", true);
		json.writeValue("color", table.getColor().toString());
		json.writeObjectEnd();
	}

}
