/**
 * Main Class for the 3D-Model project
 * 
 * @author Lennard Flegel, Robert Külpmann, Ekaterina Kuzminykh
 */

package src;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.neo4j.graphdb.Result;

@SuppressWarnings("unused")
public class Modelle
{

private static Modelle instance = null;
private int width, height;
private double lenInt, angInt;
private File dbpath = new File("../../database");
private DBController dbCtrl = DBController.getInstance(dbpath);	

public static Modelle getInstance()
{
	if (Modelle.instance == null) Modelle.instance = new Modelle(); 
	return Modelle.instance;
}
	public static void main(String [ ] args)
	{
		

//	}
		
//		//-------------------------------Starting GUI
		
		System.out.println("Starting GUI...");
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GUI().setVisible(true);
			}
		});
		
/*		Just for testing 
		ArrayList<String[]> urlList = new ArrayList<>(); 
		
		long tStart = System.currentTimeMillis(); //to keep track of time

		/**
		 * Initializing BuildLogic and DBController
		 */
		/*
		System.out.print("Creating BUILDLogic...");
		BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
		System.out.println("finished.");
		System.out.print("Loading DBController...");
		File dbpath = new File("../../database");
		DBController dbCtrl = DBController.getInstance(dbpath);	
		System.out.println("finished.");
		dbCtrl.clearAll();
		
		//Initialize aFigure for Testing
		String[] urls	= {"../../resources/cube_hole_100x100x100.obj", 
		              	   "../../resources/cube_100x100x100.txt", 
		              	   "../../resources/cube_100x100x100.png"};
		urlList.add(urls);
				
		for (String[] url : urlList)
		{
			String objURL = url[0];
			String descriptionURL = url[1];
			String pictureURL = url[2];
			System.out.print("Build GeometricFigure from File " + objURL + "...");
			aBuildLogic.buildAFigure(objURL,descriptionURL,pictureURL);
			System.out.println("finished.");
			aBuildLogic.getGeometricFigure().setObjectID(1);
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
*/
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
	
	
	public void search( List<Searchobject> searchList, double angInt, double lenInt, int width, int height) {
		SearchLogic theSearchLogic = SearchLogic.getInstance(new ArrayList<Searchobject>(searchList), lenInt,angInt);
		String cypher = theSearchLogic.generateQuery();
		displayOutput(theSearchLogic.calcSimilarity(dbCtrl.executeQuery(cypher)));
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
		dbCtrl.removeByOBJ_ID(Integer.parseInt(id));
		}
	
	public void buildObjects(List<String[]> namesList) {
		// TODO Auto-generated method stub
		
	}
	
	public DBController getDBController(){
		return dbCtrl;
	}
 
}