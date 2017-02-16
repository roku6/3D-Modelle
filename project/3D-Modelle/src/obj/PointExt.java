package obj;


import java.util.ArrayList;

/**
 * @author Robert Külpmann
 *
 * @param <T>
 */
public class PointExt<T extends Number> extends Point<T> implements Comparable<PointExt<T>>
{
	private static int number = 0;
	private int id = 0;
	private ArrayList<PointExt<Double>> neighbourList = new ArrayList<>(); 
	private double angle = 0;
	private double epsilon = 0.0001;
	
	public int getId() 										{return id;}
	public ArrayList<PointExt<Double>> getNeighbourList() 	{return neighbourList;}
	public double getAngle()								{return angle;}
	
	public static void setNumber(int number)								{PointExt.number = number;}
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
	 * @param angle
	 * @return
	 */
	public boolean compareAngle(double angle)
	{
		if (Math.abs(this.getAngle() - angle) <= epsilon)	return true;
		else return false;
	}

	@Override
	public int compareTo(PointExt<T> aPointExt)
	{
		/*
		if (aPointExt == null)
		{
			System.out.println("PROBLEM!");
		}
		
		if (this.getAVertex() == null && aPointExt.getAVertex() == null)
		{
			System.out.println("");
			return 0;
		}
		*/
		int comparisonX = this.getAVertex().getId() > aPointExt.getAVertex().getId() ? 1 : 
							this.getAVertex().getId() < aPointExt.getAVertex().getId() ? -1 : 0; 
		if (comparisonX != 0) return comparisonX;
		else 
		{
			int comparisonY = this.getATexture().getId() > aPointExt.getATexture().getId() ? 1 : 
								this.getATexture().getId() < aPointExt.getATexture().getId() ? -1 : 0; 
			if (comparisonY != 0) return comparisonY;
			else
				return this.getANormal().getId() > aPointExt.getANormal().getId() ? 1 : 
						this.getANormal().getId() < aPointExt.getANormal().getId() ? -1 : 0; 
		}

	}
	
	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}
}