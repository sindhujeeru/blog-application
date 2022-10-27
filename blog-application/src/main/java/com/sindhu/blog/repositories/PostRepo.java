package com.sindhu.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sindhu.blog.entities.Category;
import com.sindhu.blog.entities.Post;
import com.sindhu.blog.entities.User;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer>{
	
	List<Post> findAllByUser(User user);
	
	List<Post> findAllByCategory(Category category);
	
	@Query("SELECT p FROM Post p WHERE Lower(p.content) LIKE %?1%"
			+ "OR Lower(p.title) like %?1%")
	List<Post> searchByContent(String keyword);
	
	@Query("Select p from Post p where p.title like :key")
	List<Post> searchByTitle(@Param("key") String title);
	
	
	List<Post> findByTitleContaining(String keyword);

}
