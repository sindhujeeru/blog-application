package com.sindhu.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sindhu.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
