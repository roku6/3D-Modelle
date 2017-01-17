
package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.unsafe.batchinsert.BatchInserter;
import org.neo4j.unsafe.batchinsert.BatchInserters;

/**
 * @author Lennard Flegel
 *
 * Singleton-class for DB-access. Singleton NOT threadsafe.
 * Make sure to invoke initializeDB to get started 
 * and the shutdown method once your transactions are done.
 *  
 *  
 *  TODO drop constructor? to be discussed 
 *
 */
public class DBController {
	
	//Members
	private static DBController instance;
	private static File directory;
	private static GraphDatabaseService graphDb;
	private static ArrayList<Integer> usedIds;
	private static enum FigureLabels implements Label {EDGE;};
	private static enum FigureRelTypes implements RelationshipType{CONNECTED;};
	//Constructors
	/**
	 * Constructor. Creates an DBController, sets up a Neo4J DB at given directory and initializes it. 
	 * 
	 * @param directory Target directory to initialize DB
	 */
	private DBController(File directory) {
		DBController.directory = directory;
		initializeDB();
	}
	
	//Methods
	
	//Private
	
  	/**
 	 * Registers a shutdown hook for the Neo4j instance so that it
	 * shuts down nicely when the VM exits (even if you "Ctrl-C" the
	 * running application).
	 * 
	 * @param GraphDatabaseService GDBService to link the hook to
	 */
	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}
	
	
	/**
	*Fills the usedIds ArrayList with all distinct Object-Ids used in the DB and sorts the list.
	*PROBABLY INEFFICIENT
	*When extending this application, consider reworking the id-management to fit large DBs
	*/
	private static void fetchUsedIds(){
		usedIds.clear();
		ResourceIterator<Integer> tempIterator = executeQuery("MATCH (n) RETURN DISTINCT n.OBJECT_ID").columnAs("OBJECT_ID");
		tempIterator.forEachRemaining(usedIds::add);
		tempIterator.close();
		Collections.sort(usedIds);
	}
	
	/**
	 * Concatenates ObjectId and EdgeId to a new, unique Long type Id. EdgeId is ZeroPadded (5-> 00005, 12->00012) 
	 * to ensure that the Id of edge 12 is greater than edge 2 (Unwanted: 1.12 < 1.2 , now: 1.00012 > 1.00002 ).
	 * This Limits a Figure to max 99999 edges. If more is needed, edit this function or rework the indexing
	 * 
	 * @param id1 ObjectId
	 * @param id2 EdgeId
	 * @return the new Double Id: ObjectId.EgdeId
	 */
	@SuppressWarnings("null")
	private static Long concatIds(Integer id1, Integer id2){
		String id2ZeroPadding = null;
		int zeroesToAdd = 5-id2.toString().length();
		for(int i = 0; i < zeroesToAdd; i++){
			id2ZeroPadding.concat("0");
		}
		id2ZeroPadding.concat(id2.toString());
		
		Long newId = Long.parseLong( id1.toString().concat(".").concat(id2ZeroPadding) );
		return newId;
	}
	
	//Public
	
	/**
	 * Instantiate a GraphDatabaseService to
	 * create a new database or open an existing one 
	 * 
	 * @param directory Target directory to initialize DB
	 */
	public static void initializeDB(){
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( directory );
		registerShutdownHook( graphDb );
		
		fetchUsedIds();
	}
	
	/**
	 * Method to realize Singleton-Pattern
	 * 
	 * @param directory  Target directory to initialize DB
	 * @return instance of DBController to access DB
	 */
	public static DBController getInstance (File directory) {
		    if (DBController.instance == null) {
		    	DBController.instance = new DBController (directory);
		    }
		    return DBController.instance;
		 }
	  

	/**
	 * Method that executes a Cypher Query Language query
	 * 
	 * @param cypher CQL code to execute
	 * @return query result as Neo4J {@code Result} type
	 */
	public static Result executeQuery(String cypher){
		Result result;
		 try ( Transaction tx = graphDb.beginTx() )
		 {
		     result= graphDb.execute(cypher);
		     
		     tx.success();
		 }
		return result;
	}

	/**
	 * Removes all nodes with given Object-ID from DB
	 * @param id Object ID to remove
	 */
	public static void removeByOBJ_ID(Integer id){
		String cypher = "MATCH (n) WHERE n.OBJECT_ID = ";
		cypher.concat(id.toString() + " DETACH DELETE n");
		
		executeQuery(cypher);
	}
	
	/**
	 * Writes an GeometricFigure object to the DB.
	 * Currently no check if same Obj already in Db only having a different ID.
	 * The ID consists of ObjectId.EdgeId. 
	 * This method uses the Neo4J BatchInserter:
	 * The batch inserter drops support for transactions and concurrency in favor of insertion speed. When done using the batch inserter shutdown() must be invoked and complete successfully for the Neo4j store to be in consistent state.
     * Only one thread at a time may work against the batch inserter, multiple threads performing concurrent access have to employ synchronization.
     * Transactions are not supported so if the JVM/machine crashes or you fail to invoke shutdown() before JVM exits the Neo4j store can be considered being in non consistent state and the insertion has to be re-done from scratch.
	 *
	 * @param figure An object defining the object containing edges and their relations, ids, description etc
	 */
	@SuppressWarnings("null")
	public static void writeObjToDB(GeometricFigure figure ) throws Exception {
		
		if (usedIds.contains(figure.getObjectID())) {
			throw new Exception("ID already used. Check the usedIds ArrayList of your DBController and use the GeoFigure ID setter to adjust");
			}
		
		ArrayList<RelationsDefinition> relationsArray = figure.getEdgeRelations();
        
		new BatchInserters();
		BatchInserter inserter = BatchInserters.inserter(directory);
		Long edgeId1, edgeId2;
		Edge<?> edge1, edge2;
		Map<String, Object> properties = null;
		
		try{
			//schema index not needed if this works as intended
			//inserter.createDeferredSchemaIndex(FigureLabels.EDGE).on("EDGE_ID").create();
			
			for(RelationsDefinition relation:relationsArray){
				//if NOT in DB
				//writeToDB( relation);
				edge1 = relation.getEdge1();
				edge2 = relation.getEdge2();
				
				edgeId1 = concatIds(figure.getObjectID(), edge1.getId()); 
				edgeId2 = concatIds(figure.getObjectID(), edge2.getId()); 
				
				if(!inserter.nodeExists(edgeId1)){
					properties.put("LENGTH", edge1.getLength());
					properties.put("URL", figure.getPictureURL());
					properties.put("DESCRIPTION", figure.getDescription());
					properties.put("OBJECT_ID", edgeId1.intValue());
					
					inserter.createNode(edgeId1, properties, FigureLabels.EDGE);
					
					properties.clear();
				}
				
				if(!inserter.nodeExists(edgeId2)){
					properties.put("LENGTH", edge2.getLength());
					properties.put("URL", figure.getPictureURL());
					properties.put("DESCRIPTION", figure.getDescription());
					properties.put("OBJECT_ID", edgeId2.intValue());
					
					inserter.createNode(edgeId2, properties, FigureLabels.EDGE);
					
					properties.clear();
					
				}
				
				properties.put("ANGLE", relation.getWinkel());
				
				inserter.createRelationship(edgeId1, edgeId2, FigureRelTypes.CONNECTED, properties);
				
				properties.clear();
				
			}
			usedIds.add(figure.getObjectID());
			
		} catch(Exception e){
			e.printStackTrace();
			inserter.shutdown();
		}
		inserter.shutdown();
	}
	
	/**
	 * Shuts down the DB. Make sure to call this once 
	 * you no longer plan on using transactions.
	 * 
	 */
 	public static void shutdownDB(){
		graphDb.shutdown();
		}
 	
 	
 	
 	
	//Getters
	/**
	 * Getter for directory
	 * @return DB-directory
	 */
	public File getDirectory() {
		return directory;
	}
	/**
	 * Getter for usedIds
	 * @return usedIds ArrayList containing IDs being currently used in the DB
	 */
	public ArrayList<Integer> getUsedIds() {
		return usedIds;
	}
	
}
