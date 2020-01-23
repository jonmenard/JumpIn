package game;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class JIButton extends JButton {
	public int x;
	public int y;
	
	public JIButton(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
