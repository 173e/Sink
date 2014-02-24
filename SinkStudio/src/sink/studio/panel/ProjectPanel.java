package sink.studio.panel;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import sink.studio.core.Content;
import sink.studio.core.Export;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.SpringUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.NumberTextField;
import web.laf.lite.widget.WebSwitch;

public class ProjectPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public static JTextField titleField = new JTextField();
	public static NumberTextField targetWidthField = new NumberTextField();
	public static NumberTextField targetHeightField = new NumberTextField();
	public static NumberTextField screenWidthField = new NumberTextField();
	public static NumberTextField screenHeightField = new NumberTextField();
	public static NumberTextField xField = new NumberTextField();
	public static NumberTextField yField = new NumberTextField();
	public static NumberTextField audioField = new NumberTextField();
	public static JTextField iconField = new JTextField();
	public static JTextField firstSceneField = new JTextField();
	
	public static WebSwitch iconSwitch = new WebSwitch();
	public static WebSwitch resizeableSwitch = new WebSwitch();
	public static WebSwitch forceExitSwitch = new WebSwitch();
	public static WebSwitch fullScreenSwitch = new WebSwitch();
	public static WebSwitch useGL20Switch = new WebSwitch();
	
	public static WebSwitch vSyncSwitch = new WebSwitch();
	public static WebSwitch disableAudioSwitch = new WebSwitch();
	public static WebSwitch keepAspectRatioSwitch = new WebSwitch();
	public static WebSwitch isJarSwitch = new WebSwitch();
	public static WebSwitch useCloudSwitch = new WebSwitch();
	
	public static WebSwitch showFPSSwitch = new WebSwitch();
	public static WebSwitch showLoggerSwitch = new WebSwitch();
	public static WebSwitch loggingEnabledSwitch = new WebSwitch();

	public ProjectPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, true);
	    initPacker();
	    if(Content.checkProjectExists())
	    	updateProjectConfig();
	}

	
    void initPacker(){
    	JPanel content = new JPanel(new SpringLayout());
    	createRow(content, "Title", titleField);
    	createRow(content, "Target Width", targetWidthField);
    	createRow(content, "Target Height", targetHeightField);
    	createRow(content, "Screen Width", screenWidthField);
    	createRow(content, "Screen Height", screenHeightField);
    	createRow(content, "X", xField);
    	createRow(content, "Y", yField);
    	createRow(content, "Audio Buffer Count", audioField);
    	createRow(content, "Icon Location", iconField);
    	createRow(content, "First Scene Class Name", firstSceneField);
    	
    	
    	createRow(content, "Show Icon",  iconSwitch);
    	createRow(content, "Resizeable", resizeableSwitch);
    	createRow(content, "Force Exit", forceExitSwitch);
    	createRow(content, "Full Screen", fullScreenSwitch);
    	createRow(content, "Use GL20", useGL20Switch);
    	
    	createRow(content, "vSync", vSyncSwitch);
    	createRow(content, "Disable Audio", disableAudioSwitch);
    	createRow(content, "Keep Aspect Ratio", keepAspectRatioSwitch);
    	createRow(content, "Is Jar", isJarSwitch);
    	createRow(content, "Use Cloud", useCloudSwitch);
    	
    	createRow(content, "Show FPS", showFPSSwitch);
    	createRow(content, "Show Logger", showLoggerSwitch);
    	createRow(content, "Logging Enabled", loggingEnabledSwitch);
    	
    	/*createRow(content, "Use Accelerometer", new WebSwitch());
    	createRow(content, "Use Compass", new WebSwitch());
    	createRow(content, "Use WakeLock", new JTextField());
    	createRow(content, "Hide Status Bar", new WebSwitch());*/
    	
    	SpringUtils.makeCompactGrid(content,
                23, 2, //rows, cols
                10, 0, //initialX, initialY
                10, 0);//xPad, yPad
    	add(content);
    }
    
    void createRow(JPanel content, String title, Component b){
    	//JPanel hoz = new JPanel(new HorizontalFlowLayout());
    	JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		content.add(label);
		content.add(b);
    	//hoz.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.black));
    }
    
    void updateProjectConfig(){
    	JsonReader jsonReader = new JsonReader();
    	JsonValue value = jsonReader.parse(Export.readFile("config.json"));
    	titleField.setText(value.get("title").asString());
    	targetWidthField.setText(value.get("targetWidth").asString());
    	targetHeightField.setText(value.get("targetHeight").asString());
    	screenWidthField.setText(value.get("screenWidth").asString());
    	screenHeightField.setText(value.get("screenHeight").asString());
    	xField.setText(value.get("x").asString());
    	yField.setText(value.get("y").asString());
    	audioField.setText(value.get("audio").asString());
    	iconField.setText(value.get("icon").asString());
    	firstSceneField.setText(value.get("firstScene").asString());
    	
    	iconSwitch.setSelected(value.getBoolean("showIcon"));
    	resizeableSwitch.setSelected(value.getBoolean("resizeable"));
    	forceExitSwitch.setSelected(value.getBoolean("forceExit"));
    	fullScreenSwitch.setSelected(value.getBoolean("fullScreen"));
    	useGL20Switch.setSelected(value.getBoolean("useGL20"));
    	
    	vSyncSwitch.setSelected(value.getBoolean("vSync"));
    	disableAudioSwitch.setSelected(value.getBoolean("disableAudio"));
    	keepAspectRatioSwitch.setSelected(value.getBoolean("keepAspectRatio"));
    	isJarSwitch.setSelected(value.getBoolean("isJar"));
    	useCloudSwitch.setSelected(value.getBoolean("useCloud"));
    	
    	showFPSSwitch.setSelected(value.getBoolean("showFPS"));
    	showLoggerSwitch.setSelected(value.getBoolean("showLogger"));
    	loggingEnabledSwitch.setSelected(value.getBoolean("loggingEnabled"));
    }
}