package views;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

public class TestPane extends JPanel {

	/**
	 * Create the panel.
	 */
	public TestPane() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 200));
		panel.setBackground(Color.RED);
		add(panel);
		
		JPanel panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(100, 100));
		panel_1.setBackground(Color.BLUE);
		add(panel_1);

	}

}
