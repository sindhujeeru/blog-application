package com.sindhu.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sindhu.blog.payloads.ApiResponse;
import com.sindhu.blog.payloads.CommentDto;
import com.sindhu.blog.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentServ;
	
	@PostMapping("/post/{postId}/user/{userId}/comment")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId, @PathVariable Integer userId){
		
		
		CommentDto createdComment = this.commentServ.createCommentOnPost(commentDto, postId, userId);
		return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deelteComment(@PathVariable Integer commentId){
		this.commentServ.deleteCommentOnPost(commentId);
		return new ResponseEntity<>(new ApiResponse("Comment deleted successfully",true),HttpStatus.OK);
	}

}
  