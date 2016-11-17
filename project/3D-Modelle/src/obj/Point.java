/** 
 * Vertex.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Vertex-Klasse zum Speichern von Vertecies
 *  Dependencies:
 *  	Vector4 - Klasse
 * 
 *  - pos, tex, norm
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 */

package obj;

public class Point
{	
	//Aus Gründen der Bequemlichkeit: public, statt Getter+Setter
	public Vector4<Float> vertex;
	public Vector4<Float> tex;
	public Vector4<Float> norm;
	
	public Point(){}
	
	public Point(Vector4<Float> vertex, Vector4<Float> tex)
	{
		this.vertex = vertex;
		this.tex = tex;
		this.norm = new Vector4<>(0.1f); // Problem: welchen Wert für z Wählen? (am besten im Moment 0.1f)
	}

	public Point(Vector4<Float> vertex, Vector4<Float> tex, Vector4<Float> norm)
	{
		this.vertex = vertex;
		this.tex = tex;
		this.norm = norm;
	}

}