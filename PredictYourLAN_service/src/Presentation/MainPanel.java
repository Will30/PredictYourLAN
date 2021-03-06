package Presentation;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JButton;


public class MainPanel extends JPanel {

	private Image backgroundImage;
	private JScrollPane panelInfo;
	private JTextArea info;
	
	public JButton testButton;
	public JButton btnActualisationSp;
	
	public MainPanel() 
	{
		setLayout(new BorderLayout(0, 0));
		
		JPanel panelNorth = new JPanel();
		panelNorth.setOpaque(false);
		add(panelNorth, BorderLayout.NORTH);
		panelNorth.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel panelN1 = new JPanel();
		panelN1.setOpaque(false);
		panelNorth.add(panelN1);
		
		JLabel labelLogoEnterprise = new JLabel(new ImageIcon(MainPanel.class.getResource("/images/logoEnterprise_800_600.png")));
		panelN1.add(labelLogoEnterprise);
		
		JPanel panelN2 = new JPanel();
		panelN2.setOpaque(false);
		panelNorth.add(panelN2);
		
		testButton = new JButton("test");
		testButton.setVisible(true); 		// for check, set visible to true
		panelN2.add(testButton);
				
		JPanel panelN3 = new JPanel();
		panelN3.setOpaque(false);
		panelNorth.add(panelN3);
		panelN3.setLayout(new BorderLayout(0, 0));
		
		JLabel labelLogoApplication =  new JLabel(new ImageIcon(MainPanel.class.getResource("/images/logo_800_600.png")));
		panelN3.add(labelLogoApplication);
		
		JPanel panelCenter = new JPanel();
		panelCenter.setOpaque(false);
		add(panelCenter, BorderLayout.CENTER);
		panelCenter.setLayout(new BorderLayout(20, 20));			
		
		info = new JTextArea(5, 40);		
		
		panelInfo  = new JScrollPane(info);
		panelCenter.add(panelInfo);		
		
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("images/background3.jpg");
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

	public JTextArea getInfo() {
		return info;
	}

	public void setInfo(JTextArea info) {
		this.info = info;
	}


	
}
