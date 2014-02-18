package sink.studio.bar;

import javax.swing.JPanel;

import web.laf.lite.layout.ToolbarLayout;

final public class PanelToolBar extends JPanel{
	private static final long serialVersionUID = 1L;
	public PanelToolBar(){
		super(new ToolbarLayout());
    	//setDrawSides (false, true, true, true);
    	//setShadeWidth(0);
        //WebFileBreadcrumb breadcrumb = new WebFileBreadcrumb("", false);
        //breadcrumb.setEncloseLastElement(true);
       // add(breadcrumb);
	}
}
