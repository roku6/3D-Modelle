/** 
 * Texture.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Texture Klasse - Speichert Textureformat einer OBJ Datei
 *  Extends:
 *  	Vector4id - Klasse (obj)   
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  TODO: Redundanzen entfernen (siehe Vector4id, Vector4, Vertex, Texture, Normal)
 */

package obj;

public class Texture<T extends Number> extends Vector4id<T> 
{
	private static int number = 0;
	
	public static int getNumber() 	{return number;}	
	public static void setNumber(int number) 	{Texture.number = number;}
	
	Texture(Vector4<T> aVector4)
	{
		super(aVector4);
		Texture.number++;
		this.id = number;
	}
	
	public Texture(T x, T y, T z, T w)
	{
		super(x,y,z,w);
		Texture.number++;
		this.id = number;
	}
	
	public Texture(T value)
	{
		super(value);
		Texture.number++;
		this.id = number;
	}	
}
