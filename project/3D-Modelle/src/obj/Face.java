/** 
 * Face.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Face Klasse - Speichert Facesformat einer OBJ Datei
 *  Dependencies:
 *  	Point - Klasse (obj) 
 *  	
 *  	java.util.ArrayList
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  TODO: Nichts! Fertig!
 */

package obj;

import java.util.ArrayList;

/**
 * @author Login
 *
 */
public class Face 
{
	private static int number = 0;
	private int id = 0;
	private ArrayList<PointExt<Double>> pointExtList;
	
	public int getId() 										{return id;}
	public ArrayList<PointExt<Double>> getPointExtList() 	{return pointExtList;}
	
	public void setId(int id) 										{this.id = id;}
	public void setPointList(ArrayList<PointExt<Double>> pointList) {this.pointExtList = pointList;}

	Face(){}
	
	Face(ArrayList<PointExt<Double>> aPointExtList)
	{
		Face.number++;
		this.id = number;
		this.pointExtList = aPointExtList;
	}
	
	public String toString()
	{
		String aString = "" + this.getId() + "\t";
		for (PointExt<Double> aPoint : pointExtList)
		{
			aString += aPoint.toString() + "\t";
		}
		return aString;
	}
}
