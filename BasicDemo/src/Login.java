

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import sink.core.Asset;
import sink.core.Sink;

public class Login {
	
	public Login() {
		TextButton back = new TextButton("Back", Asset.skin);
		back.setSize(200, 75);
		back.addListener(new ClickListener(){
 			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				Sink.setScene("Menu");
 			}
 		});
		Sink.addActor(back);
	}
}