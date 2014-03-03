package sink.json;

import sink.core.Asset;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class SliderJson extends Slider implements Json.Serializer<SliderJson> {
	private boolean vertical = false;
	
	public SliderJson(){
		super(0, 0, 1, false, Asset.skin);
	}
	
	public SliderJson(float min, float max, float step, boolean vertical){
		super(min, max, step, vertical, Asset.skin);
		this.vertical = vertical;
	}

	@Override
	public SliderJson read(Json json, JsonValue jv, Class arg2) {
		SliderJson slider = new SliderJson(jv.getFloat("min"),jv.getFloat("max"),jv.getFloat("step")
				, jv.getBoolean("vertical"));
		slider.setName(jv.getString("name"));
		slider.setX(jv.getFloat("x"));
		slider.setY(jv.getFloat("y"));
		slider.setWidth(jv.getFloat("width"));
		slider.setHeight(jv.getFloat("height"));
		slider.setValue(jv.getFloat("value"));
		slider.setColor(Color.valueOf(jv.getString("color")));
		return slider;
	}

	@Override
	public void write(Json json, SliderJson slider, Class arg2) {
		json.writeObjectStart();
		json.writeValue("class", slider.getClass().getName());
		json.writeValue("name", slider.getName());
		json.writeValue("x", slider.getX());
		json.writeValue("y", slider.getY());
		json.writeValue("width", slider.getWidth());
		json.writeValue("height", slider.getHeight());
		json.writeValue("min", slider.getMinValue());
		json.writeValue("max", slider.getMaxValue());
		json.writeValue("step", slider.getStepSize());
		json.writeValue("vertical", vertical);
		json.writeValue("value", slider.getValue());
		json.writeValue("color", slider.getColor().toString());
		json.writeObjectEnd();
	}
}
