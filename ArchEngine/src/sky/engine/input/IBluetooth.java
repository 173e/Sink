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

package sky.engine.input;
 
public interface IBluetooth {
    public void enableBluetooth();
    public void enableDiscoveribility();
    public void discoverDevices();
    public void stopDiscovering();
    public boolean startServer();
    public void connectToServer();
    public String getTest();
    public void sendMessage(String message);
    public String getMessage();
    public boolean isConnected();
    public boolean canConnect();
    public void switchToNextDevice();
    public void switchToPrevDevice();
    public String getDevice();
    public void stop();
    public boolean isFirst();
    public boolean isLast();
    public boolean isDiscovering();
}