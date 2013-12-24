/*******************************************************************************
 * Copyright 2014 pyros2097
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

import sink.core.SceneGroup;
import sink.core.Sink;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSets;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.utils.Array;

/** The Map Class
 * <p>
 * The Map is a SceneGroup which automatically loads all the tiles and arranges them accordingly it is
 * highly recommended that you override the loadLayer method and customize the map
 * <p>
 * @author pyros2097 */
public class Map extends SceneGroup{
	protected final int tileSize;
	
	/* List of MapLayers */
	protected MapLayers mlayers;
	protected MapObjects mobjects;
	
	public static int NoOfColumns;
	public static int NoOfRows;
	protected float mapWidth;
	protected float mapHeight;
	
	public static TiledMapTileSets tileSets;
	private Array<MapActor[][]> tiles = new Array<MapActor[][]>();
	
	public Map(TiledMap map, int tileSize){
		setPosition(0, 0);
		setOrigin(0, 0);
		this.tileSize = tileSize;
		mlayers  = map.getLayers();
		tileSets = map.getTileSets();
	}
	
	public void loadLayer(int layerNo){
		Sink.log("Tiles Layer no: "+layerNo);
		TiledMapTileLayer layer = (TiledMapTileLayer)mlayers.get(layerNo);
		NoOfColumns =layer.getWidth();
		Sink.log("MapColumns: "+NoOfColumns);
		NoOfRows = layer.getHeight();
		Sink.log("MapRows: "+NoOfRows);
		tiles .add(new MapActor[NoOfRows][NoOfColumns]);
		for(int i=0; i<NoOfRows; i++)
			for(int j=0; j<NoOfColumns; j++){
				Cell c = layer.getCell(j, i);
				if(c != null){
					tiles.get(layerNo)[i][j] = new MapActor(c.getTile().getTextureRegion(),
							j, i, c.getTile().getId(), tileSize);
					addActor(tiles.get(layerNo)[i][j]);
				}
				else{
					tiles.get(layerNo)[i][j] = new MapActor((TextureRegion)null,j, i, 0, tileSize);
					addActor(tiles.get(layerNo)[i][j]);
				}
		}
		mapWidth = tileSize * NoOfColumns;
		mapHeight = tileSize * NoOfRows;
		Sink.mapOffsetX = mapWidth - Sink.camOffsetX;
		Sink.mapOffsetY = mapHeight - Sink.camOffsetYTop;
	}
	
	public void loadObjects(int no){
		Sink.log("Objects Layer no: "+no);
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
}
