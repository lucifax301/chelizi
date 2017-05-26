package com.lili.school.service;

import com.lili.cms.entity.ResponseMessage;

public interface ICmsPosterService {

    public String query(Integer type, Integer isDel);
    
    public ResponseMessage addPoster(Integer type,String imgName, String posterPic);
    
    public ResponseMessage updatePoster(Integer type, Integer isDel, String imgName, String posterPic);

	public String getUptoken();
    
}
