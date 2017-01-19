package src;

import java.util.ArrayList;

import obj.Operation;
import obj.Point;
import obj.Vector4;

/**
 * @author Robert Külpmann
 *
 * @param <T>
 */
public class Edge<T extends Number> 
{
	public static int number = 0;
	private int id = 0;
	private Point<T> point0;
	private Point<T> point1;
	private double length;
	private ArrayList<Edge<T>> neighbours = new ArrayList<>();
	private Vector4<Double> rVector = new Vector4<>();
			
	public int getId() 							{return id;}
	public Point<T> getPoint0()					{return point0;}
	public Point<T> getPoint1()					{return point1;}
	public double getLength()					{return length;}
	public ArrayList<Edge<T>> getNeighbours() 	{return neighbours;}	
	public Vector4<Double> getRVector()			{return rVector;}
	
	public void setVertex0(Point<T> point0) 					{this.point0 = point0;}
	public void setVertex1(Point<T> point1) 					{this.point1 = point1;}
	public void setLength(double length)						{this.length = length;}
	public void setNeighbours(ArrayList<Edge<T>> neighbours) 	{this.neighbours = neighbours;}
	public void setRVector(Vector4<Double> rVector)									{this.rVector = rVector;}
	
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
		this.point0 = point0;
		this.point1 = point1;
		calculateLength();
		calculateRVector();
	}
	
	private void calculateLength()
	{
		this.length = (double) 
				Operation.squareRoot(
				Operation.add4(	Operation.powerOf(Operation.sub2(point0.getAVertex().getX(), point1.getAVertex().getX()), 2.0),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getY(), point1.getAVertex().getY()), 2.0),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getZ(), point1.getAVertex().getZ()), 2.0),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getW(), point1.getAVertex().getW()), 2.0)));
	}
	/**
	 * Normalized Directionvector
	 */
	private void calculateRVector()
	{
		rVector.setX(1.0/this.length * (double)Operation.sub2(point0.getAVertex().getX(),(double)point1.getAVertex().getX()));
		rVector.setY(1.0/this.length * (double)Operation.sub2(point0.getAVertex().getY(),(double)point0.getAVertex().getY()));
		rVector.setZ(1.0/this.length * (double)Operation.sub2(point0.getAVertex().getZ(),(double)point0.getAVertex().getZ()));
		rVector.setW(1.0/this.length * (double)Operation.sub2(point0.getAVertex().getW(),(double)point0.getAVertex().getW()));
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
		if ((this.getPoint0().equals(aEdge.getPoint0()) &&
			this.getPoint1().equals(aEdge.getPoint1())) ||
			(this.getPoint0().equals(aEdge.getPoint1()) &&
			this.getPoint1().equals(aEdge.getPoint0())))
		{
			return true;
		}
		else return false;
	}
			
	public String toString()
	{
		return this.getId() + " " + this.getPoint0().toString() + " " + this.getPoint1().toString();
	}
}
