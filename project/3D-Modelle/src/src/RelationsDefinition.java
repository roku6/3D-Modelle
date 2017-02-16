package src;
/**
 * The Class Relationsefinition:
 * Simple class providing a data structure to represent 2 adjacent edges and the angle between them for easy access 
 * whilst writing to DB
 * @author Lennard Flegel, Robert Külpmann
 */
public class RelationsDefinition 
{
	//Members
	private Edge<Double> edge1; 	//Komplett alles übergeben
	private Edge<Double> edge2;	//komplett alles
	private double angle;	
	
	
	//Constructors
	
	/**
	 * Empty Constructor
	 */
	public RelationsDefinition(){}
	
	
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
	
	@Override
	public String toString()
	{
		return getEdge1().toString() + " | " + getEdge2().toString() + " | " + getAngle();
	}
	
	
	//Getters and setters
	
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
	public double getAngle() {
		return angle;
	}
	/**
	 * Setter for edge1 
	 * @param edge1 Edge to which the first Edge should be set
	 */
	public void setEdge1(Edge<Double> edge1) {
		this.edge1 = edge1;
	}
	/**
	 * Setter for edge2
	 * @param edge2 Edge to which the second Edge should be set
	 */
	public void setEdge2(Edge<Double> edge2) {
		this.edge2 = edge2;
	}
	/**
	 * Setter for angle
	 * @param angle
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}


	
}
