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

import algorithms.AsymmetricAlgorithm;
import commons.AsymmetricConstants;
import utils.FIleUtils;

public class AsymmetricPane extends JPanel {

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
	private String algorithmSelected;
	private int keySizeSelected;

	private JComboBox algorithmCbx, keySizeCbx;
	private JRadioButton encryptBtn, decryptBtn;

	// GLOBAL
	private AsymmetricAlgorithm cryptography;
	private String inputText, keyText, outputText;

	/**
	 * Create the panel.
	 */
	public AsymmetricPane() {
		algorithmSelected = "RSA";
		keySizeSelected = 512;
		listKeySize = new ArrayList<Integer>();
		listKeySize.add(512);
		listKeySize.add(1024);
		listKeySize.add(2048);
		listKeySize.add(4096);

		cryptography = new AsymmetricAlgorithm(algorithmSelected, keySizeSelected);

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
		keyTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyComponent.add(keyTf);
		keyTf.setColumns(10);

		JPanel keyFileComponent = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) keyFileComponent.getLayout();
		flowLayout_1.setHgap(15);
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		keyComponent.add(keyFileComponent);

		keyFileLbl = new JLabel("Public key");
		keyFileLbl.setEnabled(false);
		keyFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileComponent.add(keyFileLbl);

		keyFileTf = new JTextField();
		keyFileLbl.setLabelFor(keyFileTf);
		keyFileTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileTf.setEnabled(false);
		keyFileComponent.add(keyFileTf);
		keyFileTf.setColumns(33);

		keyFileBtn = new JButton("Open file");
		keyFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
//				if (decryptBtn.isSelected()) {
					int returnVal = chooser.showOpenDialog(keyFileComponent);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						keyFile = chooser.getSelectedFile();
						keyFileTf.setText(keyFile.getPath());

						keyTxt = FIleUtils.readFile(keyFile);
						keyTf.setText(keyTxt);
					}
//				} else if (encryptBtn.isSelected()) {
//					int returnVal = chooser.showSaveDialog(keyFileComponent);
//					if (returnVal == JFileChooser.APPROVE_OPTION) {
//						keyFile = chooser.getSelectedFile();
//
//						keyTxt = keyTf.getText();
//						FIleUtils.writeFile(keyTxt, keyFile);
//					}
//				}
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
					
					JOptionPane.showMessageDialog(inputComponent, "Saved");
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

		algorithmCbx = new JComboBox<AsymmetricConstants>();
		algorithmCbx.setModel(new DefaultComboBoxModel<AsymmetricConstants>(AsymmetricConstants.values()));
		algorithmCbx.setSelectedIndex(0);
		algorithmCbx.setSelectedItem(algorithmSelected);
		algorithmCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = algorithmCbx.getSelectedItem().toString();
				System.out.println(algorithmSelected);

				switch (algorithmSelected) {
				case "RSA":
					listKeySize = new ArrayList<Integer>();
					listKeySize.add(512);
					listKeySize.add(1024);
					listKeySize.add(2048);

					break;
				}
				keySizeSelected = listKeySize.get(0);
				List<String> listKeySizeStr = listKeySize.stream().map(item -> item.toString())
						.collect(Collectors.toList());

				keySizeCbx.setModel(new DefaultComboBoxModel(listKeySizeStr.toArray()));
			}
		});
		algorithmCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(algorithmCbx);

		JLabel keySizeLbl = new JLabel("Key size");
		keySizeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		keySizeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(keySizeLbl);

//		keySizeCbx = new JComboBox<String>();
		List<String> listKeySizeStr = listKeySize.stream().map(item -> item.toString()).collect(Collectors.toList());
		keySizeCbx = new JComboBox<Object>(listKeySizeStr.toArray());
		keySizeCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keySizeCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				keySizeSelected = Integer.parseInt(keySizeCbx.getSelectedItem().toString());
				System.out.println(keySizeSelected);
			}
		});
		panel.add(keySizeCbx);

		encryptBtn = new JRadioButton("Encrypt");
		encryptBtn.setHorizontalAlignment(SwingConstants.RIGHT);
		encryptBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		encryptBtn.setBounds(10, 33, 105, 21);
		encryptBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				keyFileLbl.setText("Public key");
				
//				keyFileBtn.setText("Save file");
//				keyTf.setEditable(false);
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
				keyFileLbl.setText("Private key");
				
//				keyFileBtn.setText("Open file");
//				keyFileBtn.setEnabled(true);
//				keyTf.setEditable(true);
			}
		});
		propertySection.add(decryptBtn);

		JButton runBtn = new JButton("Start");
		ImageIcon runIcon = new ImageIcon(new ImageIcon(AsymmetricPane.class.getResource("/images/run.png")).getImage().getScaledInstance(30, 30, 1));
		runBtn.setIcon(runIcon);
		runBtn.setForeground(Color.WHITE);
		runBtn.setBackground(Color.DARK_GRAY);
		runBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		runBtn.setBounds(10, 153, 220, 50);
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (inputArea.getText().trim().isEmpty()) {
					JOptionPane.showMessageDialog(inputComponent, "Input is empty");
					return;
				}

				cryptography.setAlgorithm(algorithmSelected);
				cryptography.setKeySize(keySizeSelected);
				
				try {
					inputText = inputArea.getText();;
					keyText = keyTf.getText();
					
					if (encryptBtn.isSelected()) {
						outputText = cryptography.doEncryption(inputText, keyText);
					} else if (decryptBtn.isSelected()) {
						outputText = cryptography.doDecryption(inputText, keyText);
					}
					
					outputArea.setText(outputText);
					
					keyFileBtn.setEnabled(true);
					outputFileBtn.setEnabled(true);
					
				}catch(Exception ex2) {
					JOptionPane.showMessageDialog(contentSection, ex2.getMessage());
					System.out.println(ex2.getMessage());
				}

				System.out.println("-------------START---------------");
				System.out.println("Algorithm: " + algorithmSelected);
				System.out.println("Key size: " + keySizeSelected);
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
		JPanel asymmetricPane = new AsymmetricPane();
		frame.getContentPane().add(asymmetricPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
