package sky.engine.core;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;

public class LogPane extends Group{
	Label logLabel;
	ScrollPane scroll;
	
	public LogPane(){
		setSize(300, 100);
		logLabel = new Label("", Asset.skin);
		scroll = new ScrollPane(logLabel);
		scroll.setPosition(0,0);
		scroll.setSize(300, 100);
		scroll.setBounds(0, 0, 300, 100);
		addActor(scroll);
	}
	
	public void update(String text){
		logLabel.setText(logLabel.getText() + "\n" +text);
		scroll.setScrollPercentY(100);
	}
}
