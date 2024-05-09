package com.Enquirenow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Enquirenow.entity.EnquireEntity;
import com.Enquirenow.service.EnquireService;
import com.Enquirenow.utility.EmailRequest;
import com.Enquirenow.utility.EmailService;

@RestController
public class EnquireController {

	@Autowired
	private EnquireService enquireService;

	@Autowired
	private EmailService emailService;
	 
	@GetMapping("/count")
	    public Long getInquiryCount() {
	    return enquireService.getInquiryCount();
	 }
	
	
	@PostMapping("/save")
	public ResponseEntity<EnquireEntity> saveEnquiry(@RequestBody EnquireEntity enquiry) {

		EnquireEntity savedEnquiry = enquireService.saveEnquiry(enquiry);
		if (savedEnquiry != null) {
			boolean emailSent = sendNotificationEmail(savedEnquiry);
			if (emailSent) {
				return new ResponseEntity<>(savedEnquiry, HttpStatus.CREATED);
			} else {

				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public boolean sendNotificationEmail(EnquireEntity enquire) {
		String to = enquire.getEmail();
		String subject = "New Enquire Received";
		String body = "A new enquire has been received with the following details:\n" + "Enquiry ID: " + enquire.getId()
				+ "\n" + "FullName: " + enquire.getFullName() + "\n" + "Email: " + enquire.getEmail() + "\n"
				+ "Comment: " + enquire.getComment() + "\n\n"
				+ "One of our executives will meet you shortly. Thank you, have a nice day!\n\n"
				+ "Your feedback is important to us. If you have any comments or suggestions, please feel free to reply to this email.+\n\n"
				+ "Thank you HMS TEAM..";

		return emailService.sendEmail(to, subject, body);
	}

	@GetMapping("/enquires")
	public ResponseEntity<List<EnquireEntity>> getAllEnquiries() {
		List<EnquireEntity> enquiries = enquireService.getAllEnquiries();
		if (!enquiries.isEmpty()) {
			return new ResponseEntity<>(enquiries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/enquires/{email}")
	public ResponseEntity<List<EnquireEntity>> getEnquiriesByEmail(@PathVariable String email) {
		List<EnquireEntity> enquiries = enquireService.getEnquiriesByEmail(email);
		if (!enquiries.isEmpty()) {
			return new ResponseEntity<>(enquiries, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/sendEmail")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {
		boolean sendEmail = emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(),
				emailRequest.getBody());
		if (sendEmail) {
			return new ResponseEntity<>("Email sent successfully!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Failed to send email.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
