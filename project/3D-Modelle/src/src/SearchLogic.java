package src;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

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
	private static Double toleranceLength,toleranceAngle;
	
	//Methods

	

	/**
	 *This method generates a CQL query string bases on the searchObjects ArrayList Member.
	 *To realize the ability of searching with a given tolerance, the query consists of a match for a (relatively)long list of node-rel-node patterns
	 *and a (relatively)long where clause. 
	 *The return of the query is the entire node1 and rel.Angle 
	 *
	 *    @return The CQL string to query for the properties given in the searchObjects ArrayList
	 */
	public static String generateQuery (){
		String cypher="Match ";
		String whereClausesTemp = " Where ";
		String returnsTemp =" Return ";
		for (Searchobject sObject : searchObjects){
			//Defining pattern to look for
			String temp = "(n"+ sObject.getId1().toString()+":EDGE)-[r"+sObject.getId1().toString()+": CONNECTED]-(n"+sObject.getId2().toString()+"), ";
			cypher.concat(temp);
			//Defining values to look for
			//Length
			temp=  (sObject.getLength()-toleranceLength)+"<"+"n"+ sObject.getId1().toString()+".LENGTH"+"<"+(sObject.getLength()+toleranceLength) + "AND "+
			//Angles
			(sObject.getAngle()-toleranceLength)+"<"+"r"+ sObject.getId1().toString()+".ANGLE"+"<"+(sObject.getAngle()+toleranceLength) + "AND ";
			whereClausesTemp.concat(temp);
			//Defining returns
			temp=
			"n"+ sObject.getId1().toString()+".LENGTH"+
			"n"+ sObject.getId1().toString()+".URL"+
			"n"+ sObject.getId1().toString()+".DESCRIPTION"+
			"n"+ sObject.getId1().toString()+".id"+
			",r"+sObject.getId1().toString()+".ANGLE"+","; //+",n"+ sObject.getId2().toString()+ needed? to be tested
			returnsTemp.concat(temp);
		}
		// aus den 3 teilstrings die letzen kommata/AND usw löschen, am ende ist eins zu viel
		String temp =cypher.substring(0, cypher.length()-2);
		cypher = temp;
		temp= whereClausesTemp.substring(0, whereClausesTemp.length()-4);
		whereClausesTemp = temp;
		temp= returnsTemp.substring(0, returnsTemp.length()-1);
		returnsTemp=temp;
		
		cypher.concat(whereClausesTemp).concat(returnsTemp);
		
		return cypher;
	}
	/**
	 * TODO
	 */
	public static void calcSimilarity(Result result){
		//TODO for each Row "patternFromDD" in Result do
		// compare patternFromDB to each Searchobject "object" in this.Searchobjects
		// find a way to measure similarity (subtraction?) and 
		// return an average for each pattern found^
		
		Map<Integer, String> deviationMap = null;
		double foundId=0;
		double foundAngleSum=0;
		double foundLengthSum=0;
		
		double expectedAngleSum=0;
		double expectedLengthSum=0;
		
		for (Searchobject sObj : searchObjects){
			expectedAngleSum += sObj.getAngle();
			expectedLengthSum += sObj.getLength();
		}
		
		while (result.hasNext()){
			foundId=0;
			foundAngleSum=0;
			foundLengthSum=0;
			
			Set<Map.Entry<String,Object>> entries = result.next().entrySet();
			for (Map.Entry<String, Object> entry : entries ){
				
				if (foundId==0){
					if (entry.getKey().contains("id")){
					foundId=(double) entry.getValue();
				    }
				}
				if (entry.getKey().contains("LENGTH")){
					foundLengthSum += (double)entry.getValue();
				}
				else if(entry.getKey().contains("ANGLE")){
					foundAngleSum += (double)entry.getValue();
				}
				
			}
			
			deviationMap.put((int)foundId,Math.abs(expectedLengthSum-foundLengthSum)/searchObjects.size()+", "+ Math.abs(expectedAngleSum-foundAngleSum)/searchObjects.size());
			//TODO reduzieren auf nur das ähnlichste pattern pro objekt, erdtellen von objekten der neuen klasse 
			
		}
		
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
	/**
	 * @return the toleranceAngle
	 */
	public static Double getToleranceAngle() {
		return toleranceAngle;
	}
	/**
	 * @param toleranceAngle the toleranceAngle to set
	 */
	public static void setToleranceAngle(Double toleranceAngle) {
		SearchLogic.toleranceAngle = toleranceAngle;
	}
	/**
	 * @return the tolerance
	 */
	public static Double getToleranceLength() {
		return toleranceLength;
	}
	/**
	 * @param tolerance the tolerance to set
	 */
	public static void setToleranceLength(Double tolerance) {
		SearchLogic.toleranceLength = tolerance;
	}

	
	
	
}
