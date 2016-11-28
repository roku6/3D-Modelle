package src;

import java.util.ArrayList;

import obj.Operation;
import obj.Point;
import obj.Vector4;

public class Edge<T extends Number> 
{
	int static number= 0;
	int id = 0;
	Point<T> vertex0;
	Point<T> vertex1;
	double length;
	ArrayList<Edge<T>> neighbours;
	
	Edge(Vector4<T> vertex0, Vector4<T> vertex1)
	{
		this.vertex0 = vertex0;
		this.vertex0 = vertex1;
		calculateLength();
	}
	
	private void calculateLength()
	{
		this.length = (double) 
				Operation.squareRoot(
				Operation.add4(	Operation.powerOf(Operation.sub2(vertex0.getX(), vertex1.getX()), 2),
								Operation.powerOf(Operation.sub2(vertex0.getY(), vertex1.getY()), 2),
								Operation.powerOf(Operation.sub2(vertex0.getZ(), vertex1.getZ()), 2),
								Operation.powerOf(Operation.sub2(vertex0.getW(), vertex1.getW()), 2)));
	}
	
	public void addNeighbour(Edge<T> aEdge)
	{
		neighbours.add(aEdge);
	}
	
}
