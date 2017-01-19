
package obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Login
 *
 * @param <T>
 */
public class NormalVertex<T extends Number> 
{
	private Map<Vertex<T>, Double> vertexMap = new HashMap<>();
	private Normal<T> normal;
	private int vertexAmount;
	
	public Map<Vertex<T>, Double> getVertexMap() 					{return vertexMap;}
	public Normal<T> getNormal() 									{return normal;}
	public int getNormalAmount() 									{return vertexAmount;}
	public void setVertexMap(Map<Vertex<T>, Double> vertexMap) 		{this.vertexMap = vertexMap;}
	public void setNormal(Normal<T> normal) 						{this.normal = normal;}
	public void setVertexAmount(int vertexAmount) 					{this.vertexAmount = vertexAmount;}
	
	
	public NormalVertex(){}
	public NormalVertex(Normal<T> aNormal)
	{
		setVertexMap(new HashMap<>());
		setNormal(aNormal);
	}
	
	public static ArrayList<NormalVertex<Double>> addNormalList(ArrayList<Normal<Double>> aNormalList)
	{
		ArrayList<NormalVertex<Double>> normalVertexList = new ArrayList<>();
		for (Normal<Double> aNormal : aNormalList)
		{
			normalVertexList.add(new NormalVertex<Double>(aNormal));
		}
		return normalVertexList;
	}
}
