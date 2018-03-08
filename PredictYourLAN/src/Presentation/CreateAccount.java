package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JTextField;

import javax.swing.JComboBox;
import javax.swing.JPasswordField;

/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Class represents registration for new account. Only call from login panel
 *
 */

@SuppressWarnings("serial")
public class CreateAccount extends JPanel
{

	public JPanel contentPane;
	public JButton buttonOK;
	public JButton buttonReturn;
	
	private JTextField txtUser;
	private JTextField textMail;
	private JPasswordField txtPassword;
	private JPasswordField txtPsswordConfirmed;
	
	private Image backgroundImage;

	/**
	 * Create the panel.
	 */
	public CreateAccount() 
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
		
		JLabel lblNewLabel_111 =  new JLabel(new ImageIcon(Login.class.getResource("/images/logoEnterprise_800_600.png")));
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
		
		txtUser = new JTextField();
		txtUser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) 
			{
				 txtUser.setText("");
			}
		});
		
		JLabel labelTitleUser = new JLabel("Nom d'utilisateur :");
		labelTitleUser.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTitleUser.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitleUser.setBounds(200, 20, 120, 30);
		panel_1.add(labelTitleUser);
		
		txtUser.setFont(new Font("Arial", Font.ITALIC, 18));
		txtUser.setLocation(350, 20);
		txtUser.setSize(250, 30);
		panel_1.add(txtUser);
		txtUser.setColumns(10);
		
		JPanel panel_2 = new JPanel();
		centerPanel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel labelTitlePassword = new JLabel("Mot de passe :");
		labelTitlePassword.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitlePassword.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTitlePassword.setBounds(200, 20, 120, 30);
		panel_2.add(labelTitlePassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(350, 20, 250, 30);
		panel_2.add(txtPassword);
				
		JPanel panel_3 = new JPanel();
		centerPanel.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel labelTitlePasswordConfirmed = new JLabel("Confirmer mot de passe :");
		labelTitlePasswordConfirmed.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitlePasswordConfirmed.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTitlePasswordConfirmed.setBounds(120, 20, 200, 30);
		panel_3.add(labelTitlePasswordConfirmed);
		
		txtPsswordConfirmed = new JPasswordField();
		txtPsswordConfirmed.setBounds(350, 20, 250, 30);
		panel_3.add(txtPsswordConfirmed);

		JPanel panel_4 = new JPanel();
		centerPanel.add(panel_4);
		panel_4.setLayout(null);
		
		textMail = new JTextField();
		textMail.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) 
			{
				textMail.setText("");	
			}
		});
		
		JLabel labelTitleMail = new JLabel("Email :");
		labelTitleMail.setHorizontalAlignment(SwingConstants.RIGHT);
		labelTitleMail.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTitleMail.setBounds(200, 20, 120, 30);
		panel_4.add(labelTitleMail);
		textMail.setFont(new Font("Arial", Font.ITALIC, 18));
		textMail.setColumns(10);
		textMail.setBounds(350, 20, 250, 30);
		panel_4.add(textMail);
		
		JPanel panel_5 = new JPanel();
		centerPanel.add(panel_5);
		panel_5.setLayout(null);
		
		JPanel southPanel = new JPanel();
		southPanel.setOpaque(false);
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new GridLayout(1, 2, 0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_9.setOpaque(false);
		panel_9.getLayout();
		southPanel.add(panel_9);
		
		buttonReturn = new JButton("Retour");
		panel_9.add(buttonReturn);
		
		JPanel panel_10 = new JPanel();
		panel_10.setOpaque(false);
		panel_10.getLayout();
		southPanel.add(panel_10);
		
		buttonOK = new JButton("Valider");
		panel_10.add(buttonOK);
		buttonOK.setMnemonic(KeyEvent.VK_ENTER);		
		
		panel_1.setOpaque(false);
		panel_2.setOpaque(false);
		panel_3.setOpaque(false);
		panel_4.setOpaque(false);
		panel_5.setOpaque(false);
		
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

	/////////////************************    Getters and Setters *************************

	public JTextField getTxtUser() {
		return txtUser;
	}
	public void setTxtUser(JTextField txtUser) {
		this.txtUser = txtUser;
	}
	public JTextField getTxtPassword() {
		return txtPassword;
	}

	public void setTxtPassword(JPasswordField txtPassword) {
		this.txtPassword = txtPassword;
	}

	public JTextField getTxtPsswordConfirmed() {
		return txtPsswordConfirmed;
	}

	public void setTxtPsswordConfirmed(JPasswordField txtPsswordConfirmed) {
		this.txtPsswordConfirmed = txtPsswordConfirmed;
	}

	public JTextField getTextMail() {
		return textMail;
	}

	public void setTextMail(JTextField textMail) {
		this.textMail = textMail;
	}

	/*public JComboBox<?> getComboBoxService() {
		return comboBoxService;
	}

	public void setComboBoxService(JComboBox<?> comboBoxService) {
		this.comboBoxService = comboBoxService;
	}*/
}
	
	

