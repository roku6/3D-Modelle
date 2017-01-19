package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Login
 *
 * @param <T>
 */
public class VertexNormal<T extends Number> 
{
	private Vertex<T> vertex;
	private Map<Normal<T>, Double> normalMap = new HashMap<>();
	private int normalAmount;
	
	public Vertex<T> getVertex() 					{return vertex;}
	public Map<Normal<T>, Double> getNormalMap() 	{return normalMap;}
	public int getNormalAmount() 					{return normalAmount;}
	public void setVertex(Vertex<T> vertex) 						{this.vertex = vertex;}
	public void setNormalList(Map<Normal<T>, Double> normalMap) 	{this.normalMap = normalMap;}
	public void setNormalAmount(int normalAmount) 					{this.normalAmount = normalAmount;}
	
	
	public VertexNormal(){}
	public VertexNormal(Vertex<T> aVertex)
	{
		setVertex(aVertex);
		setNormalList(new HashMap<>());
	}
	
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
