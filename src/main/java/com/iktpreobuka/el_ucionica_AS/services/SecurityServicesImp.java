package com.iktpreobuka.el_ucionica_AS.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iktpreobuka.el_ucionica_AS.entities.GradeEntity;
import com.iktpreobuka.el_ucionica_AS.entities.enums.UserRole;
import com.iktpreobuka.el_ucionica_AS.repositories.GradeRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.StudentRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.SutestRepository;
import com.iktpreobuka.el_ucionica_AS.repositories.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Service
public class SecurityServicesImp implements SecurityServices{

	
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private StudentRepository studRepo;
	@Autowired
	private SutestRepository stsRepo;
	@Autowired
	private GradeRepository gradeRepo;


	@Value("${spring.security.secret-key}")
	private String secretKey;

	
	private String getUsersUsername (HttpServletRequest request) {
		String authorizationHeader = request.getHeader("authorization");
		if((authorizationHeader == null) || !authorizationHeader.startsWith("Bearer ")) {
			return null;
		}
		String token = request.getHeader("Authorization").replace("Bearer ", "");
		Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		if(claims.get("authorities") == null) {
			return null;
		}
		return claims.getSubject();
	}
	
	@Override
	public boolean isThisMe(Integer id, HttpServletRequest request) {
		String username = getUsersUsername(request);
		if (username != null) {
			if (userRepo.findByUsername(username).get().getId()==id)
				return true;
		}
		return false;
	}

	@Override
	public boolean amIAdmin(HttpServletRequest request) {
		String username = getUsersUsername(request);
		if (username != null) {
			if (userRepo.findByUsername(username).get().getRole().equals(UserRole.ROLE_ADMIN))
				return true;
		}
		return false;
	}

	@Override
	public boolean amITheirParent(Integer stud, HttpServletRequest request) {
		if ((studRepo.existsById(stud))&&(studRepo.findById(stud).get().getParent()!=null)) {
			String username = getUsersUsername(request);
			if (username != null) {
				if (studRepo.findById(stud).get().getParent().getId() == userRepo.findByUsername(username).get().getId())
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean amITheirTeacher(Integer stud, HttpServletRequest request) {
		if (studRepo.existsById(stud)) {
			String username = getUsersUsername(request);
			if (username != null) {
				if (stsRepo.existsByStudIdAndTsTeacherUsername(stud, username))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean doIThechThemThis(Integer stud, Integer sub, HttpServletRequest request) {
		if (studRepo.existsById(stud)) {
			String username = getUsersUsername(request);
			if (username != null) {
				if (stsRepo.existsByStudIdAndTsSubjectIdAndTsTeacherUsername(stud, sub, username))
					return true;
			}
		}
		return false;
	}

	@Override
	public boolean isThisMyGrade(Integer id, HttpServletRequest request) {
		if (gradeRepo.existsById(id)) {
			GradeEntity grade = gradeRepo.findById(id).get();
			UserRole role = userRepo.findByUsername(getUsersUsername(request)).get().getRole();
			if (role.equals(UserRole.ROLE_STUDENT) && (isThisMe(grade.getInfo().getStud().getId(), request)))
				return true;
			if (role.equals(UserRole.ROLE_TEACHER) && (isThisMe(grade.getInfo().getTs().getTeacher().getId(), request)))
				return true;
			if (role.equals(UserRole.ROLE_PARENT) && (isThisMe(grade.getInfo().getStud().getParent().getId(), request)))
				return true;
		}
		return false;
	}
	
}
