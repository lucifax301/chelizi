/**
 * 
 */
package com.lili.event.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author linbo
 *
 */
public class CoachCarInOutVo implements Serializable
{
    private static final long serialVersionUID = -7640565738957727201L;
    
    private long coachId;
    
    private int carId;
    
    private Date time;
    
    private String status;

    
    
    public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
     * @return the coachId
     */
    public long getCoachId()
    {
        return coachId;
    }

    /**
     * @param coachId the coachId to set
     */
    public void setCoachId(long coachId)
    {
        this.coachId = coachId;
    }

    /**
     * @return the carId
     */
    public int getCarId()
    {
        return carId;
    }

    /**
     * @param carId the carId to set
     */
    public void setCarId(int carId)
    {
        this.carId = carId;
    }

    /**
     * @return the time
     */
    public Date getTime()
    {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time)
    {
        this.time = time;
    }
    
    
}
