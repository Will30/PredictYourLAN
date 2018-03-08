package Presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * 
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * First class started by controller. Identification is needed to launch application, in such case, user have to save a new account, 
 	though registration panel
 *
 */
@SuppressWarnings("serial")
public class Login extends JPanel
{

	public JPanel contentPane;
	public JButton connectButton;
	
	private JPasswordField password;
	private JTextField username;	

	private Image backgroundImage;
	/**
	 * Create the panel.
	 */
	public Login() 
	{
		
		this.setBorder(new EmptyBorder(5, 5, 5, 5));		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel northPanel = new JPanel();
		northPanel.setBackground(Color.WHITE);
		northPanel.setOpaque(false);
		this.add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panel_N1 = new JPanel();
		panel_N1.setOpaque(false);
		northPanel.add(panel_N1);
		panel_N1.setLayout(new BorderLayout(0, 0));
		
		
		
		JLabel lblNewLabel_111 = new JLabel(new ImageIcon(Login.class.getResource("/images/logoEnterprise_800_600.png")));
		
		panel_N1.add(lblNewLabel_111);
		lblNewLabel_111.setHorizontalAlignment(SwingConstants.LEFT);
		
		JPanel panel_N2 = new JPanel();
		panel_N2.setOpaque(false);
		northPanel.add(panel_N2);
		
		JPanel panel_N3 = new JPanel();
		panel_N3.setOpaque(false);
		northPanel.add(panel_N3);
		
		JLabel lblNewLabel_121 = new JLabel(new ImageIcon(Login.class.getResource("/images/logo_800_600.png")));
		panel_N3.add(lblNewLabel_121);
		lblNewLabel_121.setForeground(new Color(30, 144, 255));
		lblNewLabel_121.setBackground(new Color(1f, 1f, 1f, 0.7f));
		lblNewLabel_121.setFont(new Font("Snap ITC", Font.PLAIN, 25));
		
		JPanel centerPanel = new JPanel();
		centerPanel.setOpaque(false);
		centerPanel.setBackground(new Color(1f, 1f, 1f, 0.7f));
		this.add(centerPanel, BorderLayout.CENTER);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[] {200, 390, 200};
		gbl_centerPanel.rowHeights = new int[] {140, 30, 30, 140};
		gbl_centerPanel.columnWeights = new double[]{1.0, 1.0};
		gbl_centerPanel.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		JPanel panel_c1 = new JPanel();
		GridBagConstraints gbc_panel_c1 = new GridBagConstraints();
		panel_c1.setOpaque(false);
		gbc_panel_c1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_c1.fill = GridBagConstraints.BOTH;
		gbc_panel_c1.gridx = 1;
		gbc_panel_c1.gridy = 0;
		centerPanel.add(panel_c1, gbc_panel_c1);
		
		JPanel panel_c2 = new JPanel();
		panel_c2.setBorder(null);			
		panel_c2.setBackground(new Color(1f, 1f, 1f, 0.7f));
		GridBagConstraints gbc_panel_c2 = new GridBagConstraints();
		gbc_panel_c2.weighty = 1.0;
		gbc_panel_c2.weightx = 1.0;
		gbc_panel_c2.fill = GridBagConstraints.BOTH;
		gbc_panel_c2.gridx = 1;
		gbc_panel_c2.gridy = 1;
		centerPanel.add(panel_c2, gbc_panel_c2);
		panel_c2.setLayout(null);
		
		JLabel label_1 = new JLabel("Nom d'utilisateur :");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));
		label_1.setBounds(0, 5, 130, 30);
		panel_c2.add(label_1);
		
		username = new JTextField();
		username.setFont(new Font("Arial", Font.ITALIC, 18));
		username.setColumns(10);
		username.setBounds(130, 5, 250, 30);
		panel_c2.add(username);
		
		JPanel panel_c3 = new JPanel();
		panel_c3.setBorder(null);
		panel_c3.setBackground(new Color(1f, 1f, 1f, 0.7f));
		GridBagConstraints gbc_panel_c3 = new GridBagConstraints();
		gbc_panel_c3.weighty = 1.0;
		gbc_panel_c3.weightx = 1.0;
		gbc_panel_c3.fill = GridBagConstraints.BOTH;
		gbc_panel_c3.gridx = 1;
		gbc_panel_c3.gridy = 2;
		centerPanel.add(panel_c3, gbc_panel_c3);
		panel_c3.setLayout(null);
		
		JLabel label = new JLabel("Mot de passe :");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setFont(new Font("Tahoma", Font.BOLD, 14));
		label.setBounds(0, 5, 120, 30);
		panel_c3.add(label);
		
		password = new JPasswordField();
		password.setBounds(130, 5, 250, 30);
		panel_c3.add(password);
		
		JPanel panel_c4 = new JPanel();
		GridBagConstraints gbc_panel_c4 = new GridBagConstraints();
		panel_c4.setOpaque(false);
		gbc_panel_c4.insets = new Insets(0, 0, 0, 5);
		gbc_panel_c4.fill = GridBagConstraints.BOTH;
		gbc_panel_c4.gridx = 1;
		gbc_panel_c4.gridy = 3;
		centerPanel.add(panel_c4, gbc_panel_c4);
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(Color.WHITE);
		southPanel.setOpaque(false);
		this.add(southPanel, BorderLayout.SOUTH);
		southPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		connectButton = new JButton("Valider");
		southPanel.add(connectButton);
		connectButton.setMnemonic(KeyEvent.VK_ENTER);
		
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("images/background_800_600.png");
		try 
		{			
				this.backgroundImage = ImageIO.read(is);
			
		} 
		catch (IOException e) 
		{
		
			e.printStackTrace();
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.drawImage(backgroundImage, 0, 0, null);
	}

	/////////////************************    Getters and Setters *************************
	public JTextField getPassword() {
		return password;
	}


	public void setPassword(JPasswordField password) {
		this.password = password;
	}


	public JTextField getUsername() {
		return username;
	}


	public void setUsername(JTextField username) {
		this.username = username;
	}
	}
	
	

