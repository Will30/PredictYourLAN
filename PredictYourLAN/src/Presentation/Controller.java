package Presentation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.border.BevelBorder;


import org.apache.commons.validator.routines.EmailValidator;


import Metier.Bug;
import Metier.Category;
import Metier.Clock;
import Metier.DataStorage;
import Metier.Equipment;
import Metier.Network;
import Metier.StrategicPoint;
import Metier.User;



@SuppressWarnings("serial")
public class Controller extends JFrame implements ActionListener,MouseListener
{	
	private Login LoginPanel;
	private CreateAccount createAccountPanel;
	private MainWindow MainPanel;
	private SecondaryWindow panelSP;
	private AddSP addElementPanel;
	private ManageAccount manageUserPanel;
	private Detail detailPanel;
	private Historic historicPanel;	
	
	protected User myUser;	
	
	private ArrayList<StrategicPoint> listSPs;	
	private ArrayList<ArrayList<StrategicPoint>> listSPsByCategorys;
	private ArrayList<Bug> listBugsIntoDatabase;	
	private ArrayList<Category> listCategorys; 
	
	private Equipment tempEquipment;
	private Network tempNetwork;
	private DataStorage tempDataStorage;
	private Category tempCategory;

	private ArrayList<User> listUsers;
	private byte idUser=0;
	
	public Dimension screenSize;
	public static int screenWidth;
	public static int screenHeight;
	
	protected  Timer timer;
	private Clock checkLEDclock;
	
	
	
	public Controller()
	{		
		this.setTitle("PredictYourLAN");	
		this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logoTaskbar.png")));	
		
		this.setResizable(false);		
		this.setMinimumSize(new Dimension(800, 600));
		this.setLocationRelativeTo(null);
		
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int) screenSize.getWidth();
		screenHeight = (int) screenSize.getHeight();
		
		listSPsByCategorys = new ArrayList<ArrayList<StrategicPoint>>(); 
		listBugsIntoDatabase = new ArrayList<Bug>();
		listCategorys = new ArrayList<Category>();		
		
		tempEquipment = new Equipment();
		tempNetwork  = new Network();
		tempDataStorage = new DataStorage();
		tempCategory = new Category();        

		this.LoginPanel = new Login();
		this.createAccountPanel = new CreateAccount();
		this.addElementPanel = new AddSP();
		this.manageUserPanel = new ManageAccount();
		this.detailPanel = new Detail();

		LoginPanel.connectButton.addActionListener(this);

		this.setContentPane(LoginPanel);		
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);	
	}
	

	

	

	/**
	 * Several listeners managed by controller. Panel is displayed according to event generated
	 * @param e : Action event performed 
	 */	
	public void actionPerformed(ActionEvent e) 
	{	
		
		
		/**
		 * Event for MainWindow
		 * @param e : Action when click on Login panel's connectButton 
		 */
		
		if (e.getSource() == this.LoginPanel.connectButton) 
		{
			
			byte tempID=0;			
			
			myUser = new User();			
			myUser.setUsername(this.LoginPanel.getUsername().getText());
			myUser.setPassword(this.LoginPanel.getPassword().getText());				
			
			tempID = myUser.login();
			
			if(tempID != 0)			
			{		
				this.setBounds(0, 0, screenWidth, screenHeight);
				
				this.setIdUser(tempID);					
				this.getContentPane().removeAll();					
					
				
				tempCategory.getAll(this.listCategorys);		
				
				//sort SPs by category
				for(int i=0;i<this.listCategorys.size();i++)
				{	
					this.listSPsByCategorys.add(new ArrayList<StrategicPoint>());
					
					tempEquipment.getByCategory(this.listSPsByCategorys.get(i),this.listCategorys.get(i));										  
					tempNetwork.getByCategory(this.listSPsByCategorys.get(i),this.listCategorys.get(i));
					tempDataStorage.getByCategory(this.listSPsByCategorys.get(i),this.listCategorys.get(i));	
					
				}
				
				
				this.getContentPane().removeAll();
				
				if(this.MainPanel == null)
				{
					System.out.println("Creating new MainWindow()");
					this.MainPanel = new MainWindow();	
					this.panelSP = new SecondaryWindow();	
	
					System.out.println("Screen size width :"+screenWidth);
					System.out.println("Screen size height:"+screenHeight);					
				}				
				
				this.MainPanel.getPanel_c22().addMouseListener(this);
				this.MainPanel.getPanel_c23().addMouseListener(this);
				this.MainPanel.getPanel_c24().addMouseListener(this);
				this.MainPanel.getPanel_c25().addMouseListener(this);
				this.MainPanel.getPanel_c26().addMouseListener(this);
				this.MainPanel.getPanel_c27().addMouseListener(this);
				this.MainPanel.getPanel_c28().addMouseListener(this);
				

				this.MainPanel.manageUserButton.addActionListener(this);
				this.MainPanel.addUserButton.addActionListener(this);
	
				this.MainPanel.displaySPs(this.listCategorys);
				
				this.setContentPane(this.MainPanel);
				this.validate();
				
				// delay initiated to 30 seconds to launch a new Strategic Points detection					
				this.timer = new Timer(30000, null);  
				this.timer.setRepeats(true);			
				
				this.timer.start();	
				this.timer.addActionListener(this);				
			}
			else
			{
				JOptionPane.showMessageDialog(this.LoginPanel,"Veuillez entrez un nom d'utilisateur et/ou mot de passe valide","Erreur de connexion",JOptionPane.ERROR_MESSAGE);			
			}
		}
		
		/** ***********************************************************              AddSP Panel            ******************************************************
		/**
		 * Event for CreateAccount panel
		 * @param e : Action when click on Main panel's AddUser button
		 */

		if (e.getSource() == this.MainPanel.addUserButton) 
		{
			System.out.println("Registering an account...");

			
			this.createAccountPanel = new CreateAccount();		
			
			this.createAccountPanel.buttonReturn.addActionListener(this);
			this.createAccountPanel.buttonOK.addActionListener(this);

			this.getContentPane().removeAll();
			
			this.setBounds(100, 100, 450, 300);
			this.setMinimumSize(new Dimension(800, 600));		
			
			this.setContentPane(this.createAccountPanel);
			this.validate();					
		}
	
		/**
		 * Event for save a new user into database
		 * @param e : Action when click on CreateAccount panel's ValidateButton 
		 */
		if (e.getSource() == this.createAccountPanel.buttonOK) 
		{
			User newUser = new User();	
			boolean MailOK=false;
			
			newUser.setUsername(this.createAccountPanel.getTxtUser().getText());
			newUser.setPassword(this.createAccountPanel.getTxtPassword().getText());
			newUser.setEmail(this.createAccountPanel.getTextMail().getText());
			
			// Minimum 4 characters , letters and digits required
			Pattern p = Pattern.compile("(?=(.*[a-zA-Z]))(?=(.*[0-9])).{4,}");
			Matcher m = p.matcher(this.createAccountPanel.getTxtPassword().getText());
			
			// EmailValidator is a free Apache package to check email address and others strings as date,ISBN,CreditCard.
			EmailValidator emailValidator = EmailValidator.getInstance();
			
			if(emailValidator.isValid(this.createAccountPanel.getTextMail().getText()))
			{
				MailOK = true;				
			}
			else
			{
				JOptionPane.showMessageDialog(this.createAccountPanel,"Votre adresse mail est invalide. \n Merci de la saisir à nouveau.","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
				this.createAccountPanel.getTextMail().setText("");	
			}	
				
			if((this.createAccountPanel.getTxtPassword().getText().equals(this.createAccountPanel.getTxtPsswordConfirmed().getText().toString()) == true) && MailOK == true)
			{
				if(m.matches() == true)
				{
					int confirmation = newUser.add();					
					if(confirmation == 0)
					{
						JOptionPane.showMessageDialog(this.createAccountPanel,"Votre compte a été créé. \nAprès vérification de vos informations, une email de confirmation vous sera envoyé.","Enregistrement terminé",JOptionPane.INFORMATION_MESSAGE);	
					}					
				}
				else
				{
					JOptionPane.showMessageDialog(this.createAccountPanel,"Votre mode de passe est invalide ! \n(4 caractères minimum - chiffre(s) ET lettre(s) obligatoires)","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
					this.createAccountPanel.getTxtPassword().setText("");
					this.createAccountPanel.getTxtPsswordConfirmed().setText("");
				}		
			}
			else
			{
				JOptionPane.showMessageDialog(this.createAccountPanel,"Votre mot de passe est différent. \n Merci de le saisir à nouveau.","Erreur de saisie",JOptionPane.ERROR_MESSAGE);
				this.createAccountPanel.getTxtPassword().setText("");
				this.createAccountPanel.getTxtPsswordConfirmed().setText("");
			}			
		}
		
		/**
		 * Event to return to MainPanel
		 * @param e : Action when click on CreateAccount panel's ReturnButton 
		 */
		if (e.getSource() == this.createAccountPanel.buttonReturn) 
		{
			this.setBounds(0, 0, screenWidth, screenHeight);			
							
			this.getContentPane().removeAll();					

			System.out.println("Creating new MainWindow()");
			this.MainPanel = new MainWindow();	

			this.MainPanel.getPanel_c22().addMouseListener(this);
			this.MainPanel.getPanel_c23().addMouseListener(this);
			this.MainPanel.getPanel_c24().addMouseListener(this);
			this.MainPanel.getPanel_c25().addMouseListener(this);
			this.MainPanel.getPanel_c26().addMouseListener(this);
			this.MainPanel.getPanel_c27().addMouseListener(this);
			this.MainPanel.getPanel_c28().addMouseListener(this);
			

			this.MainPanel.manageUserButton.addActionListener(this);
			this.MainPanel.addUserButton.addActionListener(this);

			this.MainPanel.displaySPs(this.listCategorys);
			
			this.setContentPane(this.MainPanel);
			this.validate();	
		}
		
		/** ***********************************************************              AddSP Panel            ******************************************************
		 * Event which displays AddSP window
		 * @param e : Action when click on  AddItem button from MainWindow
		 */
		if(this.panelSP != null)
		{
			if(e.getSource() == this.panelSP.addSPButton) 
			{
				
				this.addElementPanel = new AddSP();				

				addElementPanel.btnReturn.addActionListener(this);
				addElementPanel.btnValidate.addActionListener(this);

				this.addElementPanel.getComboBoxType().setVisible(true);
				this.addElementPanel.getTxtIpAddress2().setVisible(false);
				this.addElementPanel.getTxtDataStorage().setVisible(false);
				
				this.addElementPanel.displaySPs(this.listCategorys);

				this.getContentPane().removeAll();
				this.setContentPane(this.addElementPanel);
				this.validate();			
			} 				
		}
		
		/**
		 * Event which save strategic points into database
		 */
		if(e.getSource() == this.addElementPanel.btnValidate) 
		{	
			StrategicPoint newSP = null;			

			if(this.addElementPanel.radioButton3.isSelected() || this.addElementPanel.radioButton4.isSelected() || this.addElementPanel.radioButton5.isSelected() || this.addElementPanel.radioButton6.isSelected() || this.addElementPanel.radioButton7.isSelected())
			{
				newSP = new Equipment();
			}
			
			if(this.addElementPanel.radioButton2.isSelected())
			{				
				newSP = new DataStorage();
			}
			
			if(this.addElementPanel.radioButton1.isSelected())
			{
				newSP = new Network();
			}
			
     		System.out.println("***\n  Getting info for this new Strategic Point");
			System.out.println("name -->"+this.addElementPanel.getTxtSPName().getText());
			System.out.println("description -->"+this.addElementPanel.getTxtDescription().getText());
			System.out.println("IPAddress -->"+this.addElementPanel.getTxtIpAddress1().getText());
			System.out.println("UNC Image -->"+this.addElementPanel.getSelectedFile().getAbsolutePath());
	
			newSP.setName(this.addElementPanel.getTxtSPName().getText());
			newSP.setIPAddress(this.addElementPanel.getTxtIpAddress1().getText());
			newSP.setDescription(this.addElementPanel.getTxtDescription().getText());
			newSP.convertImageToBase64(this.addElementPanel.getSelectedFile());
			
	
			if(this.addElementPanel.radioButton3.isSelected() || this.addElementPanel.radioButton4.isSelected() || this.addElementPanel.radioButton5.isSelected() || this.addElementPanel.radioButton6.isSelected() || this.addElementPanel.radioButton7.isSelected())
			{
				System.out.println("type -->"+this.addElementPanel.getComboBoxType().getSelectedItem().toString());
				System.out.println("Modele -->"+this.addElementPanel.getComboBoxModel().getSelectedItem().toString());
				
				((Equipment) newSP).setType(this.addElementPanel.getComboBoxType().getSelectedItem().toString());
				((Equipment) newSP).setModel(this.addElementPanel.getComboBoxModel().getSelectedItem().toString());
				
				if(this.addElementPanel.radioButton3.isSelected())
				{
					for(int i=0;i<this.listCategorys.size();i++ )
					{
						if(this.addElementPanel.radioButton3.getText().equals(this.listCategorys.get(i).getName()))
						{
							newSP.getCategory().setID(this.listCategorys.get(i).getID());
							break;
						}
					}
						
				}
				if(this.addElementPanel.radioButton4.isSelected())
				{
					for(int i=0;i<this.listCategorys.size();i++ )
					{
						if(this.addElementPanel.radioButton4.getText().equals(this.listCategorys.get(i).getName()))
						{
							newSP.getCategory().setID(this.listCategorys.get(i).getID());
							break;
						}
					}					
				}
				if(this.addElementPanel.radioButton5.isSelected())
				{
					for(int i=0;i<this.listCategorys.size();i++ )
					{
						if(this.addElementPanel.radioButton5.getText().equals(this.listCategorys.get(i).getName()))
						{
							newSP.getCategory().setID(this.listCategorys.get(i).getID());
							break;
						}
					}	
					
				}
				if(this.addElementPanel.radioButton6.isSelected())
				{
					for(int i=0;i<this.listCategorys.size();i++ )
					{
						if(this.addElementPanel.radioButton6.getText().equals(this.listCategorys.get(i).getName()))
						{
							newSP.getCategory().setID(this.listCategorys.get(i).getID());
							break;
						}
					}	
					
				}
				if(this.addElementPanel.radioButton7.isSelected())
				{
					for(int i=0;i<this.listCategorys.size();i++ )
					{
						if(this.addElementPanel.radioButton7.getText().equals(this.listCategorys.get(i).getName()))
						{
							newSP.getCategory().setID(this.listCategorys.get(i).getID());
							break;
						}
					}						
				}
			}
	
			if(this.addElementPanel.radioButton2.isSelected())
			{		
				System.out.println("UNC -->"+this.addElementPanel.getTxtDataStorage().getText());
				((DataStorage) newSP).setUNC(this.addElementPanel.getTxtDataStorage().getText());
				
				for(int i=0;i<this.listCategorys.size();i++ )
				{
					if(this.addElementPanel.radioButton2.getText().equals(this.listCategorys.get(i).getName()))
					{
						newSP.getCategory().setID(this.listCategorys.get(i).getID());
						break;
					}
				}
			}

			if(this.addElementPanel.radioButton1.isSelected())
			{
				System.out.println("IPAddress2 -->"+this.addElementPanel.getTxtIpAddress2().getText());	
				((Network) newSP).setIPAddress2(this.addElementPanel.getTxtIpAddress2().getText());
				for(int i=0;i<this.listCategorys.size();i++ )
				{
					if(this.addElementPanel.radioButton1.getText().equals(this.listCategorys.get(i).getName()))
					{
						newSP.getCategory().setID(this.listCategorys.get(i).getID());
						break;
					}
				}
			}
	
			System.out.println("Categorie -->"+newSP.getCategory().getID());
			
			int confirmation = newSP.add();
			
			if(confirmation == 0)
			{
				JOptionPane.showMessageDialog(this.addElementPanel,"Le nouveau point stratégique a été enregistré avec succès","Enregistrement terminé",JOptionPane.INFORMATION_MESSAGE);	
			}	
		}

		
		/**
		 * Event when return button is pressed
		 * @param e : Action when click on each return button present on some panel
		 * @version 1.01 
		 * 
		 */
		

		if(e.getSource() == this.detailPanel.returnButton)
		{
			if(this.detailPanel.isStateButtonModifyClicked() == true)  // It means that modify button has been clicked

			{
				System.out.println("ID du modifié : "+this.detailPanel.getHiddenLabel().getText());
				
				for(int i=0;i<this.listSPs.size();i++)
				{
					if(this.detailPanel.getHiddenLabel().getText().equals(String.valueOf(listSPs.get(i).getID())))
					{
						listSPs.get(i).setName(this.detailPanel.getTextFieldName().getText());
						listSPs.get(i).setDescription(this.detailPanel.getTextFieldDescription().getText());
						listSPs.get(i).setIPAddress(this.detailPanel.getTextFieldIPAddress().getText());
						
						listSPs.get(i).convertImageToBase64(this.detailPanel.getSelectedFile()); // for update icon
						
						
						if(listSPs.get(i) instanceof Equipment)
						{
							((Equipment) listSPs.get(i)).setType(this.detailPanel.getTextFieldMoreInfo().getText());
							((Equipment) listSPs.get(i)).update();								
						}
						
						if(listSPs.get(i) instanceof Network)
						{
							((Network) listSPs.get(i)).setIPAddress2(this.detailPanel.getTextFieldMoreInfo().getText());	
							((Network) listSPs.get(i)).update();
						}
						
						if(listSPs.get(i) instanceof DataStorage)
						{
							((DataStorage) listSPs.get(i)).setUNC(this.detailPanel.getTextFieldMoreInfo().getText());	
							((DataStorage) listSPs.get(i)).update();
						}								
					}
				}
			}
			this.getContentPane().removeAll();	
			
			if(this.panelSP == null || this.panelSP.isDisplayable() == false)
			{
				System.out.println("Creating new SecondaryWindow()");
				this.panelSP = new SecondaryWindow();	
			}
			
			
			this.panelSP.getPanel_c22().addMouseListener(this);
			this.panelSP.getPanel_c23().addMouseListener(this);
			this.panelSP.getPanel_c24().addMouseListener(this);
			this.panelSP.getPanel_c25().addMouseListener(this);
			this.panelSP.getPanel_c26().addMouseListener(this);
			this.panelSP.getPanel_c27().addMouseListener(this);
			this.panelSP.getPanel_c28().addMouseListener(this);
			
			this.panelSP.addSPButton.addActionListener(this);
			this.panelSP.deleteSPButton.addActionListener(this);
			this.panelSP.returnButton.addActionListener(this);	
			
			this.panelSP.displaySPs(this.listSPs,this.panelSP.getIndexDisplay());	
			
			this.setContentPane(this.panelSP);
			this.validate();
		}
			
			

		

		if(e.getSource() == this.addElementPanel.btnReturn || e.getSource() == this.manageUserPanel.buttonReturn || e.getSource() == this.panelSP.returnButton ) 
		{	
			
			// In the ManageUser panel's case, an email is send automatically to new account activated
			if(e.getSource() == this.manageUserPanel.buttonReturn)
			{
				boolean mailSent = false;
		
				if(listUsers.size()>0)
				{
					for(int i=0;i<listUsers.size();i++)				
					{
						
						mailSent = false;
						if(listUsers.get(i).isAccountEnable() == 1 && listUsers.get(i).isMailReceived() == 0)
						{
							System.out.println("Before sendmail ()Name :"+listUsers.get(i).getUsername());
							mailSent = listUsers.get(i).sendActivationMail();							
							
							if(mailSent == true)
							{
								JOptionPane.showMessageDialog(this.manageUserPanel,"L'email de confirmation de création de compte a été envoyé à "+listUsers.get(i).getUsername(),"Mail envoyé",JOptionPane.INFORMATION_MESSAGE);
								listUsers.get(i).setMailReceived((byte)1);
								listUsers.get(i).updateAccount();	
							}
							else
							{
								JOptionPane.showMessageDialog(this.manageUserPanel,"Une erreur a été rencontrée lors de l'envoi du mail à "+listUsers.get(i).getUsername(),"Problème d'envoi du mail sur la création de compte",JOptionPane.ERROR_MESSAGE);	
							}
						}				
					}					
				}				
			}
			
			this.getContentPane().removeAll();	
			
			this.MainPanel = new MainWindow();
			
			this.MainPanel.getPanel_c22().addMouseListener(this);
			this.MainPanel.getPanel_c23().addMouseListener(this);
			this.MainPanel.getPanel_c24().addMouseListener(this);
			this.MainPanel.getPanel_c25().addMouseListener(this);
			this.MainPanel.getPanel_c26().addMouseListener(this);
			this.MainPanel.getPanel_c27().addMouseListener(this);
			this.MainPanel.getPanel_c28().addMouseListener(this);

			this.MainPanel.manageUserButton.addActionListener(this);
			this.MainPanel.addUserButton.addActionListener(this);

			this.setBounds(0, 0, screenWidth, screenHeight);		
			
			this.MainPanel.displaySPs(this.listCategorys);
			
			this.setContentPane(this.MainPanel);
			this.validate();
		}
			

	
		
		
		
		
		/**
		 * Event for deleting Strategic point
		 * @param e : Action when click on Main panel's deleteButton 
		 */
		
		if(e.getSource() == this.panelSP.deleteSPButton) 
		{
			ArrayList<StrategicPoint> tempListSP = new ArrayList<StrategicPoint>();
			
			String messageDetails = "";
			boolean SPChecked = false;			
			
			
			if(this.panelSP.getCheckBox_1().isSelected() == true)
			{
				tempListSP.add(listSPs.get(0+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(0+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_2().isSelected() == true)
			{
				tempListSP.add(listSPs.get(1+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(1+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_3().isSelected() == true)
			{
				tempListSP.add(listSPs.get(2+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(2+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_4().isSelected() == true)
			{
				tempListSP.add(listSPs.get(3+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(3+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_5().isSelected() == true)
			{
				tempListSP.add(listSPs.get(4+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(4+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_6().isSelected() == true)
			{
				tempListSP.add(listSPs.get(5+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(5+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			if(this.panelSP.getCheckBox_7().isSelected() == true)
			{
				tempListSP.add(listSPs.get(6+this.panelSP.getIndexDisplay()));
				messageDetails=messageDetails+listSPs.get(6+this.panelSP.getIndexDisplay()).getName()+"\n";
				SPChecked = true;
			}
			
			if(SPChecked == true)
			{
				int valueReturned = JOptionPane.showConfirmDialog(this,"Voulez-vous supprimer ce point stratégique ?\n"+messageDetails,"Confirmation de suppression",JOptionPane.YES_NO_OPTION);
				if(valueReturned == JOptionPane.OK_OPTION)
				{
					for(int i=0;i<tempListSP.size();i++)
					{
						System.out.println("Deleting strategic point id:"+tempListSP.get(i).getID());							
						
						for(int j=0;j<listSPs.size();j++)
						{
							if(tempListSP.get(i).getID() == listSPs.get(j+this.panelSP.getIndexDisplay()).getID())
							{
								listSPs.remove(j+this.panelSP.getIndexDisplay());
							}
						}
						
						tempListSP.get(i).delete();	
						this.panelSP.getCheckBox_1().setSelected(false);
						this.panelSP.getCheckBox_2().setSelected(false);
						this.panelSP.getCheckBox_3().setSelected(false);
						this.panelSP.getCheckBox_4().setSelected(false);
						this.panelSP.getCheckBox_5().setSelected(false);
						this.panelSP.getCheckBox_6().setSelected(false);
						this.panelSP.getCheckBox_7().setSelected(false);		
					}
					
				}
			}
			else
			{
				JOptionPane.showMessageDialog(this,"Aucun point stratégique n'a été sélectionné","Choisir un point stratégique",JOptionPane.INFORMATION_MESSAGE);
			}

			this.panelSP.displaySPs(listSPs,this.panelSP.getIndexDisplay());
		}
	
		
		/**
		 * Event for ManageAccount panel
		 * @param e : Action when click on Main panel's connectButton 
		 */
		
		if(e.getSource() == this.MainPanel.manageUserButton) 
		{			
			this.manageUserPanel = new ManageAccount();
			User tempUser = new User();
			
			this.setBounds(800, 600, 450, 300);
			this.setLocationRelativeTo(null);
			
			this.manageUserPanel.buttonOK.addActionListener(this);
			this.manageUserPanel.buttonReturn.addActionListener(this);
			this.manageUserPanel.getcomboBoxUser().addActionListener(this);
			
			listUsers = new ArrayList<User>();	
			
			tempUser.getAllUsers(listUsers);

			if(listUsers.size()>0)
			{
				// Fulfill user list combobox
				for(int i=0;i<listUsers.size();i++)				
				{
					this.manageUserPanel.getcomboBoxUser().addItem(listUsers.get(i).getUsername());						
				}
				
				// updating display for the first users which appears on the list
				if(listUsers.get(0).isAccountEnable() == 1)
				{
					this.manageUserPanel.setCheckboxAccountState(true);						
				}
				else
				{
					this.manageUserPanel.setCheckboxAccountState(false);	
				}
			}

			this.getContentPane().removeAll();					
			
			this.setContentPane(this.manageUserPanel);
			this.validate();			
		}
		
		
		// update displays when you select another user from the list
		if(e.getSource() == this.manageUserPanel.getcomboBoxUser()) 
		{			
			for(int i=0;i<listUsers.size();i++)				
			{
				if(this.manageUserPanel.getcomboBoxUser().getSelectedItem().toString() == listUsers.get(i).getUsername())
				{
					if(listUsers.get(i).isAccountEnable() == 1)
					{
						this.manageUserPanel.setCheckboxAccountState(true);						
					}
					else
					{
						this.manageUserPanel.setCheckboxAccountState(false);	
					}		
					
					if(listUsers.get(i).isAdminAccess() == 1)
					{
						this.manageUserPanel.setCheckBoxAdminAccess(true);
					}
					else
					{
						this.manageUserPanel.setCheckBoxAdminAccess(false);
					}
				}										
			}							
		}
		
		
		if(e.getSource() == this.manageUserPanel.buttonOK) 
		{			
			int stateUpdate = 1;
			int i = 0;
			
			for(i=0;i<listUsers.size();i++)				
			{
				if(this.manageUserPanel.getcomboBoxUser().getSelectedItem().toString() == listUsers.get(i).getUsername())
				{
					if(this.manageUserPanel.getCheckboxAccountState().isSelected() == false)
					{
						listUsers.get(i).setAccountEnable((byte) 0); 
					}
					else
					{
						listUsers.get(i).setAccountEnable((byte) 1); 
					}
					
					if(this.manageUserPanel.getCheckBoxAdminAccess().isSelected() == false)
					{
						listUsers.get(i).setAdminAccess((byte) 0); 
					}
					else
					{
						listUsers.get(i).setAdminAccess((byte) 1); 
					}
					stateUpdate = listUsers.get(i).updateAccount();
					break;
				}										
			}
			
			if(stateUpdate == 0)
			{
				JOptionPane.showMessageDialog(this.manageUserPanel,"Le compte de "+listUsers.get(i).getUsername()+" a été mis à jour avec succès","Mise à jour terminée",JOptionPane.INFORMATION_MESSAGE);	
			}	
			else
			{
				JOptionPane.showMessageDialog(this.manageUserPanel,"Une erreur a été rencontrée lors de la mise à jour du compte","Erreur lors de la mise à jour",JOptionPane.ERROR_MESSAGE);		
			}
		}
		
		
		/******************************************************************************************************************************************************
		
		/**
		 * Event for historic panel
		 * @param e historic button present on detail panel
		 */
	
		if(this.detailPanel != null)
		{
			if(e.getSource() == this.detailPanel.historicButton) 
			{
				this.historicPanel = new Historic();
				
				this.historicPanel.returnButton.addActionListener(this);
				
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
				Date date = new Date();
				Date dateLastWeek = new Date();			

				System.out.println("Date in milliseconds: "+date.getTime());
				System.out.println("Corresponds to : "+dateFormat.format(date));
				
				dateLastWeek.setTime((date.getTime()) - Long.parseLong("604800000")); 
				System.out.println("Same date with seven days less  : "+dateLastWeek.getTime());
				System.out.println("Corresponds to  : "+dateFormat2.format(dateLastWeek));
				
				for(int i=0;i <this.listSPs.size();i++)
				{
					if(Integer.parseInt(this.detailPanel.getHiddenLabel().getText()) == this.listSPs.get(i).getID())
					{
						// By default, historic panel displays the chart of the last week.
						this.historicPanel.displaySPs(this.listSPs.get(i),dateFormat2.format(dateLastWeek), dateFormat.format(date));
					}
				}				
				
				this.getContentPane().removeAll();
				this.setContentPane(this.historicPanel);
				this.validate();
			}			
		}
		
		if(this.historicPanel != null)
		{
			if(e.getSource() == this.historicPanel.returnButton) 
			{
				this.detailPanel = new Detail();
				this.detailPanel.modifyButton.addActionListener(this);
				this.detailPanel.historicButton.addActionListener(this);
				this.detailPanel.returnButton.addActionListener(this);

				this.getContentPane().removeAll();
				this.setContentPane(this.detailPanel);
				this.detailPanel.displayDetail(this.historicPanel.getThisSP());
				
				this.validate();			
			}
		}
		
		
		
		
		/** **********************************************************
		 * Lister for timer - it will check if an LED color has been updated
		 *************************************************************/
		
		
		if (e.getSource() == this.timer) 
		{
			this.checkLEDclock = new Clock(MainPanel,panelSP,this.listCategorys,this.listSPsByCategorys);
			this.checkLEDclock.execute();
			
		}		
	}
	
	
	/////////////*************************************************    MouseListener   **************************************************
	@Override
	public void mouseClicked(MouseEvent arg0) 
	{	
		System.out.println("Mouse clicked");

		
		this.getContentPane().removeAll();			
			
		if(this.panelSP.isShowing() == false)
		{
			System.out.println("Creating new SecondaryWindow()");
			this.panelSP = new SecondaryWindow();	
			
			this.panelSP.getPanel_c22().addMouseListener(this);
			this.panelSP.getPanel_c23().addMouseListener(this);
			this.panelSP.getPanel_c24().addMouseListener(this);
			this.panelSP.getPanel_c25().addMouseListener(this);
			this.panelSP.getPanel_c26().addMouseListener(this);
			this.panelSP.getPanel_c27().addMouseListener(this);
			this.panelSP.getPanel_c28().addMouseListener(this);
			
			this.panelSP.addSPButton.addActionListener(this);
			this.panelSP.deleteSPButton.addActionListener(this);
			this.panelSP.returnButton.addActionListener(this);	
		}				
	
		
		int i=0;
		if(arg0.getSource() == this.MainPanel.getPanel_c22())
		{	
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);					
		}			
		if(arg0.getSource() == this.panelSP.getPanel_c22())
		{	
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
			
			this.setContentPane(this.detailPanel);			
		}			
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c23())
		{
			this.listSPs = this.listSPsByCategorys.get(i);			
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);				
		}		
		if(arg0.getSource() == this.panelSP.getPanel_c23())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
		
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c24())
		{				
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);					
		}	
		if(arg0.getSource() == this.panelSP.getPanel_c24())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
		
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c25())
		{			
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);					
		}	
		if(arg0.getSource() == this.panelSP.getPanel_c25())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
		
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c26())
		{			
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);
		}	
		if(arg0.getSource() == this.panelSP.getPanel_c26())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
		
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c27())
		{				
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);			
		}	
		if(arg0.getSource() == this.panelSP.getPanel_c27())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
			
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		i++;
		if(arg0.getSource() == this.MainPanel.getPanel_c28())
		{		
			this.listSPs = this.listSPsByCategorys.get(i);
			this.panelSP.displaySPs(this.listSPsByCategorys.get(i),this.panelSP.getIndexDisplay());				
			this.setContentPane(this.panelSP);
		}
		if(arg0.getSource() == this.panelSP.getPanel_c28())
		{		
			if(this.detailPanel.isShowing() == false)
			{
				this.detailPanel = new Detail();
			}
			
			this.listSPs.get(i).getBugForThisSP();				
			this.detailPanel.displayDetail(this.listSPs.get(i+this.panelSP.getIndexDisplay()));
		
			this.detailPanel.modifyButton.addActionListener(this);
			this.detailPanel.historicButton.addActionListener(this);
			this.detailPanel.returnButton.addActionListener(this);
			
			this.setContentPane(this.detailPanel);		
		}
		
		
		this.validate();
	}






	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
	//	System.out.println("Mouse entered");		

		if(this.MainPanel.isDisplayable()  == true && this.MainPanel != null)
		{
			if(arg0.getSource() == this.MainPanel.getPanel_c22())
			{
				this.MainPanel.getPanel_c221().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));									
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c23())
			{
				this.MainPanel.getPanel_c231().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c24())
			{				
				this.MainPanel.getPanel_c241().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c25())
			{			
				this.MainPanel.getPanel_c251().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c26())
			{			
				this.MainPanel.getPanel_c261().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c27())
			{				
				this.MainPanel.getPanel_c271().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c28())
			{
				this.MainPanel.getPanel_c281().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
		}
		
		if(this.getContentPane() == this.panelSP)
		{
			if(arg0.getSource() == this.panelSP.getPanel_c22())
			{
				this.panelSP.getPanel_c221().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));									
			}
			if(arg0.getSource() == this.panelSP.getPanel_c23())
			{
				this.panelSP.getPanel_c231().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
			if(arg0.getSource() == this.panelSP.getPanel_c24())
			{				
				this.panelSP.getPanel_c241().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c25())
			{			
				this.panelSP.getPanel_c251().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c26())
			{			
				this.panelSP.getPanel_c261().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
			if(arg0.getSource() == this.panelSP.getPanel_c27())
			{				
				this.panelSP.getPanel_c271().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c28())
			{
				this.panelSP.getPanel_c281().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0), new Color(255, 0, 0)));
			}
		}

	}






	@Override
	public void mouseExited(MouseEvent arg0) 
	{		
		if(this.MainPanel.isEnabled() == true && this.MainPanel != null)
		{
			if(arg0.getSource() == this.MainPanel.getPanel_c22())
			{
				this.MainPanel.getPanel_c221().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));									
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c23())
			{
				this.MainPanel.getPanel_c231().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c24())
			{				
				this.MainPanel.getPanel_c241().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c25())
			{			
				this.MainPanel.getPanel_c251().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c26())
			{			
				this.MainPanel.getPanel_c261().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c27())
			{				
				this.MainPanel.getPanel_c271().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c28())
			{
				this.MainPanel.getPanel_c281().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
		}
			
		if(this.getContentPane() == this.panelSP)
		{
			if(arg0.getSource() == this.panelSP.getPanel_c22())
			{
				this.panelSP.getPanel_c221().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));									
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c23())
			{
				this.panelSP.getPanel_c231().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c24())
			{				
				this.panelSP.getPanel_c241().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.panelSP.getPanel_c25())
			{			
				this.panelSP.getPanel_c251().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.panelSP.getPanel_c26())
			{			
				this.panelSP.getPanel_c261().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}
			if(arg0.getSource() == this.panelSP.getPanel_c27())
			{				
				this.panelSP.getPanel_c271().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c28())
			{
				this.panelSP.getPanel_c281().setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0), new Color(0, 0, 0)));
			}			
		}
		
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) 
	{
		
		if(this.MainPanel.isEnabled()  == true && this.MainPanel != null)
		{
			if(arg0.getSource() == this.MainPanel.getPanel_c22())
			{
				this.MainPanel.getPanel_c221().setBackground(new Color(102, 153, 204));								
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c23())
			{
				this.MainPanel.getPanel_c231().setBackground(new Color(102, 153, 204));		
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c24())
			{				
				this.MainPanel.getPanel_c241().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c25())
			{			
				this.MainPanel.getPanel_c251().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c26())
			{			
				this.MainPanel.getPanel_c261().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c27())
			{				
				this.MainPanel.getPanel_c271().setBackground(new Color(102, 153, 204));		
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c28())
			{
				this.MainPanel.getPanel_c281().setBackground(new Color(102, 153, 204));		
			}
		}
			
		if(this.getContentPane() == this.panelSP)
		{
			if(arg0.getSource() == this.panelSP.getPanel_c22())
			{
				this.panelSP.getPanel_c221().setBackground(new Color(102, 153, 204));									
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c23())
			{
				this.panelSP.getPanel_c231().setBackground(new Color(102, 153, 204));		
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c24())
			{				
				this.panelSP.getPanel_c241().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.panelSP.getPanel_c25())
			{			
				this.panelSP.getPanel_c251().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.panelSP.getPanel_c26())
			{			
				this.panelSP.getPanel_c261().setBackground(new Color(102, 153, 204));		
			}
			if(arg0.getSource() == this.panelSP.getPanel_c27())
			{				
				this.panelSP.getPanel_c271().setBackground(new Color(102, 153, 204));		
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c28())
			{
				this.panelSP.getPanel_c281().setBackground(new Color(102, 153, 204));		
			}			
		}
		
	}






	@Override
	public void mouseReleased(MouseEvent arg0) 
	{
		if(this.MainPanel.isEnabled()  == true && this.MainPanel != null)
		{
			if(arg0.getSource() == this.MainPanel.getPanel_c22())
			{
				this.MainPanel.getPanel_c221().setBackground(Color.WHITE);			
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c23())
			{
				this.MainPanel.getPanel_c231().setBackground(Color.WHITE);
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c24())
			{				
				this.MainPanel.getPanel_c241().setBackground(Color.WHITE);
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c25())
			{			
				this.MainPanel.getPanel_c251().setBackground(Color.WHITE);	
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c26())
			{			
				this.MainPanel.getPanel_c261().setBackground(Color.WHITE);	
			}
			if(arg0.getSource() == this.MainPanel.getPanel_c27())
			{				
				this.MainPanel.getPanel_c271().setBackground(Color.WHITE);	
			}	
			if(arg0.getSource() == this.MainPanel.getPanel_c28())
			{
				this.MainPanel.getPanel_c281().setBackground(Color.WHITE);	
			}
		}
			
		if(this.getContentPane() == this.panelSP)
		{
			if(arg0.getSource() == this.panelSP.getPanel_c22())
			{
				this.panelSP.getPanel_c221().setBackground(Color.WHITE);									
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c23())
			{
				this.panelSP.getPanel_c231().setBackground(Color.WHITE);	
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c24())
			{				
				this.panelSP.getPanel_c241().setBackground(Color.WHITE);		
			}
			if(arg0.getSource() == this.panelSP.getPanel_c25())
			{			
				this.panelSP.getPanel_c251().setBackground(Color.WHITE);	
			}
			if(arg0.getSource() == this.panelSP.getPanel_c26())
			{			
				this.panelSP.getPanel_c261().setBackground(Color.WHITE);	
			}
			if(arg0.getSource() == this.panelSP.getPanel_c27())
			{				
				this.panelSP.getPanel_c271().setBackground(Color.WHITE);	
			}	
			if(arg0.getSource() == this.panelSP.getPanel_c28())
			{
				this.panelSP.getPanel_c281().setBackground(Color.WHITE);
			}			
		}
		
	}

	/////////////************************    Getters and Setters *************************
	public byte getIdUser() {
		return idUser;
	}

	public void setIdUser(byte idUser) {
		this.idUser = idUser;
	}



	public MainWindow getMainPanel() {
		return MainPanel;
	}



	public void setMainPanel(MainWindow mainPanel) {
		MainPanel = mainPanel;
	}



	public ArrayList<StrategicPoint> getListSPs() {
		return listSPs;
	}



	public void setListSPs(ArrayList<StrategicPoint> listSPs) {
		this.listSPs = listSPs;
	}



	public ArrayList<Bug> getListBugsIntoDatabase() {
		return listBugsIntoDatabase;
	}



	public void setListBugsIntoDatabase(ArrayList<Bug> listBugsIntoDatabase) {
		this.listBugsIntoDatabase = listBugsIntoDatabase;
	}










	

}
