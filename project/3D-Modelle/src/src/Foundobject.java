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
public class Foundobject {
    
    
    private Integer id;
    private Double lenSim;
    private Double angSim;
    private String picture;
    private String description;
	
    Foundobject(Integer id, Double lenSim, Double angSim, String picture, String description )
    {
    	this.id = id;
    	this.lenSim = lenSim;
    	this.angSim = angSim;
        this.picture = picture;
        this.description = description;
    }
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Foundobject [id=" + id + ", lenSim=" + lenSim + ", angSim=" + angSim + ", picture=" + picture
				+ ", description=" + description + "]";
	}
    
    public void setId(Integer id) {
        this.id = id;  
    }
    
    public void setLenSim(Double lenSim) {
        this.lenSim = lenSim;
    }
    
    public void setAngSim(Double angSim) {
        this.angSim = angSim; 
    }
    
    public void setPic(String picture) {
        this.picture = picture;  
    }
    
    public void setDesc(String description) {
    	this.description = description;
    }
    
    public Integer getId() {
        return id;
    }
    
    public Double getLenSim() {
        return lenSim;
    }
    
    public Double getAngSim() {
        return angSim;
    }
    
    public String getPic() {
        return picture;
    }
    
    public String getDescr() {
        return description;
    }
    
    
    
}
        
        
    
    
    

