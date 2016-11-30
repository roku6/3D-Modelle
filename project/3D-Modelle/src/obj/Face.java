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

public class Face 
{
	private static int number = 0;
	private int id = 0;
	private ArrayList<Point<Double>> pointList;
	
	public int getId() 								{return id;}
	public ArrayList<Point<Double>> getPointList() 	{return pointList;}
	
	public void setId(int id) 										{this.id = id;}
	public void setPointList(ArrayList<Point<Double>> pointList) 	{this.pointList = pointList;}

	Face(){}
	
	Face(ArrayList<Point<Double>> aPointList)
	{
		Face.number++;
		this.id = number;
		this.pointList = aPointList;
	}
	
	public String toString()
	{
		String aString = "" + this.getId() + "\t";
		for (Point<Double> aPoint : pointList)
		{
			aString += aPoint.toString() + "\t";
		}
		return aString;
	}
}
