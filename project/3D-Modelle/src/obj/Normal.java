/** 
 * Normal.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Normal Klasse - Speichert Normalformat einer OBJ Datei
 *  Extends:
 *  	Vector4id - Klasse (obj)   
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  TODO: Redundanzen entfernen (siehe Vector4id, Vector4, Vertex, Texture, Normal)
 */

package obj;

public class Normal<T extends Number> extends Vector4id<T> 
{
	private static int number = 0;
	
	public static int getNumber() 	{return number;}
	public static void setNumber(int number) 	{Normal.number = number;}
	
	Normal(Vector4<T> aVector4)
	{
		super(aVector4);
		Normal.number++;
		this.id = number;
	}
	
	public Normal(T x, T y, T z, T w)
	{
		super(x,y,z,w);
		Normal.number++;
		this.id = number;
	}
	
	public Normal(T value)
	{
		super(value);
		Normal.number++;
		this.id = number;
	}	
}
