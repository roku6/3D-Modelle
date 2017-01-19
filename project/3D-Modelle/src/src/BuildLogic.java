/**
 * DONE: 
 * 	Erased same values (vertecies, textures, normals)
 * 	Filled the pointList with every single Point (every Point just once)
 *  Added all neighbours to every single Point
 *  Created a List of cornerPoints
 *  Calculated angles to cornerPoints
 *  Connected cornerPoints with each other(lineTracking)
 *  Created edges 
 *  Filled RelationDefinition
 * TODO:  + remove duplicated Faces (later), look for possible Errors
 * BUGS: a bug occured with generated Cube
 * 
 * @author Robert Külpmann
 * 
 */

package src;

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
	private ArrayList<PointExt<Double>> pointExtList = new ArrayList<>();
	private ArrayList<Edge<Double>> edgeList = new ArrayList<>();
	private ArrayList<RelationsDefinition> aRelationsDefinitionList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> a1NormalVertexList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> aEdgeVertexList = new ArrayList<>();
	private ArrayList<VertexNormal<Double>> aCornerVertexList = new ArrayList<>();
	private ArrayList<NormalVertex<Double>> aNormalVertexList = new ArrayList<>();
	private ArrayList<PointExt<Double>> cornerPointExtList = new ArrayList<>();
	
	public OBJ getaOBJ() 								{return aOBJ;}
	public ArrayList<PointExt<Double>> getPointExtList(){return pointExtList;}
	public ArrayList<Edge<Double>> getEdgeList() 		{return edgeList;}
	
	public void setaOBJ(OBJ aOBJ) 										{this.aOBJ = aOBJ;}
	public void setPointExtList(ArrayList<PointExt<Double>> pointList) 	{this.pointExtList = pointList;}
	public void setEdgeList(ArrayList<Edge<Double>> edgeList) 			{this.edgeList = edgeList;}

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
		//System.out.println(aOBJ.toString());
	}
	public void createFigure()
	{
		sortAll();
		removeDoubles();
		fillPointList();
		addPointNeighbours();		
		createVertexNormalLists();
		createNormalVertexList();
		fillCornerPointList();
		changeCornerNeighbours();
		System.out.println("Size2: " + cornerPointExtList.size() + " ");
		printConnections();
		fillRDL();
		System.out.println(aRelationsDefinitionList.toString());
		//saveToDatabase();
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
	
	/**
	 *	Not completed! 
	 *  Is not so important right now! 
	 */
	private void removeDoubleFaces()
	{
		Face oldVector4id = null;	//lastFace (Face[i-1])
		Face actVector4id = null;	//activeFace (Face[i])
		//Iteration over the list of Faces
		Iterator<Face> iterator = aOBJ.getFaceList().iterator();
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
			/*
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
			*/
		}
	}
	
	private void removeDoubles()
	{
		removeDoubleVertecies();
		removeDoubleTextures();
		removeDoubleNormals();
		removeDoublePoints();
		//removeDoubleFaces();
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
		boolean elementExists = false;
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
									ListIterator<PointExt<Double>> iterator = pointExtList.get(k).getNeighbourList().listIterator();
									elementExists = false;
									while (iterator.hasNext())
									{
										PointExt<Double> aTestPoint = iterator.next();
										if ((aTestPoint.getAVertex().getId() == aFace.getPointExtList().get(j).getAVertex().getId()) &&
											(aTestPoint.getATexture().getId() == aFace.getPointExtList().get(j).getATexture().getId()) &&
											(aTestPoint.getANormal().getId() == aFace.getPointExtList().get(j).getANormal().getId()))
										{	
											elementExists = true;
										}
									}
									if (!elementExists) 
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
				System.out.println(aVertexNormal.getNormalMap().size() + " Normalen - Fehler! Zuviele Normalen!!");
				System.out.println(aVertexNormal.getNormalMap().toString());
				aCornerVertexList.add(aVertexNormal);
			}
		}
		System.out.println("OriginalSize: " + aVertexNormalList.size());
		System.out.println("1Size: " + a1NormalVertexList.size());
		System.out.println("2Size: " + aEdgeVertexList.size());
		System.out.println("3Size: " + aCornerVertexList.size());

	}
	
	private void fillCornerPointList()
	{
		System.out.println("Size pointExtList: " + pointExtList.size());
		System.out.println("Size aCornerVertexList: " + aCornerVertexList.size());
		cornerPointExtList.clear();
		for(int i = 0; i<pointExtList.size();i++)
		{
			for(VertexNormal<Double> aVertexNormal : aCornerVertexList)
			{
				if (pointExtList.get(i).getAVertex().equals(aVertexNormal.getVertex())) 
				{					
					cornerPointExtList.add(pointExtList.get(i));
					break;
				}
			}
		}
	}
	
	
	/**
	 * Check what kind of Edge the neighbour is
	 * @param aPoint
	 * @return int
	 */
	
	/*
	 * Create DirectionVector
	 */
	private Vector4id<Double> createDirectionVector(Point<Double> aStartPoint, Point<Double> aEndPoint)
	{
		Double sub1 = Operation.sub2(aEndPoint.getAVertex().getX(), aStartPoint.getAVertex().getX());
		Double sub2 = Operation.sub2(aEndPoint.getAVertex().getY(), aStartPoint.getAVertex().getY());
		Double sub3 = Operation.sub2(aEndPoint.getAVertex().getZ(), aStartPoint.getAVertex().getZ()); 
		Vector4id<Double> dVector = new Vector4id<>(sub1,sub2,sub3,0.0);
		return dVector;
	}
	
	/*
	 * change the Neighbours of the cornerPoints to other cornerPoints
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
	 * Calculating all Angles
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
		Edge<Double> c = new Edge<>(a.getPoint1(),b.getPoint1());
		double angle;
		angle = Math.acos( (Math.pow(a.getLength(),2) + 
							Math.pow(b.getLength(),2) - 
							Math.pow(c.getLength(),2)) / 
							(2 * a.getLength() * b.getLength()));
		
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
	private void fillRDL()
	{
		/*Optimized for Triangles*/
		RelationsDefinition aRelationsDefinition = new RelationsDefinition();
		Edge<Double> aEdge, bEdge;
		for(PointExt<Double> aCorner : cornerPointExtList)
		{
			aEdge = new Edge<>(aCorner, aCorner.getNeighbourList().get(0));
			bEdge = new Edge<>(aCorner, aCorner.getNeighbourList().get(1));			
			
			for (Face aFace : aOBJ.getFaceList())
			{
				for (PointExt<Double> aFacePoint : aFace.getPointExtList())
				{
					if (aFacePoint.equals(aCorner))
					{
						aCorner.setAngle(aCorner.getAngle() + calcAngle(aEdge, bEdge));
					}
				}
			}
			
			aRelationsDefinition = new RelationsDefinition(aEdge, bEdge, aCorner.getAngle());
			aRelationsDefinitionList.add(aRelationsDefinition);
		}
	}
	
	private void saveToDatabase()
	{
		
	}
	
}
