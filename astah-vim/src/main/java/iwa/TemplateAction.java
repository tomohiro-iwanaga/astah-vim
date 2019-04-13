package iwa;

import java.awt.KeyEventDispatcher;
import java.awt.KeyEventPostProcessor;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IMindMapDiagram;
import com.change_vision.jude.api.inf.project.ProjectAccessor;
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate;
import com.change_vision.jude.api.inf.ui.IWindow;
import com.change_vision.jude.api.inf.view.IDiagramViewManager;

public class TemplateAction implements IPluginActionDelegate {

	public Object run(IWindow window) throws UnExpectedException {
		try {
			AstahAPI api = AstahAPI.getAstahAPI();
			ProjectAccessor projectAccessor = api.getProjectAccessor();
			projectAccessor.getProject();
			IDiagramViewManager diagramView = api.getViewManager().getDiagramViewManager();
			if(diagramView.getCurrentDiagram() instanceof IMindMapDiagram)
			{
					System.out.println("mindmap");
			}else
			{
					System.out.println("athor");
			}
			JFrame frame = new JFrame("vim");
			frame.setSize(100,100);
			frame.setVisible(true);

			JOptionPane.showMessageDialog(window.getParent(), "Hello");

			KeyboardFocusManager keybordMgr = KeyboardFocusManager.getCurrentKeyboardFocusManager();

			keybordMgr.addKeyEventDispatcher(new KeyEventDispatcher() {
				public boolean dispatchKeyEvent(KeyEvent e) {
					System.out.println("EventDispatcher " + e.getKeyChar());
					return false;
				}
			});

			keybordMgr.addKeyEventPostProcessor(new KeyEventPostProcessor() {
				public boolean postProcessKeyEvent(KeyEvent e) {
					System.out.println("PostProcessor " + e.getKeyChar());
					return false;
				}
			});

		} catch (ProjectNotFoundException e) {
			String message = "Project is not opened.Please open the project or create new project.";
			JOptionPane.showMessageDialog(window.getParent(), message, "Warning", JOptionPane.WARNING_MESSAGE);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert",
					JOptionPane.ERROR_MESSAGE);
			throw new UnExpectedException();
		}
		return null;
	}

}
