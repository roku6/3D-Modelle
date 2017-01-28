package obj;

import java.util.ArrayList;

/**
 * @author Login
 *
 * @param <T>
 */
public class PointExt<T extends Number> extends Point<T>
{
	private static int number = 0;
	private int id = 0;
	private ArrayList<PointExt<Double>> neighbourList = new ArrayList<>(); 
	private ArrayList<PointExt<Double>> cornerPointList = new ArrayList<>();
	private double angle = 0;
	
	public int getId() 										{return id;}
	public ArrayList<PointExt<Double>> getNeighbourList() 	{return neighbourList;}
	public ArrayList<PointExt<Double>> getCornerPointList() {return cornerPointList;}
	public double getAngle()								{return angle;}
	
	public void setAngle(double angle)										{this.angle = angle;}		
	private void setId(int id) 												{this.id = id;}
	public void setNeighbourList(ArrayList<PointExt<Double>> neighbourList)	{this.neighbourList = neighbourList;}
	
	/**
	 * 
	 */
	public PointExt()
	{
		super();
		PointExt.number++;
		this.setId(number);
	}
	/**
	 * 
	 * @param aPoint
	 */
	public PointExt(PointExt<T> aPoint)
	{
		super(aPoint.getAVertex(), aPoint.getATexture(), aPoint.getANormal());
		PointExt.number++;
		this.setId(number);
	}
	/**
	 * 
	 * @param vertex
	 * @param tex
	 * @param normal
	 */
	public PointExt(Vertex<T> vertex, Texture<T> tex, Normal<T> normal)
	{
		super(vertex, tex, normal);
		PointExt.number++;
		this.setId(number);
	}
	/**
	 * 
	 * @param aPoint
	 */
	public void addNeighbour(PointExt<Double> aPoint)
	{
		neighbourList.add(aPoint);
	}
	/**
	 * 
	 * @param aPoint
	 */
	public void addCornerPoint(PointExt<Double> aPoint)
	{
		cornerPointList.add(aPoint);
	}
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}