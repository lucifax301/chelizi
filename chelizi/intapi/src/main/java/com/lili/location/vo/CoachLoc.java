package com.lili.location.vo;

import java.io.Serializable;

public class CoachLoc implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 3089328103649478029L;
    private long coachId;
    // 经度
    private double lge;
    // 纬度
    private double lae;
    private String wstate;
    private float dir;
    
    public long getCoachId() {
		return coachId;
	}

	public void setCoachId(long coachId) {
		this.coachId = coachId;
	}

	public double getLge()
    {
        return lge;
    }

    public void setLge(double lge)
    {
        this.lge = lge;
    }

    public double getLae()
    {
        return lae;
    }

    public void setLae(double lae)
    {
        this.lae = lae;
    }

    public String getWstate()
    {
        return wstate;
    }

    public void setWstate(String wstate)
    {
        this.wstate = wstate;
    }
    
    public String toString()
    {
        return "CouchVo: {caochId=" + coachId + ",lge=" +lge+ ",lae="+lae+",wstate=" + wstate + "}";
    }

    /**
     * @return the dir
     */
    public float getDir()
    {
        return dir;
    }

    /**
     * @param dir the dir to set
     */
    public void setDir(float dir)
    {
        this.dir = dir;
    }
}
