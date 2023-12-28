package com.ll.medium.domail.post.post.controller;

import com.ll.medium.domail.member.member.entity.SiteMember;
import com.ll.medium.domail.member.member.service.MemberService;
import com.ll.medium.domail.post.post.entity.Post;
import com.ll.medium.domail.post.post.form.PostForm;
import com.ll.medium.domail.post.post.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@RequestMapping("/post")
@RequiredArgsConstructor
@Controller
public class PostController {

	private final PostService postService;
	private final MemberService memberService;

	@GetMapping("/list")
	public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
					   @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Post> paging = this.postService.getList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "domain/post/post/post_list";
	}

	@GetMapping("/list/latest")
	public String latestlist(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
					   @RequestParam(value = "kw", defaultValue = "") String kw) {
		Page<Post> paging = this.postService.getLatestList(page, kw);
		model.addAttribute("paging", paging);
		model.addAttribute("kw", kw);
		return "domain/post/post/post_latest_list";
	}

	@GetMapping(value = "/{id}")
	public String detail(Model model, @PathVariable("id") Integer id) {
		Post post = this.postService.getPost(id);
		model.addAttribute("post", post);
		return "domain/post/post/post_detail";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/write")
	public String postCreate(PostForm postForm) {
		return "domain/post/post/post_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/write")
	public String postCreate(@Valid PostForm postForm,
								 BindingResult bindingResult, Principal principal) {
		if (bindingResult.hasErrors()) {
			return "domain/post/post/post_form";
		}
		SiteMember siteMember = this.memberService.getMember(principal.getName());
		this.postService.create(postForm.getSubject(), postForm.getContent(), postForm.isPublished(), siteMember);
		return "redirect:/post/list";
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}/modify")
	public String postModify(PostForm postForm, @PathVariable("id") Integer id, Principal principal) {
		Post post = this.postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		postForm.setSubject(post.getSubject());
		postForm.setContent(post.getContent());
		postForm.setPublished(post.isPublished());
		return "domain/post/post/post_form";
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/{id}/modify")
	public String postModify(@Valid PostForm postForm, BindingResult bindingResult,
								 Principal principal, @PathVariable("id") Integer id) {
		if (bindingResult.hasErrors()) {
			return "domain/post/post/post_form";
		}
		Post post = this.postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		this.postService.modify(post, postForm.getSubject(), postForm.getContent(), postForm.isPublished());
		return String.format("redirect:/post/%s", id);
	}

	@PreAuthorize("isAuthenticated()")
	@GetMapping("/{id}/delete")
	public String postDelete(Principal principal, @PathVariable("id") Integer id) {
		Post post = this.postService.getPost(id);
		if (!post.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
		}
		this.postService.delete(post);
		return "redirect:/";
	}
}
