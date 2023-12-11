package com.ll.medium.domail.member.member.service;

import com.ll.medium.domail.member.member.entity.SiteMember;
import com.ll.medium.domail.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public SiteMember create(String username, String password) {
		SiteMember member = new SiteMember();
		member.setUsername(username);
		member.setPassword(passwordEncoder.encode(password));
		this.memberRepository.save(member);
		return member;
	}

	public SiteMember getMember(String username) {
		Optional<SiteMember> siteMember = this.memberRepository.findByUsername(username);
		if (siteMember.isPresent()) {
			return siteMember.get();
		} else {
			return null;
		}
	}
}
