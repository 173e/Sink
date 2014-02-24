package sink.studio.core;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

import sink.studio.panel.FilePanel;
import sink.studio.panel.ProjectPanel;
import sink.studio.panel.StudioPanel;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.Register;

import com.badlogic.gdx.tools.hiero.HieroPanel;
import com.badlogic.gdx.tools.particleeditor.ParticlePanel;

final public class Content extends JPanel {
	private static final long serialVersionUID = 1L;
	static JPanel con;
    public static String currentView = "Project";
    
    private static String projectFile = null;
    private static Register projectRegister;
    static ProjectPanel projectPanel;
   
	
    public static Theme theme = Asset.loadTheme(Register.getTheme());
    public static RTextScrollPane editorScroll;
    public static Editor editor;
	
	static StudioPanel studioPanel;
	static HieroPanel hieroPanel;
	static ParticlePanel particlePanel;
	
	static JLabel title = Style.createHeaderLabel("Project");
	
	private static String editorFile = null;
	private static Register editorRegister;
	
	public static void initProjects(){
		projectRegister = new Register(105);
		projectFile = Register.getString(projectRegister);
		editorRegister = new Register(106);
		editorFile = Register.getString(editorRegister);
	}
	
	Content(){
		super(new BorderLayout());
		UIUtils.setMargin(this, new Insets(3,1,0,1));
		UIUtils.setUndecorated(this, true);
		projectPanel = new ProjectPanel();
		editor = new Editor();
		theme.apply(editor);
		editorScroll = new RTextScrollPane(editor);
		editorScroll.setIconRowHeaderEnabled(true);
		editorScroll.setLineNumbersEnabled(true);
		editorScroll.getGutter().setBookmarkingEnabled(true);
		editorScroll.setVerticalScrollBarPolicy(RTextScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		UIUtils.setDrawBorder(editorScroll, false);
		studioPanel = new StudioPanel();
		studioPanel.createCanvas();
        hieroPanel = new HieroPanel((Frame) this.getParent());
        particlePanel = new ParticlePanel();

        // CardLayout
        con = new JPanel(new CardLayout());
        con.add(projectPanel, "Project");
        con.add(editorScroll, "Editor");
        con.add(new JScrollPane(studioPanel), "Studio");
        con.add(new JScrollPane(hieroPanel),"Hiero");
        con.add(particlePanel,"Particle");
		add(title, BorderLayout.NORTH);
        add(con, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g){
		Style.drawRightBorder(g, getWidth(), getHeight());
		Style.drawLeftBorder(g, getWidth(), getHeight());
	}
	
    static void showContent(String contentName){
    	if(currentView.equals(contentName))
    		return;
    	//if(currentView.equals("Scene"))
    	//	scenePanel.destroyCanvas();
    	else if(currentView.equals("Project"))
    		Export.updateConfigFile();
    	//if(currentView.equals("Hiero"));
    	//	hieroPanel.destroyCanvas();
    	//if(currentView.equals("Particle"));
    	//	particlePanel.destroyCanvas();
		((CardLayout) con.getLayout()).show(con, contentName);
		currentView = contentName;
		title.setText(contentName.toUpperCase());
    }
    
    public static void showProject(){
		showContent("Project");
	}

	public static void showEditor(){
		showContent("Editor");
	}
	
	public static void showStudio(){
		showContent("Studio");
	}
	
	public static void showHiero(){
		//hieroPanel.createCanvas();
		showContent("Hiero");
	}
	
	public static void showParticle(){
		//particlePanel.createCanvas();
		showContent("Particle");
	}
	
	public void dispose(){
		studioPanel.destroyCanvas();
		hieroPanel.destroyCanvas();
		//particlePanel.destroyCanvas();
	}
	
	public static boolean checkProjectExists(){
		if(getProject() == null || getProject().isEmpty())
			return false;
		return true;
	}
	
	public static String getProject() {
		return projectFile;
	}
	
	public static void setProject(String prjName) {
		Register.putString(projectRegister, prjName);
		projectFile = prjName;
		FilePanel.updateList();
	}
	
	public static boolean checkFileExists(){
		if(getFile() == null || getFile().isEmpty())
			return false;
		return true;
	}
	
	public static String getFile() {
		return editorFile;
	}
	
	public static void setFile(String fileName) {
		Register.putString(editorRegister, fileName);
		editorFile = fileName;
	}
}