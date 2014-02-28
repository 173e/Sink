package sink.studio.panel;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import sink.studio.core.Content;
import sink.studio.core.Export;
import sink.studio.core.Style;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.AlignPanel;
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
	    initProjectInfo();
	    if(Content.projectExists())
	    	updateProjectConfig();
	}

	
    void initProjectInfo(){
    	JPanel content1 = new JPanel(new SpringLayout());
    	UIUtils.setUndecorated(content1, true);
    	
    	createRow(content1, "Game Title", titleField);
    	createRow(content1, "Target Width", targetWidthField);
    	createRow(content1, "Target Height", targetHeightField);
    	createRow(content1, "Screen Width", screenWidthField);
    	createRow(content1, "Screen Height", screenHeightField);
    	createRow(content1, "X Position", xField);
    	createRow(content1, "Y Position", yField);
    	createRow(content1, "Audio Count", audioField);
    	createRow(content1, "Icon Location", iconField);
    	createRow(content1, "First Scene", firstSceneField);
    	
    	
    	JPanel content2 = new JPanel(new SpringLayout());
    	UIUtils.setUndecorated(content2, true);
    	createRow(content2, "Show Icon",  iconSwitch);
    	createRow(content2, "Resizeable", resizeableSwitch);
    	createRow(content2, "Force Exit", forceExitSwitch);
    	createRow(content2, "Full Screen", fullScreenSwitch);
    	
    	createRow(content2, "Use GL20", useGL20Switch);
    	createRow(content2, "vSync", vSyncSwitch);
    	createRow(content2, "Disable Audio", disableAudioSwitch);
    	createRow(content2, "Aspect Ratio", keepAspectRatioSwitch);
    	
    	createRow(content2, "Is Jar", isJarSwitch);
    	//createRow(content2, "Use Cloud", useCloudSwitch);
    	createRow(content2, "Show FPS", showFPSSwitch);
    	createRow(content2, "Show Logger", showLoggerSwitch);
    	createRow(content2, "Logging", loggingEnabledSwitch);
    	
    	JPanel content3 = new JPanel(new SpringLayout());
    	UIUtils.setUndecorated(content3, true);
    	createRow(content3, "Use Accelerometer", new WebSwitch());
    	createRow(content3, "Use Compass", new WebSwitch());
    	createRow(content3, "Use WakeLock", new WebSwitch());
    	createRow(content3, "Hide Status Bar", new WebSwitch());
    	
       	JPanel content4 = new JPanel(new SpringLayout());
    	createRow(content4, "Encoding Format", new JComboBox<String>(new String[]{"RGBA8888", "RGBA4444", "RGB888", "RGB565", 
    			"Alpha", "LuminanceAlpha", "Intensity"}));
    	createRow(content4, "Min Filter",  new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest",
    			"MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content4, "Output Format", new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content4, "Mag Filter", new JComboBox<String>(new String[]{"png", "jpg"}));
    	
    	JComboBox<String> wcb1 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb1.addItem(""+(2<<i));
    	createRow(content4, "Min Page Width", wcb1);
    	createRow(content4, "PaddingX", new JComboBox<String>());
    	JComboBox<String> wcb2 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb2.addItem(""+(2<<i));
    	createRow(content4, "Min Page Height", wcb2);
    	createRow(content4, "PaddingY", new JComboBox<String>());
    	
    	JComboBox<String> wcb3 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb3.addItem(""+(2<<i));
    	createRow(content4, "Max Page Width", wcb3);
    	createRow(content4, "ClampX", new JComboBox<String>());
    	JComboBox<String> wcb4 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb4.addItem(""+(2<<i));
    	createRow(content4, "Max Page Height", wcb4);
    	createRow(content4, "ClampY", new JComboBox<String>());
    	
    	SpringUtils.makeCompactGrid(content1, 10, 2, 10, 0, 10, 0);
    	SpringUtils.makeCompactGrid(content2, 4, 6, 10, 0, 10, 0);
    	SpringUtils.makeCompactGrid(content3, 4, 2, 10, 0, 10, 0);
    	SpringUtils.makeCompactGrid(content4, 6, 4, 10, 0, 10, 5);
    	
    	add(content1);
    	add(content2);
    	addSpace();
    	add(Style.createHeaderLabel("Android Settings"));
    	add(content3);
    	addSpace();
    	add(Style.createHeaderLabel("Texture Packer"));
    	add(content4);
    	addSpace();
    }
    
    void createRow(JPanel content, String title, Component b){
    	JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		content.add(label);
		if(b instanceof WebSwitch)
			content.add(new AlignPanel(b, AlignPanel.LEFT, AlignPanel.VERTICAL));
		else
			content.add(b);
    }
    
    public void updateProjectConfig(){
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
    
    public void addSpace(){
		add(new JLabel("       "));
	}
	
	public void addSeparator(){
		add(new JSeparator(SwingConstants.HORIZONTAL));
	}
}