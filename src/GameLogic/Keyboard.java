package GameLogic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener {
	@Override
	public void keyTyped(KeyEvent e) {
	}


	// Only track space bar presses using a flag.
	private boolean spacePressed = false;

	// Call this to query and clear the space press (one-time detection)
	public boolean isSpaceClicked() {
		if (spacePressed) {
			spacePressed = false;
			return true;
		}
		return false;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

}