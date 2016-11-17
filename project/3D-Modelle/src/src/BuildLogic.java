package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import obj.OBJ;
import obj.Vector4;

public class BuildLogic 
{
	private static BuildLogic instance;
	
	private OBJ aOBJ;
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
	
	private void createEdges()
	{
		Collections.sort(aOBJ.getVertexList());	
		Vector4<Float> oldVertex = null;
		Vector4<Float> actVertex = null;
		
		for (Iterator<Vector4<Float>> iterator = aOBJ.getVertexList().iterator(); iterator.hasNext();)
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
			while (actVertex == oldVertex)       
			{
				for (aFaceaOBJ.getFaceArray() do
				if (flaeche.vertex[x] == vertex[i+1]) flaeche.vertex[x] = vertex[i]
				vertecies.pop_back(vertex[i+1])
			end
		      end
		    end
			}
	
		}					
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
