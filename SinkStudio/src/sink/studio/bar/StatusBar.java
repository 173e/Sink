package sink.studio.bar;

import java.awt.Color;
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
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import sink.studio.core.Asset;
import sink.studio.core.LafStyle;
import web.laf.lite.layout.ToolbarLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.ButtonPopup;
import web.laf.lite.popup.PopupWay;
import web.laf.lite.utils.LafUtils;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.utils.UpdateTimer;

final public class StatusBar extends JPanel {
	private static final long serialVersionUID = 1L;
	JLabel caret, type, lineno, syntax;

	public StatusBar(){
		 super(new ToolbarLayout());
         caret = new JLabel("Smart Insert  ");
         type = new JLabel("Writable       ");
         lineno = new JLabel("Line 15,Column 42");
         syntax = new JLabel("Java   ");
         add(lineno, ToolbarLayout.START);
        // initZoomIn();
        // initZoomOut();
         //add(new JSeparator(SwingConstants.VERTICAL));
         add(type, ToolbarLayout.END);
         add(new JSeparator(SwingConstants.VERTICAL), ToolbarLayout.END);
         add(caret, ToolbarLayout.END);
         add(new JSeparator(SwingConstants.VERTICAL), ToolbarLayout.END);
         //initUpdate();
         //initNuke();
         add(syntax, ToolbarLayout.END);
         add(new JSeparator(SwingConstants.VERTICAL));
         add(new MemoryBar(), ToolbarLayout.END);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
	    LafStyle.drawHorizontalBar(g, getWidth (), getHeight ());
	    LafStyle.drawTopBorder(g, getWidth());
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
	public static final String THREAD_NAME = "SkyMemoryBar.updater";

    private Color allocatedBorderColor = Color.GRAY;
    private Color allocatedDisabledBorderColor = Color.LIGHT_GRAY;
    private Color usedBorderColor = new Color ( 130, 130, 183 );
    private Color usedFillColor = new Color ( 0, 0, 255, 50 );

    private int leftRightSpacing = 8;
    private int shadeWidth = 2;
    private int round = 2;

    private boolean allowGcAction = true;

    private boolean showTooltip = true;
    private int tooltipDelay = 1000;

    private long usedMemory = 0;
    private long allocatedMemory = 0;
    private long maxMemory = 0;

    private int refreshRate = 1000;
    private UpdateTimer updater = null;

    private boolean pressed = false;

    public MemoryBar ()
    {
        super ();

        setOpaque ( false );
        setFocusable ( true );
        setHorizontalAlignment ( JLabel.CENTER );

        updateBorder ();

        updateMemory ();
       
        addMouseListener ( new MouseAdapter ()
        {
            @Override
            public void mousePressed ( MouseEvent e )
            {
                if ( allowGcAction && isEnabled () && SwingUtilities.isLeftMouseButton ( e ) )
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

    private void updateBorder ()
    {
        //setMargin ( 2, 2 + leftRightSpacing, 2, 2 + leftRightSpacing );
    }

    protected void updateMemory ()
    {
        // Determining current memory usage state
        MemoryUsage mu = ManagementFactory.getMemoryMXBean ().getHeapMemoryUsage ();
        usedMemory = mu.getUsed ();
        allocatedMemory = mu.getCommitted ();
        maxMemory = mu.getMax ();

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

    public int getRefreshRate ()
    {
        return refreshRate;
    }

    public void setRefreshRate ( int refreshRate )
    {
        this.refreshRate = refreshRate;
        updater.setDelay ( refreshRate );
    }

    public int getRound ()
    {
        return round;
    }

    public void setRound ( int round )
    {
        this.round = round;
    }

    public int getShadeWidth ()
    {
        return shadeWidth;
    }

    public void setShadeWidth ( int shadeWidth )
    {
        this.shadeWidth = shadeWidth;
        updateBorder ();
    }

    public Color getAllocatedBorderColor ()
    {
        return allocatedBorderColor;
    }

    public void setAllocatedBorderColor ( Color allocatedBorderColor )
    {
        this.allocatedBorderColor = allocatedBorderColor;
    }

    public Color getAllocatedDisabledBorderColor ()
    {
        return allocatedDisabledBorderColor;
    }

    public void setAllocatedDisabledBorderColor ( Color allocatedDisabledBorderColor )
    {
        this.allocatedDisabledBorderColor = allocatedDisabledBorderColor;
    }

    public Color getUsedBorderColor ()
    {
        return usedBorderColor;
    }

    public void setUsedBorderColor ( Color usedBorderColor )
    {
        this.usedBorderColor = usedBorderColor;
    }

    public Color getUsedFillColor ()
    {
        return usedFillColor;
    }

    public void setUsedFillColor ( Color usedFillColor )
    {
        this.usedFillColor = usedFillColor;
    }

    public int getLeftRightSpacing ()
    {
        return leftRightSpacing;
    }

    public void setLeftRightSpacing ( int leftRightSpacing )
    {
        this.leftRightSpacing = leftRightSpacing;
        updateBorder ();
    }

    public boolean isAllowGcAction ()
    {
        return allowGcAction;
    }

    public void setAllowGcAction ( boolean allowGcAction )
    {
        this.allowGcAction = allowGcAction;
        if ( !allowGcAction && pressed )
        {
            pressed = false;
            repaint ();
        }
    }

    public boolean isShowTooltip ()
    {
        return showTooltip;
    }


    public long getAllocatedMemory ()
    {
        return allocatedMemory;
    }

    public long getUsedMemory ()
    {
        return usedMemory;
    }

    public long getMaxMemory ()
    {
        return maxMemory;
    }

    public int getTooltipDelay ()
    {
        return tooltipDelay;
    }

    public void setTooltipDelay ( int tooltipDelay )
    {
        this.tooltipDelay = tooltipDelay;
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
       int arcRound = ( Math.max ( 0, round - 1 ) ) * 2;
       return new RoundRectangle2D.Double ( 1, 1, getProgressWidth ( progress, fill ), getHeight () - 2 - ( fill ? 0 : 1 ), arcRound,
                    arcRound );
    }

    private int getProgressWidth ( long progress, boolean fill )
    {
        return Math.round ( ( float ) ( getWidth () - ( 2 ) -
                ( fill ? 0 : 1 ) ) * progress / ( allocatedMemory ) );
    }
}