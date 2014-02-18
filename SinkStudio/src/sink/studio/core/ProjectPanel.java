package sink.studio.core;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.VerticalFlowLayout;

public class ProjectPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JPanel rowsPanel;
	JTextField titleField;

	public ProjectPanel(){
		super(new VerticalFlowLayout());
		rowsPanel = new JPanel(new GridBagLayout());
		titleField = new JTextField();
		addRow("Title", titleField);
		addRow("Show Icon", titleField);
		addRow("Target Width", titleField);
		addRow("Target Height", titleField);
		add(rowsPanel);
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