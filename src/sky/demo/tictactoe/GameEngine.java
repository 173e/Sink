package sky.demo.tictactoe;

import sky.engine.core.Engine;

public class GameEngine extends Engine{
	
	@Override
	public void create(){
		super.create();
		splashPanel = new SplashPanel();
	}
	
}
