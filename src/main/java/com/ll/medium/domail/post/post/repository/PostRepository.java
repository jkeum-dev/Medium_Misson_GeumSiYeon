package com.ll.medium.domail.post.post.repository;

import com.ll.medium.domail.post.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {
	Post findBySubject(String subject);

	Post findBySubjectAndContent(String subject, String content);

	List<Post> findBySubjectLike(String subject);
	Page<Post> findAll(Pageable pageable);
	Page<Post> findAll(Specification<Post> spec, Pageable pageable);

	@Query("select "
			+ "distinct p "
			+ "from Post p "
			+ "left outer join SiteMember m1 on p.author=m1 "
			+ "where p.published=true "
			+ "and (p.subject like %:kw% "
			+ " or p.content like %:kw% "
			+ " or m1.username like %:kw%)")
	Page<Post> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

	@Query("SELECT p " +
			"FROM Post p " +
			"LEFT OUTER JOIN SiteMember m1 ON p.author=m1 " +
			"WHERE p.published=true " +
			"AND (p.subject LIKE %:kw% " +
			"OR p.content LIKE %:kw% " +
			"or m1.username like %:kw%) " +
			"ORDER BY p.id DESC")
	Page<Post> findTop30ByKeywordOrderByIdDesc(@Param("kw") String keyword, Pageable pageable);
}
