
package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * @author Lennard Flegel
 *
 * Singleton-class for DB-access. Singleton NOT threadsafe.
 *
 */
public class DBController {
	
	//Members
	private static DBController instance;
	private static File directory;
	private static GraphDatabaseService graphDb;
	private static ArrayList<Integer> usedIds;
	
	//Constructors
	/**
	 * Constructor. Creates an DBController, sets up a Neo4J DB at given directory and initializes it. 
	 * 
	 * @param directory Target directory to initialize DB
	 */
	private DBController(File directory) {
		this.directory = directory;
		initializeDB();
	}
	
	//Methods
	
	//Private
	
	/**
	 * Instantiate a GraphDatabaseService to
	 * create a new database or open an existing one 
	 * 
	 * @param directory Target directory to initialize DB
	 */
	private static void initializeDB(){
		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( directory );
		registerShutdownHook( graphDb );
		
		fetchUsedIds();
		
	}
	
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
	 * Private Method, meant to be called by writeObjToDB to write a Node-Rel-Node 3-tupel to the DB
	 * @param EdgeRel Object containing information about 2 edges and their relation (angle)
	 */
	private static void writeToDB(RelationsDefinition EdgeRel){
		//TODO
	}
	
	/**
	*Fills the usedIds ArrayList with all distinct Object-Ids used in the DB and sorts the list.
	*PROBABLY INEFFICIENT
	*When extending this application, consider reworking the id-management to fit large DBs
	*/
	private static void fetchUsedIds(){
		ResourceIterator<Integer> tempIterator = executeQuery("MATCH (n) RETURN DISTINCT n.OBJECT_ID").columnAs("OBJECT_ID");
		tempIterator.forEachRemaining(usedIds::add);
		tempIterator.close();
		Collections.sort(usedIds);
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
	 * TODO
	 * Currently no check if same Obj already in Db only having a different ID
	 * 
	 * @param figure An object defining the object containing edges and their relations, ids, description etc
	 */
	public static void writeObjToDB(GeometricFigure figure ) throws Exception {
		
		if (usedIds.contains(figure.getObjectID())) {
			throw new Exception("ID already used. Check the usedIds ArrayList of your DBController and use the GeoFigure ID setter to adjust");
			}
		ArrayList<RelationsDefinition> relationsArray = figure.getEdgeRelations();
		ArrayList<Integer> writtenEdges;
		//TODO
		for(RelationsDefinition relation:relationsArray){
			//if NOT in DB (check objID AND edgeID in usedIds and writtenegdes)
			writeToDB( relation);
			}
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
