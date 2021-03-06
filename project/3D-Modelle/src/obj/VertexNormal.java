package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * VertexNormal.java
 * (c) 2017 Robert K�lpmann
 * 
 * A Class which holds all the Normals of one Vertex
 * 
 * @author Robert K�lpmann
 *
 * @param <T>
 */
public class VertexNormal<T extends Number> 
{
	private Vertex<T> vertex;
	private Map<Normal<T>, Double> normalMap = new HashMap<>();
	
	public Vertex<T> getVertex() 					{return vertex;}
	public Map<Normal<T>, Double> getNormalMap() 	{return normalMap;}
	public void setVertex(Vertex<T> vertex) 						{this.vertex = vertex;}
	public void setNormalList(Map<Normal<T>, Double> normalMap) 	{this.normalMap = normalMap;}
	
	
	/**
	 * Empty Constructor
	 */
	public VertexNormal(){}
	
	/**
	 * 
	 * @param aVertex
	 */
	public VertexNormal(Vertex<T> aVertex)
	{
		setVertex(aVertex);
		setNormalList(new HashMap<>());
	}
	
	/**
	 * 
	 * @param aVertexList
	 * @return
	 */
	public static ArrayList<VertexNormal<Double>> addVertexList(ArrayList<Vertex<Double>> aVertexList)
	{
		ArrayList<VertexNormal<Double>> vertexNormalList = new ArrayList<>();
		for (Vertex<Double> aVertex : aVertexList)
		{
			vertexNormalList.add(new VertexNormal<Double>(aVertex));
		}
		return vertexNormalList;
	}
}
