package iwa;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.change_vision.jude.api.inf.AstahAPI;
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException;
import com.change_vision.jude.api.inf.model.IDiagram;
import com.change_vision.jude.api.inf.model.IMindMapDiagram;
import com.change_vision.jude.api.inf.presentation.INodePresentation;
import com.change_vision.jude.api.inf.presentation.IPresentation;
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
            IDiagram diagram = diagramView.getCurrentDiagram();
            IPresentation[] currentP = diagram.getPresentations();
            diagramView.select(currentP[0]);
            if (diagramView.getCurrentDiagram() instanceof IMindMapDiagram) {
                System.out.println("mindmap");
            } else {
                System.out.println("athor");
            }
            JFrame frame = new JFrame("vim");
            frame.setSize(500, 500);
            frame.setVisible(true);
            JTextField text = new JTextField();
            text.setPreferredSize(new Dimension(15, 15));
            frame.add(text);
            text.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    try {
                        update();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                public void removeUpdate(DocumentEvent e) {
                    try {
                        update();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                public void insertUpdate(DocumentEvent e) {
                    try {
                        update();
                    } catch (Exception e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }

                public void update() throws Exception {
                    String input = text.getText();
                    if (input.equals("j")) {
                        AstahAPI api = AstahAPI.getAstahAPI();
                        IDiagramViewManager diagramView = api.getViewManager()
                                .getDiagramViewManager();
                        INodePresentation currentP = (INodePresentation) diagramView
                                .getSelectedPresentations()[0];
                        diagramView.select((IPresentation) currentP.getChildren()[0]);
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                text.setText("");
                            }
                        });
                    }
                }
            });

            // KeyboardFocusManager keybordMgr =
            // KeyboardFocusManager.getCurrentKeyboardFocusManager();

            /*
             * keybordMgr.addKeyEventDispatcher(new KeyEventDispatcher() { public boolean
             * dispatchKeyEvent(KeyEvent e) { System.out.println("EventDispatcher " +
             * e.getKeyChar()); return false; } });
             * 
             * keybordMgr.addKeyEventPostProcessor(new KeyEventPostProcessor() { public boolean
             * postProcessKeyEvent(KeyEvent e) { System.out.println("PostProcessor " +
             * e.getKeyChar()); return false; } });
             */

        } catch (ProjectNotFoundException e) {
            String message = "Project is not opened.Please open the project or create new project.";
            JOptionPane.showMessageDialog(window.getParent(), message, "Warning",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.",
                    "Alert", JOptionPane.ERROR_MESSAGE);
            throw new UnExpectedException();
        }
        return null;
    }

}
