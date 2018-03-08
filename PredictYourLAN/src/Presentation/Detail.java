package Presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.xml.bind.DatatypeConverter;

import Metier.DataStorage;
import Metier.Equipment;
import Metier.Network;
import Metier.StrategicPoint;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.SystemColor;


/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Class display detail for only one strategic point. It allows to see all details, it's possible to modify info if you need  
 *
 */
@SuppressWarnings("serial")
public class Detail extends JPanel implements ActionListener
{


	public JPanel contentPane;
	
	protected StrategicPoint tempSP;
	
	public JButton modifyButton;
	public JButton historicButton;
	public JButton returnButton;
	
	private JPanel panel_c1;
	private JPanel panel_c2;
	private JPanel panel_c3;
	private JPanel panel_c4;
	private JPanel panel_c5;
	private JPanel panel_c6;
	private JPanel panel_c7;
	private JPanel panel_c8;
	private JPanel panel_N3;
	private JPanel panel_c31;
	private JPanel panel_c71;
	private JPanel panel_c81;
	private JPanel panel_c41;
	private JPanel panel_c51;
	private JPanel panel_c61;
	private JPanel panel_c10;
	private JPanel panel_c9;
	private JPanel panel_c91;
	
	private JLabel labelTitleImage;
	private JLabel labelBug;
	private JLabel labelSolution;
	private JLabel labelDescription;
	private JLabel labelIPAddress;
	private JLabel labelMoreInfo;
	private JLabel labelLED;
	private JLabel labelName;
	
	private JTextField textFieldName;
	private JTextField textFieldDescription;
	private JTextField textFieldIPAddress;
	private JTextField textFieldMoreInfo;
	private JTextField textFieldBug;
	private JTextField textFieldSolution;
	
	private JLabel hiddenLabel;
	private JPanel panel_c11;
	
	private Image backgroundImage;
	private JLabel labelImage;
	private JButton modifyImageButton;
	private boolean stateButtonModifyClicked = false;
	

	private File selectedFile;

	/**
	 * Create the frame.
	 */
	public Detail() 
	{
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_N1 = new JPanel();
		panel_N1.setBackground(Color.WHITE);
		northPanel.add(panel_N1);
		panel_N1.setLayout(new BoxLayout(panel_N1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_111 = new JLabel(new ImageIcon(Detail.class.getResource("/images/logoEnterprise.jpg")));
		lblNewLabel_111.setBackground(Color.WHITE);
		lblNewLabel_111.setHorizontalAlignment(SwingConstants.CENTER);
		panel_N1.add(lblNewLabel_111);
		
		JPanel panel_N2 = new JPanel();
		panel_N2.setBackground(Color.WHITE);
		northPanel.add(panel_N2);
		panel_N2.setLayout(null);
		
		panel_N3 = new JPanel();
		panel_N3.setBackground(Color.WHITE);
		northPanel.add(panel_N3);
		
		JLabel lblNewLabel_121 = new JLabel(new ImageIcon(MainWindow.class.getResource("/images/logo.png")));
		panel_N3.add(lblNewLabel_121);
		lblNewLabel_121.setForeground(new Color(30, 144, 255));
		lblNewLabel_121.setFont(new Font("Snap ITC", Font.PLAIN, 32));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBackground(Color.WHITE);
		this.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] {200, 500, 300, 200};
		gbl_centerPanel.rowHeights = new int[] {150, 250, 100, 100, 150, 150};
		gbl_centerPanel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
		gbl_centerPanel.rowWeights = new double[]{1.0, 1.0, 0.0, 0.0, 0.0, 0.0};
		centerPanel.setLayout(gbl_centerPanel);
		
		hiddenLabel = new JLabel("");
		GridBagConstraints gbc_hiddenLabel = new GridBagConstraints();
		gbc_hiddenLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hiddenLabel.gridx = 0;
		gbc_hiddenLabel.gridy = 0;
		centerPanel.add(hiddenLabel, gbc_hiddenLabel);
		
		panel_c3 = new JPanel();
		panel_c3.setBackground(Color.WHITE);
		panel_c3.setLayout(null);
		GridBagConstraints gbc_panel_c3 = new GridBagConstraints();
		gbc_panel_c3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c3.fill = GridBagConstraints.BOTH;
		gbc_panel_c3.gridx = 1;
		gbc_panel_c3.gridy = 0;
		centerPanel.add(panel_c3, gbc_panel_c3);
		
		panel_c31 = new JPanel();
		panel_c31.setBounds(150, 5, 300, 50);
		panel_c31.setOpaque(false);
		panel_c3.add(panel_c31);
		panel_c31.setLayout(null);
		
		labelLED = new JLabel("");
		labelLED.setBounds(7, 19, 0, 0);
		panel_c31.add(labelLED);
		labelLED.setHorizontalAlignment(SwingConstants.CENTER);
		labelLED.setFont(new Font("Arial", Font.PLAIN, 14));
		
		labelName = new JLabel("Nom :");
	//	labelName.setBounds(12, 10, 50, 30);
		panel_c31.add(labelName);
		labelName.setHorizontalAlignment(SwingConstants.CENTER);
		labelName.setFont(new Font("Arial", Font.PLAIN, 18));
		
		textFieldName = new JTextField();
		textFieldName.setBounds(80, 10, 210, 30);
		textFieldName.setFont(new Font("Arial", Font.PLAIN, 18));		
		panel_c31.add(textFieldName);
		textFieldName.setColumns(10);
		
		panel_c9 = new JPanel();
		panel_c9.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_c9 = new GridBagConstraints();
		gbc_panel_c9.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c9.fill = GridBagConstraints.BOTH;
		gbc_panel_c9.gridx = 2;
		gbc_panel_c9.gridy = 0;
		centerPanel.add(panel_c9, gbc_panel_c9);
		panel_c9.setLayout(new BorderLayout(0, 0));
		
		panel_c91 = new JPanel();
		panel_c91.setOpaque(false);
		panel_c9.add(panel_c91, BorderLayout.SOUTH);
		panel_c91.setLayout(new BorderLayout(20, 0));
		
		labelTitleImage = new JLabel("Apercu  :");
		labelTitleImage.setHorizontalAlignment(SwingConstants.LEFT);
		labelTitleImage.setFont(new Font("Arial", Font.PLAIN, 22));
		panel_c91.add(labelTitleImage, BorderLayout.WEST);
		
		modifyImageButton = new JButton("Modifier image");
		modifyImageButton.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_c91.add(modifyImageButton, BorderLayout.EAST);
		
		panel_c11 = new JPanel();
		GridBagConstraints gbc_panel_c11 = new GridBagConstraints();
		gbc_panel_c11.insets = new Insets(0, 0, 5, 0);
		gbc_panel_c11.fill = GridBagConstraints.BOTH;
		gbc_panel_c11.gridx = 3;
		gbc_panel_c11.gridy = 5;
		centerPanel.add(panel_c11, gbc_panel_c11);
		
		returnButton = new JButton(new ImageIcon(Detail.class.getResource("/images/button/returnButton.png")));					
		returnButton.setBackground(SystemColor.textHighlight);
		returnButton.setFont(new Font("Arial", Font.PLAIN, 18));
		returnButton.setAlignmentY(0.0f);
		panel_c11.add(returnButton);
		
		panel_c10 = new JPanel();
		panel_c10.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panel_c10.setLayout(null);
		GridBagConstraints gbc_panel_c10 = new GridBagConstraints();
		gbc_panel_c10.gridheight = 2;
		gbc_panel_c10.fill = GridBagConstraints.BOTH;
		gbc_panel_c10.gridx = 2;
		gbc_panel_c10.gridy = 1;
		centerPanel.add(panel_c10, gbc_panel_c10);
		
		labelImage = new JLabel("");
		labelImage.setHorizontalAlignment(SwingConstants.CENTER);
		labelImage.setBounds(2, 2, 317, 306);
		panel_c10.add(labelImage);
		
		panel_c7 = new JPanel();
		panel_c7.setBackground(Color.WHITE);
		panel_c7.setLayout(null);
		GridBagConstraints gbc_panel_c7 = new GridBagConstraints();
		gbc_panel_c7.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c7.fill = GridBagConstraints.BOTH;
		gbc_panel_c7.gridx = 1;
		gbc_panel_c7.gridy = 4;
		centerPanel.add(panel_c7, gbc_panel_c7);
		
		panel_c71 = new JPanel();
		panel_c71.setBounds(30, 10, 500, 120);
		panel_c7.add(panel_c71);
		panel_c71.setLayout(null);
		
		labelBug = new JLabel("Probl\u00E8me d\u00E9tect\u00E9(s) :");
		labelBug.setBounds(0, 5, 150, 17);
		labelBug.setHorizontalAlignment(SwingConstants.CENTER);
		labelBug.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_c71.add(labelBug);
		
		textFieldBug = new JTextField();
		textFieldBug.setFont(new Font("Arial", Font.ITALIC, 14));
		textFieldBug.setBounds(5, 25, 480, 80);
		panel_c71.add(textFieldBug);
		textFieldBug.setColumns(10);
		
		panel_c8 = new JPanel();
		panel_c8.setBackground(Color.WHITE);
		panel_c8.setLayout(null);
		GridBagConstraints gbc_panel_c8 = new GridBagConstraints();
		gbc_panel_c8.insets = new Insets(0, 0, 0, 5);
		gbc_panel_c8.fill = GridBagConstraints.BOTH;
		gbc_panel_c8.gridx = 1;
		gbc_panel_c8.gridy = 5;
		centerPanel.add(panel_c8, gbc_panel_c8);
		
		panel_c81 = new JPanel();
		panel_c81.setBounds(30, 10, 500, 120);
		panel_c8.add(panel_c81);
		panel_c81.setLayout(null);
		
		labelSolution = new JLabel("Solution: ");
		labelSolution.setBounds(5, 5, 150, 17);
		labelSolution.setHorizontalAlignment(SwingConstants.LEFT);
		labelSolution.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_c81.add(labelSolution);
		
		textFieldSolution = new JTextField();
		textFieldSolution.setFont(new Font("Arial", Font.PLAIN, 14));
		textFieldSolution.setBounds(5, 25, 480, 80);
		panel_c81.add(textFieldSolution);
		textFieldSolution.setColumns(10);
		
		panel_c4 = new JPanel();
		panel_c4.setBackground(Color.WHITE);
		panel_c4.setLayout(null);
		GridBagConstraints gbc_panel_c4 = new GridBagConstraints();
		gbc_panel_c4.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c4.fill = GridBagConstraints.BOTH;
		gbc_panel_c4.gridx = 1;
		gbc_panel_c4.gridy = 1;
		centerPanel.add(panel_c4, gbc_panel_c4);
		
		panel_c41 = new JPanel();
		panel_c41.setOpaque(false);
		panel_c41.setBounds(100, 0, 400, 200);
		panel_c4.add(panel_c41);
		panel_c41.setLayout(null);
		
		labelDescription = new JLabel("Description :");
		labelDescription.setBounds(10, 8, 100, 22);
		labelDescription.setHorizontalAlignment(SwingConstants.CENTER);
		labelDescription.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c41.add(labelDescription);
		
		textFieldDescription = new JTextField();

		textFieldDescription.setBounds(10, 40, 380, 150);
		textFieldDescription.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c41.add(textFieldDescription);
		textFieldDescription.setColumns(20);
		
		panel_c5 = new JPanel();
		panel_c5.setBackground(Color.WHITE);
		panel_c5.setLayout(null);
		GridBagConstraints gbc_panel_c5 = new GridBagConstraints();
		gbc_panel_c5.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c5.fill = GridBagConstraints.BOTH;
		gbc_panel_c5.gridx = 1;
		gbc_panel_c5.gridy = 2;
		centerPanel.add(panel_c5, gbc_panel_c5);
		
		panel_c51 = new JPanel();
		panel_c51.setBounds(150, 5, 300, 50);
		panel_c51.setOpaque(false);
		panel_c5.add(panel_c51);
		panel_c51.setLayout(null);
		
		labelIPAddress = new JLabel("Adresse IP:");
		labelIPAddress.setBounds(5, 8, 100, 30);
		labelIPAddress.setHorizontalAlignment(SwingConstants.CENTER);
		labelIPAddress.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c51.add(labelIPAddress);
		
		textFieldIPAddress = new JTextField();
		textFieldIPAddress.setBounds(125, 5, 160, 30);
		textFieldIPAddress.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c51.add(textFieldIPAddress);
		textFieldIPAddress.setColumns(10);
		
		panel_c6 = new JPanel();
		panel_c6.setBackground(Color.WHITE);
		panel_c6.setLayout(null);
		GridBagConstraints gbc_panel_c6 = new GridBagConstraints();
		gbc_panel_c6.fill = GridBagConstraints.BOTH;
		gbc_panel_c6.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c6.gridx = 1;
		gbc_panel_c6.gridy = 3;
		centerPanel.add(panel_c6, gbc_panel_c6);
		
		panel_c61 = new JPanel();
		panel_c61.setBounds(50, 5, 500, 50);
		panel_c61.setOpaque(false);
		panel_c6.add(panel_c61);
		panel_c61.setLayout(null);
		
		labelMoreInfo = new JLabel("New label");
		labelMoreInfo.setBounds(5, 8, 120, 22);
		labelMoreInfo.setHorizontalAlignment(SwingConstants.CENTER);
		labelMoreInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_c61.add(labelMoreInfo);
		
		textFieldMoreInfo = new JTextField();
		textFieldMoreInfo.setBounds(125, 5, 350, 30);
		textFieldMoreInfo.setFont(new Font("Arial", Font.PLAIN, 14));
		panel_c61.add(textFieldMoreInfo);
		textFieldMoreInfo.setColumns(10);
		
		panel_c2 = new JPanel();
		panel_c2.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_c2 = new GridBagConstraints();
		gbc_panel_c2.insets = new Insets(0, 0, 0, 5);
		gbc_panel_c2.fill = GridBagConstraints.BOTH;
		gbc_panel_c2.gridx = 0;
		gbc_panel_c2.gridy = 5;
		centerPanel.add(panel_c2, gbc_panel_c2);
		
		modifyButton = new JButton(new ImageIcon(Detail.class.getResource("/images/button/modifyButton.png")));	
		modifyButton.setBackground(SystemColor.textHighlight);
		modifyButton.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c2.add(modifyButton);
		modifyButton.setAlignmentY(0.0f);
		
		panel_c1 = new JPanel();
		panel_c1.setBackground(Color.WHITE);
		GridBagConstraints gbc_panel_c1 = new GridBagConstraints();
		gbc_panel_c1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c1.fill = GridBagConstraints.BOTH;
		gbc_panel_c1.gridx = 0;
		gbc_panel_c1.gridy = 4;
		centerPanel.add(panel_c1, gbc_panel_c1);
		
		historicButton = new JButton(new ImageIcon(Detail.class.getResource("/images/button/historicButton.png")));	
		historicButton.setBackground(SystemColor.textHighlight);
		historicButton.setFont(new Font("Arial", Font.PLAIN, 18));
		panel_c1.add(historicButton);
		historicButton.setAlignmentY(150.0f);
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("images/background.jpg");
		try 
		{			
				this.backgroundImage = ImageIO.read(is);
			
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		}
		this.backgroundImage = this.backgroundImage.getScaledInstance(Controller.screenWidth,Controller.screenHeight,Image.SCALE_DEFAULT);	
		this.hiddenLabel.setVisible(false);
		this.panel_c71.setVisible(false);
		this.panel_c81.setVisible(false);
	    this.modifyImageButton.setVisible(false);
		
		centerPanel.setOpaque(false);
		northPanel.setOpaque(false);		
		panel_N1.setOpaque(false);
		panel_N2.setOpaque(false);
		panel_N3.setOpaque(false);		
		panel_c1.setOpaque(false);
		panel_c2.setOpaque(false);
		panel_c3.setOpaque(false);
		panel_c4.setOpaque(false);
		panel_c5.setOpaque(false);
		panel_c6.setOpaque(false);
		panel_c7.setOpaque(false);
		panel_c8.setOpaque(false);
		panel_c9.setOpaque(false);
		
		panel_c11.setOpaque(false);
		
		this.modifyButton.addActionListener(this);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(this.backgroundImage, 0, 0, null);			
	}
	
	/**
	 * Displays info about one strategic point
	 * @param thisSP is a strategic point from strategic point panel 
	 * @since 1.00
	 *  
	 */
	public void displayDetail(StrategicPoint thisSP)
	{
		tempSP = thisSP;
		this.hiddenLabel.setText(String.valueOf(tempSP.getID()));

		this.setLabelLed(new ImageIcon(thisSP.getLed().getUNC()));
			
		this.textFieldName.setText(thisSP.getName());
		this.textFieldDescription.setText(thisSP.getDescription());
		this.textFieldIPAddress.setText(thisSP.getIPAddress());
		
		ImageIcon icon = new ImageIcon(DatatypeConverter.parseBase64Binary(thisSP.getIcon()));
		Image image = icon.getImage();
	    Image newImage = image.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
		
		this.labelImage.setIcon(new ImageIcon(newImage));	
		
		if(thisSP.getListBug().isEmpty() == false)
		{
			this.panel_c71.setVisible(true);
			this.panel_c81.setVisible(true);
			this.textFieldBug.setText(thisSP.getListBug().get(0).getDetail());
			this.textFieldSolution.setText(thisSP.getListBug().get(0).getListSolution().get(0).getDescription());
		}
		else
		{
			this.setLabelBug("");
			this.setLabelSolution("");
			this.getLabelBug().setVisible(false);
			this.getLabelSolution().setVisible(false);
		}
		
		if(thisSP instanceof Equipment)
		{
			this.labelMoreInfo.setText("Type :");
			this.textFieldMoreInfo.setText(((Equipment) thisSP).getType());
		}
		
		if(thisSP instanceof Network)
		{
			this.labelMoreInfo.setText("Adresse IP 2 :");
			this.textFieldMoreInfo.setText(((Network) thisSP).getIPAddress2());
		}
		
		if(thisSP instanceof DataStorage)
		{
			this.labelMoreInfo.setText("Chemin UNC :");
			this.textFieldMoreInfo.setText(((DataStorage) thisSP).getUNC());
		}
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.modifyButton) 
		{
	
			this.stateButtonModifyClicked = true;
			this.modifyImageButton.setVisible(true);
			this.modifyImageButton.addActionListener(this);
		}
		
		if(e.getSource() == this.modifyImageButton) 
		{
			JFileChooser choice = new JFileChooser();
			choice.showOpenDialog(this);
			selectedFile = choice.getSelectedFile();
			
            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
			Image image = icon.getImage();
			Image newImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
			labelImage.setIcon(new ImageIcon(newImage));			
		}

	}
	
	
	/////////////************************    Getters and Setters *************************

	public JLabel getLabelLed() {
		return labelLED;
	}

	public void setLabelLed(ImageIcon labelLed) {
		this.labelLED.setIcon(labelLed);
	}

	public JLabel getLabelDescription() {
		return labelDescription;
	}

	public void setLabelDescription(String labelDescription) {
		this.labelDescription.setText(labelDescription);
	}

	public JLabel getLabelIPAddress() {
		return labelIPAddress;
	}

	public void setLabelIPAddress(String labelIPAddress) {
		this.labelIPAddress.setText(labelIPAddress);
	}

	public JLabel getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName.setText(labelName);
	}


	public JLabel getLabelBug() {
		return labelBug;
	}

	public void setLabelBug(String labelBug) {
		this.labelBug.setText(labelBug);
	}

	public JLabel getLabelSolution() {
		return labelSolution;
	}

	public void setLabelSolution(String labelSolution) {
		this.labelSolution.setText(labelSolution);
	}

	public JTextField getTextFieldDescription() {
		return textFieldDescription;
	}

	public void setTextFieldDescription(JTextField textFieldDescription) {
		this.textFieldDescription = textFieldDescription;
	}

	public JTextField getTextFieldIPAddress() {
		return textFieldIPAddress;
	}

	public void setTextFieldIPAddress(JTextField textFieldIPAddress) {
		this.textFieldIPAddress = textFieldIPAddress;
	}

	public JTextField getTextFieldMoreInfo() {
		return textFieldMoreInfo;
	}

	public void setTextFieldMoreInfo(JTextField textFieldMoreInfo) {
		this.textFieldMoreInfo = textFieldMoreInfo;
	}

	public JLabel getHiddenLabel() {
		return hiddenLabel;
	}

	public void setHiddenLabel(JLabel hiddenLabel) {
		this.hiddenLabel = hiddenLabel;
	}

	public JTextField getTextFieldName() {
		return textFieldName;
	}

	public void setTextFieldName(JTextField textFieldName) {
		this.textFieldName = textFieldName;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public boolean isStateButtonModifyClicked() {
		return stateButtonModifyClicked;
	}

	public void setStateButtonModifyClicked(boolean stateButtonModifyClicked) {
		this.stateButtonModifyClicked = stateButtonModifyClicked;
	}

	


	
	
}
