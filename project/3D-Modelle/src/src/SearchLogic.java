package src;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.neo4j.graphdb.Result;

/**
 * @author Lennard Flegel
 *
 * Singleton class to gather information about the object to search for, generate the CQL query needed and compute the derivation of the Objects found.
 * Singleton NOT threadsafe.
 * 
 */

public class SearchLogic {

	//Members
	private static SearchLogic instance;
	private ArrayList<Searchobject> searchObjects;
	private Double toleranceLength,toleranceAngle;
	
	//Constructors
	/**
	 *Private constructor to apply singleton. Creates a searchlogic. To be called by getInstance().
	 * 
	 * @param searchObjects List of Searchobject types describing the pattern to look for defined by the users GUI input.
	 * @param tolLen Tolerance the user set for edge length
	 * @param tolAng Tolerance the user set for angles
	 */
	private SearchLogic(ArrayList<Searchobject> searchObjects, Double tolLen, Double tolAng) {
		this.searchObjects = searchObjects;
		toleranceLength =tolLen;
		toleranceAngle= tolAng;
	}
	
	
	//Methods

	/**
	 * Method to realize Singleton-Pattern
	 * @param searchObjects List of Searchobject types describing the pattern to look for defined by the users GUI input.
	 * @param tolLen Tolerance the user set for edge length
	 * @param tolAng Tolerance the user set for angles
	 * 
	 * @return the only SearchLogic object 
	 */
	public static SearchLogic getInstance (ArrayList<Searchobject> searchObjects, Double tolLen, Double tolAng) {
		    if (SearchLogic.instance == null) {
		    	SearchLogic.instance = new SearchLogic (searchObjects, tolLen, tolAng);
		    }
		    return SearchLogic.instance;
		 }

	
	/**
	 *This method generates a CQL query string based on the searchObjects ArrayList Member.
	 *To realize the ability of searching with a given tolerance, the query consists of a match for a (relatively)long list of node-rel-node patterns
	 *and a (relatively)long where clause. 
	 *The return of the query is the entire node and rel.Angle. Columns look like this:
	 * n1.Length | n1.Url | n1.Description | n1.ObjectId | r1.Angle | n2.Length | n2.Url | ... | ... 
	 *
	 *@return The CQL string to query for the properties given in the searchObjects ArrayList
	 */
	public String generateQuery (){
		String cypher="MATCH ";
		String whereClausesTemp = " WHERE ";
		String returnsTemp =" RETURN DISTINCT ";
		for (Searchobject sObject : searchObjects){
			//Defining pattern to look for
			String temp = "(n"+ sObject.getId1().toString()+":EDGE)-[r"+sObject.getId1().toString()+": CONNECTED]-(n"+sObject.getId2().toString()+"), ";
			cypher+=temp;
			//Defining values to look for
			//Length
			temp=  (sObject.getLength()-toleranceLength)+"<"+"n"+ sObject.getId1().toString()+".LENGTH"+"<"+(sObject.getLength()+toleranceLength) + " AND "+
			//Angles
			(sObject.getAngle()-toleranceAngle)+"<"+"r"+ sObject.getId1().toString()+".ANGLE"+"<"+(sObject.getAngle()+toleranceAngle) + " AND ";
			whereClausesTemp+=temp;
			//Defining returns
			temp=
			"n"+ sObject.getId1().toString()+".LENGTH"+", "+
			"n"+ sObject.getId1().toString()+".URL"+", "+
			"n"+ sObject.getId1().toString()+".DESCRIPTION"+", "+
			"n"+ sObject.getId1().toString()+".OBJECT_ID"+", "+
			"r"+sObject.getId1().toString()+".ANGLE"+", "; 
			returnsTemp+=temp;
		}
		// the generated substrings must be shortened to not include the last, redundant comma/space
		String temp =cypher.substring(0, cypher.length()-2);
		cypher = temp;
		temp= whereClausesTemp.substring(0, whereClausesTemp.length()-4);
		whereClausesTemp = temp;
		temp= returnsTemp.substring(0, returnsTemp.length()-2);
		returnsTemp=temp;
		returnsTemp+= " ORDER BY n1.OBJECT_ID";
		
		//Concatenate all substrings
		cypher+=(whereClausesTemp+=returnsTemp);
		
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
	public ArrayList<Foundobject> calcSimilarity(Result result){
		//for each Row "patternFromDD" in Result do
		// compare patternFromDB to each Searchobject "object" in this.Searchobjects
		// find a way to measure similarity (subtraction?) and 
		// return an average for each pattern found^
		ArrayList<Foundobject> foundObjectsArray= new ArrayList<Foundobject>();
		int foundId=0;
		double foundAngleSum=0;
		double foundLengthSum=0;
		String foundUrl="";
		String foundDescription="";
		
		double expectedAngleSum=0;
		double expectedLengthSum=0;
		
		Double devLength=0.;
		Double devAngle=0.;
		
		//Calc expected sum
		for (Searchobject sObj : searchObjects){
			expectedAngleSum += sObj.getAngle();
			expectedLengthSum += sObj.getLength();
		}
		
		//Calc found sum for each row of the result table and gather information like object id, description etc
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
				else if (foundUrl=="") {
					if (entry.getKey().contains("URL")) {
						foundUrl = entry.getValue().toString();
					} 
				}
				else if (foundDescription==""){
					if (entry.getKey().contains("DESCRIPTION")) {
						foundDescription = entry.getValue().toString();
					} 
				}
				else if (entry.getKey().contains("LENGTH")){
					foundLengthSum += (double)entry.getValue();
				}
				else if(entry.getKey().contains("ANGLE")){
					foundAngleSum += (double)entry.getValue();
				}
			}
			//Calc derivation from expectedSums and normalize it
			devLength = Math.abs(expectedLengthSum -foundLengthSum )/searchObjects.size();
			devAngle  = Math.abs(expectedAngleSum  -foundAngleSum  )/searchObjects.size();

			//Only add found object to results if the deviation is less than any existing entry for that object_id. 
			//Combined length/angle deviation calculated using the length of the vector(devLength,devAngle). Shorter vector -> obj more likely similar
		    boolean mustBeWrittenToResults = true;
			for (Foundobject fObj : foundObjectsArray){
		    	if(fObj.getId()==foundId){  
		    		if(Math.sqrt(fObj.getAngSim()*fObj.getAngSim() + fObj.getLenSim()*fObj.getLenSim()) >  Math.sqrt(devLength*devLength+devAngle*devAngle) ){
		    			foundObjectsArray.add(new Foundobject(foundId, devLength, devAngle, foundUrl, foundDescription));
		    			mustBeWrittenToResults=false;
		    			foundObjectsArray.remove(fObj);
		    		}else{
		    			//Same object but higher derivation -> don't add to results
		    			mustBeWrittenToResults=false;
		    		}
		    	}
		    }
			//If the mustbewritten flag is true at this point, the results array does not contain any other representation of this object yet,
			//so this is automatically the most similar one -> add this one to results
			if(mustBeWrittenToResults){
				foundObjectsArray.add(new Foundobject(foundId, devLength, devAngle, foundUrl, foundDescription));
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
	public void addSearchobject(Searchobject toAdd){
		searchObjects.add(toAdd);
	}
	
	
	//Getters and Setters
	
	/**
	 * @return the searchObjects
	 */
	public ArrayList<Searchobject> getSearchObjects() {
		return searchObjects;
	}
	/**
	 * @param searchObjects the searchObjects to set
	 */
	public void setSearchObjects(ArrayList<Searchobject> newSearchObjects) {
		searchObjects = newSearchObjects;
	}
	/**
	 * @return the toleranceAngle
	 */
	public Double getToleranceAngle() {
		return toleranceAngle;
	}
	/**
	 * @param toleranceAngle the toleranceAngle to set
	 */
	public void setToleranceAngle(Double toleranceAngle) {
		this.toleranceAngle = toleranceAngle;
	}
	/**
	 * @return the tolerance
	 */
	public Double getToleranceLength() {
		return toleranceLength;
	}
	/**
	 * @param tolerance the tolerance to set
	 */
	public void setToleranceLength(Double tolerance) {
		this.toleranceLength = tolerance;
	}

	
	
	
}
