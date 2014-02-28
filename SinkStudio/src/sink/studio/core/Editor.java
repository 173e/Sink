package sink.studio.core;

import java.awt.Insets;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.Timer;
import javax.swing.ToolTipManager;
import javax.swing.event.CaretListener;
import javax.swing.event.CaretEvent;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.LanguageSupportFactory;
import org.fife.rsta.ac.java.JavaLanguageSupport;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextAreaHighlighter;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.TextEditorPane;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;

import sink.studio.panel.AssetType;

final public class Editor extends TextEditorPane {
	private static final long serialVersionUID = 1L;
	static DefaultCompletionProvider provider = new DefaultCompletionProvider();
	public static SearchContext context = new SearchContext(); 
	
	final Timer saveTimer = new Timer(10000, new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			save();
		}
	});
	float zoomSize = 13;
	
	public Editor(){
		super(TextEditorPane.INSERT_MODE, true);
        setMargin(new Insets(0, 0, 0, 0));
        setAntiAliasingEnabled(true);
        setAutoIndentEnabled(true);
        setBracketMatchingEnabled(true);
        setUseFocusableTips(true);
        setTabSize(4);
        setLineWrap(true);
        setWrapStyleWord(true);
        setTabsEmulated(true);
		if(Content.projectExists())
			if(Content.fileExists())
				load();
		setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		LanguageSupportFactory lsf = LanguageSupportFactory.get();
		LanguageSupport support = lsf.getSupportFor(SYNTAX_STYLE_JAVA);
		LanguageSupportFactory.get().register(this);
		ToolTipManager.sharedInstance().registerComponent(this);
		JavaLanguageSupport jls = (JavaLanguageSupport)support;
		try {
			jls.getJarManager().addCurrentJreClassFileSource();
			jls.setAutoActivationEnabled(true);
			jls.setAutoCompleteEnabled(true);
			jls.setAutoActivationDelay(250);
			File jarName = new File(Asset.class.getProtectionDomain().getCodeSource().getLocation().toURI());
			jls.getJarManager().addClassFileSource(jarName);
			jls.install(this);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
        ((RSyntaxTextAreaHighlighter ) getHighlighter()).setDrawsLayeredHighlights(false);
        addFocusListener(new FocusListener(){
			@Override
			public void focusGained(FocusEvent arg0) {
				saveTimer.start();
			}

			@Override
			public void focusLost(FocusEvent arg0) {
				saveTimer.stop();
				save();
			}
        });
        
        addCaretListener(new CaretListener(){
        	@Override
        	public void caretUpdate(CaretEvent e) {
        		StatusBar.updateCaret(getCaretLineNumber()+1, getCaretOffsetFromLineStart()+1);
        	}
        });
        setDropTarget(new DropTarget() {
			private static final long serialVersionUID = 1L;
			public synchronized void drop(DropTargetDropEvent event) {
				try
			    {
			      Transferable transferable = event.getTransferable();
			      if(transferable.isDataFlavorSupported(DataFlavor.stringFlavor))
			      {
			        event.acceptDrop( DnDConstants.ACTION_MOVE );
			        String s = ( String )transferable.getTransferData( DataFlavor.stringFlavor );
			        SinkStudio.log(s);
			        String[] data = s.split(":");
			        switch(AssetType.getType(data[0])){
			        	case Font:
			        		insert("font(\""+data[1]+"\");", getCaretPosition());
			        		break;
			        	case Texture:
			        		insert("tex(\""+data[1]+"\");", getCaretPosition());
							break;
						case Animation:
							insert("anim(\""+data[1]+"\");", getCaretPosition());
							break;
						case Music:
							insert("musicPlay(\""+data[1]+")\";", getCaretPosition());
							break;
						case Sound:
							insert("soundPlay(\""+data[1]+")\";", getCaretPosition());
							break;
						
						case Particle:
							break;
						
						case Button:
							insert("new Button(Asset.skin);", getCaretPosition());
							break;
						case TextButton:
							insert("new TextButton(\"TextButton\",Asset.skin);", getCaretPosition());
							break;
						
						case None:break;
						default:break;
			        }
			        event.getDropTargetContext().dropComplete(true);
			      }
			      else{
			        event.rejectDrop();
			      }
			    }
			    catch( Exception exception ){
			      System.err.println( "Exception" + exception.getMessage() );
			      event.rejectDrop();
			    }
            }
        });
	}
	public void load(){
		if(Content.fileExists()){
			setText(Export.readFile("source/"+Content.getFile()+".java"));
			setDirty(false);
		}
	}
	
	@Override
	public void save(){
		if(isDirty()){
			if(Content.fileExists()){
				Export.writeFile("source/"+Content.getFile()+".java", getText());
				StatusBar.compile();
				setDirty(false);
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
	
	public void find(String text){
    	context.setSearchFor(text);
    	SearchEngine.find(this, context);
    }
    
    public void replace(String text, String replaceText){
    	context.setSearchFor(text);
    	context.setReplaceWith(replaceText);
    	SearchEngine.replace(this, context);
    }
    
    public void replaceAll(String text, String replaceText){
    	context.setSearchFor(text);
    	context.setReplaceWith(replaceText);
    	SearchEngine.replaceAll(this, context);
    }
}