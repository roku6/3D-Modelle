/**
 * DONE: 
 * 	Erased same values (vertecies, textures, normals, faces)
 * 	Filled the pointList with every single Point (every Point just once)
 *  Added all neighbours to every single Point
 *  Created a List of cornerPoints
 *  Calculated angles to cornerPoints
 *  Connected cornerPoints with each other(lineTracking)
 *  Created edges 
 *  Filled RelationDefinition
 * TODO: look for possible Errors
 * BUGS: a bug occured with generated Cube
 * 
 * @author Robert Külpmann
 * 
 */

package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.ListIterator;

import obj.Face;
import obj.Normal;
import obj.OBJ;
import obj.Operation;
import obj.Point;
import obj.PointExt;
import obj.Texture;
import obj.Vector4id;
import obj.Vertex;
import obj.VertexNormal;

public class BuildLogic 
{
	private static BuildLogic instance = null;
	
	private OBJ aOBJ;
	private GeometricFigure aFigure;
	private ArrayList<PointExt<Double>> pointExtList = new ArrayList<>();
	private ArrayList<Edge<Double>> edgeList = new ArrayList<>();
	private ArrayList<RelationsDefinition> aRelationsDefinitionList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> aCornerVertexList = new ArrayList<>();
	private ArrayList<PointExt<Double>> cornerPointExtList = new ArrayList<>();
	
	public OBJ getaOBJ() 								{return aOBJ;}
	public GeometricFigure getGeometricFigure()			{return aFigure;}
	public ArrayList<PointExt<Double>> getPointExtList(){return pointExtList;}
	public ArrayList<Edge<Double>> getEdgeList() 		{return edgeList;}
	
	
	public void setaOBJ(OBJ aOBJ) 										{this.aOBJ = aOBJ;}
	public void setPointExtList(ArrayList<PointExt<Double>> pointList) 	{this.pointExtList = pointList;}
	public void setEdgeList(ArrayList<Edge<Double>> edgeList) 			{this.edgeList = edgeList;}

	private BuildLogic(){}
	
	/**
	 * Singleton Pattern implementation
	 * @return the only instance of the BuildLogic
	 */
	public static BuildLogic getBuildLogic()
	{
		if (BuildLogic.instance == null) BuildLogic.instance = new BuildLogic(); 
		return BuildLogic.instance;
	}
		
	/**
	 * creates OBJ File
	 * @param filename
	 */
	private void loadOBJ(String filename)
	{
		aOBJ = new OBJ();
		aOBJ.load(filename);
		
		//System.out.println(aOBJ.toString());
	}
		
	/**
	 * This function reads a Filename of a Description file and returns the Description as a String
	 * 
	 * @param descriptionURL
	 * @return description
	 */
	private String loadDescription(String descriptionURL)
	{
		String s = "", description = "";
		if (descriptionURL == null)
		{
			System.out.println("Can't load File! No Description File available.");
			description = "No Description available.";
			return description;						
		}
		try
		{
			File aFile = new File(descriptionURL);
			FileReader fr = new FileReader(aFile);
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null)
			{
				description += s;
			}
			br.close();
			fr.close();
			System.out.println(description);
			return description;
		}
		catch(IOException e)
		{
			System.out.println("Can't load File! Error in File!");
			description = "No Description available.";
			return description;
		}
	}
	
	private void createCornerVertexList()
	{
		boolean addVertex = true;
		for (PointExt<Double> aCorner : cornerPointExtList)
		{
			addVertex = true;
			for(VertexNormal<Double> aVN : aCornerVertexList)
			{
				if (aVN.getVertex().equals(aCorner.getAVertex()))
				{
					addVertex = false;
					if (!aVN.getNormalMap().containsKey(aCorner.getANormal()))
					{
						aVN.getNormalMap().put(aCorner.getANormal(), 0.0);					
					}
				}
			}
			if (addVertex)
			{
				VertexNormal<Double> aVertexNormal= new VertexNormal<Double>(aCorner.getAVertex());
				aVertexNormal.getNormalMap().put(aCorner.getANormal(), 0.0);
				aCornerVertexList.add(aVertexNormal);
			}
		}
	}
	
	private void removeWrongPlanes()
	{
		ListIterator<VertexNormal<Double>> iter = aCornerVertexList.listIterator();
		while(iter.hasNext())
		{
			VertexNormal<Double> aVertexNormal = iter.next();
			if (aVertexNormal.getNormalMap().size() < 3) 
			{
				ListIterator<PointExt<Double>> iter2 = cornerPointExtList.listIterator();
				while(iter2.hasNext())
				{
					PointExt<Double> aCorner = iter2.next();
					if(aVertexNormal.getVertex().equals(aCorner.getAVertex()))
					{
						iter2.remove();
					}
				}
				iter.remove();
			}
		}
	}
	
	/**
	 * Public Function which will load an Object and create the GeometricFigure from it
	 * 
	 * @param objURL
	 * @param descriptionURL
	 * @param pictureURL
	 */
	public void buildAFigure(String objURL, String descriptionURL, String pictureURL)
	{
		loadOBJ(objURL);
		createFigure(descriptionURL, pictureURL);
	}	
	/**
	 * This function creates the Geometric Figure and saves it in the global geometricFigure variable
	 * 
	 * @param descriptionURL
	 * @param pictureURL
	 */
	private void createFigure(String descriptionURL, String pictureURL)
	{
		sortAll();
		removeDoubles();
		
		fillCornerList();
		updateCornerList();
		removeDoubleLines();
		createCornerVertexList();
		//removeWrongPlanes();

		fillRDL();
		//System.out.println();
		//System.out.println(aRelationsDefinitionList.toString());
		aFigure = new GeometricFigure(aRelationsDefinitionList, loadDescription(descriptionURL), pictureURL);
	}
	
	/**
	 * Sorts the VertexList/TextureList/NormalList of an OBJ File
	**/
	private void sortAll()
	{
		Collections.sort(aOBJ.getVertexList());	
		Collections.sort(aOBJ.getTextureList());	
		Collections.sort(aOBJ.getNormalList());	
		Collections.sort(aOBJ.getPointList());
	}
	
	private PointExt<Double> getCornerPoint(PointExt<Double> lastPoint, PointExt<Double> actPoint)
	{
		Vector4id<Double> direction = createDirectionVector(lastPoint, actPoint);
		for(PointExt<Double> aNeighbour:actPoint.getNeighbourList())
		{
			Vector4id<Double> direction2 = createDirectionVector(actPoint, aNeighbour);
			if (direction.equals(direction2))
			{
				for(PointExt<Double> aCornerPoint:cornerPointExtList)
				{
					if (aNeighbour.equals(aCornerPoint)) 
					{
						return aNeighbour;
					}
				}
				return getCornerPoint(actPoint, aNeighbour);
			}
		}
		return null;
	}
	
	private void updateCornerList()
	{
		boolean pointAdded = false;
		for(PointExt<Double> aCornerPoint : cornerPointExtList)
		{
			ArrayList<PointExt<Double>> aNewNeighbourList = new ArrayList<>();
			for(PointExt<Double> aNeighbour: aCornerPoint.getNeighbourList())
			{
				pointAdded = false;
				for(PointExt<Double> aCornerPoint2 : cornerPointExtList)
				{
					if (aNeighbour.equals(aCornerPoint2))
					{
						aNewNeighbourList.add(aNeighbour);
						pointAdded = true;
					}
				}
				if(!pointAdded)
				{
					aNewNeighbourList.add(getCornerPoint(aCornerPoint, aNeighbour));
					if(aNewNeighbourList.get(aNewNeighbourList.size()-1) == null)
					{
						aNewNeighbourList.remove(aNewNeighbourList.size()-1);
					}
				}
			}
			aCornerPoint.setNeighbourList(aNewNeighbourList);
		}
	}
	
	private void removeDoubleLines()
	{
		for (PointExt<Double> aCornerPoint : cornerPointExtList)
		{
			ArrayList<PointExt<Double>> aDoublePointList = new ArrayList<>();
			for (int i = 0; i<aCornerPoint.getNeighbourList().size()-1; i++)
			{
				int counter = 0;
				for (int j = i+1; j<aCornerPoint.getNeighbourList().size(); j++)
				{
					if (aCornerPoint.getNeighbourList().get(i).equals(aCornerPoint.getNeighbourList().get(j))) counter++;
				}
				if (counter > 0) aDoublePointList.add(aCornerPoint.getNeighbourList().get(i));
			}
			
			ListIterator<PointExt<Double>> iter=aCornerPoint.getNeighbourList().listIterator();
			while(iter.hasNext())
			{
				boolean removeNeighbour = false;
				PointExt<Double> aNeighbour = iter.next();
				for(PointExt<Double> aDouble:aDoublePointList)
				{
					if (aNeighbour.equals(aDouble)) 
					{
						removeNeighbour = true;
					}
				}
				if (removeNeighbour) iter.remove();
			}
		}
	}
	
	private void fillCornerList()
	{
		boolean firstCorner = false;
		Edge<Double> aEdge = null, bEdge = null;
		for(PointExt<Double> aPoint3 : aOBJ.getPointList())		//sucht einen Punkt aus der Punktliste
		{
			for (Face aFace2 : aOBJ.getFaceList())				//sucht ein Face
			{
				for(int i =0; i<3; i++)					
				{
					if (aPoint3.equals(aFace2.getPointExtList().get(i))) //überprüfe, ob einer der Punkte der Punkt ist 
					{
						firstCorner = false;
						for (int j=0; j<3; j++) 
						{							
							if (j!=i) 
							{
								aPoint3.addNeighbour(aFace2.getPointExtList().get(j));
								
								if (!firstCorner) 
								{
									aEdge = new Edge<>(aPoint3, aFace2.getPointExtList().get(j));
									firstCorner = true;
								}
								else if (firstCorner)
								{
									bEdge = new Edge<>(aPoint3, aFace2.getPointExtList().get(j));			
									aPoint3.setAngle(aPoint3.getAngle() + calcAngle(aEdge, bEdge));
								}	
							}
						}
					}
				}
			}
				
			//If Point is an Edge point:
			if (!aPoint3.compareAngle(180.0) && !aPoint3.compareAngle(360.0))
			{
				boolean inList = false;
				for(PointExt<Double> aPoint : cornerPointExtList)
				{
					if (aPoint.equals(aPoint3)) inList = true;
				}
				if (!inList) cornerPointExtList.add(aPoint3);
			}
		}
	}
	
	/**
	* This Function removes all doubleVertecies existing in the VertexList
	*/
	private void removeDoubleVertecies()
	{		
		Vertex<Double> oldVector4id = null;	//lastVertex (Vertex[i-1])
		Vertex<Double> actVector4id = null;	//activeVertex (Vertex[i])
		//Iteration over the list of Vertecies
		ListIterator<Vertex<Double>> iterator = aOBJ.getVertexList().listIterator();
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
			
			if (oldVector4id.equals(actVector4id)) 
			{
				//System.out.println("Removing: " + actVector4id.toString());
				//Iteration over all Faces
				for (int i = 0; i<aOBJ.getFaceList().size(); i++)
				{
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointExtList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointExtList().get(j).getAVertex().getId() == actVector4id.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointExtList().get(j).getAVertex().setId(oldVector4id.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}
	/**
	 * This Function removes all doubleTextures existing in the TextureList
	 */
	private void removeDoubleTextures()
	{
		if (aOBJ.getTextureList().size() <= 1) return;
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
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointExtList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointExtList().get(j).getATexture().getId() == actTexture.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointExtList().get(j).getATexture().setId(oldTexture.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}
	/**
	 * This Function removes all doubleNormals existing in the NormalList
	 */
	private void removeDoubleNormals()
	{		
		if (aOBJ.getNormalList().size() <= 1) return;
		Vector4id<Double> oldVector4id = null;	//lastVertex (Vertex[i-1])
		Vector4id<Double> actVector4id = null;	//activeVertex (Vertex[i])
		//Iteration over the list of Normals
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
					for (int j = 0; j<aOBJ.getFaceList().get(i).getPointExtList().size(); j++)
					{
						if (aOBJ.getFaceList().get(i).getPointExtList().get(j).getANormal().getId() == actVector4id.getId()) 
						{
							aOBJ.getFaceList().get(i).getPointExtList().get(j).getANormal().setId(oldVector4id.getId());
						}					
					}
				}
				iterator.remove();
			}
		}
	}
	/**
	 * This function removes all doublePoints, existing in the PointList
	 */
	private void removeDoublePoints()
	{
		
		for (Vertex<Double> aVertex : aOBJ.getVertexList())
		{
			for(PointExt<Double> aPoint : aOBJ.getPointList())
			{
				if (aPoint.getAVertex().equals(aVertex)) aPoint.setAVertex(aVertex);
			}
		}
		
		for(Face aFace : aOBJ.getFaceList())
		{
			for(PointExt<Double> aFacePoint : aFace.getPointExtList())
			{
				for (PointExt<Double> aPoint : pointExtList)
				{
					if (aFacePoint.equals(aPoint) && (aFacePoint.getId() != aPoint.getId()))
					{
						aFacePoint = aPoint;
					}
				}
			}
		}
	}
	
	/*
	 *  Algorithm:
	 *  Wähle ein Face1 von allen Faces
	 *   Wähle ein Face2 von allen Faces
	 *    Wenn sie nicht dasselbe Face sind(id Vergleich)
	 *     überprüfe, ob die PunkteIds gleich sind
	 *     ist ein Punkt gleich, addiere 1
	 *     ergibt sich die Zahl der Punkte entferne das 2. Face aus der Liste
	 *      
	 */
	private void removeDoubleFaces()
	{
		//Iteration over the list of Faces
		ListIterator<Face> iterator = aOBJ.getFaceList().listIterator();
		while (iterator.hasNext())
		{			
			Face aFace = iterator.next();
			//Iteration over all Faces
			ListIterator<Face> iterator2 = aOBJ.getFaceList().listIterator();
			while (iterator2.hasNext())
			{			
				Face aFace2 = iterator2.next();
				if (aFace.getId() != aFace2.getId()) 
				{
					int anz = 0;
					for (PointExt<Double> aPoint: aFace.getPointExtList())
					{
						for (PointExt<Double> aPoint2: aFace2.getPointExtList())
						{
							if (aPoint2.equals(aPoint)) anz++;
						}						
					}
					if (anz == aFace.getPointExtList().size()) iterator2.remove();
				}
			}
		}
	}
	/**
	 * This Function removes all Doubles, using different subfunctions
	 */
	private void removeDoubles()
	{
		removeDoubleVertecies();
		removeDoubleTextures();
		removeDoubleNormals();
		removeDoublePoints();
		removeDoubleFaces();		//needs testing
	}	
		
	/**
	 * Creates DirectionVector
	 */
	private Vector4id<Double> createDirectionVector(Point<Double> aStartPoint, Point<Double> aEndPoint)
	{
		Double sub1 = Operation.sub2(aEndPoint.getAVertex().getX(), aStartPoint.getAVertex().getX());
		Double sub2 = Operation.sub2(aEndPoint.getAVertex().getY(), aStartPoint.getAVertex().getY());
		Double sub3 = Operation.sub2(aEndPoint.getAVertex().getZ(), aStartPoint.getAVertex().getZ()); 
		Double magnitude = Math.sqrt((sub1 * sub1 + sub2 * sub2 + sub3*sub3));
		sub1 /= magnitude;
		sub2 /= magnitude;
		sub3 /= magnitude;		
		Vector4id<Double> dVector = new Vector4id<>(sub1,sub2,sub3,0.0);
		return dVector;
	}
					
	/**
	 * Calculating one Angle
	 * @param a
	 * @param b
	 * @param c
	 * @return angle as Double
	 */
	private double calcAngle(Edge<Double> a, Edge<Double> b)
	{
		//Convention a,b have same point
		Edge<Double> c = new Edge<>(a.getVertex1(),b.getVertex1());
		double angle;
		angle = Math.acos( (Math.pow(a.getLength(),2) + 
							Math.pow(b.getLength(),2) - 
							Math.pow(c.getLength(),2)) / 
							(2 * a.getLength() * b.getLength()));
		
		angle = angle  * 360.0 / (2.0 * Math.PI);
		if (angle > 180.0) angle = 360.0 - angle;		
		return angle;
	}	
		
	/**
	 * Algorithm:
	 * For each cornerPoint in cornerList
	 *  create Edge1(cornerPoint, cornerEndPoint1)
	 *  create Edge2(cornerPoint, cornerEndPoint2)
	 *  For each Face
	 *   if Face has cornerPoint
	 *    create angle at Point
	 *    add to angle at Point
	 *    
	 * create aRelationDefinition(Edge1, Edge2, angle)
	 * add to relationsDefinitionList
	 * 
	 */
	
	// Eine ID für eine Edge
	private void fillRDL()
	{
		/*Optimized for Triangles*/
		RelationsDefinition aRelationsDefinition = new RelationsDefinition();
		Edge<Double> aEdge, bEdge;
		for(PointExt<Double> aCorner : cornerPointExtList)
		{
			if (aCorner.getNeighbourList().size() > 1)
			{
				aEdge = new Edge<>(aCorner, aCorner.getNeighbourList().get(0));
				bEdge = new Edge<>(aCorner, aCorner.getNeighbourList().get(1));			
						
				for (RelationsDefinition aRDL : aRelationsDefinitionList)
				{
					if (aEdge.equals(aRDL.getEdge1())) aEdge = aRDL.getEdge1();
					else if (aEdge.equals(aRDL.getEdge2())) aEdge = aRDL.getEdge2();
					if (bEdge.equals(aRDL.getEdge1())) bEdge = aRDL.getEdge1();
					else if (bEdge.equals(aRDL.getEdge2())) bEdge = aRDL.getEdge2();
				}
			
				aRelationsDefinition = new RelationsDefinition(aEdge, bEdge, aCorner.getAngle());
				aRelationsDefinitionList.add(aRelationsDefinition);
			}
		}
	}	
}
