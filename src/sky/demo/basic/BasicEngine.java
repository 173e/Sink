package sky.demo.basic;

import sky.engine.core.Engine;

public class BasicEngine extends Engine{

	@Override
	public void create(){
		super.create();
		splashPanel = new SplashPanel();
	}
}
