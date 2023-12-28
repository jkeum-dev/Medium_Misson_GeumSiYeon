package com.ll.medium.global.init;

import com.ll.medium.domail.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class All {
	private final MemberService memberService;

	@Bean
	@Order(2)
	public ApplicationRunner initAll() {
		return args -> {
			if (memberService.getMember("system") != null) return;

			memberService.create("system", "1234");
			memberService.create("admin", "1234");
		};
	}
}
