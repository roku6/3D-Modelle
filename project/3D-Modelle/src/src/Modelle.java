/**
 * Main Class for the 3D-Model project
 * 
 * @author Lennard Flegel, Robert Külpmann, Ekaterina Kuzminykh
 */

package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Modelle
{

private static Modelle instance = null;
private int width, height;
private double lenInt, angInt;

public static Modelle getInstance()
{
	if (Modelle.instance == null) Modelle.instance = new Modelle(); 
	return Modelle.instance;
}
	public static void main(String [ ] args)
	{
		
		//-------------------------------Starting GUI
		/*
		System.out.println("Starting GUI...");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
		*/
		
		// Just for testing 
		ArrayList<String[]> urlList = new ArrayList<>(); 
		
		long tStart = System.currentTimeMillis(); //to keep track of time

		/**
		 * Initializing BuildLogic and DBController
		 */
		System.out.print("Creating BUILDLogic...");
		BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
		System.out.println("finished.");
		System.out.print("Loading DBController...");
		File dbpath = new File("../../database");
		DBController dbCtrl = DBController.getInstance(dbpath);	
		System.out.println("finished.");
		
		//Initialize aFigure for Testing
		String[] urls	= {"../../resources/cube_100x100x100_Subdivided.obj", 
		              	   "../../resources/cube_100x100x100.txt", 
		              	   "../../resources/cube_100x100x100.png"};
		urlList.add(urls);
				
		for (String[] url : urlList)
		{
			String objURL = url[0];
			String pictureURL = url[1];
			String descriptionURL = url[2];
			System.out.print("Build GeometricFigure from File " + objURL + "...");
			aBuildLogic.buildAFigure(objURL,descriptionURL,pictureURL);
			System.out.println("finished.");
			try
			{
				System.out.print("Writing OBJ to Database...");
				dbCtrl.writeObjToDB(aBuildLogic.getGeometricFigure());
				System.out.println("finished.");
			}	 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("WriteObjToDB() failed");
				dbCtrl.shutdownDB();
			}
		}				
		//dbCtrl.executeQuery("Create (n{OBJECT_ID : 5})");
		dbCtrl.removeByOBJ_ID(5);
		dbCtrl.shutdownDB();
		
		long tEnd = System.currentTimeMillis();
		long tDelta = tEnd - tStart;
		double elapsedSeconds = tDelta / 1000.0;
		
		System.out.println("Program completed in " + elapsedSeconds + " Seconds with " + urlList.size() + " Objects.");
	}
	
	
	public void search( List<Searchobject> searchList, double angInt, double lenInt, int width, int height) {
		//Searchlogic... searchList, angInt, lenInt
		this.width = width;
		this.height = height;
		this.lenInt = lenInt;
		this.angInt = angInt;
	}
	
	//to be used by searchlogic after finished search
	public void displayOutput(List<Foundobject> foundList) {
		outputGUI outg = new outputGUI(foundList, lenInt,angInt, width, height);
	}
	
	public void deleteObjFromDB(String id) {
		//dbcontroller remove obj
	}

 
}