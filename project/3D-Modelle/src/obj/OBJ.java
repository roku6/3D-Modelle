/** 
 * OBJ.java
 * (c) Copyright 04-2016 Robert Külpmann
 *  Parserklasse zum einlesen und speichern von OBJ-Files
 *  Dependencies:
 *  	Vertex - Klasse (generic.classes.gfx.Vertex 
 *  	Vector4 - Klasse (generic.math.Vector4)
 *  	
 *  	java.util
 *  	java.io
 *  
 *  @author Robert Külpmann
 *  @version 1.0
 *  
 *  ANMERKUNG: Überarbeitete Version für Studienprojekt
 *  TODO: Unnütze Funktionen entfernen
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
	private ArrayList<Point<Double>> pointList;	//Array of all points
	private ArrayList<Face> faceList;	//Array of all faces
	
	private ArrayList<Vertex<Double>> vertexList = new ArrayList<>();	//Format x/y/z
	private ArrayList<Texture<Double>> textureList = new ArrayList<>();	//Format x/y
	private ArrayList<Normal<Double>> normalList = new ArrayList<>();	//Format x/y/z	
	
	public void setFileName(String fileName)					{this.fileName = fileName;}
	public void setFaceIndicesNr(int faceIndicesNr)				{this.faceIndicesNr = faceIndicesNr;}
	public void setPointsNr(int pointsNr)						{this.pointsNr = pointsNr;}
	public void setFacesNr(int facesNr)							{this.facesNr = facesNr;}
	public void setPointList(ArrayList<Point<Double>> pointList){this.pointList = pointList;}
	public void setFaceList(ArrayList<Face> faceList)			{this.faceList = faceList;}		
	
	public void setVertexList(ArrayList<Vertex<Double>> vertexList) 	{this.vertexList = vertexList;}
	public void setTextureList(ArrayList<Texture<Double>> textureList) 	{this.textureList = textureList;}
	public void setNormalList(ArrayList<Normal<Double>> normalList) 	{this.normalList = normalList;}
	
	public String getFileName()						{return fileName;}
	public int getFaceIndicesNr()					{return faceIndicesNr;}
	public int getPointsNr()						{return pointsNr;}
	public int getFacesNr()							{return facesNr;}
	public ArrayList<Point<Double>> getPointList()	{return pointList;}
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
	
	public OBJ()
	{
		pointList = new ArrayList<>();
		faceList = new ArrayList<>();
	};
	/**
	 * Constructor
	 * @param int pointsNr
	 * @param int facesNr
	 * @param ArrayList<Point<Double>> pointList
	 * @param ArrayList<Face> faceList
	 **/
	public OBJ(int pointsNr, int facesNr, ArrayList<Point<Double>> pointList, ArrayList<Face> faceList)
	{
		setFaceIndicesNr(faceList.get(0).getPointList().size());
		setPointsNr(pointsNr);
		setFacesNr(facesNr);
		setPointList(pointList);
		setFaceList(faceList);
	}
	
	/**
	 * Method to convert 2D IndexArray in a 1D IndexArray
	 * @return 1D Array with int indices
	 **/
	/*
	public int[] convertTo1D()
	{
		int aFace[] = new int[facesNr * faceIndicesNr];
		for (int i=0; i<facesNr; i++)
		{
			aFace[i*faceIndicesNr] = faceArray[i][0]; 
			aFace[i*faceIndicesNr+1] = faceArray[i][1];
			if (faceIndicesNr >= 3) aFace[i*faceIndicesNr+2] = faceArray[i][2];
			if (faceIndicesNr >= 4) aFace[i*faceIndicesNr + 3] = faceArray[i][3];
		}
		return aFace;
	}
	*/
	private Point<Double> createPoint(int vertexId, int textureId, int normalId)
	{
		Point<Double> aPoint = new Point<>();
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
	
	/** Method for loading an Obj-File
	 * @param Filename as a String
	 **/
	public void load(String fileName)
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
				if (splitted[0].equals("v")) vertexList.add(new Vertex<>(	Double.valueOf(splitted[1]), 
																			Double.valueOf(splitted[2]), 
																			Double.valueOf(splitted[3]),
																			0.0));				
				//Reads Coordinates of a texture (2D)
				else if (splitted[0].equals("vt")) 
				{
				textureList.add(new Texture<>(	Double.valueOf(splitted[1]),
												Double.valueOf(splitted[2]),
												0.0, 0.0));
				}
				//Reads Coordinates of a normalvector (3D)
				else if (splitted[0].equals("vn")) 
				{
				normalList.add(new Normal<>( 	Double.valueOf(splitted[1]),
												Double.valueOf(splitted[2]),
												Double.valueOf(splitted[3]), 
												0.0));
				}
				//Reads indices of a face
				else if (splitted[0].equals("f")) 
				{
					faceIndicesNrLocal = splitted.length - 1; //amount of indices in a row (2 - 4) FaceRepresentation
					ArrayList<Point<Double>> aPointList = new ArrayList<>();
					for(int i = 0; i<faceIndicesNrLocal; i++)
					{
						String splitted2[] = splitted[i+1].split("/");
						splittedV = splitted2[0];
						splittedT = splitted2[1];
						splittedN = splitted2[2];
						Point<Double> aPoint = createPoint(	Integer.valueOf(splittedV),
															Integer.valueOf(splittedT),
															Integer.valueOf(splittedN));
						pointList.add(aPoint);
						aPointList.add(aPoint);
					}
					Face aFace = new Face(aPointList);
					faceList.add(aFace);
				}
			}
						
			br.close();
			fr.close();
		}
		catch (IOException e)
		{
			
		}
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
	   @param filename as a String, an 1D Array of indices, Size of the format
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