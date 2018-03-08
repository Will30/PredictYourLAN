package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;


/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * This class allows to activate new account. When user create a new account, administrator must activate it. When it done,
 * an automatic mail is sent with his credentials.
 *
 */

public class ManageAccount extends JPanel {


	public JPanel contentPane;
	public JButton buttonOK;
	public JButton buttonReturn;
	private JComboBox comboBoxUser;	
	
	private JCheckBox checkboxAccountState;
	private JCheckBox checkBoxAdminAccess;
	
	private Image backgroundImage;

	/**
	 * Create the panel.
	 */
	public ManageAccount() 
	{
		this.setBorder(new EmptyBorder(5, 5, 5, 5));		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setOpaque(false);
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_11 = new JPanel();
		panel_11.setOpaque(false);
		northPanel.add(panel_11);
		panel_11.setLayout(new BoxLayout(panel_11, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_111 = new JLabel(new ImageIcon(MainWindow.class.getResource("/images/logoEnterprise_800_600.png")));
		lblNewLabel_111.setHorizontalAlignment(SwingConstants.CENTER);
		panel_11.add(lblNewLabel_111);
		
		JPanel panel_12 = new JPanel();
		panel_12.setOpaque(false);
		northPanel.add(panel_12);
		
		JLabel lblNewLabel_121 = new JLabel(new ImageIcon(Login.class.getResource("/images/logo_800_600.png")));
		northPanel.add(lblNewLabel_121);
		lblNewLabel_121.setForeground(new Color(30, 144, 255));
		lblNewLabel_121.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		this.add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new GridLayout(5, 1, 200, 20));
		
		JPanel panel_1 = new JPanel();	
		centerPanel.add(panel_1);
		panel_1.setLayout(null);
		
		JPanel panel_2 = new JPanel();

		centerPanel.add(panel_2);
		panel_2.setLayout(null);
		
		comboBoxUser = new JComboBox<>();
		comboBoxUser.setBounds(275, 20, 250, 30);
		panel_2.add(comboBoxUser);
		
				
		JPanel panel_3 = new JPanel();
		centerPanel.add(panel_3);
		panel_3.setLayout(null);

		JPanel panel_4 = new JPanel();
		centerPanel.add(panel_4);
		panel_4.setLayout(null);
		
		checkboxAccountState = new JCheckBox("Actif");
		checkboxAccountState.setFont(new Font("Arial", Font.PLAIN, 14));
		checkboxAccountState.setBounds(370, 10, 200, 25);
		panel_4.add(checkboxAccountState);
		
		JPanel panel_5 = new JPanel();
		centerPanel.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel southPanel = new JPanel();
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_9.getLayout();
		southPanel.add(panel_9);
		
		buttonReturn = new JButton("Retour");
		panel_9.add(buttonReturn);
		
		JPanel panel_10 = new JPanel();
		panel_10.getLayout();
		southPanel.add(panel_10);
		
		buttonOK = new JButton("Valider");
		panel_10.add(buttonOK);
		buttonOK.setMnemonic(KeyEvent.VK_ENTER);

		panel_1.setOpaque(false);
		panel_2.setOpaque(false);
		panel_3.setOpaque(false);
		panel_4.setOpaque(false);
		
		checkBoxAdminAccess = new JCheckBox("Accès Admin");
		checkBoxAdminAccess.setFont(new Font("Arial", Font.PLAIN, 14));
		checkBoxAdminAccess.setBounds(370, 40, 200, 25);
		panel_4.add(checkBoxAdminAccess);
		panel_5.setOpaque(false);
		panel_9.setOpaque(false);
		panel_10.setOpaque(false);
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("images/background.jpg");
		try 
		{			
				this.backgroundImage = ImageIO.read(is);
			
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		}
		this.backgroundImage = this.backgroundImage.getScaledInstance(800,600,Image.SCALE_DEFAULT);	
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(this.backgroundImage, 0, 0, null);			
	}

	public void displayInfo()
	{
		
	}

	/////////////************************    Getters and Setters *************************

	public JComboBox getcomboBoxUser() {
		return comboBoxUser;
	}

	public void setCcomboBoxUser(JComboBox<String> comboBoxUser) {
		this.comboBoxUser = comboBoxUser;
	}

	public JCheckBox getCheckboxAccountState() {
		return checkboxAccountState;
	}

	public void setCheckboxAccountState(boolean checkboxAccountState) {
		this.checkboxAccountState.setSelected(checkboxAccountState); 
	}

	public JCheckBox getCheckBoxAdminAccess() {
		return checkBoxAdminAccess;
	}

	public void setCheckBoxAdminAccess(boolean checkBoxAdminAccess) {
		this.checkBoxAdminAccess.setSelected(checkBoxAdminAccess);
	}
	
	
	
}
	
	

