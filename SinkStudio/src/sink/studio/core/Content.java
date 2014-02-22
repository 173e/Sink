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

import sink.studio.panel.ProjectPanel;
import sink.studio.panel.SinkPanel;
import web.laf.lite.utils.UIUtils;
import web.laf.lite.widget.Register;

import com.badlogic.gdx.tools.hiero.HieroPanel;
import com.badlogic.gdx.tools.particleeditor.ParticlePanel;

final public class Content extends JPanel {
	private static final long serialVersionUID = 1L;
	static JPanel con;
    static String currentView = "Project";
    
    static ProjectPanel projectPanel;
    
    public static Theme theme = Asset.loadTheme(Register.getTheme());
    public static RTextScrollPane editorScroll;
    public static Editor editor;
	
	static SinkPanel scenePanel;
	static HieroPanel hieroPanel;
	static ParticlePanel particlePanel;
	
	static JLabel title = LafStyle.createHeaderLabel("Project");
	public static String projectFile = "C:\\game.jar";
	public static String editorFile = "";
	
	
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
		scenePanel = new SinkPanel();
        hieroPanel = new HieroPanel((Frame) this.getParent());
        particlePanel = new ParticlePanel();

        // CardLayout
        con = new JPanel(new CardLayout());
        con.add(projectPanel, "Project");
        con.add(editorScroll, "Editor");
        con.add(new JScrollPane(scenePanel), "Scene");
        con.add(new JScrollPane(hieroPanel),"Hiero");
        con.add(particlePanel,"Particle");
		add(title, BorderLayout.NORTH);
        add(con, BorderLayout.CENTER);
	}
	
	@Override
	public void paintComponent(Graphics g){
		LafStyle.drawRightBorder(g, getWidth(), getHeight());
		LafStyle.drawLeftBorder(g, getWidth(), getHeight());
	}
	
    static void showContent(String contentName){
    	if(currentView.equals("Scene"))
    		scenePanel.destroyCanvas();
    	else if(currentView.equals("Project"))
    		Export.updateConfigFile();
    	//if(currentView.equals("Hiero"));
    	//	hieroPanel.destroyCanvas();
    	//if(currentView.equals("Particle"));
    	//	particlePanel.destroyCanvas();
    	if(!currentView.equals(contentName)){
			((CardLayout) con.getLayout()).show(con, contentName);
			currentView = contentName;
			title.setText(contentName);
		}
    }
    
    public static void showProject(){
		showContent("Project");
	}

	public static void showEditor(){
		showContent("Editor");
	}
	
	public static void showScene(){
		scenePanel.createCanvas();
		showContent("Scene");
	}
	
	public static void showHiero(){
		//hieroPanel.createCanvas();
		showContent("Hiero");
	}
	
	public static void showParticle(){
		//particlePanel.createCanvas();
		showContent("Particle");
	}
	
	public static void find(String text){
    	editor.find(text);
    }
	
	public static void replace(String text, String rep){
		editor.replace(text, rep);
    }
	
	public static void replaceAll(String text, String rep){
    	editor.replaceAll(text, rep);
    }
	
	public void dispose(){
		scenePanel.destroyCanvas();
		hieroPanel.destroyCanvas();
		//particlePanel.destroyCanvas();
	}
}