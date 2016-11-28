/** 
 * Vertex.java
 * (c) Copyright 04-2016 Robert K�lpmann
 *  Vertex-Klasse zum Speichern von Vertecies
 *  Dependencies:
 *  	Vector4 - Klasse
 * 
 *  - pos, tex, norm
 *  
 *  @author Robert K�lpmann
 *  @version 1.0
 */

package obj;

public class Point
{	
	//Aus Gr�nden der Bequemlichkeit: public, statt Getter+Setter
	public int vertexId;
	public int texId;
	public int normId;
	
	public Point(){}
	
	public Point(int vertex, int tex)
	{
		this.vertexId = vertex;
		this.texId = tex;
	//	this.normId = new Vector4id<>(0.1f); // Problem: welchen Wert f�r z W�hlen? (am besten im Moment 0.1f)
		this.normId = 0;
	}

	public Point(int vertex, int tex, int norm)
	{
		this.vertexId = vertex;
		this.texId = tex;
		this.normId = norm;
	}

}