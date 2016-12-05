package src;

import java.util.ArrayList;

import org.neo4j.graphdb.Result;

/**
 * @author Lennard Flegel
 *
 * Class to gather information about the object to search for
 * and generate the query needed. 
 * 
 * TODO generate Cypher code, implement comparison, 
 * think of a smart way to hand over all the information needed
 * 
 */

public class SearchLogic {

	//Members
	private static ArrayList<Searchobject> searchObjects;
	
	//Methods

	
	/**
	 *TODO
	 */
	public static Result generateAndExecuteQuery (){
		//TODO Generate CQL code according to searchObjects
		// keep in mind that variance may be required 
		String cypher=null;
		Result result;
		result = DBController.executeQuery(cypher);
		return result;
	}
	/**
	 * TODO
	 */
	public static double calcSimilarity(Result result){
		//TODO for each Row "patternFromDD" in Result do
		// compare patternFromDB to each Searchobject "object" in this.Searchobjects
		// find a way to measure similarity (subtraction?) and 
		// return an average for each pattern found
		return 0;
		
	}

	
	/**
	 * Adds one Searchobject type to the Array of Searchobjects
	 * 
	 * @param toAdd The new Searchobject
	 */
	public static void addSearchobject(Searchobject toAdd){
		searchObjects.add(toAdd);
	}
	/**
	 * @return the searchObjects
	 */
	public static ArrayList<Searchobject> getSearchObjects() {
		return searchObjects;
	}
	/**
	 * @param searchObjects the searchObjects to set
	 */
	public static void setSearchObjects(ArrayList<Searchobject> newSearchObjects) {
		searchObjects = newSearchObjects;
	}
	
	
	
}
