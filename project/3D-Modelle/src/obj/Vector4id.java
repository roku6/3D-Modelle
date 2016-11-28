package obj;

public class Vector4id<T extends Number> extends Vector4<T> 
{
	public static int number = 0;
	public int id = 0;
		
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
}
