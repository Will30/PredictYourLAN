package Presentation;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Metier.Bug;
import Metier.Category;
import Presentation.Controller;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.border.BevelBorder;
import java.awt.CardLayout;
import javax.swing.JTextPane;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.ScrollPaneConstants;
import java.awt.SystemColor;

/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Main class. It consists of strategic points list with associated LED. LED switches on in different colors (green, orange, red or black) if bug is found.
 * Several Buttons are on the right side to manage users, add a new strategic point, or to see more details for a strageic point
 *
 */


public class MainWindow extends JPanel implements ActionListener
{
	public JPanel contentPane;
	
	private JTextArea textArea;
	private JScrollPane panelInfo;

	private JPanel panel_c1;
	private JPanel panel_c2;
	private JPanel panel_c3;
	
	private JPanel panel_c22;
	private JPanel panel_c23;
	private JPanel panel_c24;
	private JPanel panel_c25;
	private JPanel panel_c26;
	private JPanel panel_c27;
	private JPanel panel_c28;
	
	private JPanel panel_c221;
	private JPanel panel_c231;
	private JPanel panel_c241;
	private JPanel panel_c251;
	private JPanel panel_c261;
	private JPanel panel_c271;
	private JPanel panel_c281;
	private JPanel panel_c291;
	
	
	private JLabel labelIcon1;
	private JLabel labelIcon2;
	private JLabel labelIcon3;
	private JLabel labelIcon4;
	private JLabel labelIcon5;
	private JLabel labelIcon6;
	private JLabel labelIcon7;
	
	private JLabel labelLed1;
	private JLabel labelLed2;
	private JLabel labelLed3;
	private JLabel labelLed4;
	private JLabel labelLed5;
	private JLabel labelLed6;
	private JLabel labelLed7;
	
	private JLabel labelName1;
	private JLabel labelName2;
	private JLabel labelName3;
	private JLabel labelName4;
	private JLabel labelName5;
	private JLabel labelName6;
	private JLabel labelName7;
	
	protected JButton manageUserButton;
	protected JButton addUserButton;
	
	private Image backgroundImage;
	private JPanel panel_c12;

	
	/**
	 * Create the frame.
	 */
	public MainWindow() 
	{
	//	listSPs = TempListSPs;
	//	filteredList = new ArrayList<StrategicPoint>();
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(new Color(240,240,240));
		northPanel.setOpaque(false);
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_N1 = new JPanel();
		panel_N1.setBackground(Color.WHITE);
		panel_N1.setOpaque(false);
		northPanel.add(panel_N1);
		panel_N1.setLayout(new BoxLayout(panel_N1, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_111 = new JLabel(new ImageIcon(MainWindow.class.getResource("/images/logoEnterprise.jpg")));
		lblNewLabel_111.setAlignmentY(0.0f);
		lblNewLabel_111.setHorizontalAlignment(SwingConstants.CENTER);
		panel_N1.add(lblNewLabel_111);
		
		JPanel panel_N2 = new JPanel();
		panel_N2.setOpaque(false);
		panel_N2.setBackground(new Color(240,240,240));
		northPanel.add(panel_N2);
		panel_N2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_N3 = new JPanel();
		panel_N3.setBackground(Color.WHITE);
		panel_N3.setOpaque(false);
		northPanel.add(panel_N3);
		panel_N3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_121 = new JLabel(new ImageIcon(MainWindow.class.getResource("/images/logo2.png")));
		lblNewLabel_121.setBackground(Color.WHITE);
		lblNewLabel_121.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_N3.add(lblNewLabel_121);
		lblNewLabel_121.setForeground(Color.WHITE);
		lblNewLabel_121.setFont(new Font("Snap ITC", Font.PLAIN, 32));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setBorder(null);
		
		centerPanel.setBackground(Color.WHITE);
		this.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] {500, 700, 80};
		gbl_centerPanel.rowHeights = new int[]{880, 0, 0};
		gbl_centerPanel.columnWeights = new double[]{1.0, 0.0, 0.0};
		gbl_centerPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		panel_c1 = new JPanel();
		panel_c1.setOpaque(false);
		panel_c1.setLayout(null);
		GridBagConstraints gbc_panel_c1 = new GridBagConstraints();
		gbc_panel_c1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c1.weighty = 1.0;
		gbc_panel_c1.weightx = 1.0;
		gbc_panel_c1.fill = GridBagConstraints.BOTH;
		gbc_panel_c1.gridx = 0;
		gbc_panel_c1.gridy = 0;
		centerPanel.add(panel_c1, gbc_panel_c1);
		
		panel_c12 = new JPanel();
		panel_c12.setBounds(20, 110, 350, 400);
		panel_c1.add(panel_c12);
		panel_c12.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea(5, 40);	
		textArea.setFont(new Font("Arial", Font.ITALIC, 16));
		textArea.setBackground(new Color(1f, 1f, 1f));
		textArea.setBounds(20, 100, 400, 750);
		textArea.setLineWrap(true);
		
		panelInfo  = new JScrollPane(textArea);
		
		panelInfo.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panel_c12.add(panelInfo);
		
		JPanel panel_c11 = new JPanel();
		panel_c11.setBackground(new Color(1f, 1f, 1f, 0.8f));
		panel_c11.setBounds(20, 70, 250, 30);
		panel_c1.add(panel_c11);
		panel_c11.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Liste \u00E9v\u00E8nement en cours:");
		lblNewLabel.setBackground(Color.WHITE);
		panel_c11.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		panel_c2 = new JPanel();
		
		GridBagConstraints gbc_panel_c2 = new GridBagConstraints();
		gbc_panel_c2.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c2.weighty = 1.0;
		gbc_panel_c2.weightx = 1.0;
		gbc_panel_c2.fill = GridBagConstraints.BOTH;
		gbc_panel_c2.gridx = 1;
		gbc_panel_c2.gridy = 0;
		centerPanel.add(panel_c2, gbc_panel_c2);
		panel_c2.setLayout(new GridLayout(8, 1, 0, 0));
		
		JPanel panel_c21 = new JPanel();
		panel_c2.add(panel_c21);
		panel_c21.setLayout(null);
		
		panel_c22 = new JPanel();
		panel_c2.add(panel_c22);
		panel_c22.setLayout(null);
		
		labelIcon1 = new JLabel("");
		labelIcon1.setBounds(30, 0, 100, 80);
		panel_c22.add(labelIcon1);
		
		labelLed1 = new JLabel("");
		labelLed1.setBounds(150, 0, 60, 60);
		panel_c22.add(labelLed1);
		
		panel_c221 = new JPanel();
		panel_c221.setBounds(230, 0, 400, 60);
		panel_c221.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c221.setBackground(Color.WHITE);
		panel_c22.add(panel_c221);
		panel_c221.setLayout(new CardLayout(0, 0));
		
		labelName1 = new JLabel("New label");
		labelName1.setBackground(new Color(1f, 1f, 1f, 0.4f));
		labelName1.setHorizontalAlignment(SwingConstants.CENTER);
		labelName1.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c221.add(labelName1, "name_113882069678884");
		
		panel_c23 = new JPanel();
		panel_c2.add(panel_c23);
		panel_c23.setLayout(null);
		
		labelIcon2 = new JLabel("");
		labelIcon2.setBounds(30, 0, 100, 80);
		panel_c23.add(labelIcon2);
		
		labelLed2 = new JLabel("");
		labelLed2.setBounds(150, 0, 60, 60);
		panel_c23.add(labelLed2);
		
		panel_c231 = new JPanel();
		panel_c231.setBounds(230, 0, 400, 60);
		panel_c231.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c231.setBackground(Color.WHITE);
		panel_c23.add(panel_c231);
		panel_c231.setLayout(new CardLayout(0, 0));
		
		labelName2 = new JLabel("New label");
		labelName2.setHorizontalAlignment(SwingConstants.CENTER);
		labelName2.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c231.add(labelName2, "name_114187306957180");
		
		panel_c24 = new JPanel();
		panel_c2.add(panel_c24);
		panel_c24.setLayout(null);
		
		labelIcon3 = new JLabel("");
		labelIcon3.setBounds(30, 0, 100, 80);
		panel_c24.add(labelIcon3);
		
		labelLed3 = new JLabel("");
		labelLed3.setBounds(150, 0, 60, 60);
		panel_c24.add(labelLed3);
		
		panel_c241 = new JPanel();
		panel_c241.setBounds(230, 0, 400, 60);
		panel_c241.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c241.setBackground(Color.WHITE);
		panel_c24.add(panel_c241);
		panel_c241.setLayout(new CardLayout(0, 0));
		
		labelName3 = new JLabel("New label");
		labelName3.setHorizontalAlignment(SwingConstants.CENTER);
		labelName3.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c241.add(labelName3, "name_114214545317206");
		
		panel_c25 = new JPanel();
		panel_c2.add(panel_c25);
		panel_c25.setLayout(null);
		
		labelIcon4 = new JLabel("");
		labelIcon4.setBounds(30, 0, 100, 80);
		panel_c25.add(labelIcon4);
		
		labelLed4 = new JLabel("");
		labelLed4.setBounds(150, 0, 60, 60);
		panel_c25.add(labelLed4);
		
		panel_c251 = new JPanel();
		panel_c251.setBounds(230, 0, 400, 60);
		panel_c251.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c251.setBackground(Color.WHITE);
		panel_c25.add(panel_c251);
		panel_c251.setLayout(new CardLayout(0, 0));
		
		labelName4 = new JLabel("New label");
		labelName4.setHorizontalAlignment(SwingConstants.CENTER);
		labelName4.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c251.add(labelName4, "name_114216846661158");
		
		panel_c26 = new JPanel();
		panel_c2.add(panel_c26);
		panel_c26.setLayout(null);
		
		labelIcon5 = new JLabel("");
		labelIcon5.setBounds(30, 0, 100, 80);
		panel_c26.add(labelIcon5);
		
		labelLed5 = new JLabel("");
		labelLed5.setBounds(150, 0, 60, 60);
		panel_c26.add(labelLed5);
		
		panel_c261 = new JPanel();
		panel_c261.setBounds(230, 0, 400, 60);
		panel_c261.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c261.setBackground(Color.WHITE);
		panel_c26.add(panel_c261);
		panel_c261.setLayout(new CardLayout(0, 0));
		
		labelName5 = new JLabel("New label");
		labelName5.setHorizontalAlignment(SwingConstants.CENTER);
		labelName5.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c261.add(labelName5, "name_114218970204526");
		
		panel_c27 = new JPanel();
		panel_c2.add(panel_c27);
		panel_c27.setLayout(null);
		
		labelIcon6 = new JLabel("");
		labelIcon6.setBounds(30, 0, 100, 80);
		panel_c27.add(labelIcon6);
		
		labelLed6 = new JLabel("");
		labelLed6.setBounds(150, 0, 60, 60);
		panel_c27.add(labelLed6);
		
		panel_c271 = new JPanel();
		panel_c271.setBounds(230, 0, 400, 60);
		panel_c271.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c271.setBackground(Color.WHITE);
		panel_c27.add(panel_c271);
		panel_c271.setLayout(new CardLayout(0, 0));
		
		labelName6 = new JLabel("New label");
		labelName6.setHorizontalAlignment(SwingConstants.CENTER);
		labelName6.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c271.add(labelName6, "name_114221551954349");
		
		panel_c28 = new JPanel();
		panel_c2.add(panel_c28);
		panel_c28.setLayout(null);
		
		labelIcon7 = new JLabel("");
		labelIcon7.setBounds(30, 0, 100, 80);
		panel_c28.add(labelIcon7);
		
		labelLed7 = new JLabel("");
		labelLed7.setBounds(150, 0, 60, 60);
		panel_c28.add(labelLed7);
		
		panel_c281 = new JPanel();
		panel_c281.setBounds(230, 0, 400, 60);
		panel_c281.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
		panel_c281.setBackground(Color.WHITE);
		panel_c28.add(panel_c281);
		panel_c281.setLayout(new CardLayout(0, 0));
		
		labelName7 = new JLabel("New label");
		labelName7.setHorizontalAlignment(SwingConstants.CENTER);
		labelName7.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_c281.add(labelName7, "name_114225123811234");
		
		panel_c3 = new JPanel();
		GridBagConstraints gbc_panel_c3 = new GridBagConstraints();
		gbc_panel_c3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_c3.weighty = 1.0;
		gbc_panel_c3.weightx = 1.0;
		gbc_panel_c3.fill = GridBagConstraints.BOTH;
		gbc_panel_c3.gridx = 2;
		gbc_panel_c3.gridy = 0;
		centerPanel.add(panel_c3, gbc_panel_c3);
		panel_c3.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel panel_c31 = new JPanel();
		panel_c3.add(panel_c31);
		
		JPanel panel_c32 = new JPanel();
		panel_c3.add(panel_c32);
		
		JPanel panel_c33 = new JPanel();
		panel_c3.add(panel_c33);
		
		JPanel panel_c34 = new JPanel();
		panel_c3.add(panel_c34);
		panel_c34.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		addUserButton = new JButton(new ImageIcon(MainWindow.class.getResource("/images/button/addUserButton.png")));
		addUserButton.setBackground(SystemColor.textHighlight);
		panel_c34.add(addUserButton);
		
		JPanel panel_c35 = new JPanel();
		panel_c3.add(panel_c35);
		
		manageUserButton = new JButton(new ImageIcon(MainWindow.class.getResource("/images/button/accountManagementButton.png")));
		manageUserButton.setBackground(SystemColor.textHighlight);
		panel_c35.add(manageUserButton);
		
		panel_c2.setOpaque(false);
		panel_c3.setOpaque(false);		
		panel_c21.setOpaque(false);
		panel_c22.setOpaque(false);
		panel_c23.setOpaque(false);
		panel_c24.setOpaque(false);
		panel_c25.setOpaque(false);
		panel_c26.setOpaque(false);
		panel_c27.setOpaque(false);
		panel_c28.setOpaque(false);
		panel_c31.setOpaque(false);
		panel_c32.setOpaque(false);
		panel_c33.setOpaque(false);
		panel_c34.setOpaque(false);
		panel_c35.setOpaque(false);		
		
		
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
		
		// if you want to display a new category, delete these 4 lines
		panel_c28.setVisible(false);
		labelIcon7.setVisible(false);
		labelLed7.setVisible(false);
		labelName7.setVisible(false);
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(this.backgroundImage, 0, 0, null);			
	}
	
	/**
	 * Displays all strategic point with associated LED
	 * @param TempListCategorys category list 
	 * @since 1.00  
	 */
	public void displaySPs(ArrayList<Category> TempListCategorys)
	{
	
		ArrayList<Bug> BugListActive = new ArrayList<Bug>();
		Bug tempBug = new Bug();

		if(TempListCategorys.size()>0)
		{			
			this.setLabelIcon1(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(0).getUNC())));		
			this.setLabelName1(TempListCategorys.get(0).getName());
			this.setLabelLed1(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(0).getLed().getUNC())));			
		}
		
		if(TempListCategorys.size()>1)
		{
			this.setLabelIcon2(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(1).getUNC())));
			this.setLabelLed2(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(1).getLed().getUNC())));
			this.setLabelName2(TempListCategorys.get(1).getName());
		}
		
		if(TempListCategorys.size()>2)
		{
			this.setLabelIcon3(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(2).getUNC())));
			this.setLabelLed3(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(2).getLed().getUNC())));
			this.setLabelName3(TempListCategorys.get(2).getName());
		}
		
		if(TempListCategorys.size()>3)
		{			
			this.setLabelIcon4(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(3).getUNC())));
			this.setLabelLed4(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(3).getLed().getUNC())));
			this.setLabelName4(TempListCategorys.get(3).getName());
		}
		
		if(TempListCategorys.size()>4)
		{			
			this.setLabelIcon5(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(4).getUNC())));
			this.setLabelLed5(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(4).getLed().getUNC())));
			this.setLabelName5(TempListCategorys.get(4).getName());
		}
		
		if(TempListCategorys.size()>5)
		{			
			this.setLabelIcon6(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(5).getUNC())));
			this.setLabelLed6(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(5).getLed().getUNC())));
			this.setLabelName6(TempListCategorys.get(5).getName());
		}
		
		if(TempListCategorys.size()>6)
		{
			this.setLabelIcon7(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(6).getUNC())));
			this.setLabelLed7(new ImageIcon(MainWindow.class.getResource(TempListCategorys.get(6).getLed().getUNC())));
			this.setLabelName7(TempListCategorys.get(6).getName());
		}
		
		tempBug.getBugActiveForAllCategory(BugListActive);
	
		
		
		for(int i=0;i<BugListActive.size();i++)
		{
			this.textArea.setText(BugListActive.get(i).getDate() +":  "+BugListActive.get(i).getCategory() +"\n  "+BugListActive.get(i).getDetail() +"  \n"+this.textArea.getText());
		}
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		
	}
	
	
	/////////////************************    Getters and Setters *************************
	
	public void setLabelIcon1(ImageIcon labelIcon1) {
		this.labelIcon1.setIcon(labelIcon1); 
	}

	public void setLabelIcon2(ImageIcon labelIcon2) {
		this.labelIcon2.setIcon(labelIcon2); 
	}

	public void setLabelIcon3(ImageIcon labelIcon3) {
		this.labelIcon3.setIcon(labelIcon3); 
	}

	public void setLabelIcon4(ImageIcon labelIcon4) {
		this.labelIcon4.setIcon(labelIcon4); 
	}

	public void setLabelIcon5(ImageIcon labelIcon5) {
		this.labelIcon5.setIcon(labelIcon5); 
	}

	public void setLabelIcon6(ImageIcon labelIcon6) {
		this.labelIcon6.setIcon(labelIcon6); 
	}

	public void setLabelIcon7(ImageIcon labelIcon7) {
		this.labelIcon7.setIcon(labelIcon7); 
	}

	public void setLabelLed1(ImageIcon labelLed1) {
		this.labelLed1.setIcon(labelLed1); 
	}

	public void setLabelLed2(ImageIcon labelLed2) {
		this.labelLed2.setIcon(labelLed2);
	}

	public void setLabelLed3(ImageIcon labelLed3) {
		this.labelLed3.setIcon(labelLed3);
	}

	public void setLabelLed4(ImageIcon labelLed4) {
		this.labelLed4.setIcon(labelLed4);
	}

	public void setLabelLed5(ImageIcon labelLed5) {
		this.labelLed5.setIcon(labelLed5);
	}

	public void setLabelLed6(ImageIcon labelLed6) {
		this.labelLed6.setIcon(labelLed6);
	}

	public void setLabelLed7(ImageIcon labelLed7) {
		this.labelLed7.setIcon(labelLed7);
	}

	public void setLabelName1(String labelName1) {
		this.labelName1.setText(labelName1);
	}

	public void setLabelName2(String labelName2) {
		this.labelName2.setText(labelName2);
	}

	public void setLabelName3(String labelName3) {
		this.labelName3.setText(labelName3);
	}

	public void setLabelName4(String labelName4) {
		this.labelName4.setText(labelName4);
	}

	public void setLabelName5(String labelName5) {
		this.labelName5.setText(labelName5);
	}

	public void setLabelName6(String labelName6) {
		this.labelName6.setText(labelName6);
	}

	public void setLabelName7(String labelName7) {
		this.labelName7.setText(labelName7);
	}


	public JLabel getLabelIcon1() {
		return labelIcon1;
	}

	public JLabel getLabelIcon2() {
		return labelIcon2;
	}

	public JLabel getLabelIcon3() {
		return labelIcon3;
	}

	public JLabel getLabelIcon4() {
		return labelIcon4;
	}

	public JLabel getLabelIcon5() {
		return labelIcon5;
	}

	public JLabel getLabelIcon6() {
		return labelIcon6;
	}

	public JLabel getLabelIcon7() {
		return labelIcon7;
	}

	public JLabel getLabelLed1() {
		return labelLed1;
	}

	public JLabel getLabelLed2() {
		return labelLed2;
	}

	public JLabel getLabelLed3() {
		return labelLed3;
	}

	public JLabel getLabelLed4() {
		return labelLed4;
	}

	public JLabel getLabelLed5() {
		return labelLed5;
	}

	public JLabel getLabelLed6() {
		return labelLed6;
	}

	public JLabel getLabelLed7() {
		return labelLed7;
	}

	public JLabel getLabelName1() {
		return labelName1;
	}

	public JLabel getLabelName2() {
		return labelName2;
	}

	public JLabel getLabelName3() {
		return labelName3;
	}

	public JLabel getLabelName4() {
		return labelName4;
	}

	public JLabel getLabelName5() {
		return labelName5;
	}

	public JLabel getLabelName6() {
		return labelName6;
	}

	public JLabel getLabelName7() {
		return labelName7;
	}

	public JPanel getPanel_c22() {
		return panel_c22;
	}

	public void setPanel_c22(JPanel panel_c22) {
		this.panel_c22 = panel_c22;
	}

	public JPanel getPanel_c23() {
		return panel_c23;
	}

	public void setPanel_c23(JPanel panel_c23) {
		this.panel_c23 = panel_c23;
	}

	public JPanel getPanel_c24() {
		return panel_c24;
	}

	public void setPanel_c24(JPanel panel_c24) {
		this.panel_c24 = panel_c24;
	}

	public JPanel getPanel_c25() {
		return panel_c25;
	}

	public void setPanel_c25(JPanel panel_c25) {
		this.panel_c25 = panel_c25;
	}

	public JPanel getPanel_c26() {
		return panel_c26;
	}

	public void setPanel_c26(JPanel panel_c26) {
		this.panel_c26 = panel_c26;
	}

	public JPanel getPanel_c27() {
		return panel_c27;
	}

	public void setPanel_c27(JPanel panel_c27) {
		this.panel_c27 = panel_c27;
	}

	public JPanel getPanel_c28() {
		return panel_c28;
	}

	public void setPanel_c28(JPanel panel_c28) {
		this.panel_c28 = panel_c28;
	}

	public JPanel getPanel_c221() {
		return panel_c221;
	}

	public void setPanel_c221(JPanel panel_c221) {
		this.panel_c221 = panel_c221;
	}

	public JPanel getPanel_c231() {
		return panel_c231;
	}

	public void setPanel_c231(JPanel panel_c231) {
		this.panel_c231 = panel_c231;
	}

	public JPanel getPanel_c241() {
		return panel_c241;
	}

	public void setPanel_c241(JPanel panel_c241) {
		this.panel_c241 = panel_c241;
	}

	public JPanel getPanel_c251() {
		return panel_c251;
	}

	public void setPanel_c251(JPanel panel_c251) {
		this.panel_c251 = panel_c251;
	}

	public JPanel getPanel_c261() {
		return panel_c261;
	}

	public void setPanel_c261(JPanel panel_c261) {
		this.panel_c261 = panel_c261;
	}

	public JPanel getPanel_c271() {
		return panel_c271;
	}

	public void setPanel_c271(JPanel panel_c271) {
		this.panel_c271 = panel_c271;
	}

	public JPanel getPanel_c281() {
		return panel_c281;
	}

	public void setPanel_c281(JPanel panel_c281) {
		this.panel_c281 = panel_c281;
	}

	public JPanel getPanel_c291() {
		return panel_c291;
	}

	public void setPanel_c291(JPanel panel_c291) {
		this.panel_c291 = panel_c291;
	}

	
/*	public int getIndexDisplay() {
		return indexDisplay;
	}

	public void setIndexDisplay(int indexDisplay) {
		this.indexDisplay = indexDisplay;
	}*/
}
