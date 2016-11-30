package src;

import java.util.ArrayList;

import obj.Operation;
import obj.Point;

public class Edge<T extends Number> 
{
	public static int number = 0;
	private int id = 0;
	private Point<T> point0;
	private Point<T> point1;
	private double length;
	private ArrayList<Edge<T>> neighbours;
	
	public int getId() 							{return id;}
	public Point<T> getPoint0()					{return point0;}
	public Point<T> getPoint1()					{return point1;}
	public double getLength()					{return length;}
	public ArrayList<Edge<T>> getNeighbours() 	{return neighbours;}	

	public void setVertex0(Point<T> point0) 				{this.point0 = point0;}
	public void setVertex1(Point<T> point1) 				{this.point1 = point1;}
	public void setLength(double length)					{this.length = length;}
	public void neighbours(ArrayList<Edge<T>> neighbours) 	{this.neighbours = neighbours;}

	/*
	 * Constructor
	 * @param Point<T> point0
	 * @param Point<T> point1
	 */
	Edge(Point<T> point0, Point<T> point1)
	{
		Edge.number++;
		this.id = number;
		this.point0 = point0;
		this.point1 = point1;
		calculateLength();
	}
	
	private void calculateLength()
	{
		this.length = (double) 
				Operation.squareRoot(
				Operation.add4(	Operation.powerOf(Operation.sub2(point0.getAVertex().getX(), point1.getAVertex().getX()), 2),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getY(), point1.getAVertex().getY()), 2),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getZ(), point1.getAVertex().getZ()), 2),
								Operation.powerOf(Operation.sub2(point0.getAVertex().getW(), point1.getAVertex().getW()), 2)));
	}
	
	public void addNeighbour(Edge<T> aEdge)
	{
		neighbours.add(aEdge);
	}	
}
