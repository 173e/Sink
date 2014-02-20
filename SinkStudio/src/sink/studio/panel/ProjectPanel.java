package sink.studio.panel;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.SpringUtils;

public class ProjectPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JPanel rowsPanel;
	JTextField titleField;

	public ProjectPanel(){
		super(new VerticalFlowLayout());
		/*rowsPanel = new JPanel(new GridBagLayout());
		titleField = new JTextField();
		JTextField field1 = new JTextField("1234.56");
		JTextField field2 = new JTextField("9876.54");
		addRow("Title", field1);
		addRow("Show Icon", field2);
		addRow("Target Width", titleField);
		addRow("Target Height", titleField);
		add(rowsPanel);
		String[] items = {"One", "Two", "Three", "Four", "Five"};
	    JComboBox combo = new JComboBox(items);*/
	    initPacker();
	}

	void addRow (String text, JComponent com) {
		JPanel hoz = new JPanel(new HorizontalFlowLayout());
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		hoz.add(label);
		hoz.add(com);
		hoz.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.black));
		rowsPanel.add(hoz, new GridBagConstraints(0, -1, 1, 1, 1, 0, GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
			new Insets(0, 0, 0, 0), 0, 0));
	}
	
    void initPacker(){
    	JPanel content = new JPanel(new SpringLayout());
    	createRow(content, "Encoding Format", new JComboBox<String>(new String[]{"RGBA8888", "RGBA4444", "RGB888", "RGB565", 
    			"Alpha", "LuminanceAlpha", "Intensity"}));
    	createRow(content, "Min Filter",  new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest",
    			"MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content, "Output Format", new JComboBox<String>(new String[]{"Nearest", "Linear", "MipMap", "MipMapNearestNearest", "MipMapNearestLinear", "MipMapLinearNearest", "MipMapLinearLinear"}));
    	createRow(content, "Mag Filter", new JComboBox<String>(new String[]{"png", "jpg"}));
    	
    	JComboBox<String> wcb1 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb1.addItem(""+(2<<i));
    	createRow(content, "Min Page Width", wcb1);
    	createRow(content, "PaddingX", new JComboBox<String>());
    	JComboBox<String> wcb2 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb2.addItem(""+(2<<i));
    	createRow(content, "Min Page Height", wcb2);
    	createRow(content, "PaddingY", new JComboBox<String>());
    	
    	JComboBox<String> wcb3 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb3.addItem(""+(2<<i));
    	createRow(content, "Max Page Width", wcb3);
    	createRow(content, "ClampX", new JComboBox<String>());
    	JComboBox<String> wcb4 = new JComboBox<String>();
    	for(int i= 0; i <= 10; i++)
    			wcb4.addItem(""+(2<<i));
    	createRow(content, "Max Page Height", wcb4);
    	createRow(content, "ClampY", new JComboBox<String>());
    	SpringUtils.makeCompactGrid(content,
                12, 2, //rows, cols
                50, 5, //initialX, initialY
                30, 5);//xPad, yPad
    	add(content);
    }
    
    void createRow(JPanel content, String title, Component b){
    	JPanel hoz = new JPanel(new HorizontalFlowLayout());
    	JLabel label = new JLabel(title);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		content.add(label);
		content.add(b);
    	//hoz.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, java.awt.Color.black));
    }
}

/*
{
"title": "Warsong",
"showIcon": true,
"iconLocation": "icon/icon_32.png",
"targetWidth": 320,
"targetHeight": 224,
"screenWidth": 800,
"screenHeight": 480,
"x": 280,
"y": 100,
"resize": false,
"forceExit": false,
"fullScreen": false,
"useGL20": false,
"vSync": false,
"disableAudio": false,
"audioBufferCount": 20,
"isJar": false,
"keepAspectRatio": false,

"useAccelerometer": false,
"useCompass": false,
"hideStatusBar": true,
"useWakelock": true,
"useCloud": true,

"showFps": true,
"showLogger": false,
"loggingEnabled": true,

"firstScene": "splash",
"firstSceneClassName": "sky.warsong.scene.SplashScene"
}
*/