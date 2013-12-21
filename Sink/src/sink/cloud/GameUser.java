package sink.cloud;

import com.app2square.core.api.parse.datatypes.ParseData;
import com.app2square.core.api.parse.user.ParseUser;
import com.app2square.core.api.parse.json.Json.Object;


public class GameUser extends ParseUser {
	private static final String SCORE = "score";
	
	public GameUser(Object json, ParseData data) {
		super(json, data);
		data.put(SCORE, 444);
	}
	
	public void getPlayerName(){
		
	}
	
	public void setPlayerName(){
		
	}
	
	public String getScore(){
		return "";
	}
	
	public void setScore(int score){
		//put(SCORE, score);
		//saveInBackground();
	}
}
