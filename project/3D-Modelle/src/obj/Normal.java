package obj;

public class Normal<T extends Number> extends Vector4<T> 
{
	public static int number = 0;
	public int id = 0;
		
	Normal(Vector4<T> aVector4)
	{
		super(aVector4);
		this.id = number;
		Vector4id.number++;
	}
	
	public Normal(T x, T y, T z, T w)
	{
		super(x,y,z,w);
		this.id = number;
		Vector4id.number++;
	}
	
	public Normal(T value)
	{
		super(value);
		this.id = number;
		Vector4id.number++;
	}
}
