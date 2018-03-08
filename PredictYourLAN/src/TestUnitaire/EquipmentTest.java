package TestUnitaire;

import static org.junit.Assert.*;

import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import Metier.Bug;
import Metier.Equipment;
import Metier.StrategicPoint;

public class EquipmentTest
{
	
	public int IDToDeleteAfterTest = 0;
	public int IDCreatedTest;
	
	@Before
	public void setUp() throws Exception 
	{
		
	}

	@After
	public void tearDown() throws Exception 
	{		
		Equipment TempEquipment = new Equipment ();	
		
		TempEquipment.setID(this.IDCreatedTest);
		
		TempEquipment.delete();
	}

	@Test
	public void testGetByCategory()
	{
		System.out.println("\n   testgetAll()");
		Equipment TempEquipment = new Equipment ();
		
		ArrayList<StrategicPoint> listEquipment = new ArrayList<StrategicPoint> ();
		
//		TempEquipment.getByCategory(listEquipment,3);
	
		if(listEquipment.size()==0)
		{
			fail("No Equipment founded");
		}	
	}
	
	@Test
	public void testgetListTypeAndModel()
	{
		System.out.println("\n   testgetListTypeAndModel()");
		
		Equipment TempEquipment = new Equipment ();		
		ArrayList<Equipment> listEquipment = new ArrayList<Equipment> ();
		
		TempEquipment.getListTypeAndModel(listEquipment);
	
		if(listEquipment.size()==0)
		{
			fail("No Equipment founded");
		}	
		
	}
	
	@Test
	public void testAdd()
	{
		System.out.println("\n   testAdd()");
		
		Equipment TempEquipment = new Equipment ();		
		
		TempEquipment.setName("Test unitaire ");
		TempEquipment.setDescription("desription");
		TempEquipment.setIcon(null);
		TempEquipment.setIPAddress("192.168.2.99");		
		TempEquipment.setType("Switch");	
		TempEquipment.setModel("HP 2620-48");		
		TempEquipment.getLed().setID((byte) 1);
		
		int confirmation = TempEquipment.add();
	
		if(confirmation == 1 )
		{
			fail("No Equipment created");
		}	
		else
		{			
			this.IDCreatedTest = TempEquipment.getID();			
		}
	}


	@Test
	public void testconvertImageToBase64()
	{
		System.out.println("\n   testconvertImageToBase64()");
		
		String encodedImage = "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";		
		Equipment TempEquipment = new Equipment ();	

		try
		{
			DatatypeConverter.parseBase64Binary(encodedImage);
		}
		catch(IllegalArgumentException e)
		{
			fail("string parameter does not conform to lexical value --> Datatypes for xsd:base64Binary");
		}
		
	}
	
	
	@Test
	public void testUpdate()
	{
		System.out.println("\n   testUpdate()");		
	
		Equipment TempEquipment = new Equipment ();	
		
		TempEquipment.setID(IDCreatedTest); // For test, we need equipment's ID.
		TempEquipment.setName("Test unitaire 2");  // Here's the change
		TempEquipment.setDescription("desription");
		TempEquipment.setIcon(null);
		TempEquipment.setIPAddress("192.168.2.99");		
		TempEquipment.setType("Desktop");	
		TempEquipment.setModel("Dell Z230");
		TempEquipment.getLed().setID((byte) 1);
		
		int confirmation = TempEquipment.update();	
		
		
		if(confirmation == 1 )
		{
			fail("No StrategicPoint updated");
		}	
	}
	
	@Test
	public void testCreateBugIntoDatabase()
	{
		System.out.println("\n   createBugIntoDatabase()");		
		
		Equipment TempEquipment = new Equipment ();	
		TempEquipment.setListBug();
		TempEquipment.getListBug().add(new Bug());
		TempEquipment.getListBug().get(0).setDate("2017-01-17");
		TempEquipment.getListBug().get(0).setID(12);
		TempEquipment.setID(1);
		
		int confirmation = TempEquipment.createBugIntoDatabase();		

		if(confirmation == 1 )
		{
			fail("No bug created");
		}
	
	}
	
	
	@Test
	public void testDetectSP()
	{
		System.out.println("\n   testDetectSP()");
		
		Bug tempBug = new Bug();
		ArrayList<Bug> listBugsIntoDatabase = new ArrayList<Bug>();	
		tempBug.getAll(listBugsIntoDatabase);
		
		// For test, enter a real hostname present into our domain otherwise test failed (UnknownHostException)		
		Equipment TempEquipment = new Equipment ();	
		TempEquipment.setName("STA6101856");
		TempEquipment.detectSP(listBugsIntoDatabase);		
		
		System.out.println("ID du led --> "+TempEquipment.getLed().getID());			
	}
	



}