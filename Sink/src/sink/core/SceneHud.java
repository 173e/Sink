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
package sink.core;

/** The SceneHud Class extends SceneGroup Class so you better add actors to it to make it useful
 * <p>
 * If you want to make any elements/actors/group to move along with the camera like HUD's extend 
 * this class so it will automatically register your element/actor/group to the SceneCamera which
 * will position them to changes in the camera accordingly.
 * <p>
 * @author pyros2097 */
public class SceneHud extends SceneGroup{

	public SceneHud(){
		Sink.camera.registerSceneHud(this);
	}
}
