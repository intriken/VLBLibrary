
/**
 * @author qgtf 
 */
package vlb.ide.gui;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

/**
 * EnterButton creates a button that when CTRL+Enter are pressed it executes its action
 */

public class EnterButton extends JButton {
    
	private static final long serialVersionUID = 2037434704680676025L;

	public EnterButton(String name){
        super(name);
        
        super.registerKeyboardAction(
                super.getActionForKeyStroke(
                        KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0, false)),
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK, false),
                        JComponent.WHEN_IN_FOCUSED_WINDOW);
        super.registerKeyboardAction(
                super.getActionForKeyStroke(
                        KeyStroke.getKeyStroke(KeyEvent.VK_SPACE,0, true)),
                        KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, InputEvent.CTRL_MASK, true),
                        JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

}

