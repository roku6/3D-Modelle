/** 
 * OBJ.java
 * (c) Copyright 04-2016 Robert K�lpmann
 *  Parserklasse zum einlesen und speichern von OBJ-Files
 *  Dependencies:
 *  	Vertex - Klasse (generic.classes.gfx.Vertex 
 *  	Vector4 - Klasse (generic.math.Vector4)
 *  	
 *  	java.util
 *  	java.io
 *  
 *  @author Robert K�lpmann
 *  @version 1.0
 *  
 *  ANMERKUNG: �berarbeitete Version f�r Studienprojekt
 *  TODO: Unn�tze Funktionen entfernen
 */

package obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class OBJ
{
	private String fileName;
	
	private int faceIndicesNr;					//Amount of indices of a face (2-4)
	private int pointsNr;						//Amount of points
	private int facesNr; 						//Amount of faces
	private ArrayList<PointExt<Double>> pointList;	//Array of all points
	private ArrayList<Face> faceList;			//Array of all faces
	
	private ArrayList<Vertex<Double>> vertexList;	//Format x/y/z
	private ArrayList<Texture<Double>> textureList;	//Format x/y
	private ArrayList<Normal<Double>> normalList;	//Format x/y/z	
	
	public void setFileName(String fileName)					{this.fileName = fileName;}
	public void setFaceIndicesNr(int faceIndicesNr)				{this.faceIndicesNr = faceIndicesNr;}
	public void setPointsNr(int pointsNr)						{this.pointsNr = pointsNr;}
	public void setFacesNr(int facesNr)							{this.facesNr = facesNr;}
	public void setPointList(ArrayList<PointExt<Double>> pointList){this.pointList = pointList;}
	public void setFaceList(ArrayList<Face> faceList)			{this.faceList = faceList;}		
	
	public void setVertexList(ArrayList<Vertex<Double>> vertexList) 	{this.vertexList = vertexList;}
	public void setTextureList(ArrayList<Texture<Double>> textureList) 	{this.textureList = textureList;}
	public void setNormalList(ArrayList<Normal<Double>> normalList) 	{this.normalList = normalList;}
	
	public String getFileName()						{return fileName;}
	public int getFaceIndicesNr()					{return faceIndicesNr;}
	public int getPointsNr()						{return pointsNr;}
	public int getFacesNr()							{return facesNr;}
	public ArrayList<PointExt<Double>> getPointList()	{return pointList;}
	public ArrayList<Face> getFaceList()			{return faceList;}		
	
	public ArrayList<Vertex<Double>> getVertexList() 	{return vertexList;}
	public ArrayList<Texture<Double>> getTextureList() 	{return textureList;}
	public ArrayList<Normal<Double>> getNormalList() 	{return normalList;}
	
	public Vertex<Double> getVertex(int id)
	{
		for (Vertex<Double> component : vertexList) {if (component.getId() == id) return vertexList.get(id);}
		return null;		
	}
	public Texture<Double> getTexture(int id)
	{
		for (Texture<Double> component : textureList) {if (component.getId() == id) return textureList.get(id);}
		return null;		
	}
	public Normal<Double> getNormal(int id)
	{
		for (Normal<Double> component : normalList) {if (component.getId() == id) return normalList.get(id);}
		return null;		
	}	
	
	/**
	 * Constructor
	 */
	public OBJ()
	{
		pointList = new ArrayList<>();
		faceList = new ArrayList<>();
		vertexList = new ArrayList<>();
		textureList = new ArrayList<>();	
		Texture.setNumber(-1);
		textureList.add(new Texture<Double>(0.0,0.0,0.0,0.0));
		normalList = new ArrayList<>();	
		Vertex.setNumber(0);
		Texture.setNumber(0);
		Normal.setNumber(0);
		Face.setNumber(0);
		PointExt.setNumber(0);
		
	};
	/**
	 * Constructor
	 * @param pointsNr
	 * @param facesNr
	 * @param pointList
	 * @param faceList
	 **/
	public OBJ(int pointsNr, int facesNr, ArrayList<PointExt<Double>> pointList, ArrayList<Face> faceList)
	{
		setFaceIndicesNr(faceList.get(0).getPointExtList().size());
		setPointsNr(pointsNr);
		setFacesNr(facesNr);
		setPointList(pointList);
		setFaceList(faceList);
	}
	
	/**
	 * Methode to create a Point from Vertex/Texture/Normal (by ID)
	 * 
	 * @param vertexId
	 * @param textureId
	 * @param normalId
	 * @return aPoint
	 */
	private PointExt<Double> createPoint(int vertexId, int textureId, int normalId)
	{
		PointExt<Double> aPoint = new PointExt<>();
		for(Vertex<Double> aVertex : vertexList)
		{
			if (aVertex.getId() == vertexId) {aPoint.setAVertex(aVertex); break;}
		}
		for(Texture<Double> aTexture : textureList)
		{
			if (aTexture.getId() == textureId) {aPoint.setATexture(aTexture); break;}			
		}
		for(Normal<Double> aNormal : normalList)
		{
			if (aNormal.getId() == normalId) {aPoint.setANormal(aNormal); break;}
		}
		return aPoint;
	}
	
	/**
	 * Converts negative Zeros to positive Zeros
	 * 
	 * @param splitted
	 * @param size
	 * @return
	 */
	private String[] convertNegativeZeros(String splitted[], int size)
	{
		for (int i = 1; i<size+1;i++)
		{
			if (Double.valueOf(splitted[i]) == -0.0) 
			{
				splitted[i] = "0.0";
			}
		}
		return splitted;
	}
	
	/** 
	 * Method for loading an Obj-File
	 * @param fileName as a String
	 **/
	public boolean load(String fileName)
	{
		setFileName(fileName);
		String splitted[] = null;
		String splittedV = null, splittedT = null, splittedN = null;
				
		File file = new File(fileName);
		
		int faceIndicesNrLocal = 0;
		
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null)
			{
				splitted = s.split(" +");
				//Reads Coordinates of a vertex (3D)
				if (splitted[0].equals("v")) 
				{
					if(splitted.length != 4) 
					{
						br.close();
						fr.close();
						throw new IOException();
					}
					splitted = convertNegativeZeros(splitted, 3);
					vertexList.add(new Vertex<>(	Double.valueOf(splitted[1]), 
													Double.valueOf(splitted[2]), 
													Double.valueOf(splitted[3]),
													0.0));				
				}
				//Reads Coordinates of a texture (2D)
				else if (splitted[0].equals("vt")) 
				{
					if(splitted.length != 3) 
					{
						br.close();
						fr.close();
						throw new IOException();
					}
					splitted = convertNegativeZeros(splitted, 2);
					textureList.add(new Texture<>(	Double.valueOf(splitted[1]),
													Double.valueOf(splitted[2]),
													0.0, 0.0));
				}
				//Reads Coordinates of a normalvector (3D)
				else if (splitted[0].equals("vn")) 
				{
					if(splitted.length != 4) 
					{
						br.close();
						fr.close();
						throw new IOException();
					}
					splitted = convertNegativeZeros(splitted, 3);
					normalList.add(new Normal<>( 	Double.valueOf(splitted[1]),
													Double.valueOf(splitted[2]),
													Double.valueOf(splitted[3]), 
													0.0));
				}
				//Reads indices of a face
				else if (splitted[0].equals("f")) 
				{
					faceIndicesNrLocal = splitted.length - 1; //amount of indices in a row (2 - 4) FaceRepresentation
					ArrayList<PointExt<Double>> aPointExtList = new ArrayList<>();
					for(int i = 0; i<faceIndicesNrLocal; i++)
					{
						String splitted2[] = splitted[i+1].split("/");
						//Checks if there are 3 values
						if (splitted2.length == 3)
						{
							splittedV = splitted2[0];
							splittedT = splitted2[1];
							splittedN = splitted2[2];
							if (splittedT.equals("")) splittedT = "0";
							PointExt<Double> aPoint = createPoint(	Integer.valueOf(splittedV),
																	Integer.valueOf(splittedT),
																	Integer.valueOf(splittedN));
							pointList.add(aPoint);
							aPointExtList.add(aPoint);
						}
						else 
						{
							br.close();
							fr.close();
							throw new IOException();
						}
					}
					Face aFace = new Face(aPointExtList);
					faceList.add(aFace);
				}
			}
						
			br.close();
			fr.close();
		}
		catch (IOException e)
		{
			System.out.println();
			System.out.println("Fehlerhafte OBJ Datei! " + e);
			return false;
		}
		return true;
	}
	
	/**
		Method to save all vertecies in a file
		@param filename as a String
	**/
	public void saveTo(String filename)
	{
		File file = new File(filename);
		
		try
		{
			FileWriter fw = new FileWriter(file);
			fw.write("# commentar\r\n");			
			for (Vertex<Double> aVertex : vertexList)
			{
				fw.write("v");
				fw.write(" " + aVertex.getX());
				fw.write(" " + aVertex.getY());
				fw.write(" " + aVertex.getZ());
				fw.write("\r\n");
			}
			
			for (Texture<Double> aTexture : textureList)
			{
				fw.write("vt");
				fw.write(" " + aTexture.getX());
				fw.write(" " + aTexture.getY());				
				fw.write("\r\n");
			}
			for (Normal<Double> aNormal : normalList)
			{
				fw.write("vn");
				fw.write(" " + aNormal.getX()); 
				fw.write(" " + aNormal.getY()); 
				fw.write(" " + aNormal.getZ()); 
				fw.write("\r\n");
			}
			fw.close();
		}
		catch(IOException e)
		{
			
		}
	}	
	
	/**
	 * Method to add faces to an OBJ File
	 * 
	 * @param fileName as a String, an 1D Array of indices, Size of the format
	 **/
	public void writeFacesToObj(String fileName, int[] indices, int size)
	{
		File file = new File(fileName);
		int actIndex = 1;
		
		try
		{
			FileWriter fw = new FileWriter(file, true);
			for (int i = 0; i<(indices.length/size);i++)
			{
				fw.append("f");
				for(int j = 0; j<size; j++)
				{
					fw.append(" " + (int)(indices[i*size + j] + actIndex) + "//" + (int)(indices[i*size+j] + actIndex)); 
				}
				fw.append("\r\n");
			}
			actIndex = actIndex + indices.length;
			fw.close();
		}
		catch(IOException e)
		{
		}
	}
	
	/**
	 * Overrides toString Method
	 */
	@Override
	public String toString()
	{
		String aString = "File: " + fileName + "\n";
		aString += "Vertecies: \n";
		for (Vertex<Double> aVertex : vertexList)		{aString += "\t" + aVertex.toString() + "\n";}
		
		aString += "Textures: \n";
		for (Texture<Double> aTexture : textureList)	{aString += "\t" + aTexture.toString() + "\n";}
		
		aString += "Normales: \n";
		for (Normal<Double> aNormal : normalList)		{aString += "\t" + aNormal.toString() + "\n";}
		
		aString += "Faces: \n";
		for (Face aFace : faceList) 					{aString += "\t"  + aFace.toString() + "\n";}
		
		return aString;
	}
}