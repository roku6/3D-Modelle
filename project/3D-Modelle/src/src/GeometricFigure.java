package src;

import java.util.ArrayList;

/**
 * @author Lennard Flegel
 *
 * Simple class providing a data structure to represent a geom. object for easy access whilst writing to DB
 *
 */
public class GeometricFigure
{
	//Members
	private ArrayList<RelationsDefinition> edgeRelations;
	private Integer objectID;
	private String description;
	private String pictureURL;
	
	//Constructors
	/**
	 * @param edgeRelations
	 * @param objectID
	 * @param description
	 * @param pictureURL
	 */
	public GeometricFigure(ArrayList<RelationsDefinition> edgeRelations, Integer objectID, String description,
			String pictureURL) {
		this.edgeRelations = edgeRelations;
		this.objectID = objectID;
		this.description = description;
		this.pictureURL = pictureURL;
	}

	//Methods
	/**
	 * @return the edgeRelations
	 */
	public ArrayList<RelationsDefinition> getEdgeRelations() {
		return edgeRelations;
	}

	/**
	 * @param edgeRelations the edgeRelations to set
	 */
	public void setEdgeRelations(ArrayList<RelationsDefinition> edgeRelations) {
		this.edgeRelations = edgeRelations;
	}

	/**
	 * @return the objectID
	 */
	public Integer getObjectID() {
		return objectID;
	}

	/**
	 * @param objectID the objectID to set
	 */
	public void setObjectID(Integer objectID) {
		this.objectID = objectID;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the pictureURL
	 */
	public String getPictureURL() {
		return pictureURL;
	}

	/**
	 * @param pictureURL the pictureURL to set
	 */
	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	
	

}
