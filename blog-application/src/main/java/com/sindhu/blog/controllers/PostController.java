package com.sindhu.blog.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.hibernate.engine.jdbc.StreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sindhu.blog.config.AppConstants;
import com.sindhu.blog.payloads.ApiResponse;
import com.sindhu.blog.payloads.FileResponse;
import com.sindhu.blog.payloads.PostDto;
import com.sindhu.blog.payloads.PostResponse;
import com.sindhu.blog.services.FileService;
import com.sindhu.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	@Autowired
	private PostService postServ;
	
	@Autowired
	private FileService fileServ;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{catId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable Integer userId,@PathVariable Integer catId){
		
		
		PostDto createPost = this.postServ.createPost(postDto, userId, catId);
		
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/posts/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){
		
		PostDto postDt = this.postServ.updatePost(postDto, postId);
		
		return new ResponseEntity<>(postDt, HttpStatus.CREATED);
	}
	
	@GetMapping("/category/{catId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer catId){
		
		List<PostDto> postsByCategory = this.postServ.getPostsByCategory(catId);
		
		return new ResponseEntity<>(postsByCategory, HttpStatus.OK);
		
	}
	
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		
		List<PostDto> postsByUser = this.postServ.getPostsByUser(userId);
		
		return new ResponseEntity<>(postsByUser, HttpStatus.OK);
		
	}
	
	@GetMapping("posts/{postId}")
	public ResponseEntity<PostDto> getPostByItsId(@PathVariable Integer postId){
		
		PostDto postDto = this.postServ.getPostById(postId);
		
		return new ResponseEntity<>(postDto,HttpStatus.OK);
	}
	
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getPosts(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR,required = false) String sortDir
			)
	{
		PostResponse postDtosPerPage = this.postServ.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		
		return new ResponseEntity<>(postDtosPerPage,HttpStatus.OK);
	}
	 
	@DeleteMapping("/posts/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePostByItsId(@PathVariable Integer postId){
		
		this.postServ.deletePost(postId);
		return new ResponseEntity<>( new ApiResponse("Post Deleted Succesfully",true),   HttpStatus.OK);
	}
	
	@GetMapping("/posts/search/{keyword}")
	public ResponseEntity<List<PostDto>> getPostsByKeyword(@PathVariable String keyword){
		List<PostDto> postDtos = this.postServ.searchPosts(keyword);
		
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,@PathVariable Integer postId) throws IOException
	{
		String fileName = null;
		
		fileName = this.fileServ.uploadImage(path, image);
		
		
		PostDto postDto = this.postServ.getPostById(postId);
		postDto.setImageName(fileName);
		
		PostDto updatedPost = this.postServ.updatePost(postDto, postId);
				
		return new ResponseEntity<>(updatedPost,HttpStatus.OK);
	}
	
	@GetMapping(value = "post/image/{imageName}",  produces = MediaType.IMAGE_PNG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse resp) throws IOException 
	{
		 InputStream resource = this.fileServ.getResource(path, imageName);
		 
		 resp.setContentType(MediaType.IMAGE_PNG_VALUE);
		 
		 StreamUtils.copy(resource, resp.getOutputStream());
	}
	
}



















