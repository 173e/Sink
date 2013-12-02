/*******************************************************************************
 * Copyright 2013 pyros2097
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package sky.engine.map;

import sky.demo.basic.GameMan;
import sky.engine.core.Config;
import sky.engine.core.Engine;
import sky.engine.core.Panel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;

/** The Map Class
 * <p>
 * The Map is a Group which automatically loads all the tiles and arranges them accordingly
 * <p>
 * @author pyros2097 */
public final class Map extends Group{
	private final int tileSize;
	
	/* List of MapLayers */
	private MapLayers mlayers;
	private MapObjects mobjects;
	
	/* List of all the Tiles of Each Layer */
	private Array<MapTile[][]> layersTiles = new Array<MapTile[][]>();
	public final static ArrayMap<String, MapUnit> $units = new ArrayMap<String, MapUnit>();
	
	
	private int NoOfColumns;
	private int NoOfRows;
	private float mapWidth;
	private float mapHeight;
	private final float camOffsetX = 160f;
	private final float camOffsetYTop = 110f;
	private final float camOffsetYBot = 65f;
	private float mapOffsetX;
	private float mapOffsetY;

	private final Vector3 mousePos = new Vector3();
	private final Vector3 curr = new Vector3();
	private final Vector3 last = new Vector3(-1, -1, -1);
	private final Vector3 delta = new Vector3();
	private final float panSpeed = 5f;
	private final float panXLeftOffset = 100;
	private final float panXRightOffset = Config.SCREEN_WIDTH - 100;
	private final float panYUpOffset = 70;
	private final float panYDownOffset = Config.SCREEN_HEIGHT - 70;
	
	private float deltaCamX = 0;
	private float deltaCamY = 0;
	
	public static MapUnit currentFocus;
	public static MapUnit nearestUnit;
	Image select;
	
	public Map(TiledMap map, int tileSize){
		setPosition(0, 0);
		setOrigin(0, 0);
		this.tileSize = tileSize;
		mlayers  = map.getLayers();
		addController();
		addListener(touchInput);
		Engine.$stage.addActor(this);
	}
	
	public void loadLayer(int layerNo){
		Panel.$log("Tiles Layer no: "+layerNo);
		TiledMapTileLayer layer = (TiledMapTileLayer)mlayers.get(layerNo);
		NoOfColumns =layer.getWidth();
		Panel.$log("MapColumns: "+NoOfColumns);
		NoOfRows = layer.getHeight();
		Panel.$log("MapRows: "+NoOfRows);
		layersTiles.add(new MapTile[NoOfRows][NoOfColumns]);
		for(int i=0; i<NoOfRows; i++)
			for(int j=0; j<NoOfColumns; j++){
				Cell c = layer.getCell(j, i);
				if(c != null){
					layersTiles.get(layerNo)[i][j] = new MapTile(c.getTile().getTextureRegion(),
							j, i, c.getTile().getId(), tileSize);
					addTile(layersTiles.get(layerNo)[i][j]);
				}
				else{
					layersTiles.get(layerNo)[i][j] = new MapTile(null,j, i, 0, tileSize);
					addTile(layersTiles.get(layerNo)[i][j]);
				}
		}
		mapWidth = tileSize * NoOfColumns;
		mapHeight = tileSize * NoOfRows;
		mapOffsetX = mapWidth - camOffsetX;
		mapOffsetY = mapHeight - camOffsetYTop;
	}
	
	public void loadObjects(int no){
		Panel.$log("Objects Layer no: "+no);
		MapLayer layer1 = mlayers.get(no);
		mobjects = layer1.getObjects();
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if(hasControl){
			if(Config.isPan) panCamera();
			if(Config.isKeyboard) checkKeys();
		}
		if(currentFocus != null){
			if(Engine.$camera.position.x < currentFocus.getX() - 10)
				GameMan.addCamX(3);
			else if(Engine.$camera.position.x > currentFocus.getX() + 10)
				GameMan.addCamX(-3);
			else if(Engine.$camera.position.y < currentFocus.getY() - 70)
				GameMan.addCamY(3);
			else if(Engine.$camera.position.y > currentFocus.getY() - 60)
				GameMan.addCamY(-3);
			else
				currentFocus = null;
		}
	}
	
	public float getWidth(){
		return mapWidth;
	}
	
	public float getHeight(){
		return mapHeight;
	}
	
	public int getNoOfColumns(){
		return NoOfColumns;
	}
	
	public int getNoOfRows(){
		return NoOfRows;
	}
	
	public MapObjects getMapObjects(){
		return mobjects;
	}
	
	public MapLayers getMapLayers(){
		return mlayers;
	}
	
	public MapTile[][] getMapLayersTiles(int layerNo){
		return layersTiles.get(layerNo);
	}
	
	public MapTile getTile(int layerNo, int row, int col){
		return layersTiles.get(layerNo)[col][row];
	}

/***********************************************************************************************************
* 								Unit Related Functions												   	   *
************************************************************************************************************/
	public void addTile(MapTile mapTile){
		addActor(mapTile);
	}
	
/***********************************************************************************************************
* 								Controller Related Functions												   	   *
************************************************************************************************************/	
	static boolean hasControl = false;
	
	public static void addController(){
		if(hasControl)
			return;
		hasControl = true;
	}
	
	public static void removeController(){
		if(!hasControl)
			return;
		hasControl = false;
	}
	
	InputListener touchInput = new InputListener(){
		@Override
		public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
			super.touchDown(event, x, y, pointer, button);
			return true;
		}
		
		@Override
		public void touchDragged(InputEvent event, float x, float y, int pointer){
			super.touchDragged(event, x, y, pointer);
			if(hasControl)
				if(Config.isDrag)dragCam((int)x, (int)-y);
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button){
			super.touchUp(event, x, y, pointer, button);
			if(hasControl)
				last.set(-1, -1, -1);
		}
	};
	
	public void touchPad(float xPercent, float yPercent){
		//MapUnit player = Map.$units.get(0);
		//player.setX(player.getX() + xPercent*5);
		//player.setY(player.getY() + yPercent*5);
	}
	
	void checkKeys(){
		if(Gdx.input.isKeyPressed(Keys.LEFT)){
			for(MapUnit u: $units.values()) u.keyLeft();
			if(Engine.$camera.position.x > camOffsetX +5)
				GameMan.addCamX(-panSpeed);
		}
		else if(Gdx.input.isKeyPressed(Keys.RIGHT)){
			for(MapUnit u: $units.values()) u.keyRight();
			if(Engine.$camera.position.x < mapOffsetX - 5)
				GameMan.addCamX(panSpeed);
		}
		else if(Gdx.input.isKeyPressed(Keys.UP)){
			for(MapUnit u: $units.values()) u.keyUp();
			if(Engine.$camera.position.y < mapOffsetY -5)
				GameMan.addCamY(panSpeed);
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN)){
			for(MapUnit u: $units.values()) u.keyDown();
			if(Engine.$camera.position.y > camOffsetYBot +5)
				GameMan.addCamY(-panSpeed);
		}	
	}
	
	void panCamera(){
		mousePos.x = Gdx.input.getX();
		mousePos.y = Gdx.input.getY();
		if(mousePos.x > panXRightOffset && Engine.$camera.position.x < mapOffsetX - 5) GameMan.addCamX(panSpeed);
		else if(mousePos.x < panXLeftOffset && Engine.$camera.position.x > camOffsetX +5)  GameMan.addCamX(-panSpeed);
		else if(mousePos.y < panYUpOffset && Engine.$camera.position.y < mapOffsetY -5) GameMan.addCamY(panSpeed);
		else if(mousePos.y > panYDownOffset && Engine.$camera.position.y > camOffsetYBot +5) GameMan.addCamY(-panSpeed);
	}
	
	void dragCam(int x, int y){
		Engine.$camera.unproject(curr.set(x, y, 0));
		if (!(last.x == -1 && last.y == -1 && last.z == -1)) {
			Engine.$camera.unproject(delta.set(last.x, last.y, 0));
			delta.sub(curr);
			deltaCamX = delta.x + Engine.$camera.position.x;
			deltaCamY = delta.y + Engine.$camera.position.y;
			if(deltaCamX > camOffsetX && deltaCamX < mapOffsetX)
				GameMan.addCamX(delta.x);
			if(deltaCamY > camOffsetYBot && deltaCamY < mapOffsetY)
				GameMan.addCamY(delta.y);		
		}
		last.set(x, y, 0);
	}
}
