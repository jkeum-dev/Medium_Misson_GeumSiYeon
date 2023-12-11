package com.ll.medium.global.init;

import com.ll.medium.domail.member.member.entity.SiteMember;
import com.ll.medium.domail.member.member.service.MemberService;
import com.ll.medium.domail.post.post.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

import java.util.stream.IntStream;

@Configuration
@Profile("!prod")
@Slf4j
@RequiredArgsConstructor
public class NotProd {
	private final MemberService memberService;
	private final PostService postService;

	@Bean
	@Order(3)
	public ApplicationRunner initNotProd() {
		return args -> {
			if (memberService.getMember("user1") != null) return;

			SiteMember memberUser1 = memberService.create("user1", "1234");
			SiteMember memberUser2 = memberService.create("user2", "1234");
			SiteMember memberUser3 = memberService.create("user3", "1234");
			SiteMember memberUser4 = memberService.create("user4", "1234");

			postService.create("제목 1", "내용 1", false, memberUser1);
			postService.create("제목 2", "내용 2", false, memberUser1);
			postService.create("제목 3", "내용 3", false, memberUser1);
			postService.create("제목 4", "내용 4", false, memberUser1);

			postService.create("제목 5", "내용 5", false, memberUser2);
			postService.create("제목 6", "내용 6", false, memberUser2);

			IntStream.rangeClosed(7, 50).forEach(i -> {
				postService.create("제목 " + i, "내용 " + i, true, memberUser3);
			});
		};
	}
}
