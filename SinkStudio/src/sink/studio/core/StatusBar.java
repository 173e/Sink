package sink.studio.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.ButtonPopup;
import web.laf.lite.popup.PopupWay;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.utils.UpdateTimer;

final public class StatusBar extends JPanel {
	private static final long serialVersionUID = 1L;
	static JLabel caret, xy, selected;
	
	public static final JTextArea consoleArea = new JTextArea("");
	public static final JScrollPane consoleAreaPane = new JScrollPane(consoleArea);

	public StatusBar(){
		 super(new ToolbarLayout());
	     xy = new JLabel("<html><b>x: &nbsp</b>0<b>&nbsp y: &nbsp</b>0</html>");
	     selected = new JLabel("<html><b>Selected: </b> &nbsp None</html>");
	     caret = new JLabel("<html><b>Line</b> 15 <b>Column</b> 42</html>");

         add(caret, ToolbarLayout.START);
         addSpace();
         
         addSeparator();
         
         addSpace();
         add(selected, ToolbarLayout.START);
         addSpace();
         
         addSeparator();
         
         addSpace();
         add(xy, ToolbarLayout.START);
         addSpace();
         
         addSeparator();
     
         addSpace();
         initError();
         addSpace();
         
         addSeparator();
         
         addSpace();
         initWarning();
         addSpace();
         
         addSeparator();
         
         addSpace();
         initConsole();
         addSpace();
         
         addSeparator();
        
         //initUpdate();
         //initNuke();
         add(new MemoryBar(), ToolbarLayout.END);
	}
	
	public void addSpace(){
		add(new JLabel("       "), ToolbarLayout.START);
	}
	
	public void addSeparator(){
		add(new JSeparator(SwingConstants.VERTICAL), ToolbarLayout.START);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    LafStyle.drawHorizontalBar(g, getWidth (), getHeight ());
	    LafStyle.drawTopBorder(g, getWidth());
	}
	
	public static void updateCaret(int row, int col){
		caret.setText("<html><b>Line</b> "+row+" <b>Column</b> "+col+"</html>");
	}
	
	public static void updateXY(float x, float y){
		xy.setText("<html><b>x:&nbsp</b>"+(int)x+"<b>&nbsp y:&nbsp</b>"+(int)y+"</html>");
	}
	
	public static void updateSelected(String text){
		selected.setText("<html><b>Selected:</b> &nbsp "+text+"</html>");
	}
	
	void initError(){
		final JButton menuBtn1 = LafStyle.createMenuButton("<html><b>Errors:</b> 0");
        final ButtonPopup menu = new ButtonPopup(menuBtn1,PopupWay.upRight);
        menu.setRound(0);
        JPanel popupContent = new JPanel ( new VerticalFlowLayout ( 5, 5 ) );
        popupContent.setPreferredSize(new Dimension(200, 200));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     ToolBar")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     Explorer")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.setOpaque(false);
        menu.setContent(popupContent);
        add(menuBtn1, ToolbarLayout.START);
	}
	
	void initWarning(){
		final JButton menuBtn1 = LafStyle.createMenuButton("<html><b>Warnings:</b> 0");
        final ButtonPopup menu = new ButtonPopup(menuBtn1,PopupWay.upRight);
        menu.setRound(0);
        JPanel popupContent = new JPanel ( new VerticalFlowLayout ( 5, 5 ) );
        popupContent.setPreferredSize(new Dimension(200, 200));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     ToolBar")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.add(UIUtils.setBoldFont(new JLabel("     Explorer")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.setOpaque(false);
        menu.setContent(popupContent);
        add(menuBtn1, ToolbarLayout.START);
	}
	
	void initConsole(){
		final JButton menuBtn1 = LafStyle.createMenuButton("<html><b>Console</b>");
        final ButtonPopup menu = new ButtonPopup(menuBtn1,PopupWay.upRight);
        menu.setRound(0);
        menu.setShadeWidth(7);
        consoleAreaPane.setPreferredSize(new Dimension(600, 350));
        JPanel pan = new JPanel(new VerticalFlowLayout());
        pan.add(LafStyle.createHeaderLabel("Console"));
        pan.add(consoleAreaPane);
        menu.setContent(pan);
        add(menuBtn1, ToolbarLayout.START);
	}
	
	void initZoomIn(){
		final JButton zoomin = createButton("ZoomIn", "szoomin");
		zoomin.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TabPanel.zoomin();
			}
			
		});
		add(zoomin);
	}
	
	void initZoomOut(){
		final JButton zoomout = createButton("ZoomOut", "szoomout");
		zoomout.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//TabPanel.zoomout();
			}
			
		});
		add(zoomout);
	}
	
	void initUpdate(){
		final JButton update = createButton("Update", "supdate");
		ButtonPopup menu = new ButtonPopup(update, PopupWay.upCenter);
        JPanel popupContent = new JPanel (new VerticalFlowLayout(5, 10));
        popupContent.add(UIUtils.setBoldFont(new JLabel("SkyCode v0.39")));
        popupContent.add(new JSeparator(SwingConstants.HORIZONTAL));
        popupContent.add(new JLabel("Added Stuff"));
        popupContent.add(new JLabel("Bug Fixes"));
        popupContent.add(new JLabel("Bug Fixes"));
        popupContent.add(new JLabel("Bug Fixes"));
        popupContent.add(new JLabel("Bug Fixes"));
        popupContent.setOpaque(false);
        menu.setContent (popupContent);
		add(update, ToolbarLayout.END);
	}
	
	void initNuke(){
		final JButton nuke = createButton("Nuke", "snuke");
		add(nuke, ToolbarLayout.END);
	}
	
	JButton createButton(String text, String iconname){
		JButton temp = new JButton(Asset.icon(iconname));
		temp.setFocusable(false);
		UIUtils.setRolloverDecoratedOnly(temp, true);
		temp.setToolTipText(text);
		return temp;
	}	
}

class MemoryBar extends JLabel{
	private static final long serialVersionUID = 1L;
	public static final String THREAD_NAME = "MemoryBar.updater";

    private Color usedBorderColor = new Color ( 130, 130, 183 );
    private Color usedFillColor = new Color ( 0, 0, 255, 50 );

    private long usedMemory = 0;
    private long allocatedMemory = 0;

    private int refreshRate = 1000;
    
    UpdateTimer updater = null;

    private boolean pressed = false;

    public MemoryBar ()
    {
        super ();

        setOpaque ( false );
        setFocusable ( true );
        setHorizontalAlignment ( JLabel.CENTER );

        updateMemory ();
       
        addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if (SwingUtilities.isLeftMouseButton ( e ) )
                {
                    pressed = true;
                    requestFocusInWindow ();
                    doGC ();
                }
            }

            @Override
            public void mouseReleased ( MouseEvent e )
            {
                if ( pressed && SwingUtilities.isLeftMouseButton ( e ) )
                {
                    pressed = false;
                    repaint ();
                }
            }
        } );

        addFocusListener ( new FocusAdapter ()
        {
            @Override
            public void focusGained ( FocusEvent e )
            {
                repaint ();
            }

            @Override
            public void focusLost ( FocusEvent e )
            {
                repaint ();
            }
        } );

        // Values updater
        updater = UpdateTimer.install ( this, THREAD_NAME, refreshRate, new ActionListener ()
        {
            @Override
            public void actionPerformed ( ActionEvent e )
            {
                updateMemory ();
            }
        } );
    }

    public void doGC ()
    {
        System.gc ();
        updateMemory ();
    }

    protected void updateMemory ()
    {
        // Determining current memory usage state
        MemoryUsage mu = ManagementFactory.getMemoryMXBean ().getHeapMemoryUsage ();
        usedMemory = mu.getUsed ();
        allocatedMemory = mu.getCommitted ();

        // Updating bar text
        setText ( getMemoryBarText () );

        // Updating view
        repaint ();
    }
    
    private static String getDigits ( int digits )
    {
        StringBuilder stringBuilder = new StringBuilder ( digits );
        for ( int i = 0; i < digits; i++ )
        {
            stringBuilder.append ( "#" );
        }
        return stringBuilder.toString ();
    }
    

    public static final long KB = 1024;
    public static final long MB = 1024 * KB;
    public static final long GB = 1024 * MB;
    public static final long PB = 1024 * GB;
    
    public static String getFileSizeString ( long size, int digits )
    {
        DecimalFormat df = new DecimalFormat ( digits == 0 ? "#" : "#." + getDigits ( digits ) );
        if ( size < KB )
        {
            return df.format ( size ) + " " + "KB";
        }
        else if ( size >= KB && size < MB )
        {
            return df.format ( ( float ) size / KB ) + " " + "KB";
        }
        else if ( size >= MB && size < GB )
        {
            return df.format ( ( float ) size / MB ) + " " + "MB";
        }
        else
        {
            return df.format ( ( float ) size / GB ) + " " + "GB";
        }
    }


    protected String getMemoryBarText (){
        long total = allocatedMemory;
        return " " +getFileSizeString(usedMemory, getDigits(usedMemory)) + " " + " " + "of" + " " + " "+
        		getFileSizeString ( total, getDigits ( total ) );
    }

    private int getDigits ( long size )
    {
        return size < GB ? 0 : 2;
    }

    @Override
    protected void paintComponent ( Graphics g )
    {
        Graphics2D g2d = ( Graphics2D ) g;
        Object old = LafUtils.setupAntialias ( g2d );

        // Used memory background
        g2d.setPaint ( usedFillColor );
        g2d.fill ( getProgressShape ( usedMemory, true ) );

        // Used memory border
        g2d.setPaint ( usedBorderColor );
        g2d.draw ( getProgressShape ( usedMemory, false ) );

        LafUtils.restoreAntialias ( g2d, old );

        super.paintComponent ( g2d );
    }

    private Shape getProgressShape ( long progress, boolean fill )
    {
       return new RoundRectangle2D.Double ( 1, 2, getProgressWidth ( progress, fill ), getHeight () - 3 - ( fill ? 0 : 1 ), 2, 2);
    }

    private int getProgressWidth ( long progress, boolean fill )
    {
        return Math.round ( ( float ) ( getWidth () - ( 2 ) -
                ( fill ? 0 : 1 ) ) * progress / ( allocatedMemory ) );
    }
}