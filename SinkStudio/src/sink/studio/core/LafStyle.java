package sink.studio.core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sink.studio.bar.ToolBar;
import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.layout.VerticalFlowLayout;
import web.laf.lite.popup.ButtonPopup;
import web.laf.lite.popup.PopupWay;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.CenterPanel;

final public class LafStyle {
	
	 private static StyleName styleName = StyleName.STYLE2;
	 public static Color headerBg = new Color(0x2a3b57);
	 public static Color headerFg = new Color(0xffffff);
	 public static Color borderColor =  Color.GRAY;
	 public static Color focus =  new Color ( 85, 142, 239 );
	 
	 public static Color topLineColor = new Color(0x4580c8);
	 public static Color topColor = new Color(0x5d94d6);
	 public static Color bottomColor = new Color(0x1956ad);
	
	 private static Color linearTop1 = new Color(0xbcbcbc);
	 private static Color linearBot1 = new Color(0x9a9a9a);
	 
	 private static Color linearTop2 = new Color(0xe4e4e4);
	 private static Color linearBot2 = new Color(0xd1d1d1);
	 
	 private static Color linearTop3 = new Color(0x45474a);
	 private static Color linearBot3 = new Color(0x242728);
	 
	 private static Color darkTop = new Color(0x293134);
	 private static Color darkBot = new Color(0x070e12);
	 //private static Color darkTextFore = new Color(0x000000);
	 
	 private static Color scrollBg = new Color ( 245, 245, 245 );
	 private static Color scrollBorder = new Color ( 230, 230, 230 );
	 
	 private static Color scrollBarBorder = new Color ( 201, 201, 201 );
	 private static Color scrollGradientLeft = new Color ( 239, 239, 239 );
	 private static Color scrollSelGradientLeft = new Color ( 203, 203, 203 ); //33
	 private static Color scrollGradientRight = new Color ( 211, 211, 211 );        
	 private static Color scrollSelGradientRight = new Color ( 175, 175, 175 );//-45
	 
	 public static HashMap<String, JButton> btnMap = new HashMap<String, JButton>();
	 public static ArrayList<JButton> viewGroup = new ArrayList<JButton>();
	 
	 final public static void drawBottomBorder(Graphics g, int width, int height){
		 g.setColor(borderColor);
		 g.drawLine(0, height-1, width, height-1);
	 }
	 
	 final public static void drawTopBorder(Graphics g, int width){
		 g.setColor(borderColor);
		 g.drawLine(0, 0, width, 0);
	 }
	 
	 final public static void drawRightBorder(Graphics g, int width, int height){
		 g.setColor(borderColor);
		 g.drawLine(width-1, 0, width-1, height);
	 }
	 
	 final public static void drawLeftBorder(Graphics g, int width, int height){
		 g.setColor(borderColor);
		 g.drawLine(0, 0, 0, height);
	 }
	 
	 final public static void drawHorizontalBar(Graphics g, int width, int height){
		 Graphics2D g2d = ( Graphics2D ) g;
	     GradientPaint paint = new GradientPaint(0, 1, StyleName.topColor(), 0, height, StyleName.botColor());
	     g2d.setPaint(paint);
	     g2d.fillRect(0, 0, width, height);
	 }
	 
	 final public static void drawLightHorizontalBar(Graphics g, int width, int height){
		 Graphics2D g2d = ( Graphics2D ) g;
		 g2d.setPaint ( new GradientPaint ( 0, 1, Color.WHITE, 0, height, new Color ( 223, 223, 223 ) ) );
	     g2d.fillRect(0, 0, width, height);
	 }
	 
	 final public static void drawDarkHorizontalBar(Graphics g, int width, int height){
		 Graphics2D g2d = ( Graphics2D ) g;
	     GradientPaint paint = new GradientPaint(0, 1, StyleName.topColor().darker(), 0, height, StyleName.botColor().darker());
	     g2d.setPaint(paint);
	     g2d.fillRect(0, 0, width, height);
	 }
	 
	 final public static void drawTreeSelection(Graphics g, int height, Rectangle rect){
		 Graphics2D g2d = ( Graphics2D ) g;
		 g2d.setPaint ( new GradientPaint ( 0, 1, topColor, 0, height, bottomColor ) );
         g2d.fill(rect);
         g2d.setPaint(topLineColor);
         g2d.draw(rect);
	 }
	 
	 final public static void drawVerticalBar(Graphics g, int width, int height){
		 Graphics2D g2d = ( Graphics2D ) g;
		 GradientPaint paint = new GradientPaint(0, 1, StyleName.botColor(), 0, height, StyleName.topColor());
	     //GradientPaint paint = new GradientPaint(0, 1, linearTop3, width, 0, linearBot3);
	     g2d.setPaint(paint);
	     g2d.fillRect(0, 0, width, height);
	 }
	 
	 final public static void drawVerticalScrollTrack( Graphics g, Rectangle thumbRect, int width,int height,
			 boolean drawBorder ){
		 if (drawBorder){
	         Graphics2D g2d = ( Graphics2D ) g;
	         g2d.setPaint ( scrollBg );
	         g2d.fillRect ( 0, 0, width, height );
	         int vBorder = width - 1; //maybe 0
	         g2d.setColor ( scrollBorder );
	         g2d.drawLine ( vBorder, 0, vBorder, height - 1 );
	      }
	 }
	 
	 final public static void drawHorizontalScrollTrack( Graphics g, Rectangle thumbRect, int width,int height,
			 boolean drawBorder ){
		 if (drawBorder){
	         Graphics2D g2d = ( Graphics2D ) g;
	         g2d.setPaint ( scrollBg );
	         g2d.fillRect ( 0, 0, width, height );
	         g2d.setColor ( scrollBorder );
	         g2d.drawLine ( 0, 0, width, 0 );
	      }
	 }
	 
	 final public static void drawVerticalScrollBar( Graphics g, Rectangle thumbRect, int width, boolean isDragging){
		 Graphics2D g2d = ( Graphics2D ) g;
		 Color leftColor = isDragging ? scrollSelGradientLeft : scrollGradientLeft;
	     Color rightColor = isDragging ? scrollSelGradientRight : scrollGradientRight;
		 g2d.setPaint ( new GradientPaint ( 3, 0, leftColor, width - 4, 0, rightColor ) );
         g2d.fillRoundRect ( thumbRect.x + 2, thumbRect.y + 1, thumbRect.width - 4, thumbRect.height - 3, 0, 0);
         g2d.setPaint ( scrollBarBorder );
         g2d.drawRoundRect ( thumbRect.x + 2, thumbRect.y + 1, thumbRect.width - 4, thumbRect.height - 3, 0, 0);
	 }
	 
	 final public static void drawHorizontalScrollBar( Graphics g, Rectangle thumbRect, int width, boolean isDragging){
		 Graphics2D g2d = ( Graphics2D ) g;
		 Color leftColor = isDragging ? scrollSelGradientLeft : scrollGradientLeft;
	     Color rightColor = isDragging ? scrollSelGradientRight : scrollGradientRight;
	     g2d.setPaint ( new GradientPaint ( 0, thumbRect.y + 2, leftColor, 0, thumbRect.y + 2 + thumbRect.height - 4, rightColor ) );
         g2d.fillRoundRect ( thumbRect.x + 1, thumbRect.y + 2, thumbRect.width - 3, thumbRect.height - 4, 0, 0);
         g2d.setPaint ( scrollBarBorder );
         g2d.drawRoundRect ( thumbRect.x + 1, thumbRect.y + 2, thumbRect.width - 3, thumbRect.height - 4, 0, 0);
	 }

	/*
	 * Creates a JLabel/Header with current Style background color
	 */
	 final public static JLabel createHeaderLabel(String text){
		final JLabel label = new JLabel(text.toUpperCase());
		label.setOpaque(true);
		label.setBackground(headerBg);
		label.setForeground(headerFg);
		UIUtils.setBoldFont(label);
		label.setVerticalTextPosition(JLabel.CENTER);
		label.setHorizontalTextPosition(JLabel.CENTER);
		label.setHorizontalAlignment(JLabel.CENTER);
		return label;
	}

	/*
	 * Creates a plain flat JButton/Header Button with current Style background color
	 */
	 final public static JButton createHeaderButton(String text){
		final JButton btn = new JButton(text);
		btn.setOpaque(true);
		btn.setBackground(headerBg);
		btn.setForeground(headerFg);
		UIUtils.setBoldFont(btn);
		UIUtils.setRolloverDecoratedOnly(btn,true);
	    UIUtils.setLeftRightSpacing(btn, 4);
	    UIUtils.setRound(btn, 0);
	    UIUtils.setUndecorated(btn, true);
	    UIUtils.setShadeWidth(btn, 2);
		btn.setVerticalTextPosition(JLabel.CENTER);
		btn.setHorizontalTextPosition(JLabel.CENTER);
		return btn;
	}
	 /* ToolBar Related Methods */
	// This is for Menu Items
	final public static JButton createMenuButton(String title){
		JButton btn = new JButton(title);
		btn.setFocusable(false);
		btn.setOpaque(false);
	    UIUtils.setRolloverDecoratedOnly(btn, true);
	    UIUtils.setRound(btn, 0);
	    UIUtils.setLeftRightSpacing(btn, 5);
	    UIUtils.setShadeWidth(btn, 0);
	    UIUtils.setInnerShadeWidth(btn, 0);
		return btn;
	} 
	
	// This is for ToolButtons without Popup
	final public static JPanel createToolPanel(String text, String iconname,final ActionListener onClick){
		final JPanel pan = new JPanel(new VerticalFlowLayout(FlowLayout.CENTER, 0, 0));
		pan.setOpaque(false);
		UIUtils.setRound(pan, 0);
		UIUtils.setShadeWidth(pan, 0);
		JButton btn = createMenuButton(text);
		btn.setIcon(Asset.icon(iconname));
		if(onClick != null)
			btn.addActionListener(onClick);
		btnMap.put(iconname, btn);
		pan.add(btn);
		return pan;
	}

	 /* SideBar Related Methods */
	// This is for ToolButtons with Popup
	final public static JButton createToolButton(String iconname){
		final JButton btn = new JButton(Asset.icon(iconname));
		btn.setFocusable(false);
		btn.setToolTipText(iconname.toUpperCase());
		btn.setOpaque(false);
	    UIUtils.setLeftRightSpacing(btn, 5);
	    UIUtils.setRound(btn, 0);
	    UIUtils.setShadeWidth(btn, 0);
	    UIUtils.setUndecorated(btn, true);
		return btn;
	}
	
	// This is for ToolButtons without Popup
	public static JButton createPopUpToolButton(String iconname){
		JButton btn = new JButton(Asset.icon(iconname));
		btn.setFocusable(false);
		btn.setToolTipText(iconname.toUpperCase());
		btn.setOpaque(false);
	    UIUtils.setLeftRightSpacing(btn, 4);
	    UIUtils.setRound(btn, 0);
	    UIUtils.setUndecorated(btn, true);
		return btn;
	}
	
	static int currentIndex = 1;
	public static void viewButton(String text, String ic){
		final int id = Integer.valueOf(currentIndex);
		currentIndex += 1;
		JButton btn = new JButton(text,Asset.icon(ic));
		viewGroup.add(btn);
		btn.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ToolBar.toggleView(id);
			}
        });
	}
	
	/* Explorer Related Methods */
	final private static JButton createExplorerToolButton(String text,String iconname){
		final JButton btn = new JButton(Asset.icon(iconname));
	    UIUtils.setRolloverDecoratedOnly(btn,true);
	    UIUtils.setLeftRightSpacing(btn, 0);
	    btn.setFocusable(false);
	    btn.setToolTipText(text);
		return btn;
	}
	
	final private static ButtonPopup createExplorerToolMenu(JButton btn, String title){
		final ButtonPopup menu = new ButtonPopup(btn, PopupWay.upRight);
		menu.setRound(0);
		menu.setShadeWidth(7);
		final JPanel content = new JPanel(new BorderLayout());
		content.setOpaque(false);
		UIUtils.setRound(btn, 0);
		UIUtils.setLeftRightSpacing(btn, 0);
	    content.add(createHeaderLabel(title), BorderLayout.NORTH);
	    menu.setContent(content);
		return menu;
	}
	
	final public static JButton createExplorerToolButton(final String icon, final String title, 
			final JPanel panel, final String finishText, final ActionListener onClick){
		 final JButton btn = createExplorerToolButton(title, icon);
		 final ButtonPopup menu = createExplorerToolMenu(btn, title);
		 final JButton finish = createHeaderButton(finishText);
		 final JPanel hoz = new JPanel(new VerticalFlowLayout(10));
		 panel.setOpaque(false);
		 UIUtils.setMargin(hoz, new Insets(10,10,10,10));
		 hoz.setOpaque(false);
		 hoz.add(new CenterPanel(panel));
		 Box box = Box.createHorizontalBox();
		 box.add(Box.createHorizontalGlue());
		 box.add(finish);
		 hoz.add(box);
		 menu.getContentPanel().add(new CenterPanel(hoz), BorderLayout.CENTER);
	     finish.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.hidePopup();
				if(onClick != null)
					onClick.actionPerformed(arg0);
			}
	     });
		return btn;
	}
	
	final public static JButton createExplorerToolButton(final String icon, final String title,
			JTextField field, String finishText, final ActionListener onClick){
		final JButton btn = createExplorerToolButton(title, icon);
		final ButtonPopup menu = createExplorerToolMenu(btn, title);
		final JButton finish = createHeaderButton(finishText);
	    final JPanel hoz = new JPanel(new HorizontalFlowLayout(10));
		field.setPreferredSize(new Dimension(240,25));
		UIUtils.setMargin(hoz, new Insets(10,10,10,10));
		hoz.setOpaque(false);
		hoz.add(new CenterPanel(field));
		Box box = Box.createHorizontalBox();
		box.add(Box.createHorizontalGlue());
		box.add(finish);
		hoz.add(box);
		menu.getContentPanel().add(new CenterPanel(hoz), BorderLayout.CENTER);
		finish.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				menu.hidePopup();
				if(onClick != null)
					onClick.actionPerformed(arg0);
			}
	    });
		return btn;
	}
	
	public static JButton TreeButton(String iconName){
		 JButton btn = new JButton(Asset.icon(iconName));
		 btn.setVisible(false);
		 btn.setOpaque(false);
		 btn.setFocusable(false);
		 UIUtils.setRound(btn, 0);
		 //UIUtils.setShadeWidth(btn, 0);
		 //UIUtils.setUndecorated(btn, true);
		 return btn;
	}
	
	private enum StyleName{
		STYLE1, STYLE2, STYLE3, DARK;
		
		public static Color topColor(){
			switch(styleName){
				case STYLE1:return linearTop1;
				case STYLE2:return linearTop2;
				case STYLE3:return linearTop3;
				case DARK:return darkTop;
				default: return linearTop1;
			}
		}
		
		public static Color botColor(){
			switch(styleName){
				case STYLE1:return linearBot1;
				case STYLE2:return linearBot2;
				case STYLE3:return linearBot3;
				case DARK:return darkBot;
				default: return linearBot1;
			}
		}
	}
}