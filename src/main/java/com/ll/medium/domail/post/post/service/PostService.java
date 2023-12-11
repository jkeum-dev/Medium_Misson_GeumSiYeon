package com.ll.medium.domail.post.post.service;

import com.ll.medium.domail.member.member.entity.SiteMember;
import com.ll.medium.domail.post.post.entity.Post;
import com.ll.medium.domail.post.post.repository.PostRepository;
import com.ll.medium.global.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;

	public Page<Post> getList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.postRepository.findAllByKeyword(kw, pageable);
	}

	public Page<Post> getLatestList(int page, String kw) {
		List<Sort.Order> sorts = new ArrayList<>();
		sorts.add(Sort.Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
		return this.postRepository.findTop30ByKeywordOrderByIdDesc(kw, pageable);
	}

	public Post getPost(Integer id) {
		Optional<Post> post = this.postRepository.findById(id);
		if (post.isPresent()) {
			return post.get();
		} else {
			throw new DataNotFoundException("post not found");
		}
	}

	public void create(String subject, String content, boolean published, SiteMember member) {
		Post p = new Post();
		p.setSubject(subject);
		p.setContent(content);
		p.setCreateDate(LocalDateTime.now());
		p.setPublished(published);
		p.setAuthor(member);
		this.postRepository.save(p);
	}

	public void modify(Post post, String subject, String content, boolean published) {
		post.setSubject(subject);
		post.setContent(content);
		post.setPublished(published);
		post.setModifyDate(LocalDateTime.now());
		this.postRepository.save(post);
	}

	public void delete(Post post) {
		this.postRepository.delete(post);
	}
}
