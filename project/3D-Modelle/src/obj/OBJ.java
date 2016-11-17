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
 *  ANMERKUNG: Überarbeitete Version für Studienprojekt -> Muss getestet werden!
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
	private int faceIndicesNr;		//Amount of indices of a face (2-4)
	private int pointsNr;			//Amount of points
	private int facesNr; 			//Amount of faces
	private Point pointArray[];		//Array of all points
	private int faceArray[][];		//Array of all faces
	private ArrayList<Vector4<Float>> vertexList = new ArrayList<>();	//Format x/y/z
	private ArrayList<Vector4<Float>> textureList = new ArrayList<>();	//Format x/y
	private ArrayList<Vector4<Float>> normalList = new ArrayList<>();	//Format x/y/z
	private ArrayList<Vector4<Float>> faceList1 = new ArrayList<>();	//Format v v v
	private ArrayList<Vector4<Float>> faceList2 = new ArrayList<>();	//Format v/vt v/vt v/vt
	private ArrayList<Vector4<Float>> faceList3 = new ArrayList<>();	//Format v/vt/vn v/vt/vn v/vt/vn
	private ArrayList<Vector4<Float>> faceList4 = new ArrayList<>();	//Nicht benutzt! Nur der Vollständigkeit halber (Vektor4<>)
	
	public void setFaceIndicesNr(int faceIndicesNr)		{this.faceIndicesNr = faceIndicesNr;}
	public void setPointsNr(int pointsNr)				{this.pointsNr = pointsNr;}
	public void setFacesNr(int facesNr)					{this.facesNr = facesNr;}
	public void setPointArray(Point pointArray[])		{this.pointArray = pointArray;}
	public void setFaceArray(int faceArray[][])			{this.faceArray = faceArray;}	
	
	public void setVertexList(ArrayList<Vector4<Float>> vertexList) 	{this.vertexList = vertexList;}
	public void setTextureList(ArrayList<Vector4<Float>> textureList) 	{this.textureList = textureList;}
	public void setNormalList(ArrayList<Vector4<Float>> normalList) 	{this.normalList = normalList;}
	
	public int getFaceIndicesNr()		{return faceIndicesNr;}
	public int getPointsNr()			{return pointsNr;}
	public int getFacesNr()				{return facesNr;}
	public Point getPoint(int i)		{return pointArray[i];}
	public Point[] getPointArray() 		{return pointArray;}
	public int[] getFaceArray()			{return convertTo1D();}	
	
	public ArrayList<Vector4<Float>> getVertexList() 	{return vertexList;}
	public ArrayList<Vector4<Float>> getTextureList() 	{return textureList;}
	public ArrayList<Vector4<Float>> getNormalList() 	{return normalList;}
	
	public OBJ(){};
	public OBJ(int pointsNr, int facesNr, Point[] pointArray, int[][] faceArray)
	{
		setFaceIndicesNr(faceArray[0].length);
		setPointsNr(pointsNr);
		setFacesNr(facesNr);
		setPointArray(pointArray);
		setFaceArray(faceArray);
	}
	
	/**
	 * Method to convert 2D IndexArray in a 1D IndexArray
	 * @return 1D Array with int indices
	 **/
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
	
	
	/** Method for loading an Obj-File
	 * @param Filename as a String
	 **/
	public void load(String filename)
	{
		String splitted[] = null;
		String splitted3[] = null;
				
		File file = new File(filename);
		
		int faceIndicesNrLocal = 0;
		
		try
		{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String s;
			while((s = br.readLine()) != null)
			{
				splitted = s.split(" ");
				//Reads Coordinates of a vertex (3D)
				if (splitted[0].equals("v")) vertexList.add(new Vector4<>(	Float.valueOf(splitted[1]), 
																			Float.valueOf(splitted[2]), 
																			Float.valueOf(splitted[3]),
																			0.0f));				
				//Reads Coordinates of a texture (2D)
				else if (splitted[0].equals("vt")) 
				{
				textureList.add(new Vector4<>(  Float.valueOf(splitted[1]),
												Float.valueOf(splitted[2]),
												0.f, 0.f));
				}
				//Reads Coordinates of a normalvector (3D)
				else if (splitted[0].equals("vn")) 
				{
				normalList.add(new Vector4<>(  	Float.valueOf(splitted[1]),
												Float.valueOf(splitted[2]),
												Float.valueOf(splitted[3]), 
												0.0f));
				}
				
				//Reads indices of a face
				else if (splitted[0].equals("f")) 
				{
					faceIndicesNrLocal = splitted.length - 1; //amount of indices in a row (2 - 4) FaceRepresentation
					splitted3 = new String[faceIndicesNrLocal];
					for(int i = 0; i<faceIndicesNrLocal; i++)
					{
						String splitted2[] = splitted[i+1].split("/");
						splitted3[i] = splitted2[0];
					}
					if (faceIndicesNrLocal == 1)
					{
						faceList1.add(new Vector4<>(	Float.valueOf(splitted3[0]),
														0.0f, 0.0f, 0.0f));
					}
					else if (faceIndicesNrLocal == 2)
					{
						faceList2.add(new Vector4<>(	Float.valueOf(splitted3[0]),
														Float.valueOf(splitted3[1]),
														0.0f, 0.0f));
					}
					else if (faceIndicesNrLocal == 3)
					{
						faceList3.add(new Vector4<>(	Float.valueOf(splitted3[0]),
														Float.valueOf(splitted3[1]),
														Float.valueOf(splitted3[2]),
														0.0f));
					}
					else if (faceIndicesNrLocal == 4)
					{
						faceList4.add(new Vector4<Float>(	Float.valueOf(splitted3[0]),
															Float.valueOf(splitted3[1]),
															Float.valueOf(splitted3[2]),
															Float.valueOf(splitted3[3])));						
					}
				}
			}
			
			
			// unify the formats using the form v/vt/vn
			// saves the vertices in a List
			Point pointArray[] = new Point[vertexList.size()];

			for (int j=0; j<vertexList.size(); j++)
			{
				if(textureList.isEmpty() && normalList.isEmpty())				
				{
					//Welchen NormalenStandard?
					pointArray[j] = new Point(vertexList.get(j), new Vector4<>(0.f, 1.f, 0.f, 0.f), new Vector4<>(0.f, 1.f,0.f,0.f));
				}
				else if  (textureList.isEmpty())
				{
					pointArray[j] = new Point(vertexList.get(j), new Vector4<>(0.f, 1.f, 0.f, 0.f));
				}
				else pointArray[j] = new Point(vertexList.get(j), textureList.get(j), normalList.get(j));
			}
			
			//saves the faces in a 2 dimensional list
			int indexArray[][];
			if (faceIndicesNrLocal == 1) 
			{
				indexArray = new int[faceList1.size()][1];
			
				for (int j=0; j<faceList1.size(); j++)
				{
					indexArray[j][0] = faceList1.get(j).getX().intValue() - 1;
				}
			}
			if (faceIndicesNrLocal == 2) 
			{
				indexArray = new int[faceList2.size()][2];
			
				for (int j=0; j<faceList2.size(); j++)
				{
					indexArray[j][0] = faceList2.get(j).getX().intValue() - 1;
					indexArray[j][1] = faceList2.get(j).getY().intValue() - 1;
				}
			}
			else if (faceIndicesNrLocal == 3) 
			{
				indexArray = new int[faceList3.size()][3];
			
				for (int j=0; j<faceList3.size(); j++)
				{
					indexArray[j][0] = faceList3.get(j).getX().intValue() - 1;
					indexArray[j][1] = faceList3.get(j).getY().intValue() - 1;
					indexArray[j][2] = faceList3.get(j).getZ().intValue() - 1;
				}
			}
			else if (faceIndicesNrLocal == 4) 
			{
				indexArray = new int[faceList4.size()][4];
				for (int j=0; j<faceList4.size(); j++)
				{
					indexArray[j][0] = faceList4.get(j).getX().intValue() - 1;
					indexArray[j][1] = faceList4.get(j).getY().intValue() - 1;
					indexArray[j][2] = faceList4.get(j).getZ().intValue() - 1;
					indexArray[j][3] = faceList4.get(j).getW().intValue() - 1;
				}
			}
			else indexArray = null;
			
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
			for (int j = 0; j<this.pointsNr; j++)
			{
				fw.write("v");
				fw.write(" " + this.pointArray[j].vertex.x);
				fw.write(" " + this.pointArray[j].vertex.y);
				fw.write(" " + this.pointArray[j].vertex.z);
				fw.write("\r\n");
			}
			
			for (int j = 0; j<this.pointsNr; j++)
			{
				fw.write("vt");
				fw.write(" " + this.pointArray[j].tex.x);
				fw.write(" " + this.pointArray[j].tex.y);				
				fw.write("\r\n");
			}
			for (int j = 0; j<this.pointsNr; j++)
			{
				fw.write("vn");
				fw.write(" " + this.pointArray[j].norm.x); 
				fw.write(" " + this.pointArray[j].norm.y); 
				fw.write(" " + this.pointArray[j].norm.z); 
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
	
}