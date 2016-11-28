package src;
/**
 * @author Lennard Flegel, Robert Külpmann
 *
 * Simple class providing a data structure to represent 2 adjacent edges and the angle between them for easy access 
 * whilst writing to DB
 *
 */
public class RelationsDefinition 
{
	//Members
	private Edge<Double> edge1; 	//Komplett alles übergeben
	private Edge<Double> edge2;	//komplett alles
	private double angle;	
	
	//Constructors
	/**
	 * Constructor setting all 3 values
	 * 
	 * @param e1 first edge
	 * @param e2 second edge
	 * @param angle angle between e1, e2
	 */
	public RelationsDefinition(Edge<Double> e1, Edge<Double> e2, double angle) {
		this.edge1 = e1;
		this.edge2 = e2;
		this.angle = angle;
	}
	
	//Methods
	/**
	 * Getter for edge1
	 * @return edge1
	 */
	public Edge<Double> getEdge1() {
		return edge1;
	}

	/**
	 * Getter for edge2
	 * @return edge2
	 */
	public Edge<Double> getEdge2() {
		return edge2;
	}

	/**
	 * Getter for angle
	 * @return angle
	 */
	public double getWinkel() {
		return angle;
	}

}
