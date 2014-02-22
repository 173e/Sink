package sink.studio.core;

import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import sink.studio.panel.FilePanel;
import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.ButtonPopup;
import web.laf.lite.popup.NotificationManager;
import web.laf.lite.popup.PopupWay;
import web.laf.lite.utils.SwingUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.Register;
import web.laf.lite.widget.WebButtonGroup;
import web.laf.lite.widget.WebSwitch;

final public class ToolBar extends JPanel {
	private static final long serialVersionUID = 1L;
	LCD2 lcd;
    
	public ToolBar(){
		super(new ToolbarLayout());
        initAbout();
        initProject();
        initOpen();
        //initFile();
       // initEdit();
        initOptions();
        initStyle();
        addSeparator();
        
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        initLCD();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        addSpace();
        
        addSeparator();
        initView();
	}
	
	@Override
	public void paintComponent(Graphics g){
		LafStyle.drawHorizontalBar(g, getWidth (), getHeight ());
		LafStyle.drawBottomBorder(g, getWidth (), getHeight ());
	}
	
	void initLCD(){
		lcd = new LCD2();
        final JPanel pan = new JPanel(new VerticalFlowLayout(FlowLayout.CENTER));
        pan.setOpaque(false);
        pan.add(lcd);
        add(pan);
	}
	
	void initAbout(){
		add(LafStyle.createToolPanel("", "sabout", null));
		UIUtils.setUndecorated(LafStyle.btnMap.get("sabout"), true);
        final ButtonPopup menu = new ButtonPopup(LafStyle.btnMap.get("sabout"), PopupWay.downRight);
        menu.setRound(0);
        final JPanel popupContent = new JPanel (new VerticalFlowLayout(5, 10));
        final JButton licenseBtn = new JButton("Apache License v2.0");
        final JPanel author = new JPanel(new HorizontalFlowLayout());
        final JPanel hoz = new JPanel(new HorizontalFlowLayout());
        popupContent.add(UIUtils.setBoldFont(new JLabel("SkyCode v0.39")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        author.setOpaque(false);
        author.add(new JLabel("Created by: pyros2097"));
        author.add(licenseBtn);
        popupContent.add(author);
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.add(new JLabel("", Asset.icon("slibGDX"), JLabel.LEADING));
        popupContent.add(new JLabel("", Asset.icon("sweblaf"), JLabel.LEADING));
        hoz.setOpaque(false);
        hoz.add(new JLabel("", Asset.icon("shierologo"), JLabel.LEADING));
        hoz.add(new JLabel("", Asset.icon("stexturepacker"), JLabel.LEADING));
        popupContent.add(hoz);
        popupContent.setOpaque(false);
        menu.setContent (popupContent);
	}
	
	void initFile(){
		final JButton menuBtn1 = LafStyle.createMenuButton("File");
        final ButtonPopup menu = new ButtonPopup(menuBtn1,PopupWay.downRight);
        menu.setRound(0);
        JPanel popupContent = new JPanel ( new VerticalFlowLayout ( 5, 5 ) );
        popupContent.setPreferredSize(new Dimension(200, 200));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     ToolBar")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     Explorer")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.setOpaque(false);
        menu.setContent(popupContent);
        add(menuBtn1);
	}
        
    void initEdit(){
    	final JButton menuBtn2 = LafStyle.createMenuButton("Edit");
        final ButtonPopup menu2 = new ButtonPopup(menuBtn2,PopupWay.downRight);
        menu2.setRound(0);
        JPanel popupContent2 = new JPanel ( new VerticalFlowLayout ( 5, 5 ) );
        popupContent2.setPreferredSize(new Dimension(200, 200));
        popupContent2.add(UIUtils.setBoldFont(new JLabel("     ToolBar")));
        popupContent2.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent2.add(UIUtils.setBoldFont(new JLabel("     Explorer")));
        popupContent2.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent2.setOpaque(false);
        menu2.setContent(popupContent2);
        add(menuBtn2);
	}
	
	public static void run(){
		if(!LafStyle.btnMap.get("stop").isEnabled()){
			LafStyle.btnMap.get("stop").setEnabled(true);
			LafStyle.btnMap.get("run").setEnabled(false);
		}
	}
	
	public static void stop(){
		if(!LafStyle.btnMap.get("run").isEnabled()){
			LafStyle.btnMap.get("stop").setEnabled(false);
			LafStyle.btnMap.get("run").setEnabled(true);
		}
	}
	
	void initProject(){
		JButton prj = LafStyle.createMenuButton("Project");
		prj.setIcon(Asset.icon("newprj"));
		add(prj);
	}
	
	void initOpen(){
		JButton open = LafStyle.createMenuButton("Open");
		open.setIcon(Asset.icon("eopen"));
		open.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog((Frame)null, "Open Sink Project", FileDialog.LOAD);
				fd.setVisible(true);
				String filename = fd.getDirectory()+fd.getFile();
				SinkStudio.log("Opening Project: "+filename);
				if(filename != null)
					if(!filename.isEmpty() && filename.endsWith(".jar"))
						if(new File(filename).exists()){
							String oldprj = Content.projectFile;
							Content.projectFile = filename;
							if(Export.readFile("config.json").isEmpty()){
								SinkStudio.log("Could'nt Open Project: "+filename);
								NotificationManager.showNotification("This is not a valid Sink Project");
								Content.projectFile = oldprj;
							}
							else{
								SinkStudio.log("Opened Project: "+filename);
								FilePanel.updateList();
								SinkStudio.log("Current Project: "+filename);
							}
						}
			}
		});
		add(open);
	}

	void initOptions(){
	    add(LafStyle.createToolPanel("Options", "options", null));
        final ButtonPopup menu = new ButtonPopup(LafStyle.btnMap.get("options"),PopupWay.downRight);
        UIUtils.setRound(LafStyle.btnMap.get("options"), 10);
        JPanel popupContent = new JPanel ( new VerticalFlowLayout ( 5, 5 ) );
        // ToolBar Stuff
        popupContent.add(UIUtils.setBoldFont(new JLabel("     ToolBar")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        WebSwitch left = new WebSwitch(); 
        WebSwitch right = new WebSwitch();
        WebSwitch status = new WebSwitch();
        popupContent.add(menuItem(left, "Show Left SideBar"));
        popupContent.add(menuItem(right, "Show Right SideBar"));
        popupContent.add(menuItem(status, "Show StatusBar"));
        if(!left.load()) Frame.toggleLeftSideBar();
        if(!right.load()) Frame.toggleRightSideBar();
        if(!status.load()) Frame.toggleStatusBar();
        left.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleLeftSideBar();
     		}
        });
        right.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleRightSideBar();
     		}
        });
        status.addActionListener(new ActionListener(){
     		@Override
     		public void actionPerformed(ActionEvent arg0) {
     			Frame.toggleStatusBar();
     		}
        });
  
        // Editor Stuff
        popupContent.add(UIUtils.setBoldFont(new JLabel("     Editor")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        final WebSwitch showLineNumbers = new WebSwitch();
        final WebSwitch showMargin = new WebSwitch();
        final WebSwitch codeFold = new WebSwitch();
        final WebSwitch showEol = new WebSwitch();
        final WebSwitch paintTabLines = new WebSwitch();
        final WebSwitch showWhitespaces = new WebSwitch();
        popupContent.add(menuItem(showLineNumbers, "Show Line Numbers"));
        popupContent.add(menuItem(showMargin, "Show Margin"));
        popupContent.add(menuItem(showEol, "Show Eol"));
        popupContent.add(menuItem(paintTabLines, "Show Tab"));
        popupContent.add(menuItem(codeFold, "Code Folding"));
        popupContent.add(menuItem(showWhitespaces, "Show WhiteSpace"));
        
    	Content.editorScroll.setLineNumbersEnabled(showLineNumbers.isSelected());
    	Content.editorScroll.setIconRowHeaderEnabled(showMargin.isSelected());
        Content.editor.setCodeFoldingEnabled(codeFold.isSelected());
        Content.editor.setPaintTabLines(paintTabLines.isSelected());
    	Content.editor.setWhitespaceVisible(showWhitespaces.isSelected());
    	Content.editor.setEOLMarkersVisible(showEol.isSelected());
    	
    	
    	showLineNumbers.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editorScroll.setLineNumbersEnabled(showLineNumbers.isSelected());
			}
        });
    	
    	showMargin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editorScroll.setIconRowHeaderEnabled(showMargin.isSelected());
			}
        });
        
        codeFold.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setCodeFoldingEnabled(codeFold.isSelected());
			}
        });
        showEol.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setEOLMarkersVisible(showEol.isSelected());
			}
        });
        paintTabLines.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setPaintTabLines(paintTabLines.isSelected());
			}
        });
        showWhitespaces.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.editor.setWhitespaceVisible(showWhitespaces.isSelected());
			}
        });
        popupContent.setOpaque(false);
        menu.setContent(popupContent);
	}
	
	JPanel menuItem(final WebSwitch sw, String text){
		final JLabel label = new JLabel("   "+text);
		label.addMouseListener( new MouseAdapter (){
            public void mousePressed ( MouseEvent e ){
                if(SwingUtils.isLeftMouseButton ( e ) ){
                	sw.requestFocusInWindow();
                	sw.setSelected(!sw.isSelected());
                }
            }
        });
		JPanel pan = new JPanel(new ToolbarLayout());
		pan.setOpaque(false);
		pan.add(label, ToolbarLayout.START);
		pan.add(sw, ToolbarLayout.END);
        return pan;
	}
	
	void initStyle(){
		add(LafStyle.createToolPanel("Style", "style", null));
		UIUtils.setRound(LafStyle.btnMap.get("style"), 10);
        final ButtonPopup wbp = new ButtonPopup(LafStyle.btnMap.get("style"),PopupWay.downRight);
        JPanel lafStyles = new JPanel ( new VerticalFlowLayout (5, 5) );
        lafStyles.setOpaque(false);
        JLabel lafTitle = new JLabel("    LookAndFeel");
        lafTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        lafStyles.add(UIUtils.setBoldFont(lafTitle));
        lafStyles.add(new JSeparator(SwingConstants.HORIZONTAL));
        UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
        LafButton[] lafButtons = new LafButton[lafInfo.length+1];
        lafButtons[0] =	createLafItem("Web", "web.laf.lite.ui.WebLookAndFeelLite");
        int i = 1;
        for(UIManager.LookAndFeelInfo laf: lafInfo){
        	lafButtons[i] = createLafItem(laf.getName(), laf.getClassName());
        	i++;
        }
        WebButtonGroup textGroup = new WebButtonGroup(WebButtonGroup.VERTICAL, true, lafButtons);
        textGroup.setButtonsDrawFocus(false);
        lafStyles.add(textGroup);
        
        JPanel editorStyles = new JPanel ( new VerticalFlowLayout (5, 5) );
        editorStyles.setOpaque(false);
        JLabel editorTitle = new JLabel("Editor Themes");
        editorTitle.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        editorStyles.add(UIUtils.setBoldFont(editorTitle));
        editorStyles.add(new JSeparator(SwingConstants.HORIZONTAL));
        ThemeButton[] themeButtons = {
        		createThemeItem("IntelliJ IDEA", "idea"),
        		createThemeItem("Dark", "dark"),
        		createThemeItem("Visual Studio", "vs"),
        		createThemeItem("Eclipse", "eclipse")
        };
        WebButtonGroup themeGroup = new WebButtonGroup(WebButtonGroup.VERTICAL, true, themeButtons);
        themeGroup.setButtonsDrawFocus(false);
        themeGroup.setButtonsDrawFocus(false);
        editorStyles.add(themeGroup);
        JPanel popupContent = new JPanel(new HorizontalFlowLayout(20));
        popupContent.setOpaque(false);
        popupContent.add(lafStyles);
        popupContent.add(new JSeparator(SwingConstants.VERTICAL));
        popupContent.add(editorStyles);
        wbp.setContent (popupContent);
	}
	
	LafButton createLafItem(String title, String className){
		final LafButton lafButton = new LafButton(title, className);
		if(Register.getLaf().equals(title)) lafButton.setSelected(true);
		lafButton .addActionListener (new ActionListener (){
            public void actionPerformed (ActionEvent e){
                    SinkStudio.setLaf(lafButton.getClassName());
                    Register.setLaf(lafButton.getClassName());
           }
        });
		return lafButton;
	}
	
	ThemeButton createThemeItem(String title, String iconname){
		final ThemeButton themeButton = new ThemeButton(title, iconname);
		if(Register.getTheme().equals(iconname)) themeButton.setSelected(true);
		themeButton .addActionListener (new ActionListener (){
            public void actionPerformed (ActionEvent e){
            	Register.setTheme(themeButton.iconame);
            	Content.theme = Asset.loadTheme(themeButton.iconame);
            	Content.theme.apply(Content.editor);
           }
        });
		return themeButton;
	}
	
	void initView(){
		JPanel pan = new JPanel(new VerticalFlowLayout(FlowLayout.CENTER, 0, 0));
		LafStyle.viewButton("Editor", "editor");
        LafStyle.viewButton("Project", "screen");
        LafStyle.viewButton("Studio", "level");
        LafStyle.viewButton("Hiero", "shiero");
        LafStyle.viewButton("Particle", "sparticle");
        WebButtonGroup textGroup = new WebButtonGroup(true,LafStyle.viewGroup.toArray(new JButton[LafStyle.viewGroup.size()]));
        textGroup.setButtonsDrawFocus(false);
        pan.setOpaque(false);
        pan.add(textGroup);
        add(pan, ToolbarLayout.MIDDLE);
        toggleView(2);
	}
	

    public static void toggleView(int index) {
		for(JButton b: LafStyle.viewGroup){
			if(index-1 == LafStyle.viewGroup.indexOf(b))
				b.setSelected(true);
			else
				b.setSelected(false);
		}
    	switch(index){
	    	case 1: Content.showEditor(); break;
	    	case 2: Content.showProject();break;
	    	case 3: Content.showScene(); break;
	    	case 4: Content.showHiero(); break;
	    	case 5: Content.showParticle(); break;
	    	default: break;
    	}
    }
    
    public void addSpace(){
		add(new JLabel("       "), ToolbarLayout.START);
	}
	
	public void addSeparator(){
		add(new JSeparator(SwingConstants.VERTICAL), ToolbarLayout.START);
	}
}

final class LafButton extends JToggleButton{
	private static final long serialVersionUID = 1L;
	String className;
	LafButton(String text, String className){
		super(text);
		this.className = className;
		setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFocusable(false);
        //setRound(0);
	}
	
	public String getClassName(){
		return className;
	}
}

final class ThemeButton extends JToggleButton{
	private static final long serialVersionUID = 1L;
	String iconame;
	ThemeButton(String text, String ic){
		super(text, Asset.icon(ic));
		iconame = ic;
		setVerticalTextPosition(SwingConstants.BOTTOM);
        setHorizontalTextPosition(SwingConstants.CENTER);
        setFocusable(false);
	}
}

final class LCD2 extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public LCD2(){
		UIUtils.setRound(this, 2);
		UIUtils.setAlwaysDrawFocus(this, true);
		UIUtils.setDrawFocus(this, false);
        setPreferredSize(new Dimension(280, 22));
        
        JButton searchUpBtn = LafStyle.createToolButton("up");
        UIUtils.setLeftRightSpacing(searchUpBtn, 0);
        UIUtils.setUndecorated(searchUpBtn, true);
        //UIUtils.setTrailingComponent(this, searchBtn);
        
        JButton searchDownBtn = LafStyle.createToolButton("down");
        UIUtils.setLeftRightSpacing(searchDownBtn, 0);
        UIUtils.setUndecorated(searchDownBtn, true);
        
        JPanel updownPanel = new JPanel(new HorizontalFlowLayout(0));
        updownPanel.add(searchUpBtn);
        updownPanel.add(searchDownBtn);
        updownPanel.setOpaque(false);
        UIUtils.setUndecorated(updownPanel, true);
        UIUtils.setTrailingComponent(this, updownPanel);
        
        
        final ButtonPopup searchMenu = new ButtonPopup(searchDownBtn, PopupWay.downCenter);
        JPanel searchMenuContent = new JPanel(new VerticalFlowLayout(10, 10));
        searchMenuContent.setOpaque(false);

        JPanel hoz1 = new JPanel(new HorizontalFlowLayout(10));
        hoz1.setOpaque(false);
        JCheckBox cs = new JCheckBox("CS");cs.setFocusable(false);hoz1.add(cs);
        JCheckBox ww = new JCheckBox("WW");ww.setFocusable(false);hoz1.add(ww);
        JCheckBox fd = new JCheckBox("FD");fd.setFocusable(false);hoz1.add(fd);
        JCheckBox rv = new JCheckBox("RV");rv.setFocusable(false);hoz1.add(rv);
        
        JPanel hoz2 = new JPanel(new HorizontalFlowLayout(10));
        hoz2.setOpaque(false);
        JButton find = LafStyle.createHeaderButton("FIND", null);hoz2.add(find);
        JButton replace = LafStyle.createHeaderButton("REPLACE", null);hoz2.add(replace);
        JButton replaceall = LafStyle.createHeaderButton("REPLACE ALL", null);hoz2.add(replaceall);
        JButton clear = LafStyle.createHeaderButton("CLEAR", null);hoz2.add(clear);
        
        searchMenuContent.add(hoz1);
        searchMenuContent.add(hoz2);
        searchMenu.setContent(searchMenuContent);
        
        replace.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.replace(getText(), "");
			}
        	
        });
        
        replaceall.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.replaceAll(getText(), "");
			}
        	
        });     
        addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Content.find(getText());
			}
        });
	}
}