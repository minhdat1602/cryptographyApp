package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

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
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import algorithms.HashAlgorithm;
import commons.HashConstants;
import utils.FIleUtils;

public class HashPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final int RIGHT_WIDTH = 240;
	private static final int LEFT_WIDTH = WIDTH - 240;

	JComboBox<Object> hashAlgorithmCbx;
	JButton saveBtn;

	/**
	 * Create the panel.
	 */
	public HashPanel() {
		GUI();
	}

	void GUI() {
		setLayout(new BorderLayout(0, 0));

		JPanel contentSection = new JPanel();
		contentSection.setPreferredSize(new Dimension(560, 500));
		add(contentSection, BorderLayout.CENTER);
		contentSection.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		contentSection.add(panel);
		panel.setLayout(new GridLayout(2, 1, 0, 0));

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setBorder(new TitledBorder(null, "Input", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		JTextArea inputArea = new JTextArea();
		inputArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		JScrollPane inputSrp = new JScrollPane(inputArea);
		panel_1.add(inputSrp, BorderLayout.CENTER);

		JPanel panel_3 = new JPanel();
		panel_3.setBackground(new Color(240, 240, 240));
		panel_3.setPreferredSize(new Dimension(70, 100));
		panel_1.add(panel_3, BorderLayout.EAST);
		panel_3.setLayout(new BorderLayout(0, 0));

		JButton openBtn = new JButton("Open");
		openBtn.setBackground(new Color(240, 240, 240));
		openBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		openBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(panel_3);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						String text = FIleUtils.readFile(file);
						inputArea.setText(text);
					} catch (Exception openEx) {
						JOptionPane.showMessageDialog(panel_3, openEx.getMessage());
					}
				}
			}

		});
		panel_3.add(openBtn, BorderLayout.SOUTH);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setBorder(new TitledBorder(null, "Output", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JTextArea hashArea = new JTextArea();
		hashArea.setFont(new Font("Tahoma", Font.PLAIN, 13));
		hashArea.setEditable(false);
		JScrollPane hashSrp = new JScrollPane(hashArea);
		panel_2.add(hashSrp, BorderLayout.CENTER);

		JPanel panel_4 = new JPanel();
		panel_4.setBackground(new Color(240, 240, 240));
		panel_4.setPreferredSize(new Dimension(70, 100));
		panel_2.add(panel_4, BorderLayout.EAST);
		panel_4.setLayout(new BorderLayout(0, 0));

		saveBtn = new JButton("Save");
		saveBtn.setBackground(new Color(240, 240, 240));
		saveBtn.setEnabled(false);
		saveBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(panel_4);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = chooser.getSelectedFile();
					try {
						FIleUtils.writeFile(hashArea.getText(), file);
						JOptionPane.showMessageDialog(panel, "Saved");
					} catch (Exception ex2) {
						JOptionPane.showMessageDialog(panel, ex2.getMessage());
					}
				}
			}

		});
		panel_4.add(saveBtn, BorderLayout.SOUTH);

		JLabel lblNewLabel = new JLabel("");
		ImageIcon imageIcon = new ImageIcon(new ImageIcon(HashPanel.class.getResource("/images/hash-function.png")).getImage()
						.getScaledInstance(650, 280, 0));
		lblNewLabel.setIcon(imageIcon);
		contentSection.add(lblNewLabel);

		JPanel propertySection = new JPanel();
		propertySection.setBackground(new Color(240, 240, 240));
		propertySection.setLayout(null);
		propertySection.setPreferredSize(new Dimension(RIGHT_WIDTH, HEIGHT));
		propertySection.setBorder(new TitledBorder(
				new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Select",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		propertySection.setBounds(LEFT_WIDTH, 0, RIGHT_WIDTH, HEIGHT);
		add(propertySection, BorderLayout.EAST);

		JButton runBtn = new JButton("Start");
		ImageIcon runIcon = new ImageIcon(new ImageIcon(AsymmetricPane.class.getResource("/images/run.png")).getImage().getScaledInstance(30, 30, 1));
		runBtn.setIcon(runIcon);
		runBtn.setForeground(Color.WHITE);
		runBtn.setBackground(Color.DARK_GRAY);
		runBtn.setFont(new Font("Tahoma", Font.PLAIN, 16));
		runBtn.setBounds(10, 85, 220, 50);
		runBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = inputArea.getText();
				String algorithm = hashAlgorithmCbx.getSelectedItem().toString();
				if (text.trim().isEmpty()) {
					JOptionPane.showMessageDialog(panel, "Input is empty");
					return;
				}
				try {
					System.out.println("----HASH FUNCTION------");
					System.out.println("Algorithm: " + algorithm);
					System.out.println("Input: " + text);

					byte[] hashBytes = HashAlgorithm.doHash(text, algorithm);
//					String hash = Base64.getEncoder().encodeToString(hashBytes);
					String hash = HashAlgorithm.bytesToHex(hashBytes);
					System.out.println("Output: " + hash);

					hashArea.setText(hash);
					saveBtn.setEnabled(true);
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(panel, ex.getMessage());
				}

			}

		});
		propertySection.add(runBtn);

		hashAlgorithmCbx = new JComboBox<Object>();
		hashAlgorithmCbx.setBackground(Color.WHITE);
		hashAlgorithmCbx.setFont(new Font("Tahoma", Font.PLAIN, 12));
		hashAlgorithmCbx.setModel(new DefaultComboBoxModel<>(HashConstants.hashAlgorithm.toArray()));
		hashAlgorithmCbx.setSelectedIndex(0);
		hashAlgorithmCbx.setBounds(120, 35, 110, 30);
		propertySection.add(hashAlgorithmCbx);
		
		JLabel algorithmLbl = new JLabel("Algorithm");
		algorithmLbl.setHorizontalAlignment(SwingConstants.CENTER);
		algorithmLbl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		algorithmLbl.setBounds(10, 35, 100, 30);
		propertySection.add(algorithmLbl);

	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("ABC");
		JPanel asymmetricPane = new HashPanel();
		frame.getContentPane().add(asymmetricPane);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
