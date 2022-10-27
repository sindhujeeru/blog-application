package com.sindhu.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sindhu.blog.entities.Comment;
import com.sindhu.blog.entities.Post;
import com.sindhu.blog.entities.User;
import com.sindhu.blog.exceptions.ResourceNotFoundException;
import com.sindhu.blog.payloads.CommentDto;
import com.sindhu.blog.repositories.CommentRepo;
import com.sindhu.blog.repositories.PostRepo;
import com.sindhu.blog.repositories.UserRepo;
import com.sindhu.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public void deleteCommentOnPost(Integer commentId) {
		 Comment comment = this.commentRepo.findById(commentId)
				 .orElseThrow(() -> new ResourceNotFoundException("Comment","comment id",commentId));
		 
		 this.commentRepo.delete(comment);
	}

	@Override
	public CommentDto createCommentOnPost(CommentDto commentDto, Integer postId, Integer userId) {
		 
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post","post id",postId));
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User","User id",userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		
		Comment createdComment = this.commentRepo.save(comment);
		
		 
		
		return this.modelMapper.map(createdComment,CommentDto.class);
	}

}
