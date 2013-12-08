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

package sink.map;

import sink.core.Config;
import sink.core.Scene;
import sink.core.Stage;

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
		Stage.addActor(this);
	}
	
	public void loadLayer(int layerNo){
		Scene.log("Tiles Layer no: "+layerNo);
		TiledMapTileLayer layer = (TiledMapTileLayer)mlayers.get(layerNo);
		NoOfColumns =layer.getWidth();
		Scene.log("MapColumns: "+NoOfColumns);
		NoOfRows = layer.getHeight();
		Scene.log("MapRows: "+NoOfRows);
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
		Stage.camera.mapOffsetX = mapWidth - Stage.camera.camOffsetX;
		Stage.camera.mapOffsetY = mapHeight - Stage.camera.camOffsetYTop;
	}
	
	public void loadObjects(int no){
		Scene.log("Objects Layer no: "+no);
		MapLayer layer1 = mlayers.get(no);
		mobjects = layer1.getObjects();
	}
	
	@Override
	public void act(float delta){
		super.act(delta);
		if(hasControl){
			if(Config.usePan) Stage.camera.panCameraWithMouse();
			if(Config.useKeyboard) Stage.camera.panCameraWithKeyboard();
		}
		if(currentFocus != null){
			Stage.camera.follow(currentFocus);
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
			//if(hasControl)
				//if(Config.isDrag)dragCam((int)x, (int)-y);
		}
		
		@Override
		public void touchUp(InputEvent event, float x, float y, int pointer, int button){
			super.touchUp(event, x, y, pointer, button);
			//if(hasControl)
			//	last.set(-1, -1, -1);
		}
	};
	
	public void touchPad(float xPercent, float yPercent){
		//MapUnit player = Map.$units.get(0);
		//player.setX(player.getX() + xPercent*5);
		//player.setY(player.getY() + yPercent*5);
	}
}
