
package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
 * The DBController Class:
 * Singleton-class for DB-access. Singleton NOT threadsafe.
 * Make sure to invoke the shutdown method once your transactions are done.
 *  
 * @author Lennard Flegel 
 */
public class DBController {
	
	//Members
	private static DBController instance;
	private static File directory;
	private GraphDatabaseService graphDb;
	private ArrayList<Integer> usedIds = new ArrayList<Integer>();
	private enum FigureLabels implements Label {EDGE;};
	private enum FigureRelTypes implements RelationshipType{CONNECTED;};
	
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
	private void registerShutdownHook( final GraphDatabaseService graphDb )
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
	private void fetchUsedIds(){
		if(!(usedIds.isEmpty())){
			usedIds.clear();
		}
		Result result;
		try ( Transaction tx = graphDb.beginTx() ){
		     result= graphDb.execute("MATCH (n) RETURN DISTINCT n.OBJECT_ID");
			 ResourceIterator<Integer> tempIterator = result.columnAs("n.OBJECT_ID");
			 tempIterator.forEachRemaining(usedIds::add);
			 tempIterator.close();
		     
		     tx.success();
		 }

		Collections.sort(usedIds);
	}
	
	
	/**
	 * Concatenates ObjectId and EdgeId to a new, unique Long type Id. EdgeId is ZeroPadded (5-> 00005, 12->00012,
	 * so Object 1 edge 12 would be 100012, edge 5 would be 100005) for consistent indexes and the possibility to resepereate them.
	 * This Limits a Figure to max 99999 edges. If more is needed, edit this function or rework the indexing
	 * 
	 * @param id1 ObjectId
	 * @param id2 EdgeId
	 * @return the new Long Id: ObjectId.EgdeId
	 */
	private Long concatIds(Integer id1, Integer id2){
		String id2ZeroPadding ="";
		int zeroesToAdd = 5-id2.toString().length();
		for(int i = 0; i < zeroesToAdd; i++){
			id2ZeroPadding = id2ZeroPadding.concat("0");
		}
		id2ZeroPadding = id2ZeroPadding.concat(id2.toString());
		
		String temp = id1.toString();
		
		temp= temp.concat(id2ZeroPadding);
		
		Long newId = Long.valueOf( temp.trim());
		return newId;
	}
	
	
	/**
	 * Instantiate a GraphDatabaseService to
	 * create a new database or open an existing one 
	 * 
	 * @param directory Target directory to initialize DB
	 */
	private void initializeDB(){
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( directory );
		registerShutdownHook( graphDb );
		
		fetchUsedIds();
	}
	
	
	//Public
	
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
	public Result executeQuery(String cypher){
		Result result;
		try ( Transaction tx = graphDb.beginTx() ){
		     result= graphDb.execute(cypher);
		     
		     tx.success();
		 }
		return result;
	}

	
	/**
	 * Removes all nodes with given Object-ID from DB
	 * 
	 * @param id Object ID to remove
	 */
	public void removeByOBJ_ID(String id){
		if (usedIds.contains(Integer.parseInt(id)))
		{
			String cypher = "MATCH (n) WHERE n.OBJECT_ID = ";
			cypher=cypher.concat(id + " DETACH DELETE n");
			executeQuery(cypher);
			usedIds.remove(usedIds.indexOf(Integer.parseInt(id)));
		}
		else System.out.println(id + " nicht in der Datenbank vorhanden");
	}
	
	
	/**
	 * Removes all nodes and relations from DB
	 */
	public void clearAll(){
		String cypher = "MATCH (n) DETACH DELETE n";
		
		executeQuery(cypher);
		usedIds.clear();
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
	public void writeObjToDB(GeometricFigure figure ) throws Exception {
		
		if (usedIds.contains(figure.getObjectID())) {
			throw new Exception("ID already used. Check the usedIds ArrayList of your DBController and use the GeoFigure ID setter to adjust");
		}  
		
		ArrayList<RelationsDefinition> relationsArray = figure.getEdgeRelations();
        
		//The batch Inserter replaces the standard graphDBservice for writing access, thus the graphDBservice is shut down here
		graphDb.shutdown();
		
		//Initializing the BatchInserter
		new BatchInserters();
		BatchInserter inserter = BatchInserters.inserter(directory);
		Long edgeId1, edgeId2;
		Edge<?> edge1, edge2;
		Map<String, Object> properties = new HashMap<String, Object> ();
		
		try{
			
			for(RelationsDefinition relation:relationsArray){
				//if NOT in DB
				//writeToDB( relation);
				edge1 = relation.getEdge1();
				edge2 = relation.getEdge2();
				
				edgeId1 = concatIds(figure.getObjectID(), edge1.getId()); 
				edgeId2 = concatIds(figure.getObjectID(), edge2.getId()); 
				
				if(!inserter.nodeExists(edgeId1)){
					properties.put("LENGTH", ((double)Math.round(edge1.getLength() * 100d) / 100d));
					properties.put("URL", figure.getPictureURL());
					properties.put("DESCRIPTION", figure.getDescription());
					properties.put("OBJECT_ID", figure.getObjectID());
					
					inserter.createNode(edgeId1, properties, FigureLabels.EDGE);
					
					properties.clear();
				}
				
				if(!inserter.nodeExists(edgeId2)){
					properties.put("LENGTH",  ((double)Math.round(edge2.getLength() * 100d) / 100d));
					properties.put("URL", figure.getPictureURL());
					properties.put("DESCRIPTION", figure.getDescription());
					properties.put("OBJECT_ID", figure.getObjectID());
					
					inserter.createNode(edgeId2, properties, FigureLabels.EDGE);
					
					properties.clear();
					
				}
				
				properties.put("ANGLE", ((double)Math.round(relation.getAngle() * 100d) / 100d));
				
				inserter.createRelationship(edgeId1, edgeId2, FigureRelTypes.CONNECTED, properties);
				
				properties.clear();
				
			}
			usedIds.add(figure.getObjectID());
			
		} catch(Exception e){
			e.printStackTrace();
			inserter.shutdown();
		}
		inserter.shutdown();
		
		//Now that the BatchInserter is shut down, set up the regular graphDBservice again
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( directory );
		registerShutdownHook( graphDb );
	}
	
	
	/**
	 * Shuts down the DB. Make sure to call this once 
	 * you no longer plan on using transactions.
	 * 
	 */
 	public void shutdownDB(){
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

	/**
	 * Prints the amount of currently stored 3d-objects to system.out
	 */
	public void printOBJAmount(){
		int objsInDb = usedIds.size();
	    
		System.out.println(objsInDb + " Objects currrently stored in DB ");
	}
}

