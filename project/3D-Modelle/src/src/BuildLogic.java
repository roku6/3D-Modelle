/**
 * DONE: 
 * 	Erased same values (vertecies, textures, normals)
 * 	Filled the pointList with every single Point (every Point just once)
 * TODO: Create EdgeList, createConnections, createAngles etc... + remove duplicates (later)
 */

package src;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

import obj.Normal;
import obj.OBJ;
import obj.Point;
import obj.Texture;
import obj.Vector4id;
import obj.Vertex;

public class BuildLogic 
{
	private static BuildLogic instance = null;
	
	private OBJ aOBJ;
	private ArrayList<Point<Double>> pointList = new ArrayList<Point<Double>>();
	private ArrayList<Edge<Double>> edgeList = new ArrayList<Edge<Double>>();
	
	public OBJ getaOBJ() 							{return aOBJ;}
	public ArrayList<Point<Double>> getPointList() 	{return pointList;}
	public ArrayList<Edge<Double>> getEdgeList() 	{return edgeList;}
	
	public void setaOBJ(OBJ aOBJ) 									{this.aOBJ = aOBJ;}
	public void setPointList(ArrayList<Point<Double>> pointList) 	{this.pointList = pointList;}
	public void setEdgeList(ArrayList<Edge<Double>> edgeList) 		{this.edgeList = edgeList;}

	private BuildLogic(){}
	
	public static BuildLogic getBuildLogic()
	{
		if (BuildLogic.instance == null) BuildLogic.instance = new BuildLogic(); 
		return BuildLogic.instance;
	}
	
	
	/**
	 * creates OBJ File
	 * @param filename
	 */
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
		Vector4id<Double> oldVector4id = null;	//lastVertex (Vertex[i-1])
		Vector4id<Double> actVector4id = null;	//activeVertex (Vertex[i])
		//Iteration over the list of Vertecies
		Iterator<Vertex<Double>> iterator = aOBJ.getVertexList().iterator();
		while (iterator.hasNext())
		{
			//saves last activeVertex in oldVertex and changes activeVertex to the next Vertex
			if (actVector4id == null) 
			{
				oldVector4id = iterator.next();
				actVector4id = iterator.next();
			}
			else 
			{
				oldVector4id = actVector4id;
				actVector4id = iterator.next();
			}
			
			if (Double.compare(oldVector4id.getX(), actVector4id.getX()) == 0 && 
				Double.compare(oldVector4id.getY(), actVector4id.getY()) == 0 &&
				Double.compare(oldVector4id.getZ(), actVector4id.getZ()) == 0)
			{
				//Iteration over all Faces
				for (int i = 0; i<aOBJ.getFaceList().size(); i++)
				{
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointList().get(j).getAVertex().getId() == actVector4id.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointList().get(j).getAVertex().setId(oldVector4id.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}
	
	private void removeDoubleTextures()
	{
		
		Texture<Double> oldTexture = null;	//lastVertex (Vertex[i-1])
		Texture<Double> actTexture = null;	//activeVertex (Vertex[i])
		//Iteration over the list of Vertecies
		Iterator<Texture<Double>> iterator = aOBJ.getTextureList().iterator();
		while (iterator.hasNext())
		{
			//saves last activeVertex in oldVertex and changes activeVertex to the next Vertex
			if (actTexture == null) 
			{
				oldTexture = iterator.next();
				actTexture = iterator.next();
			}
			else 
			{
				oldTexture = actTexture;
				actTexture = iterator.next();
			}
			
			if (Double.compare(oldTexture.getX(), actTexture.getX()) == 0 && 
				Double.compare(oldTexture.getY(), actTexture.getY()) == 0 &&
				Double.compare(oldTexture.getZ(), actTexture.getZ()) == 0)
			{
				//Iteration over all Faces
				for (int i = 0; i<aOBJ.getFaceList().size(); i++)
				{
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointList().get(j).getATexture().getId() == actTexture.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointList().get(j).getATexture().setId(oldTexture.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}

	private void removeDoubleNormals()
	{		
		Vector4id<Double> oldVector4id = null;	//lastVertex (Vertex[i-1])
		Vector4id<Double> actVector4id = null;	//activeVertex (Vertex[i])
		//Iteration over the list of Vertecies
		Iterator<Normal<Double>> iterator = aOBJ.getNormalList().iterator();
		while (iterator.hasNext())
		{
			//saves last activeVertex in oldVertex and changes activeVertex to the next Vertex
			if (actVector4id == null) 
			{
				oldVector4id = iterator.next();
				actVector4id = iterator.next();
			}
			else 
			{
				oldVector4id = actVector4id;
				actVector4id = iterator.next();
			}
			
			if (Double.compare(oldVector4id.getX(), actVector4id.getX()) == 0 && 
				Double.compare(oldVector4id.getY(), actVector4id.getY()) == 0 &&
				Double.compare(oldVector4id.getZ(), actVector4id.getZ()) == 0)
			{
				//Iteration over all Faces
				for (int i = 0; i<aOBJ.getFaceList().size(); i++)
				{
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointList().get(j).getANormal().getId() == actVector4id.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointList().get(j).getANormal().setId(oldVector4id.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}

	/**
	 * Sorts the VertexList/TextureList/NormalList of an OBJ File
	**/
	private void sortAll()
	{
		Collections.sort(aOBJ.getVertexList());	
		Collections.sort(aOBJ.getTextureList());	
		Collections.sort(aOBJ.getNormalList());	
	}
		
	private void createPointSet()
	{
		boolean elementExists = false;
		for (int i = 0; i<aOBJ.getFaceList().size(); i++)
		{
			for (Point<Double> aPoint : aOBJ.getFaceList().get(i).getPointList())
			{
				if (pointList.size() != 0)
				{
					ListIterator<Point<Double>> iterator = pointList.listIterator();
					elementExists = false;
					while (iterator.hasNext())
					{
						Point<Double> aTestPoint = iterator.next();
						if ((aTestPoint.getAVertex().getId() == aPoint.getAVertex().getId()) &&
							(aTestPoint.getATexture().getId() == aPoint.getATexture().getId()) &&
							(aTestPoint.getANormal().getId() == aPoint.getANormal().getId()))
						{	
							elementExists = true;
						}
					}
					if (!elementExists) pointList.add(aPoint);
				}
				else pointList.add(aPoint);
			}
		}
	}
	
	private void createEdges()
	{
		sortAll();
		removeDoubleVertecies();
		removeDoubleTextures();
		removeDoubleNormals();
		createPointSet();
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
