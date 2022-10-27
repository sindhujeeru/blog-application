package com.sindhu.blog.services;

import java.util.List;

import com.sindhu.blog.payloads.CommentDto;

public interface CommentService {
	
	
	void deleteCommentOnPost(Integer CommentId);
	
	CommentDto createCommentOnPost(CommentDto commentDto, Integer postId, Integer userId);

}
