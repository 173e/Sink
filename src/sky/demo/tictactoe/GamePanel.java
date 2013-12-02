package sky.demo.tictactoe;

import java.util.Random;

import sky.engine.core.Asset;
import sky.engine.core.Panel;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GamePanel extends Panel{
	Box[][] boxes;
	public static int turnCounter;
	
	public GamePanel(){
		super();
	}
	
	@Override
	public void init(){
		boxes = new Box[3][3];
		for(int i = 0;i < 3;i++){
			for(int j = 0;j < 3;j++){
				Box box = new Box(i, j);
				boxes[i][j] = box;
				addActor(box, box.getWidth()*j, box.getHeight()*i);
			}
		}
		addActor(Panel.backBtn, 50, 300);
	}
	
	public void reset(){
		Panel.$log("Reset");
		turnCounter = 0;
		this.clear();
		init();
	}
	
	public void update(){
		AI();
		if(turnCounter < 9){
			for(int i=0; i<3;i++){
				checkForRowY(i);
				checkForRowX(i);
			}
			checkForDiagonal();
		}
		else{
			Panel.$log("Draw Match");
			reset();
		}
	}
	
	int random1 = 0;
	int random2 = 0;
	Random rand = new Random();
	void AI(){
		if(GameMan.$mode(GameMode.SINGLE_PLAYER_VS_COMPUTER)){
			if(GameMan.currentTurn == Turn.Computer){
				Panel.$log("AI");
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
		Panel.$log("Player Win");
		reset();
	}
	
	public void computerWin(){
		Panel.$log("Computer Win");
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

class Box extends Group{
	private final int row, col;
	private final Image bg = new Image(Asset.$tex("square"));
	private final Image x = new Image(Asset.$tex("x"));
	private final Image o = new Image(Asset.$tex("o"));
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
					if(GameMan.$mode(GameMode.SINGLE_PLAYER))
						if(GameMan.currentTurn == Turn.Player)
							markByPlayer();
						else
							markByComputer();
					else if(GameMan.$mode(GameMode.SINGLE_PLAYER_VS_COMPUTER))
						if(GameMan.currentTurn == Turn.Player)
							markByPlayer();
					Panel.$log("Box Clicked "+Box.this.row+Box.this.col);
				}
 			}
		});
	}
	
	public void markByPlayer(){
		isMarked = true;
		GamePanel.turnCounter++;
		addActor(x);
		GameMan.currentTurn = Turn.Computer;
		type = MarkType.X;
	}
	
	public void markByComputer(){
		isMarked = true;
		GamePanel.turnCounter++;
		addActor(o);
		GameMan.currentTurn = Turn.Player;
		type = MarkType.O;
	}
}