/** 
 * Vertex.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Vertex Klasse - Speichert Vertexformat einer OBJ Datei
 *  Extends:
 *  	Vector4id - Klasse (obj)   
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  TODO: Redundanzen entfernen (siehe Vector4id, Vector4, Vertex, Texture, Normal)
 */

package obj;

public class Vertex<T extends Number> extends Vector4id<T> 
{
	/* 
	 * Attention: First ID and First number = 1 !
	 */
	private static int number = 0;
	
	public static int getNumber() 	{return number;}	
	public static void setNumber(int number) 	{Vertex.number = number;}

	/**
	 * 
	 * @param aVector4
	 */
	public Vertex(Vector4<T> aVector4)
	{
		super(aVector4);
		Vertex.number++;
		this.id = number;
	}
	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public Vertex(T x, T y, T z, T w)
	{
		super(x,y,z,w);
		Vertex.number++;
		this.id = number;
	}
	
	/**
	 * 
	 * @param value
	 */
	public Vertex(T value)
	{
		super(value);
		Vertex.number++;
		this.id = number;
	}
}
