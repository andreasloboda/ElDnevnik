package com.iktpreobuka.el_ucionica_AS.services;

import java.time.LocalDate;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;

@Service
public class EmailServicesImp implements EmailServices{

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void informParentAboutGrade(GradeEntity grade) {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		try {
			if (grade.getInfo().getStud().getParent() == null)
				return;
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
	}
	
	
}
