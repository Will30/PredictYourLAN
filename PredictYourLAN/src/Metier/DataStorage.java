package Metier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Donnees.GetData;
import Donnees.UpdateData;

/**
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Class for DataStorage. It often represented by a folder/repository into a computer. In our project, data storage receives files at each time 
   interval.
 */

public class DataStorage extends StrategicPoint
{
	private String UNC; 		// it can be just a folder name or a way to a folder (example --> c:\temp\myFolder)
	private String lastDate;		// the last time when the last file has been received
	private int lastUpdateDelay = 0;
	private int type = 0;		// Data storage are grouped according to their frequency reception : 5 or 15 min
	
	/**
	 * When created, data storage is associated at a LED and service
	 * @since 1.00
	 */
	public DataStorage() 
	{
		super.led = new Led();
		super.listBug = new ArrayList<Bug>();
		super.category = new Category();
	}

	@Override
	public void getByCategory(ArrayList<StrategicPoint> listdataStorage, Category Category)
	{
		String url ="http://"+SERVER_NAME+":"+PORT_NUMBER+"/dataStorages/category/"+Category.getID();
		int j=0;

		int listLenght = listdataStorage.size();
		GetData getSPs = new GetData();

		JSONArray json = getSPs.Start(url);	

		for(int i=listLenght;i<json.length()+listLenght;i++)
		{
			try 
			{	
				JSONObject jsonObject = json.getJSONObject(j);
				j++;
				if(jsonObject.length()>0)
				{
					listdataStorage.add(new DataStorage());	
					
					listdataStorage.get(i).setID( jsonObject.getInt("id"));
					listdataStorage.get(i).setName(jsonObject.getString("name"));
					listdataStorage.get(i).setDescription(jsonObject.getString("description"));

					listdataStorage.get(i).setIcon(jsonObject.getString("icon"));
					listdataStorage.get(i).setIPAddress(jsonObject.getString("IPaddress"));	


					listdataStorage.get(i).getLed().setID((byte) jsonObject.getInt("id_LED")); 
					listdataStorage.get(i).getLed().setColor(jsonObject.getString("color"));
					listdataStorage.get(i).getLed().setUNC(jsonObject.getString("UNCLed"));				

					((DataStorage) listdataStorage.get(i)).setUNC(jsonObject.getString("UNCFolder"));						
					((DataStorage) listdataStorage.get(i)).setLastDate(jsonObject.getString("lastfile"));		
					((DataStorage) listdataStorage.get(i)).setType(jsonObject.getInt("type"));	

					listdataStorage.get(i).setCategory(category);
				}
			} 
			
			catch (JSONException e) 
			{				
				e.printStackTrace();
			}
		}
		
	}



	@Override
	public int add() 
	{
		UpdateData postData = new UpdateData();
		JSONObject json = new JSONObject(); 
		String method ="POST";
		String jsonReturned = null;
		String Server_Rest_Address = "http://"+SERVER_NAME+":"+PORT_NUMBER+"/dataStorages";
		JSONObject tempJson = null;
		int stateConnexion=-1;		
		
	//	System.out.println("DataStorage.add() - service r�cup�r� --> "+this.getService().getName());
		
		try 
		{
			json.put("name",this.getName());
			json.put("description",this.getDescription());
			json.put("icon",this.getIcon());
			json.put("IPaddress",this.getIPAddress());
			
			json.put("date","2017-01-01 00:00:00");					// By default
			json.put("UNC",this.getUNC());			
			json.put("id_LED",this.getLed().getID()); 			
			
		} 
		catch (JSONException e) 
		{			
			e.printStackTrace();
		}
		
		if(json.length()>0)
		{			
			System.out.println("Ready to send JSON :"+String.valueOf(json));
			jsonReturned = postData.Start(json,Server_Rest_Address,method);  	
			try 
			{
				tempJson = new JSONObject(jsonReturned);
			} 
			catch (JSONException e) 
			{
				System.out.println("DataStorage.Add() unable to convert string to JSON");
				e.printStackTrace();
			}
		}
		
		System.out.println("retour du JSON "+ String.valueOf(tempJson));
		
		try 
		{
			stateConnexion = tempJson.getInt("status");
			super.setID(tempJson.getInt("id"));
			System.out.println("DataStorage.Add()   Last ID --> "+tempJson.getInt("id"));
			System.out.println("DataStorage.Add() 	Status -->"+stateConnexion);
			
		} 
		catch (JSONException e1) 
		{
			System.out.println("DataStorage.Add() unable to convert json to int ");
			e1.printStackTrace();
		}			
		
		return (stateConnexion);
	}
	
	/**
	 * Function which delete a strategic point
	 * @version 1.00
	 */
	public void delete()
	{
		super.delete();
	}

	@Override
	public void convertImageToBase64(File  file) 
	{
	
		super.convertImageToBase64(file);
	}


	/**
	 * After the detection, this function will update Strategic points's LED into database
	 * @return stateConnexion it's an integer with only two values (as a boolean) to know if strategic point has been updated successfully
	 * @version 1.00
	 */
	public int update()
	{
		int state = super.update();
		return state;		
	}

	/**
	 * After the detection, this function will update Strategic points's LED into database
	 * @return stateConnexion it's an integer with only two values (as a boolean) to know if strategic point has been updated successfully
	 * @version 1.00
	 */
	public int updateLastFile()
	{
		String method ="PUT";
		int stateConnexion = 0;
		UpdateData postData = new UpdateData();
		JSONObject json = new JSONObject(); 	
		String jsonReturned = null;
		String Server_Rest_Address = "http://"+SERVER_NAME+":"+PORT_NUMBER+"/datastorages/"+this.getID();
		
		System.out.println("DataStorage.updateLastFile() -- last file --> "+this.getLastDate());
	
		JSONObject tempJson = null;

		try 
		{			
			
			json.put("lastFile",this.getLastDate());
		} 
		catch (JSONException e) 
		{			
			e.printStackTrace();
		}
		
		if(json.length()>0)
		{				
			jsonReturned = postData.Start(json,Server_Rest_Address,method);  	
			try 
			{
				tempJson = new JSONObject(jsonReturned);
			} 
			catch (JSONException e) 
			{
				System.out.println("DataStorage.updateLastFile() unable to convert string to JSON");
				e.printStackTrace();
			}
		}

		try 
		{
			stateConnexion = tempJson.getInt("status");
		} 
		catch (JSONException e1) 
		{
			System.out.println("DataStorage.updateLastFile() unable to convert json to int");
			e1.printStackTrace();
		}					
	
		return (stateConnexion);
	}




	

	/**
	 * Function which start command ping and detect if Strategic point is available
	 * @param listBugIntoDatabase this is a list of all bu available/possible from database
	 * @version 1.03 Modify for network
	 */
	@Override
	public void detectSP(ArrayList<Bug> listBugIntoDatabase)
	{
		InetAddress address = null;		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.FRANCE);
		Date dateOfDay = new Date();			
		
		//Delete old bug if exist
		if(this.getListBug().isEmpty() == false)
		{
			this.getListBug().removeAll(this.getListBug());
		}
		
		try 
		{												
			address = InetAddress.getByName(this.getIPAddress());		
		} 
		catch (UnknownHostException e) 
		{
			System.out.println("DataStorage.detectSP() - Bad IP address format");
			e.printStackTrace();
		}
	
		try 
		{			
			if (address.isReachable(5000)) 
			{					
				this.getLed().setID((byte)4); // id => 4 equals to green color
				this.setIPAddress(address.getHostAddress());    // Force an IPAddress update because into domain area, equipment's IPaddress often change 
			} 
			else 
			{
				if(this.getIPAddress().equals("X.X.X.X") == false)		// Meteo France FTP server doesn't respond to ping command
				{
					System.out.println("DataStorage.detectSP() - "+this.getName() + ": Unable to detect the StrategicPoint - Unable to communicate with ");
					
					this.getLed().setID((byte)1);		// id => 1 equals to black color
					if(this.getListBug().isEmpty() == false)
					{																			
							this.getListBug().set(0, listBugIntoDatabase.get(8));	// bug called "ping timeout"
							this.getListBug().get(0).setDate(dateFormat.format(dateOfDay));								
					}
					else
					{
						this.getListBug().add(listBugIntoDatabase.get(8));
						this.getListBug().get(0).setDate(dateFormat.format(dateOfDay));	
					}
				}
	
			}
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * After the detection and if bug is detected, it will create this bug in the database
	 * @return state it's an integer with only two values (as a boolean) to know if bug has been created successfully
	 * @version 1.00
	 */
	public int createBugIntoDatabase()
	{		
		int state = super.createBugIntoDatabase();
		return state;		
	}
	

	/**
	 * Function verify activity for data storage. Normally a data storage is a folder which receives files each "x" seconds, minutes or hours 
	 * (as an archive storage). This function checks if files are still uploaded into.
	 * @param ftpClient instance of FTPClient class. Initiated when PredictYourLAN_service starts
	 * @param listBugIntoDatabase List of all bugs available/possible in database
	 * @version 1.00
	 */
	
	public void diagnose(FTPClient ftpClient,ArrayList<Bug> listBugIntoDatabase)
	{	
		int numberFiles=0;	
		String tempUNC; 
		FTPFile[] lames = null;
		File[] tabFile;				
	
		int lastModifiedDay = 0;
		int lastModifiedHours= 0;
		int lastModifiedMinutes= 0;
		int actualDay= 0;
		int actualHours= 0;
		int actualMinutes= 0;
		int difference = 0;
		int i=0;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
		SimpleDateFormat dateFormatUK = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		System.out.println("DataStorage.diagnose()   - Date :"+dateFormat.format(date)); //2013/10/15 16:16:39		

		
		Integer.parseInt(dateFormat.format(date).substring(0,4));
		
		actualDay = Integer.parseInt(dateFormat.format(date).substring(8,10));
		Integer.parseInt(dateFormat.format(date).substring(5,7));
		actualHours = Integer.parseInt(dateFormat.format(date).substring(11,13));
		actualMinutes = Integer.parseInt(dateFormat.format(date).substring(14,16));	
	
		if(this.getIPAddress().equals("X.X.X.X"))   // Data storage Meteo France
		{	
			
			try 
			{
				// "Lames" from Meteo France
				System.out.println("UNC : "+this.getUNC());
				
				ftpClient.changeToParentDirectory();
				boolean success = ftpClient.changeWorkingDirectory(this.getUNC());
				if(success == false)
				{
					System.out.println("Unable to change directory");
					
				}
				lames = ftpClient.listFiles();
				
				for (FTPFile FTPfile : lames) 
				{		        
				
					numberFiles++;					// to get the latest file modification
			    }
				
				System.out.println("Nombre de fichiers :"+numberFiles);
				System.out.println("La dernière date de modification (heure GMT)" +dateFormat.format(lames[numberFiles-1].getTimestamp().getTime()));
	
				lastModifiedDay = Integer.parseInt(dateFormatUK.format(lames[numberFiles-1].getTimestamp().getTime()).substring(8, 10));
				lastModifiedHours = (Integer.parseInt(dateFormatUK.format(lames[numberFiles-1].getTimestamp().getTime()).substring(11, 13)));
				lastModifiedMinutes = Integer.parseInt(dateFormatUK.format(lames[numberFiles-1].getTimestamp().getTime()).substring(14, 16));
			  
			} catch (IOException e) 
			{
				System.out.println("Unable to change directory");		
				if(this.getListBug().isEmpty() == true)
				{
					this.getListBug().add(listBugIntoDatabase.get(8));
				}
				else
				{
					this.getListBug().set(0, listBugIntoDatabase.get(8));
				}
				
				this.getListBug().get(0).setDate(dateFormat.format(date));
				e.printStackTrace();
			}
		}
		else
		{
				// Output folder
			if(this.getUNC().equals("Data\\Metropole\\Output") || this.getUNC().equals("Data\\REU\\Output") || this.getUNC().equals("Data\\DOM\\Output"))
			{			
				if(Integer.parseInt(dateFormat.format(date).substring(5,7)) <10 )
				{
					if(actualDay<10)
					{
						tempUNC = "\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\"+this.getType()+"\\"+Integer.parseInt(dateFormat.format(date).substring(0,4))+"\\0"+Integer.parseInt(dateFormat.format(date).substring(5,7))+"\\0"+actualDay+"\\";
					}
					else
					{
						tempUNC = "\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\"+this.getType()+"\\"+Integer.parseInt(dateFormat.format(date).substring(0,4))+"\\0"+Integer.parseInt(dateFormat.format(date).substring(5,7))+"\\"+actualDay+"\\";
					}
					
				}
				else
				{
					if(actualDay<10)
					{
						tempUNC = "\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\"+this.getType()+"\\"+Integer.parseInt(dateFormat.format(date).substring(0,4))+"\\"+Integer.parseInt(dateFormat.format(date).substring(5,7))+"\\0"+actualDay+"\\";
					}
					else
					{
						tempUNC = "\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\"+this.getType()+"\\"+Integer.parseInt(dateFormat.format(date).substring(0,4))+"\\"+Integer.parseInt(dateFormat.format(date).substring(5,7))+"\\"+actualDay+"\\";
					}
						
				}	
				
				
				System.out.println("UNC ---> "+tempUNC);
				numberFiles = new File(tempUNC).list().length;
				System.out.println("DataStorage.diagnose()   - Numbers of files  --> "+numberFiles);
				if(numberFiles>0)
				{								
					tabFile = new File(tempUNC+"\\").listFiles();
					
					for (File file  : tabFile) 
					{		        
						i++;					// to get the latest file modification
				    }				
					System.out.println("La dernière Date de modification (heure GMT) " +dateFormat.format(tabFile[i-1].lastModified()));			
					
					lastModifiedDay = Integer.parseInt(dateFormat.format(tabFile[i-1].lastModified()).substring(8, 10));
					lastModifiedHours = (Integer.parseInt(dateFormat.format(tabFile[i-1].lastModified()).substring(11, 13))); 
					lastModifiedMinutes = Integer.parseInt(dateFormat.format(tabFile[i-1].lastModified()).substring(14, 16));
				}			
			}
			else
			{		
				// PROD Folder
				System.out.println("UNC ---> "+"\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\");
				tempUNC = "\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC();
				numberFiles = new File("\\"+"\\"+this.getIPAddress()+"\\"+this.getUNC()+"\\").list().length;
				System.out.println("DataStorage.diagnose()   - Numbers of files  --> "+numberFiles);	
				
				if(numberFiles>0)
				{								
					tabFile = new File(tempUNC+"\\").listFiles();					
							
					System.out.println("La derni�re Date de modification (heure GMT) " +dateFormat.format(tabFile[0].lastModified()));	// to get the oldest file modified		
					
					lastModifiedDay = Integer.parseInt(dateFormat.format(tabFile[0].lastModified()).substring(8, 10));
					lastModifiedHours = (Integer.parseInt(dateFormat.format(tabFile[0].lastModified()).substring(11, 13))); 
					lastModifiedMinutes = Integer.parseInt(dateFormat.format(tabFile[0].lastModified()).substring(14, 16));
				}			
			}
			
		
		}
		
		
		// difference between actual "datetime", and last modified or creation files "datetime"	
		if(numberFiles==0)
		{
			// it means that files has been treated by other application, thus it works
			lastModifiedDay = actualDay;
			lastModifiedHours = actualHours ; 
			lastModifiedMinutes = actualMinutes;
		}

		// Actualisation de la date
		String tempDate = String.valueOf(dateFormat.format(date).substring(0,4))+"-"+String.valueOf(dateFormat.format(date).substring(5,7))+"-"+String.valueOf(lastModifiedDay)+" "+String.valueOf(lastModifiedHours)+":"+String.valueOf(lastModifiedMinutes)+":00";
		
		
		this.setLastDate(tempDate);
		System.out.println("Last date ********************* :" + tempDate);
		
		if(actualDay - lastModifiedDay >= 0)
		{
			difference = (actualDay - lastModifiedDay) * 1440;
		}
		else
		{
			difference = ((actualDay+lastModifiedDay) - lastModifiedDay - 1) * 1440;
		}
		
		if(actualHours - lastModifiedHours >= 0)
		{
			
				difference = difference + (actualHours - lastModifiedHours) * 60; // to get value in minutes	
		}
		else
		{
			difference = difference + (24 +(actualHours-lastModifiedHours)) * 60; // to get value in minutes
		}
		

		difference = difference + (actualMinutes-lastModifiedMinutes);

		
		System.out.println("DataStorage.diagnose()   - (TOTAL) Last update is --> "+difference+" minutes");
		
		this.lastUpdateDelay = difference ;
	}
	

	
	/** 
	 * @param listBugIntoDatabase this is a list of all bu available/possible from database
	 */
	public void checkAlert(ArrayList<Bug> listBugIntoDatabase)
	{
		
		System.out.println("******************** Check ALERT   *************");
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.FRANCE);
		Date date = new Date();
		
	
	
		if(this.getType() == 5)
		{
			if(lastUpdateDelay >= listBugIntoDatabase.get(9).getThresholdValue())  			// Corresponds to the minimal alert ==> DataStorage5MinimalAlert
			{
				if(lastUpdateDelay >= listBugIntoDatabase.get(10).getThresholdValue()) 		// Corresponds to the maximal alert ==> DataStorage5MaximalAlert
				{
					if(lastUpdateDelay >= listBugIntoDatabase.get(11).getThresholdValue())	// Indicates that data storage doesn't receive files ==> DataStorage5Down
					{
						this.getLed().setID(listBugIntoDatabase.get(11).getIDcolor());				
						
						if(this.getListBug().isEmpty() == true)
						{
							this.getListBug().add(listBugIntoDatabase.get(11));				// Maximal Alert
						}
						else
						{
							this.getListBug().set(0, listBugIntoDatabase.get(11));			// Maximal Alert
						}
						
						this.getListBug().get(0).setDate(dateFormat.format(date));
					}
					else
					{
						this.getLed().setID(listBugIntoDatabase.get(10).getIDcolor());			
						
						
						if(this.getListBug().isEmpty() == true)
						{
							this.getListBug().add(listBugIntoDatabase.get(10));				// Minimal Alert
						}
						else
						{
							this.getListBug().set(0, listBugIntoDatabase.get(10));			// Minimal Alert
						}
						
						this.getListBug().get(0).setDate(dateFormat.format(date));
					}
				}
				else
				{
					this.getLed().setID(listBugIntoDatabase.get(9).getIDcolor());		
					
					
					if(this.getListBug().isEmpty() == true)
					{
						this.getListBug().add(listBugIntoDatabase.get(9));				// DataStorage down > 25 minutes
					}
					else
					{
						this.getListBug().set(0, listBugIntoDatabase.get(9));			// DataStorage down > 25 minutes
					}
					this.getListBug().get(0).setDate(dateFormat.format(date));
				}
			}
			else
			{
				this.getLed().setID((byte) 4);
				if(this.getListBug().isEmpty() == false)
				{
					this.getListBug().removeAll(this.getListBug());
				}				
			}			
		}
		
		if(this.getType() == 15)
		{
			if(lastUpdateDelay >= listBugIntoDatabase.get(24).getThresholdValue())  			// Corresponds to the minimal alert ==> DataStorage15MinimalAlert
			{
				if(lastUpdateDelay >= listBugIntoDatabase.get(25).getThresholdValue()) 		// Corresponds to the maximal alert ==> DataStorage15MaximalAlert
				{
					if(lastUpdateDelay >= listBugIntoDatabase.get(26).getThresholdValue())	// Indicates that date storage doesn't receive files ==> DataStorage15Down
					{
						this.getLed().setID(listBugIntoDatabase.get(26).getIDcolor());				
						
						if(this.getListBug().isEmpty() == true)
						{
							this.getListBug().add(listBugIntoDatabase.get(26));
						}
						else
						{
							this.getListBug().set(0, listBugIntoDatabase.get(26));
						}
						
						this.getListBug().get(0).setDate(dateFormat.format(date));
					}
					else
					{
						this.getLed().setID(listBugIntoDatabase.get(25).getIDcolor());			
						
						
						if(this.getListBug().isEmpty() == true)
						{
							this.getListBug().add(listBugIntoDatabase.get(25));
						}
						else
						{
							this.getListBug().set(0, listBugIntoDatabase.get(25));
						}
						
						this.getListBug().get(0).setDate(dateFormat.format(date));
					}
				}
				else
				{
					this.getLed().setID(listBugIntoDatabase.get(24).getIDcolor());		
					
					
					if(this.getListBug().isEmpty() == true)
					{
						this.getListBug().add(listBugIntoDatabase.get(24));
					}
					else
					{
						this.getListBug().set(0, listBugIntoDatabase.get(24));
					}
					this.getListBug().get(0).setDate(dateFormat.format(date));
				}
			}
			else
			{
				this.getLed().setID((byte) 4);
				if(this.getListBug().isEmpty() == false)
				{
					this.getListBug().removeAll(this.getListBug());
				}
				
			}
			
		}
		
		if(this.getListBug().isEmpty() == false)
		{
			for(int j =0;j<this.getListBug().size();j++)
			{
				
				System.out.println("StrategicPoint name---> "+this.getName());
				System.out.println("/!\\   /!\\       /!\\      Problem detected ! ---> "+this.getListBug().get(j).getDetail());
				System.out.println("List bug size -->" + this.getListBug().size());
				System.out.println("Couleur du voyant ---------------------------->"+this.getLed().getColor());
				System.out.println("********************************************************************");
			}
		}
		
		
	}
		
				
	


	/////////////************************    Getters and Setters *************************
	
	public String getUNC() 
	{
		return UNC;
	}
	

	public void setUNC(String uNC) {
		UNC = uNC;
	}

	public String getLastDate() {
		return lastDate;
	}
	

	public void setLastDate(String lastDate) 
	{
		this.lastDate = lastDate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	
}