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
import obj.NormalVertex;
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
	private ArrayList<VertexNormal<Double>> a1NormalVertexList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> aEdgeVertexList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> aCornerVertexList = new ArrayList<>();
	private ArrayList<NormalVertex<Double>> aNormalVertexList = new ArrayList<>();
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
		try
		{
			File aFile = new File(descriptionURL);
			FileReader fr = new FileReader(aFile);
			BufferedReader br = new BufferedReader(fr);
			while((s = br.readLine()) != null)
			{
				description.concat(s);
			}
			br.close();
			fr.close();
			return description;
		}
		catch(IOException e)
		{
			System.out.println("Can't load File!");
			description = "No Description available.";
			return description;
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
		changeNegativeNormals();
		removeDoubles();
		fillPointList();
		addPointNeighbours();		
		createVertexNormalLists();
		createNormalVertexList();
		fillCornerPointList();
		changeCornerNeighbours();
		calcAllCornerAngles();
		remove1DLines();
		//System.out.println("Size2: " + cornerPointExtList.size() + " ");
		//printConnections();
		fillRDL();
		System.out.println();
		System.out.println(aRelationsDefinitionList.toString());
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
	}

	
	private void changeNegativeNormals()
	{
		for (Normal<Double> aNormal : aOBJ.getNormalList())
		{
			if (aNormal.getX() < 0) aNormal.setX(aNormal.getX()*-1.0);
			if (aNormal.getY() < 0) aNormal.setY(aNormal.getY()*-1.0);
			if (aNormal.getZ() < 0) aNormal.setZ(aNormal.getZ()*-1.0);
			if (aNormal.getW() < 0) aNormal.setW(aNormal.getW()*-1.0);			
		}
	}
	
	/**
	* This Function removes all doubleVertecies existing in the VertexList
	*/
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
	 * 	Fills a List with all possible Points, without adding any Neighbours
	 * Iterates over alle Faces
	 * If a Point is not existing in the List add it to the PointExtList
	 */
	private void fillPointList()
	{
		boolean elementExists = false;
		for (int i = 0; i<aOBJ.getFaceList().size(); i++)
		{
			for (PointExt<Double> aPoint : aOBJ.getFaceList().get(i).getPointExtList())
			{
				if (pointExtList.size() != 0)
				{
					ListIterator<PointExt<Double>> iterator = pointExtList.listIterator();
					elementExists = false;
					while (iterator.hasNext())
					{
						PointExt<Double> aTestPoint = iterator.next();
						if ((aTestPoint.getAVertex().getId() == aPoint.getAVertex().getId()) &&
							(aTestPoint.getATexture().getId() == aPoint.getATexture().getId()) &&
							(aTestPoint.getANormal().getId() == aPoint.getANormal().getId()))
						{	
							elementExists = true;
						}
					}
					if (!elementExists) 
					{
						pointExtList.add(aPoint);
					}
				}
				else pointExtList.add(aPoint);
			}
		}
	}
	
	/*
	 * Algorithm:
	 * add all possible Neighbours to the Point
	 * Iterates over PointExtList
	 * Look PointExtList up in FaceList
	 * add Neighbours if the Point exists and if Neighbour is not in the Neighbourlist (Iterate over Neighbourlist)
	 */
	private void addPointNeighbours()
	{
		//Optimized for Triangles!!
		if (pointExtList.size() != 0 && aOBJ.getFaceList().size() != 0)						//Checks if a PointList exists
		{
			for (int k = 0; k<pointExtList.size(); k++)	
			{
				for (Face aFace : aOBJ.getFaceList())
				{
					for (int i = 0; i<3; i++)				
					{
						if (pointExtList.get(k).equals(aFace.getPointExtList().get(i)))		//Checks if the Point exists in any Face
						{
							for (int j = 0; j<3; j++)
							{
								if (j != i)										//For all Points in the Face except the point itself
								{
									for (PointExt<Double> aPoint : pointExtList)
									{
										if (aFace.getPointExtList().get(j).equals(aPoint))
											pointExtList.get(k).getNeighbourList().add(aPoint);										
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This function creates Lists with a Normal and all it's Vertecies
	 */
	private void createNormalVertexList()
	{
		aNormalVertexList = NormalVertex.addNormalList(aOBJ.getNormalList());
		
		for(NormalVertex<Double> aNormalVertex : aNormalVertexList)
		{
			for (Point<Double> aPoint : pointExtList)
			{
				//test if Normal of Point is Normal in NormalVertexList
				if (aPoint.getANormal().equals(aNormalVertex.getNormal()))
				{
					
					if (!aNormalVertex.getVertexMap().containsKey(aPoint.getAVertex()))
						aNormalVertex.getVertexMap().put(aPoint.getAVertex(), 0.0);					
				}
			}
		}		
	}
	
	
	/**
	 * This function creates a List with a Vertex and all it's normals
	 */
	private void createVertexNormalLists()
	{
		ArrayList<VertexNormal<Double>> aVertexNormalList = new ArrayList<>();
		aVertexNormalList = VertexNormal.addVertexList(aOBJ.getVertexList());
		
		
		for(VertexNormal<Double> aVertexNormal : aVertexNormalList)
		{
			for (Point<Double> aPoint : pointExtList)
			{
				//test if Vertex of Point is Vertex in VertexNormalList
				if (aPoint.getAVertex().equals(aVertexNormal.getVertex()))
				{
					
					if (!aVertexNormal.getNormalMap().containsKey(aPoint.getANormal()))
					{
						aVertexNormal.getNormalMap().put(aPoint.getANormal(), 0.0);					
					}
				}
			}
		}
		
		//Split VertexNormalList in 3 Lists, return error if there are more than 3 normals for a point
		for (VertexNormal<Double> aVertexNormal : aVertexNormalList)
		{
			if (aVertexNormal.getNormalMap().size() == 1) 	a1NormalVertexList.add(aVertexNormal);
			else if (aVertexNormal.getNormalMap().size() == 2)  aEdgeVertexList.add(aVertexNormal);
			else if (aVertexNormal.getNormalMap().size() == 3)  aCornerVertexList.add(aVertexNormal);
			else 
			{
				System.out.println("OBJ - CreateVertexList-Error!");
				System.out.println(aVertexNormal.getNormalMap().size() + " Normalen - Fehler! Zuviele Normalen!!");
				System.out.println(aVertexNormal.getNormalMap().toString());
				aEdgeVertexList.add(aVertexNormal);
			}
		}
		//System.out.println("OriginalSize: " + aVertexNormalList.size());
		//System.out.println("1Size: " + a1NormalVertexList.size());
		//System.out.println("2Size: " + aEdgeVertexList.size());
		//System.out.println("3Size: " + aCornerVertexList.size());

	}
	/**
	 * This function adds every possible cornerPoint to a List
	 */
	private void fillCornerPointList()
	{
		//System.out.println("Size pointExtList: " + pointExtList.size());
		//System.out.println("Size aCornerVertexList: " + aCornerVertexList.size());
		cornerPointExtList.clear();
		for(int i = 0; i<pointExtList.size();i++)
		{
			for(VertexNormal<Double> aVertexNormal : aCornerVertexList)
			{
				if (pointExtList.get(i).getAVertex().equals(aVertexNormal.getVertex())) 
				{					
					cornerPointExtList.add(pointExtList.get(i));
					//System.out.println("Corner: " + pointExtList.get(i));
					break;
				}
			}
		}
		//System.out.println("CornerListSize: " + cornerPointExtList.size());
	}
		
	/**
	 * Creates DirectionVector
	 */
	private Vector4id<Double> createDirectionVector(Point<Double> aStartPoint, Point<Double> aEndPoint)
	{
		Double sub1 = Operation.sub2(aEndPoint.getAVertex().getX(), aStartPoint.getAVertex().getX());
		Double sub2 = Operation.sub2(aEndPoint.getAVertex().getY(), aStartPoint.getAVertex().getY());
		Double sub3 = Operation.sub2(aEndPoint.getAVertex().getZ(), aStartPoint.getAVertex().getZ()); 
		Vector4id<Double> dVector = new Vector4id<>(sub1,sub2,sub3,0.0);
		return dVector;
	}
	
	/**
	 * changes the Neighbours of the cornerPoints to other cornerPoints
	 * 
	 * Algorithmus:
	 * Für jede Normale
	 * 	Durchlaufe alle Eckpunkte
	 *   wenn die Normale der Normale des Eckpunktes entspricht:
	 *   Für jeden Nachbarn:
	 *    Berechne den Richtungsvektor ausgehend von dem Punkt
	 *    füge dem Eckpunkt einen Nachbar hinzu
	 * 	
	 */
	private void changeCornerNeighbours()
	{
		for (int i = 0; i<aNormalVertexList.size(); i++)
		{
			for(int k = 0; k<cornerPointExtList.size(); k++)
			{
				//Checks if aPoint is a CornerPoint and in the right 2D Space
				if(aNormalVertexList.get(i).getNormal().equals(cornerPointExtList.get(k).getANormal()))			
				{
					//Add CornerPoints if exist
					for(PointExt<Double> neighbour : cornerPointExtList.get(k).getNeighbourList())
					{
						Vector4id<Double> dVector2 = createDirectionVector(cornerPointExtList.get(k), neighbour);
						//cornerPointExtList.get(k).addCornerPoint(updateCornerNeighbour(neighbour, dVector2));	
						cornerPointExtList.get(k).addCornerPoint(updateCornerNeighbour(neighbour, dVector2));	
						
						//remove all null elements
						ListIterator<PointExt<Double>> listIt = cornerPointExtList.get(k).getCornerPointList().listIterator();
						while(listIt.hasNext())
						{
							PointExt<Double> delPoint = listIt.next();
							if(delPoint == null) listIt.remove();
						}	
					}
				}
			}	
		}
	}
	
	/**
	 * 
	 * @param aPoint
	 * @param dVector
	 * @return an updated NeighbourPoint
	 */
	private PointExt<Double> updateCornerNeighbour(PointExt<Double> aPoint, Vector4id<Double> dVector)
	{		
		if (aPoint == null) System.out.println("Großes Problem!!");
		for(VertexNormal<Double> aCornerNeighbour : aCornerVertexList)	
		{
			//Check if point is a Corner
			if (aCornerNeighbour.getVertex().equals(aPoint.getAVertex())) 
			{
				//System.out.println(aPoint.toString() + " is a CornerPoint!");
				return aPoint;
			}
			// If the point is not a Corner
			else
			{
				//check if point is an edgePoint
				for(VertexNormal<Double> aVertex2 : aEdgeVertexList)
				{
					if (aPoint.getAVertex().equals(aVertex2.getVertex())) 
					{
						//System.out.println(aPoint.toString() + " is an Edge! Neighbours: " + aPoint.getNeighbourList().toString());
						for(PointExt<Double> neighbour : aPoint.getNeighbourList())
						{
							Vector4id<Double> dVector2 = createDirectionVector(aPoint, neighbour);
							if (dVector.equals(dVector2))
							{
								return updateCornerNeighbour(neighbour, dVector2);	
							}
						}
					}
				}
			}
		}
		return null;
	}		
	
	/**
	 * Prints all connections of cornerPoints, just an Output, for testing purpose
	 */
	private void printConnections()
	{
		System.out.println("pointExtSize: " + cornerPointExtList.size());
		int i = 0;
		for (PointExt<Double> aCornerPoint : cornerPointExtList)	
		{ 
			if (!aCornerPoint.getCornerPointList().isEmpty()) 
			{	
				System.out.println(i + " " + aCornerPoint.toString() + " -> " + aCornerPoint.getCornerPointList().toString());
				i++;
			}
			else
			{
				System.out.println(i + " " + aCornerPoint.toString());
				i++;
			}
		}
	}
		
	/**
	 * Calculating all Angles //NOT USED!
	 * @param a
	 * @param b
	 * @param c
	 * @return angles (as a double Array)
	 */
	private double[] calcAngles(Edge<Double> a, Edge<Double> b, Edge<Double> c)
	{
		double[] angles = new double[3];
		angles[0] = Math.acos(Math.pow(a.getLength(),2) - Math.pow(b.getLength(),2) - Math.pow(c.getLength(),2) + 2 * b.getLength() * c.getLength());
		angles[1] = Math.acos(Math.pow(a.getLength(),2) - Math.pow(b.getLength(),2) - Math.pow(c.getLength(),2) + 2 * b.getLength() * c.getLength());
		angles[2] = Math.acos(Math.pow(a.getLength(),2) - Math.pow(b.getLength(),2) - Math.pow(c.getLength(),2) + 2 * b.getLength() * c.getLength());
		
		for (int i = 0; i < 3; i++) 
			if (angles[i] > 180.0) 
				angles[i] = 360.0 - angles[i];
		
		return angles;
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
	 * removes all Lines from an Edge which appear twice as neighbours
	 * If they appear twice it is a Line which is not an Edge, but lies on the surface
	 */
	private void remove1DLines()
	{
		boolean addPoint = true; 
		ArrayList<PointExt<Double>> aTempList = new ArrayList<>();
		boolean samePoint = false;
		for(int k = 0; k<cornerPointExtList.size(); k++)
		{
			aTempList.clear();
			for(int i= 0; i<cornerPointExtList.get(k).getNeighbourList().size(); i++)
			{
				samePoint = false;
				addPoint = true;
				for(int j = 0; j<cornerPointExtList.get(k).getNeighbourList().size(); j++)
				{					
					if(cornerPointExtList.get(k).getNeighbourList().get(i).equals(cornerPointExtList.get(k).getNeighbourList().get(j)) && (samePoint == true)) 
					{ addPoint = false;}
					else if(cornerPointExtList.get(k).getNeighbourList().get(i).equals(cornerPointExtList.get(k).getNeighbourList().get(j))  && (samePoint == false)) 
					{ samePoint = true;}
				}				
				if (addPoint) {aTempList.add(cornerPointExtList.get(k).getNeighbourList().get(i));}
			}
			boolean inList = false;
			ListIterator<PointExt<Double>> iterator = cornerPointExtList.get(k).getNeighbourList().listIterator();
			while(iterator.hasNext())
			{
				PointExt<Double> aPoint = iterator.next();
				for(PointExt<Double> aPoint2 : aTempList)
				{
					if (aPoint2.equals(aPoint)) inList = true;
				}
				if (!inList) iterator.remove();
				else inList = false;
			}
			//System.out.println(cornerPointExtList.get(k).getNeighbourList().toString());
		}
	}
	
	
	// Abschneiden bei 90.0 Grad in Grad umrechnen
	private void calcAllCornerAngles()
	{
		boolean firstCorner = false;
		Edge<Double> aEdge = null, bEdge = null;
		for(PointExt<Double> aCorner : cornerPointExtList)
		{
			for (Face aFace : aOBJ.getFaceList())
			{
				for (PointExt<Double> aFacePoint : aFace.getPointExtList())
				{
					if (aFacePoint.equals(aCorner))
					{
						firstCorner = false;
						for (PointExt<Double> aFacePoint2 : aFace.getPointExtList())
						{
							if (!aFacePoint.equals(aFacePoint2))
							{
								if (!firstCorner) 
								{
									aEdge = new Edge<>(aFacePoint, aFacePoint2);
									firstCorner = true;
								}
								else if (firstCorner)
								{
									bEdge = new Edge<>(aFacePoint, aFacePoint2);			
									aCorner.setAngle(aCorner.getAngle() + calcAngle(aEdge, bEdge));
								}
							}
						}
						aCorner.setAngle(aCorner.getAngle());
					}
				}
			}
		}
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
			if (aCorner.getNeighbourList().size() > 0)
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
