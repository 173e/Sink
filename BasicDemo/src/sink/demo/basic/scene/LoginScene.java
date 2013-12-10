package sink.demo.basic.scene;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sink.core.Asset;
import sink.core.Scene;
import sink.core.SceneManager;

public class LoginScene extends Scene{

	@Override
	protected void init() {
		TextButton back = new TextButton("Back", Asset.skin);
		back.setSize(200, 75);
		back.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				SceneManager.setCurrentScene("menu");
 			}
 		});
		addActor(back);
	}

}