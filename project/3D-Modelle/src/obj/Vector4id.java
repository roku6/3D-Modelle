package obj;

public class Vector4id<T extends Number> extends Vector4<T> 
{
	private static int number = 0;
	protected int id = 0;
	
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
	public String toString()
	{
		return 	this.getId() + " " + this.getX() + " " + this.getY() + " " + this.getZ();
	}
}
