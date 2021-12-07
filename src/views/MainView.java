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

	private JButton symmetricNavBtn, asymmetricNavBtn, generatePairNavBtn, hashFunctionNavBtn;

	JPanel mainPane;
	private JPanel symmetricPanel, asymmetricPanel, generatePairPanel, hashPanel;

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
		
		mainPane = symmetricPanel;

		GUI();
	}

	void GUI() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBackground(new Color(240, 240, 240));
		toolBar.setPreferredSize(new Dimension(850, 50));
		contentPane.add(toolBar, BorderLayout.NORTH);

		symmetricNavBtn = new JButton("Symmetric");
		ImageIcon symmetricIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/symmetric.png")).getImage().getScaledInstance(25, 25, 1));
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

		asymmetricNavBtn = new JButton("Asymmetric");
		ImageIcon asymmetricIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/asymmetric.png")).getImage().getScaledInstance(25, 25, 1));
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
		
		generatePairNavBtn = new JButton("Generate Pair");
		ImageIcon pairIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/pair.png")).getImage().getScaledInstance(25, 25, 1));
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
		
		hashFunctionNavBtn = new JButton("Hash          ");
		ImageIcon hashIcon = new ImageIcon(new ImageIcon(MainView.class.getResource("/images/hash.png")).getImage().getScaledInstance(25, 25, 1));
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

		contentPane.add(mainPane, BorderLayout.CENTER);
	}

	void resetColorForNav() {
		symmetricNavBtn.setBackground(new Color(255, 255, 255));
		asymmetricNavBtn.setBackground(new Color(255, 255, 255));
		generatePairNavBtn.setBackground(new Color(255, 255, 255));
		hashFunctionNavBtn.setBackground(new Color(255, 255, 255));
	}

}
