package com.ll.medium.domail.member.member.repository;

import com.ll.medium.domail.member.member.entity.SiteMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<SiteMember, Long> {
	Optional<SiteMember> findByUsername(String username);
}
