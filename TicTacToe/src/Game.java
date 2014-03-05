import java.util.Random;


import sink.core.SceneGroup;
import sink.core.Sink;
import sink.demo.tictactoe.GameMode;
import sink.demo.tictactoe.GameState;
import sink.demo.tictactoe.MarkType;
import sink.demo.tictactoe.Turn;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Game extends SceneGroup {
	Box[][] boxes;
	public static int turnCounter;
	
	/* Game Constants */
	private static GameState gameState = GameState.GAME_MENU;
	private static GameMode gameMode = GameMode.SINGLE_PLAYER_VS_COMPUTER;
	public static Turn currentTurn = Turn.Player;
	
	public static void startLevel() {
		setState(GameState.GAME_RUNNING);
	}
	
	/* Check if state is current return true */
	public static boolean mode(GameMode gm){
		if(gameMode == gm)
			return true;
		else
			return false;
	}
	
	public static void setMode(GameMode gm){
		gameMode = gm;
		Sink.log("Game State: " + gameState.toString());
	}
	
	/* Check if state is current return true */
	public static boolean state(GameState ss){
		if(gameState == ss)
			return true;
		else
			return false;
	}
	
	public static void setState(GameState ss){
		gameState = ss;
		Sink.log("Game State: " + gameState.toString());
	}
	
	public Game(){
		boxes = new Box[3][3];
		for(int i = 0;i < 3;i++){
			for(int j = 0;j < 3;j++){
				Box box = new Box(i, j);
				boxes[i][j] = box;
				Sink.addActor(box, box.getWidth()*j, box.getHeight()*i);
			}
		}
		Sink.addActor(this);
	}
	
	public void reset(){
		Sink.log("Reset");
		turnCounter = 0;
		Sink.setScene("Game");
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if (state(GameState.GAME_RUNNING)){
		}
		AI();
		if(turnCounter < 9){
			for(int i=0; i<3;i++){
				checkForRowY(i);
				checkForRowX(i);
			}
			checkForDiagonal();
		}
		else{
			Sink.log("Draw Match");
			reset();
		}
	}
	
	int random1 = 0;
	int random2 = 0;
	Random rand = new Random();
	void AI(){
		if(mode(GameMode.SINGLE_PLAYER_VS_COMPUTER)){
			if(currentTurn == Turn.Computer){
				Sink.log("AI");
				random1 = rand.nextInt(3);
				random2 = rand.nextInt(3);
				if(!boxes[random1][random2].isMarked){
					boxes[random1][random2].markByComputer();
					return;
				}
			}
		}
	}
	
	
	public void playerWin(){
		Sink.log("Player Win");
		reset();
	}
	
	public void computerWin(){
		Sink.log("Computer Win");
		reset();
	}
	
	void checkForRowY(int j){
		if(boxes[j][0].type != MarkType.None && boxes[j][1].type != MarkType.None  
				&& boxes[j][2].type != MarkType.None )
			if(boxes[j][0].type ==  boxes[j][1].type && boxes[j][0].type == boxes[j][2].type )
				if(boxes[j][0].type == MarkType.X)
					playerWin();
				else
					computerWin();
	}
	
	void checkForRowX(int j){
		if(boxes[0][j].type != MarkType.None && boxes[1][j].type != MarkType.None  
				&& boxes[2][j].type != MarkType.None )
			if(boxes[0][j].type ==  boxes[1][j].type && boxes[0][j].type == boxes[2][j].type )
				if(boxes[0][j].type == MarkType.X)
					playerWin();
				else
					computerWin();
	}
	
	void checkForDiagonal(){
		if(boxes[0][0].type != MarkType.None && boxes[1][1].type != MarkType.None  
				&& boxes[2][2].type != MarkType.None )
			if(boxes[0][0].type ==  boxes[1][1].type && boxes[0][0].type == boxes[2][2].type )
				if(boxes[0][0].type == MarkType.X)
					playerWin();
				else
					computerWin();
		if(boxes[0][2].type != MarkType.None && boxes[1][1].type != MarkType.None  
				&& boxes[2][0].type != MarkType.None )
			if(boxes[0][2].type ==  boxes[1][1].type && boxes[0][2].type == boxes[2][0].type )
				if(boxes[0][2].type == MarkType.X)
					playerWin();
				else
					computerWin();
	}

}

class Box extends SceneGroup{
	private final int row, col;
	private final Image bg = new Image(tex("square"));
	private final Image x = new Image(tex("x"));
	private final Image o = new Image(tex("o"));
	public boolean isMarked = false;
	public MarkType type = MarkType.None;
	
	Box(int row, int col){
		this.row = row;
		this.col = col;
		bg.setPosition(row*bg.getWidth(), col*bg.getHeight());
		o.setPosition(row*bg.getWidth(), col*bg.getHeight());
		x.setPosition(row*bg.getWidth(), col*bg.getHeight());
		addActor(bg);
		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				super.clicked(event, x, y);
				if(!Box.this.isMarked){
					if(Game.mode(GameMode.SINGLE_PLAYER))
						if(Game.currentTurn == Turn.Player)
							markByPlayer();
						else
							markByComputer();
					else if(Game.mode(GameMode.SINGLE_PLAYER_VS_COMPUTER))
						if(Game.currentTurn == Turn.Player)
							markByPlayer();
					Sink.log("Box Clicked "+Box.this.row+Box.this.col);
				}
 			}
		});
	}
	
	public void markByPlayer(){
		isMarked = true;
		Game.turnCounter++;
		addActor(x);
		Game.currentTurn = Turn.Computer;
		type = MarkType.X;
	}
	
	public void markByComputer(){
		isMarked = true;
		Game.turnCounter++;
		addActor(o);
		Game.currentTurn = Turn.Player;
		type = MarkType.O;
	}
}