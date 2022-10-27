package com.sindhu.blog.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sindhu.blog.payloads.FileResponse;
import com.sindhu.blog.services.FileService;

@RestController
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileServ;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/upload")
	public ResponseEntity<FileResponse> fileUpload(
			@RequestParam("image") MultipartFile image)
	{
		
		String fileName = null;
		try {
			fileName = this.fileServ.uploadImage(path, image);
		}catch(IOException e) {
			e.printStackTrace();
			return new ResponseEntity<>(new FileResponse(fileName,"Error uploading image due to server error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
				
		return new ResponseEntity<>(new FileResponse(fileName,"Successfully added image"), HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/images/{imageName}",  produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse resp) throws IOException 
	{
		 InputStream resource = this.fileServ.getResource(path, imageName);
		 
		 resp.setContentType(MediaType.IMAGE_PNG_VALUE);
		 
		 StreamUtils.copy(resource, resp.getOutputStream());
	}
 
}
