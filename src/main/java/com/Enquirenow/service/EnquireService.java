package com.Enquirenow.service;

import java.util.List;
import com.Enquirenow.entity.EnquireEntity;

public interface EnquireService {

	public EnquireEntity saveEnquiry(EnquireEntity enquiry);

	public List<EnquireEntity> getAllEnquiries();
	
	public Long getInquiryCount();

	public List<EnquireEntity> getEnquiriesByEmail(String email);
}
