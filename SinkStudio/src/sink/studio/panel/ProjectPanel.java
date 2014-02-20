package sink.studio.panel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.utils.SpringUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.WebSwitch;

public class ProjectPanel extends JPanel{
	private static final long serialVersionUID = 1L;

	public ProjectPanel(){
		super(new VerticalFlowLayout());
		UIUtils.setUndecorated(this, true);
	    initPacker();
	}

	
    void initPacker(){
    	JPanel content = new JPanel(new SpringLayout());
    	createRow(content, "Title", new JTextField(""));
    	createRow(content, "Show Icon",  new WebSwitch());
    	createRow(content, "Icon Location", new JTextField(""));
    	createRow(content, "Target Width", new JTextField(""));
    	createRow(content, "Target Height", new JTextField(""));
    	createRow(content, "Screen Width", new JTextField(""));
    	createRow(content, "Screen Height", new JTextField(""));
    	createRow(content, "X", new JTextField(""));
    	createRow(content, "Y", new JTextField(""));

    	createRow(content, "Resizeable", new WebSwitch());
    	createRow(content, "Force Exit", new WebSwitch());
    	createRow(content, "Full Screen", new WebSwitch());
    	createRow(content, "Use GL20", new WebSwitch());
    	
    	createRow(content, "vSync", new WebSwitch());
    	createRow(content, "Audio Buffer Count", new JTextField());
    	createRow(content, "Disable Audio", new WebSwitch());
    	createRow(content, "Keep Aspect Ratio", new WebSwitch());
    	createRow(content, "Is Jar", new WebSwitch());
    	createRow(content, "Use Cloud", new WebSwitch());
    	
    	createRow(content, "Show FPS", new WebSwitch());
    	createRow(content, "Show Logger", new WebSwitch());
    	createRow(content, "Logging Enabled", new WebSwitch());
    	createRow(content, "First Scene Class Name", new JTextField());
    	
    	/*createRow(content, "Use Accelerometer", new WebSwitch());
    	createRow(content, "Use Compass", new WebSwitch());
    	createRow(content, "Use WakeLock", new JTextField());
    	createRow(content, "Hide Status Bar", new WebSwitch());*/
    	
    	SpringUtils.makeCompactGrid(content,
                23, 2, //rows, cols
                0, 0, //initialX, initialY
                0, 0);//xPad, yPad
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
}