package obj;

/**
 * @author Login
 *
 * @param <T>
 */
public class Vector4id<T extends Number> extends Vector4<T> 
{
	private static int number = 0;
	protected int id = 0;
	private double epsilon = 0.00001;
	
	public int getId() 				{return id;}
	public void setId(int id) 		{this.id = id;}
	
	Vector4id(Vector4<T> aVector4)
	{
		super(aVector4);
		this.id = number;
		Vector4id.number++;
	}
	
	public Vector4id(T x, T y, T z, T w)
	{
		super(x,y,z,w);
		this.id = number;
		Vector4id.number++;
	}
	
	public Vector4id(T value)
	{
		super(value);
		this.id = number;
		Vector4id.number++;
	}
	
	@Override
	public int hashCode()
	{
		//System.out.println("id" + id + " :" + getX().hashCode());
		return getY().hashCode(); 
	}
	
	public boolean equals(Object obj)
	{
		if (obj instanceof Vector4id)
		{
			Vector4id<Double> aVector = (Vector4id<Double>) obj;
			Vector4id<Double> aVector2 = (Vector4id<Double>) this;
			
			if (Math.abs(aVector.getX() - aVector2.getX()) <= epsilon &&
				Math.abs(aVector.getY() - aVector2.getY()) <= epsilon &&
				Math.abs(aVector.getZ() - aVector2.getZ()) <= epsilon &&
				Math.abs(aVector.getW() - aVector2.getW()) <= epsilon
				)
				
				/*
			if (aVector.getX().compareTo(aVector2.getX()) == 0 &&
				aVector.getY().compareTo(aVector2.getY()) == 0 &&
				aVector.getZ().compareTo(aVector2.getZ()) == 0 &&
				aVector.getW().compareTo(aVector2.getW()) == 0)
				*/
				return true;	
		}
		else
		{
			System.out.println("Ohje!");
		}
		return false;
	}

	public String toString()
	{
		return 	this.getId() + "(" + this.getX() + "|" + this.getY() + "|" + this.getZ() + "|" + this.getW() +")";
	}
}
