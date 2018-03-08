package Presentation;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import Metier.Category;
import Metier.Equipment;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.BevelBorder;

/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Class for adding a new strategic point
 *
 */
@SuppressWarnings("serial")
public class AddSP extends JPanel implements ActionListener
{
	private JPanel panelNorth;
	private JPanel panel_1;
	private JPanel panel_c232;	
	
	private JTextField txtSPName;
	private JTextField txtDescription;

	private JTextField txtIpAddress1;
	
	private JLabel labelImage;
	
	public JRadioButton radioButton1 ;	
	public JRadioButton radioButton2;
	public JRadioButton radioButton3;
	public JRadioButton radioButton4;
	public JRadioButton radioButton5;
	public JRadioButton radioButton6;
	public JRadioButton radioButton7;
	public ButtonGroup bg;

	private JLabel labelMoreInfo;
	private JLabel labelMoreInfo2;
	private JComboBox<String> comboBoxType;
	private JComboBox<String> comboBoxModel;
	private JTextField txtIpAddress2;
	private JTextField txtDataStorage;
	
	public JButton btnValidate;
	public JButton btnReturn;	
	private JButton addImageButton;	
	
	private Image backgroundImage;
	private File selectedFile;
	
	private ArrayList<Equipment> listTypeAndModel;

	/**
	 * Create the panel.
	 */
	public AddSP() 
	{

		this.setLayout(new BorderLayout(0, 0));
		this.setOpaque(false);
		
		bg = new ButtonGroup();
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panelNorth = new JPanel();
		panelNorth.setOpaque(false);
		panel.add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panelNorth.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel labelLogo = new JLabel(new ImageIcon(AddSP.class.getResource("/images/logoEnterprise.jpg")));
		panel_1.add(labelLogo);
		
		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panelNorth.add(panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setOpaque(false);
		panelNorth.add(panel_3);
		
		JLabel label = new JLabel(new ImageIcon(AddSP.class.getResource("/images/logo.png")));
		panel_3.add(label);
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setForeground(new Color(30, 144, 255));
		label.setFont(new Font("Snap ITC", Font.PLAIN, 32));
		
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		panel.add(panelCenter, BorderLayout.CENTER);
		GridBagLayout gbl_panelCenter = new GridBagLayout();
		gbl_panelCenter.columnWidths = new int[] {250, 650, 380};
		gbl_panelCenter.rowHeights = new int[] {900};
		gbl_panelCenter.columnWeights = new double[]{1.0, 1.0, 1.0};
		gbl_panelCenter.rowWeights = new double[]{1.0};
		panelCenter.setLayout(gbl_panelCenter);
		
		JPanel panel_c1 = new JPanel();
		panel_c1.setLayout(null);
		GridBagConstraints gbc_panel_c1 = new GridBagConstraints();
		gbc_panel_c1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c1.fill = GridBagConstraints.BOTH;
		gbc_panel_c1.gridx = 0;
		gbc_panel_c1.gridy = 0;
		panel_c1.setOpaque(false);
		panelCenter.add(panel_c1, gbc_panel_c1);
		
		JPanel panel_c11 = new JPanel();
		panel_c11.setOpaque(false);
		panel_c11.setBounds(0, 250, 210, 200);
		panel_c11.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c1.add(panel_c11);		
		panel_c11.setLayout(new BoxLayout(panel_c11, BoxLayout.Y_AXIS));
		
		
		JLabel label_1 = new JLabel("Choisir le type :");
		panel_c11.add(label_1);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setFont(new Font("Arial", Font.PLAIN, 18));
		
		radioButton1 = new JRadioButton("");
		radioButton1.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton1);
		radioButton1.setFont(new Font("Arial", Font.PLAIN, 16));	
		
		radioButton2 = new JRadioButton("");
		radioButton2.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton2);
		radioButton2.setFont(new Font("Arial", Font.PLAIN, 16));		
		
		radioButton3 = new JRadioButton("");
		radioButton3.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton3);
		radioButton3.setFont(new Font("Arial", Font.PLAIN, 16));			
		
		radioButton4 = new JRadioButton("");
		radioButton4.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton4);
		radioButton4.setFont(new Font("Arial", Font.PLAIN, 16));
		
		radioButton5 = new JRadioButton("");
		radioButton5.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton5);
		radioButton5.setFont(new Font("Arial", Font.PLAIN, 16));
		
		radioButton6 = new JRadioButton("");
		radioButton6.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton6);
		radioButton6.setFont(new Font("Arial", Font.PLAIN, 16));
		
		radioButton7 = new JRadioButton("");
		radioButton7.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.add(radioButton7);
		radioButton7.setFont(new Font("Arial", Font.PLAIN, 16));
		
		bg.add(radioButton1);
		bg.add(radioButton2);
		bg.add(radioButton3);
		bg.add(radioButton4);
		bg.add(radioButton5);
		bg.add(radioButton6);
		bg.add(radioButton7);
		
		JPanel panel_c2 = new JPanel();
		GridBagConstraints gbc_panel_c2 = new GridBagConstraints();
		gbc_panel_c2.fill = GridBagConstraints.BOTH;
		gbc_panel_c2.gridx = 1;
		gbc_panel_c2.gridy = 0;
		panelCenter.add(panel_c2, gbc_panel_c2);
		GridBagLayout gbl_panel_c2 = new GridBagLayout();
		gbl_panel_c2.columnWidths = new int[] {700};
		gbl_panel_c2.rowHeights = new int[] {150, 150, 150, 200};
		gbl_panel_c2.columnWeights = new double[]{0.0};
		gbl_panel_c2.rowWeights = new double[]{0.0, 0.0, 1.0, 0.0};
		panel_c2.setLayout(gbl_panel_c2);
		panel_c2.setOpaque(false);
		
		JPanel panel_c21 = new JPanel();
		GridBagConstraints gbc_panel_c21 = new GridBagConstraints();
		gbc_panel_c21.fill = GridBagConstraints.BOTH;
		gbc_panel_c21.gridx = 0;
		gbc_panel_c21.gridy = 0;
		panel_c2.add(panel_c21, gbc_panel_c21);
		panel_c21.setOpaque(false);
		panel_c21.setLayout(null);
		
		JPanel panel_c211 = new JPanel();
		panel_c211.setOpaque(false);
		panel_c211.setBounds(200, 100, 400, 50);
		panel_c211.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c21.add(panel_c211);
		panel_c211.setLayout(null);
		
		JLabel labelName = new JLabel("Nom :");
		labelName.setBounds(5, 15, 47, 25);
		panel_c211.add(labelName);
		labelName.setVerticalAlignment(SwingConstants.TOP);
		labelName.setFont(new Font("Arial", Font.PLAIN, 18));
		
		txtSPName = new JTextField();
		txtSPName.setBounds(100, 5, 200, 40);
		panel_c211.add(txtSPName);
		txtSPName.setFont(new Font("Arial", Font.ITALIC, 16));
		txtSPName.setColumns(10);
		
		JPanel panel_c22 = new JPanel();
		panel_c22.setLayout(null);
		GridBagConstraints gbc_panel_c22 = new GridBagConstraints();
		gbc_panel_c22.fill = GridBagConstraints.BOTH;
		gbc_panel_c22.gridx = 0;
		gbc_panel_c22.gridy = 1;
		panel_c22.setOpaque(false);
		panel_c2.add(panel_c22, gbc_panel_c22);
		
		JPanel panel_c221 = new JPanel();
		panel_c221.setOpaque(false);
		panel_c221.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c221.setBounds(200, 70, 400, 50);
		panel_c22.add(panel_c221);
		panel_c221.setLayout(null);
		
		JLabel labelIP = new JLabel("Adresse IP:");
		labelIP.setBounds(5, 15, 91, 22);
		panel_c221.add(labelIP);
		labelIP.setVerticalAlignment(SwingConstants.TOP);
		labelIP.setFont(new Font("Arial", Font.PLAIN, 18));
		
		txtIpAddress1 = new JTextField();
		txtIpAddress1.setBounds(100, 5, 200, 40);
		panel_c221.add(txtIpAddress1);
		txtIpAddress1.setFont(new Font("Arial", Font.ITALIC, 18));
		txtIpAddress1.setColumns(10);
		
		JPanel panel_c23 = new JPanel();
		panel_c23.setLayout(null);
		GridBagConstraints gbc_panel_c23 = new GridBagConstraints();
		gbc_panel_c23.fill = GridBagConstraints.BOTH;
		gbc_panel_c23.insets = new Insets(0, 0, 0, 5);
		gbc_panel_c23.gridx = 0;
		gbc_panel_c23.gridy = 2;
		panel_c23.setOpaque(false);
		panel_c2.add(panel_c23, gbc_panel_c23);
		
		JPanel panel_c231 = new JPanel();
		panel_c231.setOpaque(false);
		panel_c231.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c231.setBounds(150, 50, 450, 50);
		panel_c23.add(panel_c231);
		panel_c231.setLayout(null);
		
		labelMoreInfo = new JLabel("");
		labelMoreInfo.setBounds(5, 15, 140, 25);
		panel_c231.add(labelMoreInfo);
		labelMoreInfo.setVerticalAlignment(SwingConstants.TOP);
		labelMoreInfo.setFont(new Font("Arial", Font.PLAIN, 18));
		
		comboBoxType = new JComboBox<String>();
		comboBoxType.setFont(new Font("Arial", Font.PLAIN, 18));
		comboBoxType.setBounds(150, 5, 200, 40);
		panel_c231.add(comboBoxType);
		comboBoxType.setToolTipText("");
		
		txtIpAddress2 = new JTextField();
		txtIpAddress2.setBounds(150, 5, 200, 40);
		panel_c231.add(txtIpAddress2);
		txtIpAddress2.setFont(new Font("Tahoma", Font.ITALIC, 18));
		txtIpAddress2.setColumns(10);
		
		txtDataStorage = new JTextField();
		txtDataStorage.setBounds(150, 5, 200, 40);
		panel_c231.add(txtDataStorage);
		txtDataStorage.setFont(new Font("Tahoma", Font.ITALIC, 18));
		txtDataStorage.setColumns(10);
		
		panel_c232 = new JPanel();
		panel_c232.setOpaque(false);
		panel_c232.setBackground(new Color(1f, 1f, 1f, 0.8f));	
		panel_c232.setBounds(150, 110, 450, 50);
		panel_c23.add(panel_c232);
		panel_c232.setLayout(null);
		
		labelMoreInfo2 = new JLabel("Mod\u00E8le :");
		labelMoreInfo2.setBounds(5, 15, 140, 25);
		panel_c232.add(labelMoreInfo2);
		labelMoreInfo2.setVerticalAlignment(SwingConstants.TOP);
		labelMoreInfo2.setFont(new Font("Arial", Font.PLAIN, 18));
		
		comboBoxModel = new JComboBox<String>();
		comboBoxModel.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBoxModel.setBounds(150, 5, 200, 40);
		panel_c232.add(comboBoxModel);
		
		JPanel panel_c24 = new JPanel();
		panel_c24.setLayout(null);
		GridBagConstraints gbc_panel_c24 = new GridBagConstraints();
		gbc_panel_c24.weighty = 1.0;
		gbc_panel_c24.fill = GridBagConstraints.BOTH;
		gbc_panel_c24.gridx = 0;
		gbc_panel_c24.gridy = 3;
		panel_c24.setOpaque(false);
		panel_c2.add(panel_c24, gbc_panel_c24);
		
		JLabel lblDescriptionfalculatatif = new JLabel("Description (falcultatif):");
		lblDescriptionfalculatatif.setBounds(100, 5, 183, 20);
		panel_c24.add(lblDescriptionfalculatatif);
		lblDescriptionfalculatatif.setVerticalAlignment(SwingConstants.TOP);
		lblDescriptionfalculatatif.setFont(new Font("Arial", Font.PLAIN, 18));
		
		txtDescription = new JTextField();
		txtDescription.setBounds(100, 30, 500, 200);
		panel_c24.add(txtDescription);
		txtDescription.setFont(new Font("Arial", Font.ITALIC, 16));
		txtDescription.setColumns(10);
		
		JPanel panel_c3 = new JPanel();
		panel_c3.setLayout(null);
		GridBagConstraints gbc_panel_c3 = new GridBagConstraints();
		gbc_panel_c3.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c3.fill = GridBagConstraints.BOTH;
		gbc_panel_c3.gridx = 2;
		gbc_panel_c3.gridy = 0;
		panel_c3.setOpaque(false);
		panelCenter.add(panel_c3, gbc_panel_c3);
		
		addImageButton = new JButton("Ajouter image");
		addImageButton.setBounds(70, 200, 150, 25);
		panel_c3.add(addImageButton);
		
		JPanel panel_c31 = new JPanel();
		panel_c31.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK));
		panel_c31.setBounds(70, 230, 200, 200);
		panel_c3.add(panel_c31);
		panel_c31.setLayout(new BorderLayout(0, 0));
		
		labelImage = new JLabel(new ImageIcon(AddSP.class.getResource("/images/no-image.png")));
		panel_c31.add(labelImage);
		
		
		JPanel panelEast = new JPanel();
		panelEast.setOpaque(false);
		panel.add(panelEast, BorderLayout.EAST);
		panelEast.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panel_10 = new JPanel();
		panel_10.setOpaque(false);
		panelEast.add(panel_10);
		panel_10.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JPanel panelWest = new JPanel();
		panelWest.setOpaque(false);
		panel.add(panelWest, BorderLayout.WEST);
		panelWest.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_4.setOpaque(false);
		panelWest.add(panel_4);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
	
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panelWest.add(panel_6);
		
		JPanel panelSouth = new JPanel();
		panelSouth.setOpaque(false);
		panel.add(panelSouth, BorderLayout.SOUTH);
		panelSouth.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_11.setOpaque(false);
		panelSouth.add(panel_11);
		
		btnReturn = new JButton(new ImageIcon(AddSP.class.getResource("/images/button/returnButton.png")));
		panel_11.add(btnReturn);
		
		JPanel panel_12 = new JPanel();
		panel_12.setOpaque(false);
		panelSouth.add(panel_12);
		
		btnValidate = new JButton(new ImageIcon(AddSP.class.getResource("/images/button/validateButton.png")));
		panel_12.add(btnValidate);
		
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
		
		
		panel_c232.setVisible(false);
		
		this.radioButton1.setVisible(false);
		this.radioButton2.setVisible(false);
		this.radioButton3.setVisible(false);
		this.radioButton4.setVisible(false);
		this.radioButton5.setVisible(false);
		this.radioButton6.setVisible(false);
		this.radioButton7.setVisible(false);
		
		this.comboBoxType.setVisible(false);
		this.txtIpAddress2.setVisible(false);		
		this.txtDataStorage.setVisible(false);
		
		this.addImageButton.addActionListener(this);
		
		this.radioButton1.addActionListener(this);
		this.radioButton2.addActionListener(this);
		this.radioButton3.addActionListener(this);
		this.radioButton4.addActionListener(this);
		this.radioButton5.addActionListener(this);
		this.radioButton6.addActionListener(this);
		this.radioButton7.addActionListener(this);
		
		this.comboBoxType.addActionListener(this);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(this.backgroundImage, 0, 0, null);			
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(e.getSource() == this.addImageButton) 
		{
			JFileChooser choice = new JFileChooser();
			int option = choice.showOpenDialog(this);
			
			if(option == JFileChooser.APPROVE_OPTION)
			{
				selectedFile = choice.getSelectedFile();
				
	            ImageIcon icon = new ImageIcon(selectedFile.getAbsolutePath());
				Image image = icon.getImage();
				Image newImage = image.getScaledInstance(200, 200, Image.SCALE_DEFAULT);
				labelImage.setIcon(new ImageIcon(newImage));	
			}				
		}
		
		if(e.getSource() == this.comboBoxType) 
		{
			this.comboBoxModel.removeAllItems();
			for(int i=0;i<this.listTypeAndModel.size();i++)
			{
				if(this.comboBoxType.getSelectedItem().toString().equals(this.listTypeAndModel.get(i).getType()))
				{
					this.comboBoxModel.addItem(this.listTypeAndModel.get(i).getModel());
				}
			}
		}
		
		if(e.getSource() == this.radioButton1 || e.getSource() == this.radioButton2 || e.getSource() == this.radioButton3 || e.getSource() == this.radioButton4 || e.getSource() == this.radioButton5 || e.getSource() == this.radioButton6 || e.getSource() == this.radioButton7) 
		{
			if(e.getSource() == this.radioButton3 || e.getSource() == this.radioButton4 || e.getSource() == this.radioButton5 || e.getSource() == this.radioButton6 || e.getSource() == this.radioButton7) 
			{
				panel_c232.setVisible(true);
				
				this.labelMoreInfo.setText("Type :");
				this.labelMoreInfo2.setText("Mod�le :");
				this.comboBoxType.setVisible(true);
				this.txtIpAddress2.setVisible(false);		
				this.txtDataStorage.setVisible(false);				
				
				this.comboBoxModel.removeAllItems();
				for(int i=0;i<this.listTypeAndModel.size();i++)
				{
					if(this.comboBoxType.getSelectedItem().toString().equals(this.listTypeAndModel.get(i).getType()))
					{
						this.comboBoxModel.addItem(this.listTypeAndModel.get(i).getModel());
					}
				}
			}
			
			if(e.getSource() == this.radioButton2) 
			{
				this.panel_c232.setVisible(false);
				
				this.labelMoreInfo.setText("Chemin UNC:");
				this.comboBoxType.setVisible(false);
				this.txtIpAddress2.setVisible(false);		
				this.txtDataStorage.setVisible(true);				
			}
			
			if(e.getSource() == this.radioButton1) 
			{
				this.panel_c232.setVisible(false);
				
				this.labelMoreInfo.setText("2e Adresse IP:");
				this.comboBoxType.setVisible(false);
				this.txtIpAddress2.setVisible(true);		
				this.txtDataStorage.setVisible(false);				
			}
		}
		
	}
	
	public void displaySPs(ArrayList<Category> TempListCategorys)
	{
		Equipment equipment = new Equipment();
		listTypeAndModel = new ArrayList<Equipment>();
		equipment.getListTypeAndModel(listTypeAndModel);		
		ArrayList<String> tempListType = new ArrayList<String>() ;
		
		boolean sameType=false;
		
		this.radioButton1.setSelected(true);
		this.labelMoreInfo.setVisible(true);
		this.labelMoreInfo.setText("2e Adresse IP:");
		this.comboBoxType.setVisible(false);
		this.txtIpAddress2.setVisible(true);		
		this.txtDataStorage.setVisible(false);
		

		for(int i=0;i<listTypeAndModel.size();i++)
		{
			sameType=false;
			if(tempListType.size()>0)
			{
				for(int j=0;j<tempListType.size();j++)
				{
					if(listTypeAndModel.get(i).getType().equals(tempListType.get(j)))
					{
						sameType=true;
						break;
					}							
				}				
			}		
			
			if(sameType==false)
			{
				tempListType.add(listTypeAndModel.get(i).getType());	
			}
		}
		
		for(int i=0;i<tempListType.size();i++)
		{
			this.comboBoxType.addItem(tempListType.get(i));
		}

		for(int i=0;i<listTypeAndModel.size();i++)
		{
			this.comboBoxModel.addItem(listTypeAndModel.get(i).getModel());
		}

		
		if(TempListCategorys.size()>0)
		{
			this.radioButton1.setVisible(true);		
			this.radioButton1.setText(TempListCategorys.get(0).getName());
		}
		if(TempListCategorys.size()>1)
		{
			this.radioButton2.setVisible(true);		
			this.radioButton2.setText(TempListCategorys.get(1).getName());
		}
		if(TempListCategorys.size()>2)
		{
			this.radioButton3.setVisible(true);		
			this.radioButton3.setText(TempListCategorys.get(2).getName());
		}
		if(TempListCategorys.size()>3)
		{
			this.radioButton4.setVisible(true);		
			this.radioButton4.setText(TempListCategorys.get(3).getName());
		}
		if(TempListCategorys.size()>4)
		{
			this.radioButton5.setVisible(true);		
			this.radioButton5.setText(TempListCategorys.get(4).getName());
		}
		if(TempListCategorys.size()>5)
		{
			this.radioButton6.setVisible(true);		
			this.radioButton6.setText(TempListCategorys.get(5).getName());
		}
		if(TempListCategorys.size()>06)
		{
			this.radioButton7.setVisible(true);		
			this.radioButton7.setText(TempListCategorys.get(6).getName());
		}
		
		
	}
	
	/////////////************************    Getters and Setters *************************
	public JTextField getTxtAdresseIp2() {
		return txtIpAddress2;
	}

	public JComboBox<String> getComboBoxType() {
		return comboBoxType;
	}

	public JTextField getTxtSPName() {
		return txtSPName;
	}

	public void setTxtSPName(JTextField txtSPName) {
		this.txtSPName = txtSPName;
	}

	public JTextField getTxtDescription() {
		return txtDescription;
	}

	public void setTxtDescription(JTextField txtDescription) {
		this.txtDescription = txtDescription;
	}

	public JTextField getTxtIpAddress1() {
		return txtIpAddress1;
	}

	public void setTxtIpAddress1(JTextField txtIpAddress1) {
		this.txtIpAddress1 = txtIpAddress1;
	}

	public JTextField getTxtIpAddress2() {
		return txtIpAddress2;
	}

	public void setTxtIpAddress2(JTextField txtIpAddress2) {
		this.txtIpAddress2 = txtIpAddress2;
	}

	public void setComboBoxType(JComboBox<String> comboBoxType) {
		this.comboBoxType = comboBoxType;
	}

	public JTextField getTxtDataStorage() {
		return txtDataStorage;
	}

	public void setTxtDataStorage(JTextField txtDataStorage) {
		this.txtDataStorage = txtDataStorage;
	}

	public File getSelectedFile() {
		return selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public JComboBox<String> getComboBoxModel() {
		return comboBoxModel;
	}

	public void setComboBoxModel(JComboBox<String> comboBoxModel) {
		this.comboBoxModel = comboBoxModel;
	}
	
	
}
