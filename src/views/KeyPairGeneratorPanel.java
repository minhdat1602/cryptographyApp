package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import algorithms.AsymmetricAlgorithm;
import commons.AsymmetricConstants;
import utils.FIleUtils;

public class KeyPairGeneratorPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final int RIGHT_WIDTH = 240;
	private static final int LEFT_WIDTH = WIDTH - 240;

	private JComboBox<AsymmetricConstants> algorithmCbx;
	private JComboBox<Object> keySizeCbx;
	private JTextField privateKeyTf, publicKeyTf;
	private JButton privateKeyBtn, publicKeyBtn;

	// GLOBAL
	List<Integer> listKeySize;
	private String algorithmSelected;
	private int keySizeSelected;

	private AsymmetricAlgorithm cryptography;

	/**
	 * Create the panel.
	 */
	public KeyPairGeneratorPanel() {
		
		// Initilization
		algorithmSelected = "RSA";
		listKeySize = new ArrayList<Integer>();
		listKeySize.add(512);
		listKeySize.add(1024);
		listKeySize.add(2048);
		listKeySize.add(4096);
		keySizeSelected = listKeySize.get(0);
		
		cryptography = new AsymmetricAlgorithm(algorithmSelected, keySizeSelected);

		GUI();
	}

	void GUI() {
		setLayout(new BorderLayout(0, 0));

		JPanel contentSection = new JPanel();
		contentSection.setPreferredSize(new Dimension(LEFT_WIDTH, HEIGHT));
//		contentSection.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(contentSection, BorderLayout.CENTER);
		contentSection.setLayout(new BorderLayout(20, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		contentSection.add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(2, 1, 20, 10));

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_1.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));

		privateKeyTf = new JTextField();
		privateKeyTf.setEditable(false);
		privateKeyTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_4.add(privateKeyTf, BorderLayout.CENTER);
		privateKeyTf.setColumns(33);

		privateKeyBtn = new JButton("   Save   ");
		privateKeyBtn.setBackground(new Color(240, 240, 240));
		privateKeyBtn.setEnabled(false);
		privateKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		privateKeyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(panel_4);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File fileSelected = chooser.getSelectedFile();
					FIleUtils.writeFile(privateKeyTf.getText(), fileSelected);

					JOptionPane.showMessageDialog(panel_4, "Saved");
				}

			}

		});
		panel_4.add(privateKeyBtn, BorderLayout.EAST);
		
		JLabel privateKeyLbl = new JLabel("Private Key");
		privateKeyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		privateKeyLbl.setPreferredSize(new Dimension(80, 100));
		privateKeyLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_4.add(privateKeyLbl, BorderLayout.WEST);

		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.WHITE);
		panel_1.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));

		publicKeyTf = new JTextField();
		publicKeyTf.setEditable(false);
		publicKeyTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.add(publicKeyTf, BorderLayout.CENTER);
		publicKeyTf.setColumns(33);

		publicKeyBtn = new JButton("   Save   ");
		publicKeyBtn.setBackground(new Color(240, 240, 240));
		publicKeyBtn.setEnabled(false);
		publicKeyBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		publicKeyBtn.setHorizontalAlignment(SwingConstants.LEFT);
		publicKeyBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(panel_4);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File fileSelected = chooser.getSelectedFile();
					FIleUtils.writeFile(publicKeyTf.getText(), fileSelected);

					JOptionPane.showMessageDialog(panel_5, "Saved");
				}

			}

		});

		panel_5.add(publicKeyBtn, BorderLayout.EAST);
		
		JLabel publicKeyLbl = new JLabel("Public Key ");
		publicKeyLbl.setHorizontalAlignment(SwingConstants.CENTER);
		publicKeyLbl.setPreferredSize(new Dimension(80, 100));
		publicKeyLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_5.add(publicKeyLbl, BorderLayout.WEST);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.WHITE);
		panel_2.setPreferredSize(new Dimension(800, 170));
		contentSection.add(panel_2, BorderLayout.NORTH);
		panel_2.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBackground(Color.WHITE);
//		ImageIcon imageIcon1 = new ImageIcon(new ImageIcon(HashPanel.class.getResource("/images/6.jpg")).getImage().getScaledInstance(580, 250, 1));
//		lblNewLabel_1.setIcon(imageIcon1);
		panel_2.add(lblNewLabel_1);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.WHITE);
		panel_3.setPreferredSize(new Dimension(800, 260));
		contentSection.add(panel_3, BorderLayout.SOUTH);
		panel_3.setLayout(new GridLayout(1, 0, 0, 0));

		JLabel lblNewLabel = new JLabel("");
		ImageIcon imageIcon2 = new ImageIcon(new ImageIcon(HashPanel.class.getResource("/images/Public-Key-and-the-Private-Key.jpg")).getImage().getScaledInstance(580, 280, 0));
		lblNewLabel.setIcon(imageIcon2);
		panel_3.add(lblNewLabel);
		// Output section end

		JPanel propertySection = new JPanel();
		propertySection.setBackground(new Color(240, 240, 240));
		propertySection.setLayout(null);
		propertySection.setPreferredSize(new Dimension(RIGHT_WIDTH, HEIGHT));
		propertySection.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Select", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		propertySection.setBounds(LEFT_WIDTH, 0, RIGHT_WIDTH, HEIGHT);
		add(propertySection, BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 220, 83);
		propertySection.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 5));

		JLabel algorithmLbl = new JLabel("Algorithms");
		algorithmLbl.setHorizontalAlignment(SwingConstants.CENTER);
		algorithmLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(algorithmLbl);

		algorithmCbx = new JComboBox<AsymmetricConstants>();
		algorithmCbx.setBackground(Color.WHITE);
		algorithmCbx.setModel(new DefaultComboBoxModel<AsymmetricConstants>(AsymmetricConstants.values()));
		algorithmCbx.setSelectedIndex(0);
		algorithmCbx.setSelectedItem(algorithmSelected);
		algorithmCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				algorithmSelected = algorithmCbx.getSelectedItem().toString();
				System.out.println(algorithmSelected);

				switch (algorithmSelected) {
				case "RSA":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(512);
					listKeySize.add(1024);
					listKeySize.add(2048);
					listKeySize.add(4096);
					break;
				}
				keySizeSelected = listKeySize.get(0);
				List<String> listKeySizeStr = listKeySize.stream().map(item -> item.toString())
						.collect(Collectors.toList());

				keySizeCbx.setModel(new DefaultComboBoxModel<Object>(listKeySizeStr.toArray()));

			}
		});
		algorithmCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(algorithmCbx);

		JLabel keySizeLbl = new JLabel("Key size");
		keySizeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		keySizeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(keySizeLbl);

		List<String> listKeySizeStr = listKeySize.stream().map(item -> item.toString()).collect(Collectors.toList());
		keySizeCbx = new JComboBox<Object>(listKeySizeStr.toArray());
		keySizeCbx.setBackground(Color.WHITE);
//		keySizeCbx.setModel(new DefaultComboBoxModel(listKeySizeStr.toArray()));
		keySizeCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keySizeCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				keySizeSelected = Integer.parseInt(keySizeCbx.getSelectedItem().toString());
				System.out.println(keySizeSelected);

			}
		});
		panel.add(keySizeCbx);

		JButton runBtn = new JButton("Start");
		ImageIcon runIcon = new ImageIcon(new ImageIcon(AsymmetricPane.class.getResource("/images/run.png")).getImage().getScaledInstance(30, 30, 1));
		runBtn.setIcon(runIcon);
		runBtn.setForeground(Color.WHITE);
		runBtn.setBackground(Color.DARK_GRAY);
		runBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		runBtn.setBounds(10, 153, 220, 50);
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					KeyPair keypair = cryptography.generateKey();
					privateKeyTf.setText(Base64.getEncoder().encodeToString(keypair.getPrivate().getEncoded()));
					publicKeyTf.setText(Base64.getEncoder().encodeToString(keypair.getPublic().getEncoded()));

					privateKeyBtn.setEnabled(true);
					publicKeyBtn.setEnabled(true);
				} catch (NoSuchAlgorithmException e1) {
					e1.printStackTrace();
				}

				System.out.println("-------------START---------------");
				System.out.println("Algorithm: " + algorithmSelected);
				System.out.println("Key size: " + keySizeSelected);

			}
		});
		propertySection.add(runBtn);

		JCheckBox generatePairCb = new JCheckBox("Generate key pair");
		generatePairCb.setBackground(Color.WHITE);
		generatePairCb.setSelected(true);
		generatePairCb.setEnabled(false);
		generatePairCb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		generatePairCb.setBounds(10, 24, 220, 30);
		propertySection.add(generatePairCb);

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ABC");
		JPanel asymmetricPane = new AsymmetricPane();
		frame.getContentPane().add(asymmetricPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
