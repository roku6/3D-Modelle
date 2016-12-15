/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;


/**
 *
 * @author Ekaterina Kuzminykh
 */
public class Searchobject {
    
    
    private Integer id1;
    private Double length;
    private Double angle;
    private Integer id2;
	
    Searchobject(Integer id1, Double length, Double angle, Integer id2)
    {
	this.id1 = id1;
	this.length = length;
	this.angle = angle;
        this.id2 = id2;
    }
    
    public void setId1(Integer id1) {
        this.id1 = id1;  
    }
    
    public void setLength(Double length) {
        this.length = length;
    }
    
    public void setAngle(Double angle) {
        this.angle = angle; 
    }
    
    public void setId2(Integer id2) {
        this.id2 = id2;  
    }
    
    public Integer getId1() {
        return id1;
    }
    
    public Double getLength() {
        return length;
    }
    
    public Double getAngle() {
        return angle;
    }
    
    public Integer getId2() {
        return id2;
    }
    
    
    
}
        
        
    
    
    

