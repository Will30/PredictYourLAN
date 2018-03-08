package Metier;


import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.SwingWorker;

import Presentation.MainWindow;
import Presentation.SecondaryWindow;



public class Clock extends SwingWorker<Object ,String>
{
	private MainWindow mainPanel;
	private SecondaryWindow panelSP;
	private ArrayList<Category> listCategorys;
	private  ArrayList<ArrayList<StrategicPoint>> listSPsByCategorys;
	
	/**
	 * @param mainPanel Mainpanel display which needs to be updated
	 * @param panelSP strategic point panel display which needs to be updated
	 * @param listCategorys category list
	 * @param listSPsByCategorys List of strategic point by category
	 * @since 1.00
	 */
	public Clock(MainWindow mainPanel, SecondaryWindow panelSP, ArrayList<Category> listCategorys, ArrayList<ArrayList<StrategicPoint>> listSPsByCategorys)
	{       
		this.mainPanel = mainPanel;
		this.panelSP = panelSP;
		this.listCategorys =  listCategorys;
		this.listSPsByCategorys = listSPsByCategorys;
	}

	/**
	 * Changes color of LED present on panel. If MainPanel is swhowing, this is the category's LED will change, otherwise, if it's the panel SP, this is strategic point's LED will change
	 * @param Name strategic point name
	 * @param newImageUNC LED image UNC from database (updated by PredictYourlAN server)
	 * @param oldImageUNC LED image UNC from application (actually LED)
	 */
	public void updateLEDPanel(String Name,String newImageUNC,String oldImageUNC)
	{
		// Event for Main SP
		if(this.mainPanel.isShowing() == true)
		{
			//Looks for the category in MainPanel display			
			if(this.mainPanel.getLabelName1().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed1(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed1(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName2().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed2(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed2(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName3().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed3(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed3(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName4().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed4(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed4(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName1().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed5(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed5(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName6().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed6(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed6(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.mainPanel.getLabelName7().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.mainPanel.setLabelLed7(new ImageIcon(MainWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.mainPanel.setLabelLed7(new ImageIcon(MainWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
		}
		
		// Event for Panel SP
		if(this.panelSP.isShowing() == true)
		{
			//Looks for SP in PanelSP display			
			if(this.panelSP.getLabelName1().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed1(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed1(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			
			if(this.panelSP.getLabelName2().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed2(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed2(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.panelSP.getLabelName3().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed3(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed3(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.panelSP.getLabelName4().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed4(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed4(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.panelSP.getLabelName5().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed5(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed5(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.panelSP.getLabelName6().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed6(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed6(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}
			if(this.panelSP.getLabelName7().getText().equals(Name))
			{
				int i = 0;
				do
				{					
					try 
					{
						this.panelSP.setLabelLed7(new ImageIcon(SecondaryWindow.class.getResource(oldImageUNC)));
						Thread.sleep(500);
						this.panelSP.setLabelLed7(new ImageIcon(SecondaryWindow.class.getResource(newImageUNC)));
						Thread.sleep(500);
					} 
					catch (InterruptedException e) 
					{						
						e.printStackTrace();
					}
				i++;
				}while(i>5);				
			}	
		}
	}
	
	
	@Override
	protected Object doInBackground() throws Exception 
	{
		System.out.println("Lancement de la détection des changements des voyants");
		
		// 1- Get all strategic point and list of category from database into temporary variable
		ArrayList<Category> tempListCategorys = new ArrayList<Category> ();
		ArrayList<ArrayList<StrategicPoint>> tempListSPsByCategorys = new ArrayList<ArrayList<StrategicPoint>>();
		
		Equipment tempEquipment = new Equipment();
		Network tempNetwork  = new Network();		
		DataStorage tempDataStorage = new DataStorage();
		Category tempCategory = new Category();
		
		tempCategory.getAll(tempListCategorys);		
		

		for(int i=0;i<tempListCategorys.size();i++)
		{	
			tempListSPsByCategorys.add(new ArrayList<StrategicPoint>());		
			
			tempEquipment.getByCategory(tempListSPsByCategorys.get(i),this.listCategorys.get(i));										  
			tempNetwork.getByCategory(tempListSPsByCategorys.get(i),this.listCategorys.get(i));
			tempDataStorage.getByCategory(tempListSPsByCategorys.get(i),this.listCategorys.get(i));	
		}
		
		// 2- compare with actually data (list of strategic point and list of category)		
		for(int i=0;i<tempListCategorys.size();i++)
		{
			if(tempListCategorys.get(i).getLed().getID() != this.listCategorys.get(i).getLed().getID())
			{
				updateLEDPanel(tempListCategorys.get(i).getName(),tempListCategorys.get(i).getLed().getUNC(),this.listCategorys.get(i).getLed().getUNC());
				
				this.listCategorys.get(i).getLed().setUNC(tempListCategorys.get(i).getLed().getUNC());
				this.listCategorys.get(i).getLed().setID(tempListCategorys.get(i).getLed().getID());
				this.listCategorys.get(i).getLed().setColor(tempListCategorys.get(i).getLed().getColor());
			}
		}
		
		for(int i=0;i<tempListCategorys.size();i++)
		{

			for(int j=0;j<tempListSPsByCategorys.get(i).size();j++)
			{

				for(int k=0;k<tempListSPsByCategorys.get(i).size();k++)
				{

					if(tempListSPsByCategorys.get(i).get(j).getName().equals(this.listSPsByCategorys.get(i).get(k).getName()))
					{
						if(tempListSPsByCategorys.get(i).get(j).getLed().getID() != this.listSPsByCategorys.get(i).get(k).getLed().getID())
						{
							System.out.println(" *********************************           Mise à jour de l'affichage - changement de couleur sur un point stratégique !!!");
							updateLEDPanel(tempListSPsByCategorys.get(i).get(j).getName(),tempListSPsByCategorys.get(i).get(j).getLed().getUNC(),this.listSPsByCategorys.get(i).get(k).getLed().getUNC());
							
							this.listSPsByCategorys.get(i).get(k).getLed().setUNC(tempListSPsByCategorys.get(i).get(j).getLed().getUNC());
							this.listSPsByCategorys.get(i).get(k).getLed().setID(tempListSPsByCategorys.get(i).get(j).getLed().getID());
							this.listSPsByCategorys.get(i).get(k).getLed().setColor(tempListSPsByCategorys.get(i).get(j).getLed().getColor());
						}
						break;
					}						
				}
				
			}			
		}

		return null;
	}
}
