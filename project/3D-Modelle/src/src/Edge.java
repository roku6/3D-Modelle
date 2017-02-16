package src;

import obj.Operation;
import obj.Point;
import obj.Vertex;

/**
 * Edge Class
 * Stores: 
 * - 2 Vertecies (EndPoints)
 * - an id 
 * - the length of the edge 
 * 
 * @author Robert Külpmann
 *
 * @param <T>
 */
public class Edge<T extends Number> 
{
	private static int number = 0;
	private int id = 0;
	private Vertex<T> vertex0;
	private Vertex<T> vertex1;
	private double length;
			
	public int getNumber()						{return number;}
	public int getId() 							{return id;}
	public Vertex<T> getVertex0()				{return vertex0;}
	public Vertex<T> getVertex1()				{return vertex1;}
	public double getLength()					{return length;}
	
	public void setVertex0(Vertex<T> vertex0) 					{this.vertex0 = vertex0;}
	public void setVertex1(Vertex<T> vertex1) 					{this.vertex1 = vertex1;}
	public void setLength(double length)						{this.length = length;}
	
	/**
	 * Empty Constructor
	 */
	public Edge(){}
	/**
	 * Constructor using 2 Points
	 * 
	 * @param point0
	 * @param point1
	 */
	public Edge(Point<T> point0, Point<T> point1)
	{
		Edge.number++;
		this.id = number;
		this.vertex0 = point0.getAVertex();
		this.vertex1 = point1.getAVertex();
		calculateLength();
	}

	/**
	 * Constructor using 2 Vertecies
	 * 
	 * @param vertex0
	 * @param vertex1
	 */
	public Edge(Vertex<T> vertex0, Vertex<T> vertex1)
	{
		Edge.number++;
		this.id = number;
		this.vertex0 = vertex0;
		this.vertex1 = vertex1;
		calculateLength();
	}

	/**
	 * Calculates length of the edge
	 */
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
	 * Compares the Vertecies of the Edges
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
			
	@Override
	/**
	 * Returns Id, Vertecies and Length as String
	 */
	public String toString()
	{
		return this.getId() + " " + this.getVertex0().toString() + " - " + this.getVertex1().toString() + "|" + this.getLength();
	}
}
