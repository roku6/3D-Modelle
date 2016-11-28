package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import obj.OBJ;
import obj.Vector4id;

public class BuildLogic 
{
	private static BuildLogic instance;
	
	private OBJ aOBJ;
	private ArrayList<Point<Float>> pointList;
	private ArrayList<Edge<Double>> edgeList;
	
	public void createOBJ(String filename)
	{
		aOBJ = new OBJ();
		aOBJ.load(filename);
	}
	public void createFigure()
	{
		createEdges();
		createConnections();
		calculateAngles();
		saveToDatabase();
	}
	
	private void removeDoubleVertecies()
	{
		Collections.sort(aOBJ.getVertexList());	
		Vector4id<Float> oldVertex = null;
		Vector4id<Float> actVertex = null;
				
		for (Iterator<Vector4id<Float>> iterator = aOBJ.getVertexList().iterator(); iterator.hasNext();)
		{
			if (actVertex == null) 
			{
				oldVertex = iterator.next();
				iterator.next();
			}
			else 
			{
				oldVertex = actVertex;
				actVertex = iterator.next();
			}
			for (int id = 0; id<aOBJ.getFacesNr(); id++)
			{
				int testVertexID = aOBJ.getFaceComponent(id,0);
				if (aOBJ.getVertex(testVertexID) == oldVertex) 
				{
					for (int x = 0; x<aOBJ.getFaceIndicesNr();x++)
					{
						aOBJ.setFaceComponent(x,0, actVertex.id);
					}	
					//Possible??? -> REMOVE_Last Operation?
					aOBJ.getVertexList().remove(actVertex.id);			
				}
			}			
		}		
	}
	
	private void createPointList()
	{
		for (int aIndex : aOBJ.getFaceArray1d())
		{
			pointList.add(getFace(aIndex));
		}
	}
	
	private void createEdges()
	{
		removeDoubleVertecies();
		createPointList();
		
		
	}
	
	
	
	private void createConnections()
	{
		
	}
	private void calculateAngles()
	{
		
	}
	private void saveToDatabase()
	{
		
	}
	
}
