/** 
 * Point.java
 * (c) Copyright 2016 Robert Külpmann
 *  Pointclass zum Speichern von 
 *  Dependencies:
 *  	Vector4 - Klasse
 * 
 *  - pos, tex, norm
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  TODO: Nichts! Fertig!
 */

package obj;

public class Point<T extends Number>
{		
	private Vertex<T> aVertex;
	private Texture<T> aTexture;
	private Normal<T> aNormal;
	
	public Vertex<T> getAVertex() 		{return aVertex;}
	public Texture<T> getATexture() 	{return aTexture;}
	public Normal<T> getANormal() 		{return aNormal;}
	
	public void setAVertex(Vertex<T> aVertex) 		{this.aVertex = aVertex;}
	public void setATexture(Texture<T> aTexture)	{this.aTexture = aTexture;}
	public void setANormal(Normal<T> aNormal) 		{this.aNormal = aNormal;}

	/**Empty Constructor
	 * initialize the vertex, texture and normal of a Pointobject 
	**/
	public Point()
	{
		this.aVertex = null;
		this.aTexture = null;
		this.aNormal = null;
	}
	
	/**
	 * Constructor without Normal
	 * @param Vertex<T> vertex
	 * @param Texture<T> texture
	 **/
	public Point(Vertex<T> vertex, Texture<T> tex)
	{
		this.aVertex = vertex;
		this.aTexture = tex;
		this.aNormal = null;
	}
	/**
	 * Constructor without Normal
	 * @param Vertex<T> vertex
	 * @param Texture<T> texture
	 * @param Normal<T> normal
	 **/

	public Point(Vertex<T> vertex, Texture<T> tex, Normal<T> normal)
	{
		this.aVertex = vertex;
		this.aTexture = tex;
		this.aNormal = normal;
	}
	
	/** To String Methode
	 * Overrides the standard to String methode
	 * @return String aString shows Vertex-/Texture- and NormalID
	 */
	@Override
	public String toString()
	{
		String aString = "";
		if (this.aVertex != null) aString += this.aVertex.getId() + "/";
		else aString += "-/";
		if (this.aTexture != null) aString += this.aTexture.getId() + "/";
		else aString += "-/";
		if (this.aNormal != null) aString += this.aNormal.getId() + "/";
		else aString += "-/";
		return aString;
	}
}