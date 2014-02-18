package sink.studio.bar;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import sink.studio.core.LafStyle;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.utils.SpringUtils;

enum FileFormat {
	PNG, JPEG;
}

final public class ExplorerToolBar extends JPanel {
	private static final long serialVersionUID = 1L;

    public ExplorerToolBar(){
    	super(new ToolbarLayout(ToolbarLayout.HORIZONTAL));
        initNewProject();
        initFolder();
        initFile();
        initOpen();
        add(new JSeparator(SwingConstants.VERTICAL));
        initPackage();
        initClass();
        initInterface();
        initEnum();
        add(new JSeparator(SwingConstants.VERTICAL));
        initPacker();
	}
    
    @Override
	public void paintComponent(Graphics g){
    	sink.studio.core.LafStyle.drawHorizontalBar(g, getWidth (), getHeight ());
        // Extra
        g.setColor(Color.GRAY);
        g.drawLine(0, 0, getWidth(), 0);
	}
    
    void initNewProject(){
        final JList<String> list = new JList<String>(new String[]{"New Project", "Import Project"});
        list.setVisibleRowCount(4);
        list.setSelectedIndex(0);
        final JPanel panel =  new JPanel(new BorderLayout());
        panel.add(list, BorderLayout.NORTH);
        final JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Select target directory");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        JButton btn = LafStyle.createExplorerToolButton("newprj", "New Project", panel, "OK", null);
        btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fd1 = new FileDialog((Frame)null, "New Project", FileDialog.LOAD);
				fd1.setFile("*.");
				fd1.setVisible(true);
				String filename = fd1.getDirectory();
				if (filename == null)
					  System.out.println("You cancelled the choice");
					else
					  System.out.println("You chose " + filename);
			}
	     });
        add(btn);
	}
    
    void initFolder(){
    	final JTextField nameField = new JTextField ();
        add(LafStyle.createExplorerToolButton("newfolder", "New Folder", nameField, "OK", null));
	}
    
    void initFile(){
    	final JTextField nameField = new JTextField ();
    	add(LafStyle.createExplorerToolButton("newfile", "New File", nameField, "OK", null));
    }
    
    void initOpen(){
    	add(LafStyle.createExplorerToolButton("eopen", "Open File", new JPanel(), "OK",new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//for(File f: filePanel.getSelectedFiles())
				//	Content.addTab(f);
			}
		 }));
	}
    
    void initPackage(){
    	final JTextField nameField = new JTextField ();
        add(LafStyle.createExplorerToolButton("newpack", "New Package", nameField, "OK", null));
    }
    
    void initClass(){
    	final JTextField nameField = new JTextField ();
    	add(LafStyle.createExplorerToolButton("newclass", "New Class", nameField, "OK", null));
    }
    
    void initInterface(){
    	final JTextField nameField = new JTextField ();
 		add(LafStyle.createExplorerToolButton("newinterface", "New Interface", nameField, "OK", null));
    }
    
    void initEnum(){
    	final JTextField nameField = new JTextField ();
 		add(LafStyle.createExplorerToolButton("newenum", "New Enum", nameField, "OK", null));
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
                6, 4, //rows, cols
                5, 5, //initialX, initialY
                5, 5);//xPad, yPad
    	add(LafStyle.createExplorerToolButton("packer", "PACK SETTINGS", content, "Pack All", null));
    }
    
    void createRow(JPanel content, String title, Component b){
    	content.add(new JLabel(title));
    	content.add(b);
    }
}