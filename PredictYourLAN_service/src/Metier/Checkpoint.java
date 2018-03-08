package Metier;


import java.awt.Color;
import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.SwingWorker;

import org.apache.commons.net.ftp.FTPClient;




/**
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.04
 * Class witch launch a detection on each strategic point, and detect if bug is present. It is started in new thread, in background process.
 */
public class Checkpoint extends SwingWorker<Object ,String>
{

	private Category category;
	private ArrayList<StrategicPoint> listSPs;	
	private ArrayList<Bug> listBugsIntoDatabase;	
	private Presentation.MainPanel MainPanel;
	private Color color;
	private FTPClient ftpClient;
	private boolean checkFTPMeteoFrance;
	private ArrayList<User> listAdminUsers;	
	
	
	/**
	 * @param category category name checked
	 * @param listSPs strategic point list
	 * @param listBugsIntoDatabase List of all bugs in the database
	 * @param MainPanel panel display which needs to be updated
	 * @param color actually color name
	 * @param ftpClient instance of FTPClient class. Initiated when application starts
	 * @param checkFTPMeteoFrance when application starts, password is required to connect with Meteo France FTP server. True if password is correct, otherwise false
	 * @param listAdminUsers list of users who are administrator. This is for sending alert mails
	 * @since 1.00
	 */
	public Checkpoint(Category category,ArrayList<StrategicPoint> listSPs, ArrayList<Bug> listBugsIntoDatabase,Presentation.MainPanel MainPanel, Color color, FTPClient ftpClient, boolean checkFTPMeteoFrance, ArrayList<User> listAdminUsers)
	{       
		this.category = category ;
		this.listSPs = listSPs;
		this.listBugsIntoDatabase = listBugsIntoDatabase;
		this.MainPanel = MainPanel;
		this.color = color ;
		this.ftpClient = ftpClient;
		this.checkFTPMeteoFrance = checkFTPMeteoFrance;
		this.listAdminUsers = listAdminUsers;
	}


	@Override
	public Object doInBackground() 
	{
		Calendar cal = Calendar.getInstance();
	    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");	   
	    Bug tempBug = new Bug();
	    ArrayList<Bug> tempListBug = new ArrayList<Bug>();
	    File f = null;
		
		f = new File ("D:\\MonitorYourLAN\\log\\"+sdf.format(cal.getTime()).substring(6,10)+"\\"+sdf.format(cal.getTime()).substring(3,5)+"\\"+sdf.format(cal.getTime()).substring(0,2)+"\\log.txt");
		if(f.exists() == false)
		{
			// Creating new directory, with log file
			new File ("D:\\MonitorYourLAN\\log\\"+sdf.format(cal.getTime()).substring(6,10)+"\\"+sdf.format(cal.getTime()).substring(3,5)+"\\"+sdf.format(cal.getTime()).substring(0,2)).mkdirs();	
		}		  
		
		// updating log file
		try 
		{
			FileWriter fw = new FileWriter (f,true);
			fw.write("\r\n"+sdf.format(cal.getTime())+"       Détection en cours de la catégorie  "+this.category.getName().toUpperCase());
			fw.close();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		
		int oldLedColorID = 0;
		
		for (int i = 0; i < listSPs.size(); i++) 
		{			
			System.out.println("************************************ Starting detection for "+this.listSPs.get(i).getName());		
			System.out.println("Ancienne couleur: "+listSPs.get(i).getLed().getColor());   //For check
			
			if(this.listSPs.get(i).getIPAddress().equals("x.x.x.x") && this.checkFTPMeteoFrance == false)
			{
				continue;
			}
			
			oldLedColorID = listSPs.get(i).getLed().getID();
	
			// updating log file
			try 
			{
				FileWriter fw = new FileWriter (f,true);
				fw.write("\r\nNom du point stratégique -->"+listSPs.get(i).getName());
				fw.write("\r\nCouleur voyant actuel -->"+listSPs.get(i).getLed().getColor());
				fw.close();
			} 
			catch (IOException e) 
			{			
				e.printStackTrace();
			}
			
			if(listSPs.get(i) instanceof Equipment)
			{				
				((Equipment) listSPs.get(i)).detectSP(this.listBugsIntoDatabase);
				
				// Launch a diagnostic to check more element inside an equipment
				if(listSPs.get(i).getLed().getID() != 1) 				// we need to have a communication with strategic point (1 means no communication), equals to black color
				{	
					if(((Equipment) listSPs.get(i)).getListOID().size() > 0)
					{							
						((Equipment) listSPs.get(i)).diagnose();
						((Equipment) listSPs.get(i)).checkAlert(this.listBugsIntoDatabase);
						
						if(listSPs.get(i).getLed().getID() != oldLedColorID && listSPs.get(i).getListBug().isEmpty() == false)
						{
							this.MainPanel.getInfo().setForeground(this.color);
							this.MainPanel.getInfo().setText(sdf.format(cal.getTime())+"       Détection en cours de la catégorie  "+this.category.getName().toUpperCase()+"\n"+this.MainPanel.getInfo().getText());						
							this.MainPanel.getInfo().setText("                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail()+"\n"+this.MainPanel.getInfo().getText());
							this.MainPanel.getInfo().setText( "Nom du point stratégique -->"+listSPs.get(i).getName() +"\n"+this.MainPanel.getInfo().getText());							
							this.MainPanel.getInfo().setText("Nouveau voyant -->"+listSPs.get(i).getLed().getColor()+"\n"+this.MainPanel.getInfo().getText());
							
							// updating log file
							try 
							{
								FileWriter fw = new FileWriter (f,true);
								fw.write("\r\n                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail());
								fw.close();
							} 
							catch (IOException e) 
							{			
								e.printStackTrace();
							}
						}
					}						
				}	
			}
			
			
			if(listSPs.get(i) instanceof Network)
			{
				System.out.println("Network ");   //For check
				((Network) listSPs.get(i)).detectSP(this.listBugsIntoDatabase);
				
				if(listSPs.get(i).getLed().getID() != oldLedColorID && listSPs.get(i).getListBug().isEmpty() == false)
				{
					this.MainPanel.getInfo().setForeground(this.color);
					this.MainPanel.getInfo().setText(sdf.format(cal.getTime())+"       Détection en cours de la catégorie  "+this.category.getName().toUpperCase()+"\n"+this.MainPanel.getInfo().getText());						
					this.MainPanel.getInfo().setText("                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail()+"\n"+this.MainPanel.getInfo().getText());
					this.MainPanel.getInfo().setText( "Nom du point stratégique -->"+listSPs.get(i).getName() +"\n"+this.MainPanel.getInfo().getText());							
					this.MainPanel.getInfo().setText("Nouveau voyant -->"+listSPs.get(i).getLed().getColor()+"\n"+this.MainPanel.getInfo().getText());
					
					// updating log file
					try 
					{
						FileWriter fw = new FileWriter (f,true);
						fw.write("\r\n                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail());
						fw.close();
					} 
					catch (IOException e) 
					{			
						e.printStackTrace();
					}
				}
			}
			
			if(listSPs.get(i) instanceof DataStorage)
			{			
				((DataStorage) listSPs.get(i)).detectSP(this.listBugsIntoDatabase);
				((DataStorage) listSPs.get(i)).diagnose(this.ftpClient,this.listBugsIntoDatabase);
				((DataStorage) listSPs.get(i)).checkAlert(this.listBugsIntoDatabase);
				((DataStorage) listSPs.get(i)).updateLastFile();				
				
				if(listSPs.get(i).getLed().getID() != oldLedColorID && listSPs.get(i).getListBug().isEmpty() == false)
				{
					this.MainPanel.getInfo().setForeground(this.color);
					this.MainPanel.getInfo().setText(sdf.format(cal.getTime())+"       Détection en cours de la catégorie  "+this.category.getName().toUpperCase()+"\n"+this.MainPanel.getInfo().getText());						
					this.MainPanel.getInfo().setText("                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail()+"\n"+this.MainPanel.getInfo().getText());
					this.MainPanel.getInfo().setText( "Nom du point stratégique -->"+listSPs.get(i).getName() +"\n"+this.MainPanel.getInfo().getText());							
					this.MainPanel.getInfo().setText("Nouveau voyant -->"+listSPs.get(i).getLed().getColor()+"\n"+this.MainPanel.getInfo().getText());
					
					// updating log file
					try 
					{
						FileWriter fw = new FileWriter (f,true);
						fw.write("\r\n                        ***** "+sdf.format(cal.getTime())+"             ***** Bug trouvé :"+listSPs.get(i).getListBug().get(0).getDetail());
						fw.close();
					} 
					catch (IOException e) 
					{			
						e.printStackTrace();
					}
				}
			}

			
			
			// ******************** UPDATING Database
			// If after new detection, Led color has changed , an update of this will starting
			if(listSPs.get(i).getLed().getID() != oldLedColorID)
			{		
				// Updating color for SP
				switch(listSPs.get(i).getLed().getID())
				{
					case 1: listSPs.get(i).getLed().setColor("black");
					break;
					case 2: listSPs.get(i).getLed().setColor("red");
					break;
					case 3: listSPs.get(i).getLed().setColor("orange");
					break;
					case 4: listSPs.get(i).getLed().setColor("green");
					break;
					default: listSPs.get(i).getLed().setColor("black");
					break;				
				}
				
				System.out.println("Changement de couleur "+listSPs.get(i).getLed().getColor());   

				
				// updating log file
				try 
				{
					FileWriter fw = new FileWriter (f,true);
					fw.write("\r\n CHANGEMENT couleur du voyant -->"+listSPs.get(i).getLed().getColor());
					fw.close();
				} 
				catch (IOException e) 
				{			
					e.printStackTrace();
				}
				
				if(listSPs.get(i) instanceof Equipment)
				{
					((Equipment) listSPs.get(i)).update();					
					if(listSPs.get(i).getListBug().isEmpty() == false)   // if led color switch on green color
					{
						// Check if bug for this StrategicPoint is running
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();

						// Get active bug about this SP into database
						tempBug.getBugActiveForThisSP(tempListBug,this.listSPs.get(i).getID());
						
						// if no bug running or if bug led color found is more higher than the older, create new one
						if(tempListBug.size()==0 || tempListBug.get(0).getIDcolor()>this.listSPs.get(i).getListBug().get(0).getIDcolor())
						{

							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								
								switch(this.listSPs.get(i).getListBug().get(0).getIDcolor())
								{
									case 3:	mail.setSubject("MonitorYourLAN - Alerte minimimale sur "+this.listSPs.get(i).getName());
									break;
									case 2:	mail.setSubject("MonitorYourLAN - Alerte maximale sur "+this.listSPs.get(i).getName());
									break;
									case 1:	mail.setSubject("MonitorYourLAN - PROBLEME DE COMMUNICATION AVEC "+this.listSPs.get(i).getName());
									break;	
									default: mail.setSubject("MonitorYourLAN - Alerte déclenchée sur "+this.listSPs.get(i).getName());
									break;
								
								}
								
								mail.setEmailBody("Description du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+this.listSPs.get(i).getListBug().get(0).getDetail()+"\n\rSolution possible : "+this.listSPs.get(i).getListBug().get(0).getListSolution().get(0).getDescription());
								mail.start();								
							}					

							tempBug.update(0, listSPs.get(i).getID(), dateFormat.format(date)); // Set inactive if there was an old bug 
							
							dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
							((Equipment) listSPs.get(i)).createBugIntoDatabase();				// create new one
						}						
					}	
					else
					{
						// it means that color switch on green color == no default active
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();
						
						// Get active bug about this SP into database
						tempBug.getBugActiveForThisSP(tempListBug,this.listSPs.get(i).getID());
						
						// check if a bug is active about it
						if(tempListBug.size()>0)
						{					
							tempBug.update(0, this.listSPs.get(i).getID(),dateFormat.format(date)); 
							
							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail de fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								mail.setSubject("MontorYourLAN - Fin d'Alerte pour "+this.listSPs.get(i).getName());
								mail.setEmailBody("Le point stratégique est dans un état stable.\n\nDate de fin du BUG : "+dateFormat.format(date)+"Description du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+tempListBug.get(0).getDetail()+"\n\rSolution possible : "+tempListBug.get(0).getListSolution().get(0).getDescription());
								mail.start();									
							}					
						}					
					}
				}
				
				if(listSPs.get(i) instanceof Network)
				{
					((Network) listSPs.get(i)).update();
					if(listSPs.get(i).getListBug().isEmpty() == false)   // if led color switch on green color
					{
						// Check if bug for this StrategicPoint is running
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();						
					
						tempBug.getBugDuringPeriod(tempListBug, this.listSPs.get(i).getID() , dateFormat.format(date), dateFormat.format(date.getTime()  + ( Long.parseLong("86400000")))); // one day interval
						
						// if no bug running, create new one
						if(tempListBug.size()==0 || tempListBug.get(0).getIDcolor()>=this.listSPs.get(i).getListBug().get(0).getIDcolor())
						{			
							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								
								switch(this.listSPs.get(i).getListBug().get(0).getIDcolor())
								{
									case 3:	mail.setSubject("PredictYourLAN - Alerte minimimale sur "+this.listSPs.get(i).getName());
									break;
									case 2:	mail.setSubject("PredictYourLAN - Alerte maximale sur "+this.listSPs.get(i).getName());
									break;
									case 1:	mail.setSubject("PredictYourLAN - PROBLEME DE COMMUNICATION AVEC "+this.listSPs.get(i).getName());
									break;	
									default: mail.setSubject("PredictYourLAN - Alerte déclenchée sur "+this.listSPs.get(i).getName());
									break;
								
								}
								
								mail.setEmailBody("Description du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+this.listSPs.get(i).getListBug().get(0).getDetail()+"\n\rSolution possible : "+this.listSPs.get(i).getListBug().get(0).getListSolution().get(0).getDescription());
								mail.start();								
							}

							tempBug.update(0, listSPs.get(i).getID(), dateFormat.format(date)); // Set inactive if there was an old bug 
							
							dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
							((Network) listSPs.get(i)).createBugIntoDatabase();				// create new one
						}						
					}	
					
					else
					{
						// it means that color switch on green color == no default active
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();						
						
						// Get active bug about this SP into database
						tempBug.getBugActiveForThisSP(tempListBug,this.listSPs.get(i).getID());
						
						// check if a bug is active about it
						if(tempListBug.size()>0)
						{					
							tempBug.update(0, this.listSPs.get(i).getID(),dateFormat.format(date)); 
							
							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail de fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								mail.setSubject("MonitorYourLAN - Fin d'Alerte pour "+this.listSPs.get(i).getName());
								mail.setEmailBody("Le point stratégique est dans un état stable.\n\nDate de fin du BUG : "+dateFormat.format(date)+"\n\rDescription du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+tempListBug.get(0).getDetail()+"\n\rSolution possible : "+tempListBug.get(0).getListSolution().get(0).getDescription());
								mail.start();									
							}					
						}	
					}
				}
				
				if(listSPs.get(i) instanceof DataStorage)
				{
					((DataStorage) listSPs.get(i)).update();
					if(listSPs.get(i).getListBug().isEmpty() == false)   // if led color switch on green color
					{
						// Check if bug for this StrategicPoint is running
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();
						
						tempBug.getBugDuringPeriod(tempListBug, this.listSPs.get(i).getID() , dateFormat.format(date), dateFormat.format(date.getTime()  + ( Long.parseLong("86400000")))); // one day interval
						
						// if no bug running, create new one
						if(tempListBug.size()==0 || tempListBug.get(0).getIDcolor()>this.listSPs.get(i).getListBug().get(0).getIDcolor())
						{
							
							
							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								
								switch(this.listSPs.get(i).getListBug().get(0).getIDcolor())
								{
									case 3:	mail.setSubject("MonitorYourLAN - Alerte minimimale sur "+this.listSPs.get(i).getName());
									break;
									case 2:	mail.setSubject("MonitorYourLAN - Alerte maximale sur "+this.listSPs.get(i).getName());
									break;
									case 1:	mail.setSubject("MonitorYourLAN - PROBLEME DE COMMUNICATION AVEC "+this.listSPs.get(i).getName());
									break;	
									default: mail.setSubject("MonitorYourLAN - Alerte déclenchée sur "+this.listSPs.get(i).getName());
									break;
								
								}
								
								mail.setEmailBody("Description du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+this.listSPs.get(i).getListBug().get(0).getDetail()+"\n\rSolution possible : "+this.listSPs.get(i).getListBug().get(0).getListSolution().get(0).getDescription());
								mail.start();								
							}

							
							tempBug.update(0, listSPs.get(i).getID(), dateFormat.format(date)); // Set inactive if there was an old bug 
							
							dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
							((DataStorage) listSPs.get(i)).createBugIntoDatabase();				// create new one
						}						
					}						
					else
					{
						// it means that color switch on green color == no default active
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.FRANCE);
						Date date = new Date();
						
						// Get active bug about this SP into database
						tempBug.getBugActiveForThisSP(tempListBug,this.listSPs.get(i).getID());
						
						// check if a bug is active about it
						if(tempListBug.size()>0)
						{					
							tempBug.update(0, this.listSPs.get(i).getID(),dateFormat.format(date)); 
							
							// send mail to each admin user
							for(int j=0; j< this.listAdminUsers.size();j++)
							{
								// updating log file
								try 
								{
									FileWriter fw = new FileWriter (f,true);
									fw.write("\r\nEnvoi d'un mail de fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
									fw.close();
								} 
								catch (IOException e) 
								{			
									e.printStackTrace();
								}
								
								System.out.println("Envoi d'un mail fin d'alerte à "+this.listAdminUsers.get(j).getUsername()+" + sauvegarde du Bug dans la base de données");
								Mail mail = new Mail();
								mail.setSender("x@x.com");
								mail.setUsername("x@x.com");
								mail.setRecipient(this.listAdminUsers.get(j).getEmail());
								mail.setPassword("your_password");
								mail.setSubject("MonitorYourLAN - Fin d'Alerte pour "+this.listSPs.get(i).getName());
								mail.setEmailBody("Le point stratégique est dans un état stable.\n\nDate de fin du BUG : "+dateFormat.format(date)+"\n\rDescription du point stratégique : "+this.listSPs.get(i).getDescription()+"\n\rErreur rencontrée : "+tempListBug.get(0).getDetail()+"\n\rSolution possible : "+tempListBug.get(0).getListSolution().get(0).getDescription());
								mail.start();									
							}					
						}	
					}
				}				
				

				// delete all bug before to change strategic point
				if(tempListBug.size()>0)
				{
					tempListBug.removeAll(tempListBug);
				}
			}			
		}
		
		// Update LED field for category
		this.category.getLed().setID((byte)5);	
		
		for (int j = 0; j < listSPs.size(); j++) 
		{
			if(this.listSPs.get(j).getLed().getID() < this.category.getLed().getID())
			{
				this.category.getLed().setID(this.listSPs.get(j).getLed().getID());
			}
		}
		this.category.update(this.category.getLed().getID());		
		System.out.println(" *** Mise à jour du voyant CATEGORIE "+this.category.getName()+" : "+this.category.getLed().getID());
		
		// updating log file
		try 
		{
			FileWriter fw = new FileWriter (f,true);
			fw.write("\r\n Mise à jour du voyant de la catégorie "+this.category.getName()+":"+this.category.getLed().getID());
			fw.write("\r\nFIN de la détection de la catégorie "+this.category.getName());
			fw.close();
		} 
		catch (IOException e) 
		{			
			e.printStackTrace();
		}
		
		System.out.println("FIN de la détection de la catégorie "+this.category.getName());


	return "done";
		
	}
}


	
	
	
	
	
	
	
	
