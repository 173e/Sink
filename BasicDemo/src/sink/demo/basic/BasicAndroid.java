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

package sink.demo.basic;

import sink.core.Config;


public class BasicAndroid {
	
}
/*public class MainActivity extends AndroidApplication {
	 import android.os.Bundle;
import android.os.Handler;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
	 
	 public static final int MESSAGE_STATE_CHANGE = 1;
	 public static final int MESSAGE_READ = 2;
	 public static final int MESSAGE_WRITE = 3;
	 public static final int MESSAGE_DEVICE_NAME = 4;
	 public static final int MESSAGE_TOAST = 5;
	 
	 public static final String DEVICE_NAME = "device_name";
	 public static final String TOAST = "toast";
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        BluetoothManager btm = new BluetoothManager(this, new Handler());
        btm.getAdapter();
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = Config.useAccelerometer;
        cfg.useGL20 = Config.useGL20;
        cfg.useCompass = Config.useCompass;
        cfg.hideStatusBar = Config.hideStatusBar;
        cfg.useWakelock =  Config.useWakelock;
        initialize(new Engine(), cfg);
    }
}
*/