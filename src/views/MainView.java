package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.border.EmptyBorder;

public class MainView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private static final int WIDTH = 850;

	private JButton symmetricNavBtn, asymmetricNavBtn;
	
	JPanel mainPane;
	private JPanel symmetricPanel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 590);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//initialize main panel
		symmetricPanel = new SymmetricPane();
		mainPane = new SymmetricPane();
		
		GUI();
	}

	void GUI() {
		JToolBar toolBar = new JToolBar();
		toolBar.setPreferredSize(new Dimension(WIDTH, 25));
		contentPane.add(toolBar, BorderLayout.NORTH);

		symmetricNavBtn = new JButton("Symmetric");
		symmetricNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		symmetricNavBtn.setBackground(Color.WHITE);
		symmetricNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				symmetricNavBtn.setBackground(Color.WHITE);
			}

		});
		toolBar.add(symmetricNavBtn);

		asymmetricNavBtn = new JButton("Asymmetric");
		asymmetricNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		asymmetricNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				asymmetricNavBtn.setBackground(Color.WHITE);
			}

		});
		toolBar.add(asymmetricNavBtn);

		contentPane.add(mainPane, BorderLayout.CENTER);
	}

	void resetColorForNav() {
		symmetricNavBtn.setBackground(new Color(240, 240, 240));
		asymmetricNavBtn.setBackground(new Color(240, 240, 240));
	}

}
