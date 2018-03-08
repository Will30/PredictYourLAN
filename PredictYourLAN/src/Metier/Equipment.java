package Metier;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Donnees.GetData;
import Donnees.UpdateData;


/**
 * @author Cyril or Will30 (GitHub) or Will15 (GitLab)
 * @version 1.00
 * Class for Equipment. It can be a computer, a printer, a switch,etc..
 */
public class Equipment extends StrategicPoint
{

	private String type;							// for this project, only these value: switch, desktop, printer or router
	private String model;							// Model for each type. For example "Dell Z230"
	private ArrayList<ObjectIdentifier> listOID;    // OID
	
	private float RAMLoad = 0;						// Random Access Memory load
	private float HDDLoad = 0;						// Hard Disk Drive load
	private float CPULoad = 0;						// Central Processing Unit load
	
	private float TonerRemaining = 100;					// for printer
	private float ImagingUnitRemaining = 100;			// for printer
	private float WasteTonerState = 0;					// for printer
	private float DrumCartridgeRemaining = 100;			// for printer
	private float DevelopperCartridgeRemaining = 100;	// for printer
	
	private float bandwidthLoad = 0;					// equipment with network card


	/**
	 * When created, it is associated at a LED and service
	 * @since 1.00
	 */
	public Equipment() 
	{
		super.led = new Led();
		super.listBug = new ArrayList<Bug>();
		super.category = new Category();
	}
	
	@Override
	public void getByCategory(ArrayList<StrategicPoint> listEquipment, Category Category)
	{	
		int tempSeveralValues = 0;
		String url ="http://"+SERVER_NAME+":"+PORT_NUMBER+"/equipments/category/"+Category.getID();

		GetData getSPs = new GetData();
		
		JSONArray json = getSPs.Start(url);	
		
		for(int i=0;i<json.length();i++)
		{
			try 
			{	
				JSONObject jsonObject = json.getJSONObject(i);
		
				if(jsonObject.length()>0)
				{
					listEquipment.add(new Equipment());
					
					listEquipment.get(i).setID( jsonObject.getInt("id"));
					listEquipment.get(i).setName(jsonObject.getString("name"));
					listEquipment.get(i).setDescription(jsonObject.getString("description"));
					listEquipment.get(i).setIcon(jsonObject.getString("icon"));
					listEquipment.get(i).setIPAddress(jsonObject.getString("IPaddress"));	

					listEquipment.get(i).getLed().setID((byte) jsonObject.getInt("id_LED")); 
					listEquipment.get(i).getLed().setColor(jsonObject.getString("color"));
					listEquipment.get(i).getLed().setUNC(jsonObject.getString("UNC"));						
									
					((Equipment) listEquipment.get(i)).setType(jsonObject.getString("type"));
					((Equipment) listEquipment.get(i)).setModel(jsonObject.getString("model"));
					
				//	listEquipment.get(i).getCategory().setID(IDCategory);
					listEquipment.get(i).setCategory(category);
					
					url ="http://"+SERVER_NAME+":"+PORT_NUMBER+"/equipments/"+listEquipment.get(i).getID();
					
					((Equipment) listEquipment.get(i)).setListOID(); 
					
					JSONArray json2 = getSPs.Start(url);	
	
					for(int j=0;j<json2.length();j++)
					{
						try 
						{	
							JSONObject jsonObject2 = json2.getJSONObject(j);
					
							if(jsonObject2.length()>0)
							{

								((Equipment) listEquipment.get(i)).getListOID().add(new ObjectIdentifier());								
								
								((Equipment) listEquipment.get(i)).getListOID().get(j).setID(jsonObject2.getInt("id"));
								((Equipment) listEquipment.get(i)).getListOID().get(j).setNumOID(jsonObject2.getString("OID"));
								((Equipment) listEquipment.get(i)).getListOID().get(j).setName(jsonObject2.getString("name"));
								((Equipment) listEquipment.get(i)).getListOID().get(j).setDescription(jsonObject2.getString("description"));	
								tempSeveralValues = jsonObject2.getInt("severalValues");
								if(tempSeveralValues == 0)
								{
									((Equipment) listEquipment.get(i)).getListOID().get(j).setSeveralValue(false);
								}
								else
								{
									((Equipment) listEquipment.get(i)).getListOID().get(j).setSeveralValue(true);
								}
							}
						} 
						
						catch (JSONException e) 
						{				
							e.printStackTrace();
						}	
				
					}
				}
			} 
			
			catch (JSONException e) 
			{				
				e.printStackTrace();
			}	
		}
		
		
	}
	
	/**
	 * Function which get a list all type and model saved in the database
	 * @param listTypeAndModel it's a list  with type and model for an equipment
	 * @version 1.00
	 * 
	 */	
	public void getListTypeAndModel(ArrayList<Equipment> listTypeAndModel)
	{
		String url ="http://"+SERVER_NAME+":"+PORT_NUMBER+"/equipments/list";

		GetData getSPs = new GetData();		
		JSONArray json = getSPs.Start(url);	

		for(int i=0;i<json.length();i++)
		{
			try 
			{	
				JSONObject jsonObject = json.getJSONObject(i);
		
				if(jsonObject.length()>0)
				{
					listTypeAndModel.add(i,new Equipment());
					
					listTypeAndModel.get(i).setID( jsonObject.getInt("id"));
					listTypeAndModel.get(i).setType(jsonObject.getString("type"));
					listTypeAndModel.get(i).setModel(jsonObject.getString("model"));

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
		String Server_Rest_Address = "http://"+SERVER_NAME+":"+PORT_NUMBER+"/equipments";
		JSONObject tempJson = null;
		int stateConnexion=-1;		
		
		try 
		{
			json.put("name",this.getName());
			json.put("description",this.getDescription());
			json.put("icon",this.getIcon());
			json.put("IPaddress",this.getIPAddress());	
			json.put("idCategory",this.getCategory().getID());
			json.put("type",this.getType());
			json.put("model",this.getModel());	
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
				System.out.println("Equipment.Add() unable to convert string to JSON");
				e.printStackTrace();
			}
		}
				
		try 
		{
			stateConnexion = tempJson.getInt("status");
			super.setID(tempJson.getInt("id"));
			System.out.println("Equipment.Add()     Last ID --> "+tempJson.getInt("id"));
			System.out.println("Equipment.Add() 	status -->"+stateConnexion);
			
		} 
		catch (JSONException e1) 
		{
			System.out.println("Equipment.Add() unable to convert json to int ");
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
	
	/**
	 * Converts image (byte[]) to String base64
	 * @param file File is an image. Then, this image will be converting into string base64 format	
	 */
	public void convertImageToBase64(File file)
	{			
		super.convertImageToBase64(file);
	}
	
	/**
	 * After the detection, it will save new Strategic points's LED into database
	 * @return stateConnexion it's an integer with only two values (as a boolean) to know if strategic point has been updated successfully
	 * @version 1.00
	 */
	public int update()
	{
		int state = super.update();
		return state;	
	}
	
	/**
	 * After the detection and if bug is detected, it will create this bug in the database
	 * @return stateConnexion it's an integer with only two values (as a boolean) to know if bug has been created successfully
	 * @version 1.00
	 */
	public int createBugIntoDatabase()
	{	
		
		int state = super.createBugIntoDatabase();
		return state;		
		
	}
	
	/**
	 * Start command ping and detect if Strategic point is available
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
			System.out.println("detectSP() -- Unknown host, please check manually with PING command");
			e.printStackTrace();
		}

		try 
		{			
			if (address.isReachable(5000)) 
			{			
				this.getLed().setID((byte)4); // id => 4 equals to green color
			} 
			else 
			{
				System.out.println(this.getName() + ": Unable to detect the StrategicPoint - Unable to communicate with ");
				
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
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Start a hard detection for equipment. Thought SNMP protocol, a request is sent to agent and return values (as RAM, CPU, Hard disk 
	 * capacity, bandwidth for switch,etc...)
	 * @version 1.00
	 */
	public void diagnose()
	{
		int i=0;
		String updateOID = null;
		long tempifInOctetValue =0;
		long tempifOutOctetValue =0;
		float bandWidth =0;
		int timeSleep = 10000;   // By default, on OS windows, bandwidth value change in approximatively each 10 seconds

		float tempLoadCPU=0;	
		float tempLoadRAM=0;	
		float tempLoadHDD1=0,tempLoadHDD2=0,tempLoadHDD3=0;
		
		String tempHDDSize1=null,tempHDDSize2=null,tempHDDSize3=null;	
		String tempHDDUsed1=null,tempHDDUsed2=null, tempHDDUsed3=null;	
		
		ArrayList <String> printerCapacityValue = new ArrayList<String>();
		ArrayList <String> printerLevelValue = new ArrayList<String>();
		
		ObjectIdentifier tempObjectIdentifier = new ObjectIdentifier();		
		
		
		switch(this.getType())
		{
			case "Serveur" : if(this.getName().equals("Serveur Data"))
							{
								updateOID = ".10";
							}
							else
							{
								updateOID = ".11";
							}
			
				
			break;
			case "Pare-feu" : updateOID = ".5";
			break;
			case "Datapath" : 	if(this.getName().equals("VISION800-PC"))
								{
									updateOID = ".19";
								}
								
			break;						
		}
	
		System.out.println("IP address :"+this.getIPAddress());		
	

		// First, get all OID value for an equipment
		for(i=0;i<this.getListOID().size();i++)
		{

			// For bandWidth, this is special treatment
			if(this.getListOID().get(i).getName().equals("ifInOctets") || this.getListOID().get(i).getName().equals("ifOutOctets") || this.getListOID().get(i).getName().equals("ifSpeed"))
			{	
					if(i == 0)
					{
						this.getListOID().get(i).checkValue(this.getIPAddress(),updateOID);                         
						if(this.getListOID().get(i).getValue().equals("0") && this.getType().equals("Serveur"))
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),".12"); 
						}			
						tempifInOctetValue = Long.parseLong(this.getListOID().get(i).getValue());
						System.out.println("Value 1:"+this.getListOID().get(i).getValue());
						
						this.getListOID().get(i+1).checkValue(this.getIPAddress(),updateOID);
						if(this.getListOID().get(i+1).getValue().equals("0") && this.getType().equals("Serveur"))
						{
							this.getListOID().get(i+1).checkValue(this.getIPAddress(),".12");
						}				
						tempifOutOctetValue = Long.parseLong(this.getListOID().get(i+1).getValue());
						System.out.println("Value 2:"+this.getListOID().get(i+1).getValue());
						
						try 
						{
							Thread.sleep(timeSleep);
						} catch (InterruptedException e) 
						{
							
							e.printStackTrace();
						}
					}
					if(i == 1)
					{
						this.getListOID().get(i-1).checkValue(this.getIPAddress(),updateOID);
						this.getListOID().get(i).checkValue(this.getIPAddress(),updateOID);
						if(this.getListOID().get(i-1).getValue().equals("0") && this.getType().equals("Serveur"))
						{
							this.getListOID().get(i-1).checkValue(this.getIPAddress(),".12");
							this.getListOID().get(i).checkValue(this.getIPAddress(),".12");
						}
						
						System.out.println("Value 3:"+this.getListOID().get(i-1).getValue());
						System.out.println("Value 4:"+this.getListOID().get(i).getValue());
	
					}
					
					
					if(i == 2)
					{
						this.getListOID().get(i).checkValue(this.getIPAddress(),updateOID);
						//this.getListOID().get(i).checkAllValue(this.getIPAddress());
						if(this.getListOID().get(i).getValue().equals("0") && this.getType().equals("Serveur"))
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),".12");
						}
						System.out.println("Value 5 speed:"+this.getListOID().get(i).getValue());
					}
					continue;	
			}
			
			


			if(this.getName().equals("Vigie NATIONAL") == false)
			{				
			
				// for others OID
				if(this.getListOID().get(i).isSeveralValue() == true)
				{		
		
					// for printer
					if(this.getModel().equals("Konica C35"))
					{
					
						for(int j=1;j<13;j++)
						{						
							this.getListOID().get(i).checkValue(this.getIPAddress(),"."+j);
							printerCapacityValue.add(this.getListOID().get(i).getValue());
						}	
						
						i++;
						for(int j=1;j<13;j++)
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),"."+j);
							printerLevelValue.add(this.getListOID().get(i).getValue());
						}
						continue;
					}
					
					if(this.getModel().equals("Konica C364"))
					{
						for(int j=1;j<14;j++)
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),"."+j);
							printerCapacityValue.add(this.getListOID().get(i).getValue());
						}
						
						i++;
						for(int j=1;j<14;j++)
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),"."+j);
							printerLevelValue.add(this.getListOID().get(i).getValue());
						}
						continue;
					}
							
					this.getListOID().get(i).checkAllValue(this.getIPAddress());
					continue;					
				}
				else
				{
					// For HDD, it can have several physical storage on equipment
					if(this.getListOID().get(i).getName().equals("hrStorageSize") || this.getListOID().get(i).getName().equals("hrStorageUsed"))
					{
						tempObjectIdentifier.setNumOID("1.3.6.1.2.1.25.2.3.1.4");
						tempObjectIdentifier.checkValue(this.getIPAddress(), ".2");	// Usually C:
						
						
						if(tempObjectIdentifier.getValue().equals("4096"))			// it means physical HDD
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),".2");	
							tempHDDSize1 = this.getListOID().get(i).getValue();
							
							this.getListOID().get(i+1).checkValue(this.getIPAddress(),".2");	
							tempHDDUsed1 = this.getListOID().get(i+1).getValue();
							
						}
						
						tempObjectIdentifier.checkValue(this.getIPAddress(), ".3");	// Usually D:			
						if(tempObjectIdentifier.getValue().equals("4096"))			// it means physical HDD
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),".3");	
							tempHDDSize2 = this.getListOID().get(i).getValue();
							
							this.getListOID().get(i+1).checkValue(this.getIPAddress(),".3");	
							tempHDDUsed2 = this.getListOID().get(i+1).getValue();						
						}
						
						tempObjectIdentifier.checkValue(this.getIPAddress(), ".4");	// Usually E:				
						if(tempObjectIdentifier.getValue().equals("4096"))			// it means physical HDD
						{
							this.getListOID().get(i).checkValue(this.getIPAddress(),".4");
							tempHDDSize3 = this.getListOID().get(i).getValue();
							
							this.getListOID().get(i+1).checkValue(this.getIPAddress(),".4");	
							tempHDDUsed3 = this.getListOID().get(i+1).getValue();
						}
						
						i++;
					}
					else
					{
						this.getListOID().get(i).checkValue(this.getIPAddress(),"");					
					}
				}				
			}
		}
		
		
		
		// **************  Determines total for RAMLoad, CPULoad, HDDLoad, TonerRemaining and bandwithLoad     **********
		System.out.println("  ********   SP Result  "+this.getName()+" *********");

		if(this.getType().equals("Imprimante") == false)
		{
			bandWidth = (float) ((((Long.parseLong(this.getListOID().get(0).getValue()))-tempifInOctetValue)+((Long.parseLong(this.getListOID().get(1).getValue()))-tempifOutOctetValue))/(timeSleep/1000));   /// 2 values are necessary to defines the bandwidth
			System.out.println("delta entre T1 et T2 sur 1 seconde :"+bandWidth);
			bandWidth = ((bandWidth * 8)  / Float.parseFloat(this.getListOID().get(2).getValue())) * 100; // calculate definitively bandwidth percentage - bandWidth multiply by 8 to get bit value
			System.out.println("Utilisation de la bande passante sur 1 seconde : "+bandWidth+"%");
	
			this.bandwidthLoad = bandWidth;
	
			if(this.getType().equals("Pare-feu") == false && this.getName().equals("Vigie NATIONAL") == false) // These both equipments haven't got additional OID
			{
				// For CPU Load
				if(this.getListOID().get(6).getValue().equals("0") == false)
				{
					System.out.println("Total charge des "+this.getListOID().get(3).getCountValues()+" multi-processor :"+this.getListOID().get(3).getValue());
					tempLoadCPU = (float) (Integer.parseInt(this.getListOID().get(3).getValue()))/this.getListOID().get(3).getCountValues();
				}
				else
				{
					System.out.println("Total charge des "+this.getListOID().get(3).getCountValues()+" multi-processor :"+this.getListOID().get(3).getValue());
					tempLoadCPU = 0;
				}			
			
				// For RAM Load
				tempLoadRAM = (float) (Integer.parseInt(this.getListOID().get(5).getValue()))/(Integer.parseInt(this.getListOID().get(4).getValue())) * 100;  
				
				// For HDD Load 
				if(tempHDDSize1 != null)
				{
					tempLoadHDD1 = (float) (Long.parseLong(tempHDDUsed1))/(Long.parseLong(tempHDDSize1)) * 100 ;
					System.out.println("HDD (C:) used percentage : "+tempLoadHDD1+"%");
				}
				
				if(tempHDDSize2 != null)
				{
					tempLoadHDD2 = (float) (Long.parseLong(tempHDDUsed2))/(Long.parseLong(tempHDDSize2)) * 100 ;
					System.out.println("HDD (D:) used percentage : "+tempLoadHDD2+"%");
				}
				if(tempHDDSize3 !=null)
				{
					tempLoadHDD3 = (float) (Long.parseLong(tempHDDUsed3))/(Long.parseLong(tempHDDSize3)) * 100 ;
					System.out.println("HDD (E:) used percentage : "+tempLoadHDD3+"%");
				}
			
				if(tempLoadHDD1>tempLoadHDD2)
				{
					if(tempLoadHDD1>tempLoadHDD3)
					{
						this.HDDLoad = tempLoadHDD1;
					}
					else
					{
						this.HDDLoad = tempLoadHDD3;
					}
				}
				else
				{
					if(tempLoadHDD2>tempLoadHDD3)
					{
						this.HDDLoad = tempLoadHDD2;
					}
					else
					{
						this.HDDLoad = tempLoadHDD3;
					}
				}
				
				System.out.println("CPU load: "+tempLoadCPU+"%");
				System.out.println("RAM load: "+tempLoadRAM+"%");
			
				
				this.CPULoad = tempLoadCPU;
				this.RAMLoad = tempLoadRAM;				
			}			
		}
		else
		{			
			if(this.getModel().equals("Konica C35"))
			{
				
				System.out.println("Niveau Toner cyan :"+printerLevelValue.get(0));
				System.out.println("Niveau Toner magenta :"+printerLevelValue.get(1));
				System.out.println("Niveau Toner yellow :"+printerLevelValue.get(2));
				System.out.println("Niveau Toner black :"+printerLevelValue.get(3));
				System.out.println("Niveau Unite imagerie Cyan :"+printerLevelValue.get(4));
				System.out.println("Niveau Unite imagerie Magenta :"+printerLevelValue.get(5));
				System.out.println("Niveau Unite imagerie Yellow :"+printerLevelValue.get(6));
				System.out.println("Niveau Unite imagerie Black :"+printerLevelValue.get(7));
				System.out.println("Niveau Reserve de toner usag� :"+printerLevelValue.get(11));

				// Looking for minimal value (toner and Image unit)
				for(int j=0;j<8;j++)
				{
					if(j>=0 && j<4)
					{
						if(j==0)
						{
							TonerRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j))) *100;
						}
						else
						{
							if((Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j))) *100 < TonerRemaining)
							{
								TonerRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j))) *100;	
							}						
						}
					}
					
					if(j>3 && j<8)
					{
						if(j==4)
						{
							ImagingUnitRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j)))*100;	
						}
						else
						{
							if((Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j)))*100<ImagingUnitRemaining)
							{
								ImagingUnitRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j)))*100;							
							}						
						}
					}
				}				
				WasteTonerState = Float.parseFloat(printerLevelValue.get(11));	
				
				System.out.println("\n Toner le plus faible :"+TonerRemaining);
				System.out.println("Unit� d'imagerie la plus faible :"+ImagingUnitRemaining);
				
				if(WasteTonerState==1)
				{
					System.out.println("Réserve de toner usagé: Presque plein");
				}
				else
				{
					System.out.println("Réserve de toner usagé : Niveau correct");
				}			
				
			}
			
			
			if(this.getModel().equals("Konica C364"))
			{

				
				System.out.println("Niveau Toner cyan :"+printerLevelValue.get(0));
				System.out.println("Niveau Toner magenta :"+printerLevelValue.get(1));
				System.out.println("Niveau Toner yellow :"+printerLevelValue.get(2));
				System.out.println("Niveau Toner black :"+printerLevelValue.get(3));
				System.out.println("Niveau Unite Tambour Cyan :"+printerLevelValue.get(4));
				System.out.println("Niveau Unite developpement Cyan :"+printerLevelValue.get(5));
				System.out.println("Niveau Unite Tambour Magenta :"+printerLevelValue.get(6));
				System.out.println("Niveau Unite developpement Magenta :"+printerLevelValue.get(7));
				System.out.println("Niveau Unite Tambour Yellow :"+printerLevelValue.get(8));
				System.out.println("Niveau Unite developpement Yellow :"+printerLevelValue.get(9));
				System.out.println("Niveau Unite Tambour Black :"+printerLevelValue.get(10));
				System.out.println("Niveau Unite developpement Black :"+printerLevelValue.get(11));
				System.out.println("Niveau Reserve de toner usag� :"+printerLevelValue.get(12));
				
				for(int j=0;j<4;j++)
				{
					
					if(j==0)
					{
						TonerRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j)))*100;
					}
					else
					{
						if(Float.parseFloat(printerLevelValue.get(j))<TonerRemaining)
						{
							TonerRemaining = (Float.parseFloat(printerLevelValue.get(j)) / Float.parseFloat(printerCapacityValue.get(j)))*100;	
						}						
					}
					
				}
					
				for(int j=4;j<11;j=j+2)
				{					
					if(j==4)
					{
						DrumCartridgeRemaining = Float.parseFloat(printerLevelValue.get(j));	
					}
					else
					{
						if(Float.parseFloat(printerLevelValue.get(j))<DrumCartridgeRemaining)
						{
							DrumCartridgeRemaining = Float.parseFloat(printerLevelValue.get(j));							
						}						
					}				
				}
				
				for(int j=5;j<12;j=j+2)
				{					
					if(j==5)
					{
						DevelopperCartridgeRemaining = Float.parseFloat(printerLevelValue.get(j));	
					}
					else
					{
						if(Float.parseFloat(printerLevelValue.get(j))<DevelopperCartridgeRemaining)
						{
							DevelopperCartridgeRemaining = Float.parseFloat(printerLevelValue.get(j));							
						}						
					}				
				}
				
				System.out.println("\n Toner le plus faible :"+TonerRemaining);
				System.out.println("Unit� tambour la plus faible :"+DrumCartridgeRemaining);
				System.out.println("Unit� d�veloppement la plus faible :"+DevelopperCartridgeRemaining);
					
				if(WasteTonerState==1)
				{
					System.out.println("Réserve de toner usagé : Presque plein");
				}
				else
				{
					System.out.println("Réserve de toner usagé : Niveau correct");
				}
			}		
			
			
		}
	}
	
	/**
	 * Checks if value founded are more important than threshold value
	 * @param listBugIntoDatabase all bug list from database
	 * @version 1.00
	 */
	public void checkAlert(ArrayList<Bug> listBugIntoDatabase)
	{	
		
	    boolean BugActive = false;

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.FRANCE);
		Date dateOfDay = new Date();		
		
		int indexBug = 0;
		System.out.println("************************************** CHECK ALERT");
		System.out.println("Name :"+this.getName());
		
	
		
		for(int i=0;i<listBugIntoDatabase.size();i++)
		{
			switch(listBugIntoDatabase.get(i).getID())
			{
			case 1: if(this.RAMLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
					break;
					
			case 2: if(this.RAMLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}						
					}
					break;
					
			case 3: if(this.HDDLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{
						if(BugActive == true)
						{
							indexBug++;
						}
						else
						{							
							BugActive = true;
						}
						
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(this.getListBug().size()-1).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
								this.getListBug().set(indexBug,listBugIntoDatabase.get(i));									
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));												
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}
						else
						{							
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}
					}
					break;
					
			case 4: if(this.HDDLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{					
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));						
						}													
					}
					break;
					
			case 5: if(this.CPULoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{
						if(BugActive == true)
						{
							indexBug++;
						}
						else
						{							
							BugActive = true;
						}
						
						if(this.getListBug().isEmpty() == false)
						{
							if(this.getListBug().get(this.getListBug().size()-1).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
								this.getListBug().set(indexBug,listBugIntoDatabase.get(i));								
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}
					}
					break;
					
			case 6: if(this.CPULoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{							
						
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
									
					}
					break;
					
					
			case 7: if(this.bandwidthLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							if(this.getListBug().get(this.getListBug().size()-1).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
								this.getListBug().set(indexBug,listBugIntoDatabase.get(i));								
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));
						}
					}
					break;
					
			case 8: if(this.bandwidthLoad >= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{
						
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());							
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));
								
							}
											
					}
					break;
					
			case 13: if(this.TonerRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
						{		
							BugActive = true;
							if(this.getListBug().isEmpty() == false)
							{								
								if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
								{
									
									this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
									this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
									this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
								}
								else
								{
									this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
								}
							}	
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
								this.getListBug().add(listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}							
						}
			break;
			
			case 14:if(this.TonerRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{									
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
							}									
					}
			break;
	/*		case 15: if(this.TonerRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
						{		
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
							}						
						}
			break;*/
			case 16: if(this.ImagingUnitRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
			break;
			case 17: if(this.ImagingUnitRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}						
					}
			break;
			
			case 18: if(this.WasteTonerState == (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
			break;
			case 19: if(this.WasteTonerState == (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
			break;
			
			case 23: if(this.DrumCartridgeRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
			break;
			case 24: if(this.DrumCartridgeRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}						
					}
			break;
			
			case 25: if(this.DevelopperCartridgeRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						BugActive = true;
						if(this.getListBug().isEmpty() == false)
						{
							
							if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
							{
								
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
								this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
								this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
							}
							else
							{
								this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());
							}
						}	
						else
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());	
							this.getListBug().add(listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));								
						}
						
					}
			break;
			case 26: if(this.DevelopperCartridgeRemaining <= (float)listBugIntoDatabase.get(i).getThresholdValue())
					{		
						
						if(this.getListBug().get(indexBug).getDetail() != listBugIntoDatabase.get(i).getDetail())
						{
							this.getLed().setID(listBugIntoDatabase.get(i).getIDcolor());						
							this.getListBug().set(indexBug, listBugIntoDatabase.get(i));	
							this.getListBug().get(indexBug).setDate(dateFormat.format(dateOfDay));							
						}						
					}
			break;
							
			default: 	
				
					break;			
			}			

		}
		
		if(this.getListBug().isEmpty() == false)
		{
			for(int j =0;j<this.getListBug().size();j++)
			{
				
				System.out.println("StrategicPoint name---> "+this.getName());
				System.out.println("Problem detected ! ---> "+this.getListBug().get(j).getDetail());
				System.out.println("List bug size -->" + this.getListBug().size());
				System.out.println("********************************************************************");
			}
		}
		else
		{
			if(BugActive == false)
			{
				this.getLed().setID((byte) 4);
				if(this.getListBug().isEmpty() == false)
				{
					this.getListBug().removeAll(this.getListBug());
				}
			}
		}
	}
	
	
	/////////////************************    Getters and Setters *************************
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public ArrayList<ObjectIdentifier> getListOID() {
		return listOID;
	}
	public void setListOID() {
		this.listOID = new ArrayList<ObjectIdentifier>();	
	}
	public float getRAMLoad() {
		return RAMLoad;
	}
	public void setRAMLoad(float rAMLoad) {
		RAMLoad = rAMLoad;
	}
	public float getHDDLoad() {
		return HDDLoad;
	}
	public void setHDDLoad(float hDDLoad) {
		HDDLoad = hDDLoad;
	}
	public float getCPULoad() {
		return CPULoad;
	}
	public void setCPULoad(float cPULoad) {
		CPULoad = cPULoad;
	}
	public float getBandwidthLoad() {
		return bandwidthLoad;
	}
	public void setBandwidthLoad(float bandwidthLoad) {
		this.bandwidthLoad = bandwidthLoad;
	}

	public float getTonerRemaining() {
		return TonerRemaining;
	}

	public void setTonerRemaining(float tonerRemaining) {
		TonerRemaining = tonerRemaining;
	}

	
	
}
