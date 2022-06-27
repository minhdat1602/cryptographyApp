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
import java.util.Base64;
import java.util.List;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import algorithms.DigitalSignature;
import utils.AlgorithmUtils;

public class SignPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final int RIGHT_WIDTH = 240;
	private static final int LEFT_WIDTH = WIDTH - 240;

	// LEFT
	private JTextArea inputArea, outputArea;
	private JTextField keyTf;
	private JLabel inputFileLbl, keyFileLbl, outputFileLbl;
	private JTextField inputFileTf, keyFileTf, outputFileTf;
	private JButton inputFileBtn, keyFileBtn, outputFileBtn;
	ButtonGroup typeCipher;

	private String inputTxt, privateKeyTxt, outputTxt;

	// RIGHT
	private List<Integer> listKeySize;
	private List<String> listAlgorithm;
	private String algorithmSelected;
	private Integer keySizeSelected;

	private JComboBox<Object> algorithmCbx, keySizeCbx;

	// GLOBAL

	/**
	 * Create the panel.
	 */
	public SignPanel() {

		// initializing values for Combo Box
		listKeySize = new ArrayList<Integer>();
//		listKeySize.add(1024);
		listKeySize.add(2048);
		keySizeSelected = listKeySize.get(0);

		listAlgorithm = new ArrayList<String>();
		listAlgorithm.add("RSA");
//		listAlgorithm.add("DSA");
		algorithmSelected = listAlgorithm.get(0);

		GUI();
	}

	void GUI() {
		setLayout(new BorderLayout(0, 0));

		JPanel contentSection = new JPanel();
		contentSection.setPreferredSize(new Dimension(LEFT_WIDTH, HEIGHT));
		add(contentSection, BorderLayout.CENTER);
		contentSection.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// INPUT COMPONENT BEGIN...
		JPanel inputComponent = new JPanel();
		inputComponent.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"String SHA-256", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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

		inputFileLbl = new JLabel("Input File");
		inputFileLbl.setEnabled(false);
		inputFileLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileComponent.add(inputFileLbl);

		inputFileTf = new JTextField();
		inputFileLbl.setLabelFor(inputFileTf);
		inputFileTf.setEnabled(false);
		inputFileTf.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileComponent.add(inputFileTf);
		inputFileTf.setColumns(35);

		// Open Input File Button
		inputFileBtn = new JButton("Open file");
		inputFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		inputFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser inputFileChooser = new JFileChooser();
				int returnVal = inputFileChooser.showOpenDialog(inputComponent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File inputFile = inputFileChooser.getSelectedFile();

					String inputFilePath = inputFile.getPath();
					String inputVal = new String(AlgorithmUtils.readFile(inputFile));
					inputTxt = inputVal;
					// set value from file into text field, textArea
					inputFileTf.setText(inputFilePath);
					inputArea.setText(inputVal);
				}
			}
		});
		inputFileComponent.add(inputFileBtn);
		// INPUT COMPONENT ENDDDDDDDDDDDDDDDDDDD

		// KEY COMPONENT BEGINNNNNNNNNNNNNNNNNNNNN
		JPanel keyComponent = new JPanel();
		keyComponent.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"Private Key", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
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

		// Open Private Key File Button
		keyFileBtn = new JButton("Open file");
		keyFileBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keyFileBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser keyFileChooser = new JFileChooser();
				int returnVal = keyFileChooser.showOpenDialog(keyComponent);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File privateKeyFile = keyFileChooser.getSelectedFile();

					String keyFilePath = privateKeyFile.getPath();
					String privateKeyVal = new String(AlgorithmUtils.readFile(privateKeyFile));
					privateKeyTxt = privateKeyVal;
					// set value from file into textfield, textAre

					String privateKeyB64 = new String(Base64.getEncoder().encode(privateKeyTxt.getBytes()));

					keyTf.setText(privateKeyB64);
					keyFileTf.setText(keyFilePath);
				}
			}
		});
		keyFileComponent.add(keyFileBtn);

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
					File outputFile = chooser.getSelectedFile();

					String outputFilePath = outputFile.getPath();
					outputFileTf.setText(outputFilePath);

					// save file
					AlgorithmUtils.writeFile(outputFile, Base64.getDecoder().decode(outputArea.getText().toString()));
					System.out.println("signature is Saved");

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
		propertySection.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Select",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		propertySection.setBounds(LEFT_WIDTH, 0, RIGHT_WIDTH, HEIGHT);
		add(propertySection, BorderLayout.EAST);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)),
				"properties", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 20, 220, 83);
		propertySection.add(panel);
		panel.setLayout(new GridLayout(2, 2, 0, 5));

		JLabel algorithmLbl = new JLabel("Algorithms");
		algorithmLbl.setHorizontalAlignment(SwingConstants.CENTER);
		algorithmLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(algorithmLbl);

		algorithmCbx = new JComboBox<>();
		algorithmCbx.setModel(new DefaultComboBoxModel<>(listAlgorithm.toArray()));
		algorithmCbx.setSelectedItem(algorithmSelected);
		algorithmCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				algorithmSelected = (String) algorithmCbx.getSelectedItem().toString();
				System.out.println("Algorithm: " + algorithmSelected);
			}
		});
		algorithmCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(algorithmCbx);

		JLabel keySizeLbl = new JLabel("Key size");
		keySizeLbl.setHorizontalAlignment(SwingConstants.CENTER);
		keySizeLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		panel.add(keySizeLbl);

		keySizeCbx = new JComboBox<Object>(new String[] { ("" + keySizeSelected) });
		keySizeCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		keySizeCbx.setModel(new DefaultComboBoxModel<>(listKeySize.toArray()));
		keySizeCbx.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				keySizeSelected = Integer.parseInt(keySizeCbx.getSelectedItem().toString());
				System.out.println("KeySize: " + keySizeSelected);
			}
		});
		panel.add(keySizeCbx);

		JButton runBtn = new JButton("Start");
		ImageIcon runIcon = new ImageIcon(new ImageIcon(AsymmetricPane.class.getResource("/images/run.png")).getImage()
				.getScaledInstance(30, 30, 1));
		runBtn.setIcon(runIcon);
		runBtn.setBackground(Color.DARK_GRAY);
		runBtn.setForeground(Color.WHITE);
		runBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		runBtn.setBounds(10, 113, 220, 50);
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String input;
				String key;
				byte[] signatureBytes;
				// sign file
				if (inputFileTf.getText() != null && !inputFileTf.getText().isEmpty() && keyFileTf.getText() != null
						&& !keyFileTf.getText().isEmpty()) {

					// get input and private key file path
					input = inputFileTf.getText().toString();
					key = keyFileTf.getText().toString();

					// sign files
					signatureBytes = DigitalSignature.signFile(input, key);
				}
				// sign text
				else {
					// get value entered from users
					input = inputArea.getText().toString();
					key = new String(Base64.getDecoder().decode(keyTf.getText().toString().getBytes()));

					// sign text
					signatureBytes = DigitalSignature.signStr(input, key);
				}

				// set value for output text fied
				outputTxt = new String(signatureBytes);
				outputArea.setText(new String(Base64.getEncoder().encode(signatureBytes)));

				// logging input, private key, output
				System.out.println("INPUT:" + input);
				System.out.println("KEY:" + key);
				System.out.println("OUTPUT:" + signatureBytes);

				outputFileBtn.setEnabled(true);;
			}
		});
		propertySection.add(runBtn);

		typeCipher = new ButtonGroup();
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

	public String getInputTxt() {
		return inputTxt;
	}

	public void setInputTxt(String inputTxt) {
		this.inputTxt = inputTxt;
	}

	public String getPrivateKeyTxt() {
		return privateKeyTxt;
	}

	public void setPrivateKeyTxt(String privateKeyTxt) {
		this.privateKeyTxt = privateKeyTxt;
	}

	public String getOutputTxt() {
		return outputTxt;
	}

	public void setOutputTxt(String outputTxt) {
		this.outputTxt = outputTxt;
	}

}
