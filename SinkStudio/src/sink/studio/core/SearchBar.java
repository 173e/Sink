package sink.studio.core;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import web.laf.lite.layout.HorizontalFlowLayout;
import web.laf.lite.utils.UIUtils;

final public class SearchBar extends JTextField {
	private static final long serialVersionUID = 1L;
	
	public SearchBar(){
		UIUtils.setRound(this, 2);
		UIUtils.setDrawFocus(this, false);
		UIUtils.setInputPrompt(this, "Search");
        setPreferredSize(new Dimension(280, 22));
        
        JButton searchUpBtn = Style.createToolButton("up");
        UIUtils.setLeftRightSpacing(searchUpBtn, 0);
        UIUtils.setUndecorated(searchUpBtn, true);
        
        JButton searchDownBtn = Style.createToolButton("down");
        UIUtils.setLeftRightSpacing(searchDownBtn, 0);
        UIUtils.setUndecorated(searchDownBtn, true);
        
        JPanel updownPanel = new JPanel(new HorizontalFlowLayout(0));
        updownPanel.add(searchUpBtn);
        updownPanel.add(searchDownBtn);
        updownPanel.setOpaque(false);
        UIUtils.setUndecorated(updownPanel, true);
        UIUtils.setTrailingComponent(this, updownPanel);
          
        searchUpBtn.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.context.setSearchForward(false);
				Content.editor.find(getText());
			}
        });
        
        searchDownBtn.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Editor.context.setSearchForward(true);
				Content.editor.find(getText());
			}
        });
	}
}