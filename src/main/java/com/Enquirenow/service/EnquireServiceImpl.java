package com.Enquirenow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.Enquirenow.entity.EnquireEntity;
import com.Enquirenow.repository.EnquireRepo;

@Service
public class EnquireServiceImpl implements EnquireService {

    @Autowired
    private EnquireRepo enquireRepo;

	@Override
	public Long getInquiryCount() {
		return enquireRepo.count();
	}

    @Override
    public EnquireEntity saveEnquiry(EnquireEntity enquiry) {
        EnquireEntity save = enquireRepo.save(enquiry);
        if (save != null) {
            return save;
        }
        return null;
    }

    @Override
    public List<EnquireEntity> getAllEnquiries() {
        return enquireRepo.findAll();
    }

    @Override
    public List<EnquireEntity> getEnquiriesByEmail(String email) {
        return enquireRepo.findByEmail(email);
    }


}