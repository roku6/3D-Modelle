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
 * 
 */

public class SearchLogic {

	//Members
	private static ArrayList<Searchobject> searchObjects;
	private static Double toleranceLength,toleranceAngle;
	
	//Methods

	

	/**
	 *This method generates a CQL query string based on the searchObjects ArrayList Member.
	 *To realize the ability of searching with a given tolerance, the query consists of a match for a (relatively)long list of node-rel-node patterns
	 *and a (relatively)long where clause. 
	 *The return of the query is the entire node and rel.Angle. Columns look like this:
	 * n1.Length | n1.Url | n1.Description | n1.ObjectId | r1.Angle | n2.Length | n2.Url | ... | ... 
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
			(sObject.getAngle()-toleranceAngle)+"<"+"r"+ sObject.getId1().toString()+".ANGLE"+"<"+(sObject.getAngle()+toleranceAngle) + "AND ";
			whereClausesTemp.concat(temp);
			//Defining returns
			temp=
			"n"+ sObject.getId1().toString()+".LENGTH"+","+
			"n"+ sObject.getId1().toString()+".URL"+","+
			"n"+ sObject.getId1().toString()+".DESCRIPTION"+","+
			"n"+ sObject.getId1().toString()+".OBJECT_ID"+","+
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
	 * This Method calculates the similarity (rather derivation) to the exact pattern entered for query for every row in result and
	 * creates an array of Foundobjects containing only the most similar representation per Object found.
	 * To calculate the derivation, the lengths and angles from both the wanted and the found patterns are added up and the difference divided
	 * by the amount of edges/angles to normalize the result.  
	 * 
	 * @param result The result of a CQL query
	 * @return An ArrayList of Foundobjects, containing only the most similar representation per Object found
	 * 
	 */
	@SuppressWarnings("null")
	public static ArrayList<Foundobject> calcSimilarity(Result result){
		//for each Row "patternFromDD" in Result do
		// compare patternFromDB to each Searchobject "object" in this.Searchobjects
		// find a way to measure similarity (subtraction?) and 
		// return an average for each pattern found^
		ArrayList<Foundobject> foundObjectsArray=null;
		int foundId=0;
		double foundAngleSum=0;
		double foundLengthSum=0;
		String foundUrl="";
		String foundDescription="";
		
		double expectedAngleSum=0;
		double expectedLengthSum=0;
		
		Double devLength=0.;
		Double devAngle=0.;
		
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
				
				if (entry.getKey().contains("OBJECT_ID")){
					if (foundId==0){
					foundId=(int) entry.getValue();
				    }
				}
				if (foundUrl=="") {
					if (entry.getKey().contains("URL")) {
						foundUrl = entry.getValue().toString();
					} 
				}
				if (foundDescription==""){
					if (entry.getKey().contains("DESCRIPTION")) {
						foundDescription = entry.getValue().toString();
					} 
				}
				if (entry.getKey().contains("LENGTH")){
					foundLengthSum += (double)entry.getValue();
				}
				else if(entry.getKey().contains("ANGLE")){
					foundAngleSum += (double)entry.getValue();
				}
				
			}
			devLength = Math.abs(expectedLengthSum -foundLengthSum )/searchObjects.size();
			devAngle  = Math.abs(expectedAngleSum  -foundAngleSum  )/searchObjects.size();

			
			
			//Only add found object to results if the deviation is less than any existing entry for that Object_id. 
			//Combined length/angle deviation calculated using the length of the vector(devLength,devAngle). Shorter vector -> obj more likely similar
		    for (Foundobject fObj : foundObjectsArray){
		    	if(fObj.getId()==foundId &&  
		    			Math.sqrt(fObj.getAngSim()*fObj.getAngSim() + fObj.getLenSim()*fObj.getLenSim()) >
		    			Math.sqrt(devLength*devLength+devAngle*devAngle) ){
		    		foundObjectsArray.add(new Foundobject(foundId, devLength, devAngle, foundUrl, foundDescription));	
		    		foundObjectsArray.remove(fObj);
		    	}
		    }
		    
		}
		result.close();
		return foundObjectsArray;
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
