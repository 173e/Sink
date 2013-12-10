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

import sink.core.Scene;
import sink.core.Sink;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.scenes.scene2d.Group;
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
		//Stage.addActor(this);
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
		Sink.camera.mapOffsetX = mapWidth - Sink.camera.camOffsetX;
		Sink.camera.mapOffsetY = mapHeight - Sink.camera.camOffsetYTop;
	}
	
	public void loadObjects(int no){
		Scene.log("Objects Layer no: "+no);
		MapLayer layer1 = mlayers.get(no);
		mobjects = layer1.getObjects();
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
}
