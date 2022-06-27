package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
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

	private JButton symmetricNavBtn, asymmetricNavBtn, generatePairNavBtn, hashFunctionNavBtn, signNavBtn, verifyNavBtn;

	JPanel mainPane;
	private JPanel symmetricPanel, asymmetricPanel, generatePairPanel, hashPanel, signPanel, verifyPanel;

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
		this.setTitle("Cryptography");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(100, 100, 850, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		// initialize main panel
		symmetricPanel = new SymmetricPane();
		asymmetricPanel = new AsymmetricPane();
		generatePairPanel = new KeyPairGeneratorPanel();
		hashPanel = new HashPanel();
		signPanel = new SignPanel();
		verifyPanel = new VerifyPanel();

		mainPane = signPanel;

		GUI();
	}

	void GUI() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(240, 240, 240));
		toolBar.setPreferredSize(new Dimension(850, 50));
		contentPane.add(toolBar, BorderLayout.NORTH);
		
		/*
		// Symmetric
		symmetricNavBtn = new JButton("Symmetric");
		ImageIcon symmetricIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/symmetric.png"))
				.getImage().getScaledInstance(25, 25, 1));
		symmetricNavBtn.setIcon(symmetricIcon);
		symmetricNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		symmetricNavBtn.setBackground(Color.LIGHT_GRAY);
		symmetricNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				symmetricNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = symmetricPanel;
				contentPane.add(mainPane);
				repaint();
			}

		});
		toolBar.add(symmetricNavBtn);
		
		//ASymmetric
		asymmetricNavBtn = new JButton("Asymmetric");
		ImageIcon asymmetricIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/asymmetric.png"))
				.getImage().getScaledInstance(25, 25, 1));
		asymmetricNavBtn.setIcon(asymmetricIcon);
		asymmetricNavBtn.setBackground(Color.WHITE);
		asymmetricNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		asymmetricNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				asymmetricNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = asymmetricPanel;
				contentPane.add(mainPane);
				validate();
				repaint();
			}

		});
		toolBar.add(asymmetricNavBtn);
		*/
		
		//Key Pair
		generatePairNavBtn = new JButton("Genkey pair");
		ImageIcon pairIcon = new ImageIcon(
				new ImageIcon(MainView.class.getResource("/images/pair.png")).getImage().getScaledInstance(25, 25, 1));
		generatePairNavBtn.setIcon(pairIcon);
		generatePairNavBtn.setBackground(Color.WHITE);
		generatePairNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		generatePairNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				generatePairNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = generatePairPanel;
				contentPane.add(mainPane);
				validate();
				repaint();
			}

		});
		toolBar.add(generatePairNavBtn);

		// Hash Navigation
		hashFunctionNavBtn = new JButton("Hash          ");
		ImageIcon hashIcon = new ImageIcon(
				new ImageIcon(MainView.class.getResource("/images/hash.png")).getImage().getScaledInstance(25, 25, 1));
		hashFunctionNavBtn.setIcon(hashIcon);
		hashFunctionNavBtn.setBackground(Color.WHITE);
		hashFunctionNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hashFunctionNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				hashFunctionNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = hashPanel;
				contentPane.add(mainPane);
				validate();
				repaint();
			}

		});
		toolBar.add(hashFunctionNavBtn);

		// Signature Navigation
		signNavBtn = new JButton("Signature");
		ImageIcon signIcon = new ImageIcon(
				new ImageIcon(MainView.class.getResource("/images/hash.png")).getImage().getScaledInstance(25, 25, 1));
		signNavBtn.setIcon(signIcon);
		signNavBtn.setBackground(Color.WHITE);
		signNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		signNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				signNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = signPanel;
				contentPane.add(mainPane);
				validate();
				repaint();
			}

		});
		toolBar.add(signNavBtn);
		
		// Vefiry Panel
		verifyNavBtn = new JButton("Verify     ");
		ImageIcon verifyIcon = new ImageIcon(
				new ImageIcon(MainView.class.getResource("/images/hash.png")).getImage().getScaledInstance(25, 25, 1));
		verifyNavBtn.setIcon(verifyIcon);
		verifyNavBtn.setBackground(Color.WHITE);
		verifyNavBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		verifyNavBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetColorForNav();
				verifyNavBtn.setBackground(Color.LIGHT_GRAY);

				contentPane.remove(mainPane);
				mainPane = verifyPanel;
				contentPane.add(mainPane);
				validate();
				repaint();
			}

		});
		toolBar.add(verifyNavBtn);

		contentPane.add(mainPane, BorderLayout.CENTER);
	}

	void resetColorForNav() {
//		symmetricNavBtn.setBackground(new Color(255, 255, 255));
//		asymmetricNavBtn.setBackground(new Color(255, 255, 255));
		generatePairNavBtn.setBackground(new Color(255, 255, 255));
		hashFunctionNavBtn.setBackground(new Color(255, 255, 255));
		signNavBtn.setBackground(new Color(255, 255, 255));
		verifyNavBtn.setBackground(new Color(255, 255, 255));
	}

}
