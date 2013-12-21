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

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MapTile extends MapActor{
	protected TextureRegion tileImage;
	protected int index;
	
	public MapTile(TextureRegion region, int x, int y, int in, int tileSize){
		super(x,y, tileSize);
		index = in;
		this.tileSize = tileSize;
		if(region != null){
			tileImage = region;
			setSize(tileImage.getRegionWidth(), tileImage.getRegionHeight());
		}
	}
	
	@Override
	public void draw(Batch batch, float parentAlpha){
		super.draw(batch, parentAlpha);
		batch.setColor(getColor());		// WARNING THIS IS THE ORIGINAL TILE SIZE DONOT CHANGE
		if(tileImage != null)
			batch.draw(tileImage, getX(), getY(), this.tileSize, this.tileSize);
	}
}
