package sink.studio.bar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JToolBar;

import sink.studio.core.Asset;
import sink.studio.core.LafStyle;
import web.laf.lite.utils.UIUtils;

final public class EngineToolBar extends JToolBar {
	private static final long serialVersionUID = 1L;
	JComboBox<String> sizes;

    public EngineToolBar(){
		super(JToolBar.HORIZONTAL);
        setFloatable (false);
        setRollover(true);
        ScreenToolButton btn1 = new ScreenToolButton("New", "console");
        
        
        JLabel sizesLabel = new JLabel("Screen Size:");
        String[] sizesNames = {"800x480", "960x540", "1024x600", "1280x720", "1920x1080" };
        sizes = new JComboBox<String>(sizesNames);
        sizes.addActionListener ( new ActionListener (){
            public void actionPerformed ( ActionEvent e ){
            		String list[] = sizes.getSelectedItem().toString().split("x");
            		//EnginePanel.setScreenSize(Integer.parseInt(list[0]), Integer.parseInt(list[1]));
           }
        });
        
        add(btn1);
        //new GroupPanel (sizesLabel, sizes)
	}
    
    @Override
	public void paintComponent(Graphics g){
		LafStyle.drawHorizontalBar(g, getWidth (), getHeight ());
	}
}

final class ScreenToolButton extends JButton{
	private static final long serialVersionUID = 1L;
	ScreenToolButton(String text, String iconame){
		super(text, Asset.icon(iconame));
        setForeground(Color.BLACK);
        setFocusable(false);
        UIUtils.setRolloverDecoratedOnly(this, true);
	}
}