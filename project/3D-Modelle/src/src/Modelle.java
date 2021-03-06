/**
 * Main Class Modelle:
 * Mainclass for the 3D-Model project
 * 
 * @author Lennard Flegel, Robert K�lpmann, Ekaterina Kuzminykh
 */

package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.neo4j.graphdb.Result;

@SuppressWarnings("unused")
public class Modelle
{

private static Modelle instance = null;
private int width, height;
private double lenInt, angInt;
private static DBController dbCtrl;
private static SearchLogic theSearchLogic = SearchLogic.getInstance();
private static BuildLogic aBuildLogic;

public static Modelle getInstance()
{
	if (Modelle.instance == null) Modelle.instance = new Modelle(); 
	return Modelle.instance;
}
	public static void main(String [ ] args)
	{
		long tStart = System.currentTimeMillis(); //to keep track of time
		
		/**
		 * Initializing BuildLogic and DBController
		 */		
		System.out.print("Creating BUILDLogic...");
		aBuildLogic = BuildLogic.getBuildLogic();
		System.out.println("finished.");
		System.out.print("Loading DBController...");
		File dbpath = new File("../../database");
		dbCtrl = DBController.getInstance(dbpath);	
		System.out.println("finished.");
		//dbCtrl.clearAll();
		
		dbCtrl.printOBJAmount();
		
//		//-------------------------------Starting GUI
		
		System.out.println("Starting GUI...");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new InputGUI().setVisible(true);
			}
		});
		
		//vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvTESTING SEARCHLOGICvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv
//		
//		ArrayList<Searchobject> aSObjList = new ArrayList<Searchobject>();
//		
//		Searchobject sObj1 = new Searchobject(1,100.0,90.0,2);
//		Searchobject sObj2 = new Searchobject(2,100.0,90.0,4);
//		//Searchobject sObj3 = new Searchobject(4,100.0,90.0,2);
//		
//		aSObjList.add(sObj1);
//		aSObjList.add(sObj2);
//		
//		SearchLogic theSearchLogic = SearchLogic.getInstance(aSObjList, 1., 1.);
//		
//		String cypher = theSearchLogic.generateQuery();
//		System.out.print("Starting query...");
//		Result aResult = dbCtrl.executeQuery(cypher);
//		System.out.println("done.");
//		
//		ArrayList<Foundobject> foundObjects = theSearchLogic.calcSimilarity(aResult);
//		
//		for(Foundobject fobj: foundObjects){
//			System.out.println(fobj.toString());
//		}
//		
		//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^TESTING SLOGIC END^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		
//		dbCtrl.shutdownDB();
		
//		long tEnd = System.currentTimeMillis();
//		long tDelta = tEnd - tStart;
//		double elapsedSeconds = tDelta / 1000.0;
//		
//		System.out.println("Program completed in " + elapsedSeconds + " Seconds with " + urlList.size() + " Objects.");
//		
//		
	}
	
	
	/**
	 * 
	 * @param searchList
	 * @param angInt
	 * @param lenInt
	 * @param width
	 * @param height
	 */
	public void search( List<Searchobject> searchList, double angInt, double lenInt, int width, int height) {
		this.width = width;
		this.height = height;
		this.lenInt = lenInt;
		this.angInt = angInt;
		
		theSearchLogic.setToleranceAngle(angInt);
		theSearchLogic.setToleranceLength(lenInt);
		theSearchLogic.setSearchObjects((ArrayList<Searchobject>) searchList);
		String cypher = theSearchLogic.generateQuery();
		displayOutput(theSearchLogic.calcSimilarity(dbCtrl.executeQuery(cypher)));

	}
	
	//to be used by searchlogic after finished search
	/**
	 * 
	 * @param foundList
	 */
	public void displayOutput(List<Foundobject> foundList) {
		OutputGUI outg = new OutputGUI(foundList, lenInt,angInt, width, height);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteObjFromDB(String id) {
		dbCtrl.removeByOBJ_ID(id);
		dbCtrl.printOBJAmount();
		}
	
	/**
	 * 
	 * @param namesList
	 */
	public void buildObjects(List<String[]> namesList) 
	{				
		int id = 0;
		for (String[] url : namesList)
		{
			String objURL = url[0];
			String descriptionURL = url[1];
			String pictureURL = url[2];
			System.out.print("Build GeometricFigure from Files " + objURL + "...");
			if (aBuildLogic.buildAFigure(objURL,descriptionURL,pictureURL))
			{
				System.out.println("finished.");
			
				if (!dbCtrl.getUsedIds().isEmpty())		
					aBuildLogic.getGeometricFigure().setObjectID(Collections.max(dbCtrl.getUsedIds()) + 1);
				else 									
					aBuildLogic.getGeometricFigure().setObjectID(1);
			
				try
				{
					System.out.print("Writing OBJ to Database...");
					dbCtrl.writeObjToDB(aBuildLogic.getGeometricFigure());
					System.out.println("finished.");
					dbCtrl.printOBJAmount();
					System.out.print("Clear BuildLogic...");
					aBuildLogic.clearAll();
					System.out.println("finished.");
				}	 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("WriteObjToDB() failed");
					dbCtrl.shutdownDB();
				}
			}
			else System.out.println("Error building geometric Figure!");
		}								
	}
	
	/**
	 * 
	 * @return
	 */
	public DBController getDBController(){
		return dbCtrl;
	}
 
}