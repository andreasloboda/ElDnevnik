package com.iktpreobuka.el_ucionica_AS.services;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class FileHandlerImp implements FileHandler{

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
