package src;

import java.util.ArrayList;

import obj.Operation;
import obj.Point;
import obj.Vector4;
import obj.Vertex;

/**
 * @author Robert Külpmann
 *
 * @param <T>
 */
public class Edge<T extends Number> 
{
	public static int number = 0;
	private int id = 0;
	private Vertex<T> vertex0;
	private Vertex<T> vertex1;
	private double length;
	private ArrayList<Edge<T>> neighbours = new ArrayList<>();
	private Vector4<Double> rVector = new Vector4<>();
			
	public int getId() 							{return id;}
	public Vertex<T> getVertex0()				{return vertex0;}
	public Vertex<T> getVertex1()				{return vertex1;}
	public double getLength()					{return length;}
	public ArrayList<Edge<T>> getNeighbours() 	{return neighbours;}	
	public Vector4<Double> getRVector()			{return rVector;}
	
	public void setVertex0(Vertex<T> vertex0) 					{this.vertex0 = vertex0;}
	public void setVertex1(Vertex<T> vertex1) 					{this.vertex1 = vertex1;}
	public void setLength(double length)						{this.length = length;}
	public void setNeighbours(ArrayList<Edge<T>> neighbours) 	{this.neighbours = neighbours;}
	public void setRVector(Vector4<Double> rVector)				{this.rVector = rVector;}
	
	Edge(){}
	/**
	 * 
	 * @param point0
	 * @param point1
	 */
	Edge(Point<T> point0, Point<T> point1)
	{
		Edge.number++;
		this.id = number;
		this.vertex0 = point0.getAVertex();
		this.vertex1 = point1.getAVertex();
		calculateLength();
		calculateRVector();
	}

	Edge(Vertex<T> vertex0, Vertex<T> vertex1)
	{
		Edge.number++;
		this.id = number;
		this.vertex0 = vertex0;
		this.vertex1 = vertex1;
		calculateLength();
		calculateRVector();
	}

	private void calculateLength()
	{
		this.length = (double) 
				Operation.squareRoot(
				Operation.add4(	Operation.powerOf(Operation.sub2(vertex1.getX(), vertex0.getX()), 2.0),
								Operation.powerOf(Operation.sub2(vertex1.getY(), vertex0.getY()), 2.0),
								Operation.powerOf(Operation.sub2(vertex1.getZ(), vertex0.getZ()), 2.0),
								Operation.powerOf(Operation.sub2(vertex1.getW(), vertex0.getW()), 2.0)));
	}
	/**
	 * Normalized Directionvector
	 */
	private void calculateRVector()
	{
		rVector.setX(1.0/this.length * (double)Operation.sub2(vertex1.getX(),(double)vertex0.getX()));
		rVector.setY(1.0/this.length * (double)Operation.sub2(vertex1.getY(),(double)vertex0.getY()));
		rVector.setZ(1.0/this.length * (double)Operation.sub2(vertex1.getZ(),(double)vertex0.getZ()));
		rVector.setW(1.0/this.length * (double)Operation.sub2(vertex1.getW(),(double)vertex0.getW()));
	}
	
	/**
	 * 
	 * @param aEdge
	 */
	
	public void addNeighbour(Edge<T> aEdge)
	{
		neighbours.add(aEdge);
	}	
	
	/**
	 * 
	 * @param aEdge
	 * @return true if equals, false if not
	 */
	public boolean equals(Edge<T> aEdge)
	{
		if ((this.getVertex0().equals(aEdge.getVertex0()) &&
			this.getVertex1().equals(aEdge.getVertex1())) ||
			(this.getVertex0().equals(aEdge.getVertex1()) &&
			this.getVertex1().equals(aEdge.getVertex0())))
		{
			return true;
		}
		else return false;
	}
			
	public String toString()
	{
		return this.getId() + " " + this.getVertex0().toString() + " " + this.getVertex1().toString();
	}
}
