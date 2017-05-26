package com.lili.file.vo;


import java.io.Serializable;

public class TitleTypeVo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 2166453167343755514L;

	/**
	 * 
	 */

	private Integer id;

    private Integer channel;

    private String name;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

}