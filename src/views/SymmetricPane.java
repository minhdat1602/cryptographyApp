package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.ButtonGroup;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import algorithms.SymmetricAlgorithm;
import commons.SymmetricConstants;
import utils.FIleUtils;

public class SymmetricPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final int RIGHT_WIDTH = 240;
	private static final int LEFT_WIDTH = WIDTH - 240;

	// LEFT
	private JTextArea inputArea, outputArea;
	private JTextField keyTf;
	private JLabel inputFileLbl, outputFileLbl, keyFileLbl;
	private JTextField inputFileTf, outputFileTf, keyFileTf;
	private JButton inputFileBtn, outputFileBtn, keyFileBtn;
	ButtonGroup typeCipher;

	private File inputFile, outputFile, keyFile;
	private String inputTxt, outputTxt, keyTxt;

	// RIGHT
	List<Integer> listKeySize;
	List<String> listMode, listPadding;
	private String algorithmSelected, modeSelected, paddingSelected;
	private int keySizeSelected;

	private JComboBox<SymmetricConstants> algorithmCbx;
	private JComboBox<Object> keySizeCbx, paddingCbx;
	JComboBox<Object> modeCbx;

	private JRadioButton encryptBtn, decryptBtn;
	private JCheckBox autoGenerationCb;

	// GLOBAL
	private SymmetricAlgorithm cryptography;
	private String inputText, keyText, outputText;

	/**
	 * Create the panel.
	 */
	public SymmetricPane() {
		algorithmSelected = "DES";
		keySizeSelected = 56;
		modeSelected = "None";
		paddingSelected = "noPadding";

		listMode = new ArrayList<String>();
		listMode.add("None");
//		listMode.add("CBC");
		listMode.add("CTR");
		listMode.add("CFB");
		listMode.add("OFB");

		listPadding = new ArrayList<String>();
		listPadding.add("NoPadding");
		listPadding.add("PKCS5Padding");

		cryptography = new SymmetricAlgorithm(algorithmSelected, keySizeSelected);

		GUI();
	}

	void GUI() {
		setLayout(new BorderLayout(0, 0));

		JPanel contentSection = new JPanel();
		contentSection.setPreferredSize(new Dimension(LEFT_WIDTH, HEIGHT));
//		contentSection.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		add(contentSection, BorderLayout.CENTER);
		contentSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// INPUT COMPONENT BEGIN...
		JPanel inputComponent = new JPanel();
		inputComponent.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		inputComponent.setPreferredSize(new Dimension(LEFT_WIDTH, 200));
		contentSection.add(inputComponent);
		inputComponent.setLayout(new BorderLayout(0, 0));

		inputArea = new JTextArea();
		JScrollPane inputSp = new JScrollPane(inputArea);
		inputComponent.add(inputSp, BorderLayout.CENTER);

		JPanel inputFileComponent = new JPanel();
		FlowLayout flowLayout = (FlowLayout) inputFileComponent.getLayout();
		flowLayout.setHgap(15);
		flowLayout.setAlignment(FlowLayout.LEFT);
		inputFileComponent.setPreferredSize(new Dimension(LEFT_WIDTH - 12, 30));
		inputComponent.add(inputFileComponent, BorderLayout.SOUTH);

		inputFileLbl = new JLabel("Input file");
		inputFileLbl.setEnabled(false);
		inputFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileComponent.add(inputFileLbl);

		inputFileTf = new JTextField();
		inputFileLbl.setLabelFor(inputFileTf);
		inputFileTf.setEnabled(false);
		inputFileTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileComponent.add(inputFileTf);
		inputFileTf.setColumns(35);

		inputFileBtn = new JButton("Open file");
		inputFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooseInputFile = new JFileChooser();
				int returnVal = chooseInputFile.showOpenDialog(inputComponent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					inputFile = chooseInputFile.getSelectedFile();

					inputFileTf.setText(inputFile.getName());

					inputTxt = FIleUtils.readFile(inputFile);
					inputArea.setText(inputTxt);
				} else {
					System.out.println("NOOOOOOOOOOOOOOOOO");
				}
			}
		});
		inputFileComponent.add(inputFileBtn);
		// INPUT COMPONENT ENDDDDDDDDDDDDDDDDDDD

		// KEY COMPONENT BEGINNNNNNNNNNNNNNNNNNNNN
		JPanel keyComponent = new JPanel();
		keyComponent.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Key",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		keyComponent.setPreferredSize(new Dimension(LEFT_WIDTH, 85));
		contentSection.add(keyComponent);
		keyComponent.setLayout(new GridLayout(2, 1, 0, 0));

		keyTf = new JTextField();
		keyTf.setEditable(false);
		keyTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyComponent.add(keyTf);
		keyTf.setColumns(10);

		JPanel keyFileComponent = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) keyFileComponent.getLayout();
		flowLayout_1.setHgap(15);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		keyComponent.add(keyFileComponent);

		keyFileLbl = new JLabel("Key file");
		keyFileLbl.setEnabled(false);
		keyFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileComponent.add(keyFileLbl);

		keyFileTf = new JTextField();
		keyFileLbl.setLabelFor(keyFileTf);
		keyFileTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileTf.setEnabled(false);
		keyFileComponent.add(keyFileTf);
		keyFileTf.setColumns(36);

		keyFileBtn = new JButton("Save file");
		keyFileBtn.setEnabled(false);
		keyFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				if (decryptBtn.isSelected()) {
					int returnVal = chooser.showOpenDialog(keyFileComponent);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						keyFile = chooser.getSelectedFile();
						keyFileTf.setText(keyFile.getPath());

						keyTxt = FIleUtils.readFile(keyFile);
						keyTf.setText(keyTxt);
					}
				} else if (encryptBtn.isSelected()) {
					if (!autoGenerationCb.isSelected()) {
						int returnVal = chooser.showOpenDialog(keyFileComponent);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							File file = chooser.getSelectedFile();
							keyFileTf.setText(file.getPath());

							keyTxt = FIleUtils.readFile(file);
							keyTf.setText(keyTxt);
						}
					} else {
						int returnVal = chooser.showSaveDialog(keyFileComponent);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							keyFile = chooser.getSelectedFile();

							keyTxt = keyTf.getText();
							FIleUtils.writeFile(keyTxt, keyFile);
							
							JOptionPane.showMessageDialog(contentSection, "Saved");
						}
					}

				}

			}
		});
		keyFileComponent.add(keyFileBtn);
		// KEY COMPONENT ENDDDDDDDDDDDDDDDDDDDDDDDD

		// OUTPUT COMPONENT BEGINNNNNNNNNNNNNNNNNNN
		JPanel outputComponent = new JPanel();
		outputComponent.setBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		outputComponent.setPreferredSize(new Dimension(LEFT_WIDTH, 200));
		contentSection.add(outputComponent);
		outputComponent.setLayout(new BorderLayout(0, 0));

		outputArea = new JTextArea();
		outputArea.setEditable(false);
		JScrollPane outputSp = new JScrollPane(outputArea);
		outputComponent.add(outputSp, BorderLayout.CENTER);

		JPanel outputFileComponent = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) outputFileComponent.getLayout();
		flowLayout_2.setHgap(15);
		flowLayout_2.setAlignment(FlowLayout.LEFT);
		outputFileComponent.setPreferredSize(new Dimension(LEFT_WIDTH - 12, 30));
		outputComponent.add(outputFileComponent, BorderLayout.SOUTH);

		outputFileLbl = new JLabel("Output file");
		outputFileLbl.setEnabled(false);
		outputFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		outputFileComponent.add(outputFileLbl);

		outputFileTf = new JTextField();
		outputFileTf.setEditable(false);
		outputFileTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		outputFileComponent.add(outputFileTf);
		outputFileTf.setColumns(34);

		outputFileBtn = new JButton("Save file");
		outputFileBtn.setEnabled(false);
		outputFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		outputFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(inputComponent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					outputFile = chooser.getSelectedFile();
					outputTxt = outputArea.getText();
					FIleUtils.writeFile(outputTxt, outputFile);
					
					JOptionPane.showMessageDialog(contentSection, "Saved");
				} else {
					System.out.println("NOOOOOOOOOOOOOOOOO");
				}
			}
		});
		outputFileComponent.add(outputFileBtn);
		// Output section end

		JPanel propertySection = new JPanel();
		propertySection.setLayout(null);
		propertySection.setPreferredSize(new Dimension(RIGHT_WIDTH, HEIGHT));
		propertySection.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Select", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		propertySection.setBounds(LEFT_WIDTH, 0, RIGHT_WIDTH, HEIGHT);
		add(propertySection, BorderLayout.EAST);

		JPanel panel = new JPanel();
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

		algorithmCbx = new JComboBox<SymmetricConstants>();
		algorithmCbx.setModel(new DefaultComboBoxModel<SymmetricConstants>(SymmetricConstants.values()));
//		algorithmCbx.setSelectedIndex(0);
		algorithmCbx.setSelectedItem(algorithmSelected);
		algorithmCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = algorithmCbx.getSelectedItem().toString();
				System.out.println(algorithmSelected);

				switch (algorithmSelected) {
				case "DES":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(56);

//					listMode = new ArrayList<String>();
//					listMode.add("CBC");
//					listMOde.add("ECB");
					break;
				case "AES":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(128);
					listKeySize.add(192);
					listKeySize.add(256);
					break;
				case "DESede":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(112);
					listKeySize.add(168);
					break;
				case "RC2":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(56);
					listKeySize.add(112);
					listKeySize.add(128);
					listKeySize.add(256);
					break;
				case "RC4":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(56);
					listKeySize.add(112);
					listKeySize.add(128);
					listKeySize.add(256);
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

		keySizeCbx = new JComboBox<Object>(new String[] { ("" + keySizeSelected) });
//		keySizeCbx.setModel(new DefaultComboBoxModel());
		keySizeCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keySizeCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				keySizeSelected = Integer.parseInt(keySizeCbx.getSelectedItem().toString());
				System.out.println(keySizeSelected);
			}
		});
		panel.add(keySizeCbx);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Option",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel_1.setBounds(10, 160, 220, 83);
		propertySection.add(panel_1);
		panel_1.setLayout(new GridLayout(2, 2, 0, 5));

		JLabel modeLbl = new JLabel("Mode");
		modeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		modeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(modeLbl);

		modeCbx = new JComboBox<Object>(listMode.toArray());
		modeCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		modeCbx.setModel(new DefaultComboBoxModel());
		modeCbx.setSelectedIndex(0);
		modeCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				modeSelected = modeCbx.getSelectedItem().toString();
				if (modeSelected.equalsIgnoreCase("None")) {
					paddingCbx.setEnabled(false);
				} else {
					paddingCbx.setEnabled(true);
				}
			}

		});
		panel_1.add(modeCbx);

		JLabel paddingLbl = new JLabel("Padding");
		paddingLbl.setHorizontalAlignment(SwingConstants.CENTER);
		paddingLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel_1.add(paddingLbl);

		paddingCbx = new JComboBox<Object>(listPadding.toArray());
		paddingCbx.setEnabled(false);
		paddingCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
//		paddingCbx.setModel(new DefaultComboBoxModel());
		paddingCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				paddingSelected = paddingCbx.getSelectedItem().toString();
			}

		});
		panel_1.add(paddingCbx);

		encryptBtn = new JRadioButton("Encrypt");
		encryptBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		encryptBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		encryptBtn.setBounds(10, 33, 105, 21);
		encryptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				autoGenerationCb.setEnabled(true);
				if(autoGenerationCb.isSelected()) {
					keyFileBtn.setText("Save file");
					keyFileBtn.setEnabled(false);
					keyTf.setEditable(false);
				}else {
					keyFileBtn.setText("Open file");
					keyFileBtn.setEnabled(true);
					keyTf.setEditable(true);
				}
			}

		});
		propertySection.add(encryptBtn);

		decryptBtn = new JRadioButton("Decrypt");
		decryptBtn.setHorizontalAlignment(SwingConstants.LEFT);
		decryptBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		decryptBtn.setBounds(125, 33, 105, 21);
		decryptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				keyFileBtn.setText("Open file");
				keyFileBtn.setEnabled(true);
				keyTf.setEditable(true);
				
				autoGenerationCb.setEnabled(false);
			}
		});
		propertySection.add(decryptBtn);

		autoGenerationCb = new JCheckBox("Auto generate key");
		autoGenerationCb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		autoGenerationCb.setBounds(10, 249, 220, 21);
		autoGenerationCb.setSelected(true);
		autoGenerationCb.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (autoGenerationCb.isSelected()) {
					keyTf.setEditable(false);
					keyFileBtn.setText("Save file");
					keyFileBtn.setEnabled(false);
				} else {
					keyTf.setEditable(true);
					keyFileBtn.setText("Open file");
					keyFileBtn.setEnabled(true);
				}
			}

		});
		propertySection.add(autoGenerationCb);

		JButton runBtn = new JButton("Start");
		ImageIcon runIcon = new ImageIcon(new ImageIcon(AsymmetricPane.class.getResource("/images/run.png")).getImage().getScaledInstance(30, 30, 1));
		runBtn.setIcon(runIcon);
		runBtn.setBackground(Color.DARK_GRAY);
		runBtn.setForeground(Color.WHITE);
		runBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		runBtn.setBounds(10, 309, 220, 50);
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputArea.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(inputComponent, "Input is empty");
					return;
				}

				cryptography.setAlgorithm(algorithmSelected);
				cryptography.setKeySize(keySizeSelected);
				cryptography.setMode(modeSelected);
				cryptography.setPadding(paddingSelected);

				try {
					inputText = inputArea.getText();
					if (encryptBtn.isSelected()) {
						if(autoGenerationCb.isSelected()) {
							keyText = cryptography.generateKey();
							keyTf.setText(keyText);
						}else {
							keyText = keyTf.getText();
						}
						outputText = cryptography.encrypt(inputText, keyText);
						outputArea.setText(outputText);

						keyFileBtn.setEnabled(true);
						outputFileBtn.setEnabled(true);
					} else if (decryptBtn.isSelected()) {
						outputText = cryptography.decrypt(inputText, keyTf.getText());
						System.out.println(outputText);
						outputArea.setText(outputText);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(contentSection, ex.getMessage());
				}

				System.out.println("-------------START---------------");
				System.out.println("Algorithm: " + algorithmSelected);
				System.out.println("Key size: " + keySizeSelected);
				System.out.println("Mode: " + modeSelected);
				System.out.println("Padding: " + paddingSelected);
				System.out.println("Type: " + (encryptBtn.isSelected() ? "encrypt" : "decrypt"));

			}

		});
		propertySection.add(runBtn);

		typeCipher = new ButtonGroup();
		typeCipher.add(encryptBtn);
		typeCipher.add(decryptBtn);
		encryptBtn.setSelected(true);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ABC");
		JPanel sysmetricPane = new SymmetricPane();
		frame.getContentPane().add(sysmetricPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
