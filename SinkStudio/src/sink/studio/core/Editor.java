package sink.studio.core;

import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.Timer;
import javax.swing.ToolTipManager;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.FileLocation;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaHighlighter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;

import web.laf.lite.popup.NotificationManager;

final public class Editor extends TextEditorPane {
	private static final long serialVersionUID = 1L;
	static DefaultCompletionProvider provider = new DefaultCompletionProvider();
	
	final Timer saveTimer = new Timer(10000, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			//$log("Timer Action: "+file.getName());
			save();
		}
	});
	final File file;
	float zoomSize = 13;
	
	public Editor(File nfile) throws IOException{
		super(TextEditorPane.INSERT_MODE, true, FileLocation.create(nfile));
		file = nfile;
		setCodeFoldingEnabled(true);
        setMargin(new Insets(0, 5, 0, 0));
        setAntiAliasingEnabled(true);
        setUseFocusableTips(true);
        setTabSize(4);
        setLineWrap(true);
        setWrapStyleWord(true);
        //setMarkOccurrences(true);
        //setPaintMarkOccurrencesBorder(true);
        //setPaintMatchedBracketPair(true);
        //requestFocusInWindow();
		setTabsEmulated(true);
		setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		LanguageSupportFactory lsf = LanguageSupportFactory.get();
		LanguageSupport support = lsf.getSupportFor(SYNTAX_STYLE_JAVA);
		LanguageSupportFactory.get().register(this);
		ToolTipManager.sharedInstance().registerComponent(this);
		JavaLanguageSupport jls = (JavaLanguageSupport)support;
		try {
			jls.getJarManager().addCurrentJreClassFileSource();
			//jls.install(this);
			//jsls.getJarManager().addClassFileSource(ji);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
        ((RSyntaxTextAreaHighlighter ) getHighlighter()).setDrawsLayeredHighlights(false);
        addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent arg0) {
				//$log("Timer Started: "+file.getName());
				saveTimer.start();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				//$log("Timer Stopped: "+file.getName());
				saveTimer.stop();
				save();
			}
        });
	}
	
	@Override
	public void save(){
		if(isDirty()){
			try {
				super.save();
			} catch (IOException e) {
				NotificationManager.showNotification("Could'nt Save File: "+file.getName());
				e.printStackTrace();
			}
		}
	}
	
	
	public void zoomin(){
		zoomSize += 1;
		setFont(getFont().deriveFont(zoomSize));
	}
	
	public void zoomout(){
		zoomSize -= 1;
		setFont(getFont().deriveFont(zoomSize));
	}
}
