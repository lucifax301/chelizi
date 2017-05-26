package com.lili.location.dao.Po;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "coachloc")
public class CoachPo 
{
    /**
     * 
     */
    @Id
    private long coachId;
    @GeoSpatialIndexed
    private Double[] loc=new Double[2];
    //教练车方向
    private float dir;
    private double distance; 
    
    private long lastUploadTime;
    
    

    public long getLastUploadTime() {
		return lastUploadTime;
	}

	public void setLastUploadTime(long lastUploadTime) {
		this.lastUploadTime = lastUploadTime;
	}

	public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

    public Double[] getLoc()
    {
        return loc;
    }

    public void setLoc(Double[] loc)
    {
        this.loc = loc;
    }
    

    public float getDir() {
		return dir;
	}

	public void setDir(float dir) {
		this.dir = dir;
	}
	
	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String toString()
    {
        return "{coachId=" + coachId + ",lge=" +loc[0]+ ",lae="+loc[1] + ",dir="+dir+",distance="+distance+"}";
    }
}
