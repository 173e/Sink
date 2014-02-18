package sink.studio.core;

public class Register{
	int index = 0;
	
	public Register(){
		
	}
	
	public Register(int value){
		index = value;
	}
	
	public void setValue(int value){
		index = value;
	}
	
	public int getValue(){
		return index;
	}
}
