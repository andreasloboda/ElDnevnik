package com.iktpreobuka.el_ucionica_AS.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;

@Service
public class OtherServicesImp implements OtherServices{

	@Autowired
	private JavaMailSender mailSender;
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	
	@Override
	public void informParentAboutGrade(GradeEntity grade) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			if (grade.getInfo().getStud().getParent() == null) {
				logger.warn("Student with ID " + grade.getInfo().getStud().getId() + " has no parent registered. Email about new grade not sent.");
				return;
			}
			helper.setTo(grade.getInfo().getStud().getParent().getEmail());
			helper.setSubject("Your child recieved a new grade.");
			LocalDate date = LocalDate.now();
			String body = "<html><body>Respected Mx " + grade.getInfo().getStud().getParent().getName() + " " + grade.getInfo().getStud().getParent().getSurname() +
					"<br>Your child, " + grade.getInfo().getStud().getName() + " " + grade.getInfo().getStud().getSurname() + " has recieved a new grade." +
					"<br>Information:\n <table><tr>"
					+ "<th>Date</th>"
					+ "<th>Teacher</th>"
					+ "<th>Subject</th>"
					+ "<th>Grade</th>"
					+ "</tr><tr>"
					+ "<th>" + date.toString() + "</th>"
					+ "<th>" + grade.getInfo().getTs().getTeacher().getName() + " " + grade.getInfo().getTs().getTeacher().getSurname() + "</th>"
					+ "<th>" + grade.getInfo().getTs().getSubject().getName() + "</th>"
					+ "<th>" + grade.getGrade() + "</th>"
					+ "</tr></table>></body></html>";
			helper.setText(body, true);
		}
		 catch (MessagingException e) {
				e.printStackTrace();
			}
			mailSender.send(message);
			logger.info("Email about new grade sent to the parent of student with ID" + grade.getInfo().getStud().getId() + ".");
	}
	
	@Override
	public ResponseEntity<?> downloadLog() {
		Path path = Paths.get("E:\\ITprekval\\04.Bekend\\SpringToolSuite\\el_ucionica_AS\\logs\\happenings.log");
		Resource resource = null;
		try {
			resource = new UrlResource(path.toUri());
		}
		catch (MalformedURLException e) {
			logger.info("Downloading Log: error occured while forming resource");
		}
		if (!resource.exists()) {
			logger.info("Downloading log: download canceled due to an error.");
			return new ResponseEntity<> ("Download unsuccessful. Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		String contentType = "application/octet-stream";
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
}
