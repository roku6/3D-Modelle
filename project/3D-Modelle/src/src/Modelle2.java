//Filename als File

package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public  class Modelle2
{
	private static Modelle2 instance = null;
	private int width, height;
	private double lenInt, angInt;

	
	public static Modelle2 getInstance()
	{
		if (Modelle2.instance == null) Modelle2.instance = new Modelle2(); 
		return Modelle2.instance;
	}
	
	public static void main(String [ ] args)
	{
		
		
		BuildLogic aBuildLogic = BuildLogic.getBuildLogic();
		
	//	aBuildLogic.createOBJ("../../resources/cube_100x100x100_Subdivided.obj");
		
		//aBuildLogic.createFigure();
		//System.out.println(aBuildLogic.getaOBJ().toString());
		
		
		java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	
                new GUI().setVisible(true);
            }
        });
		
		
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