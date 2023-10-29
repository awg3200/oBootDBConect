package com.oracle.oBootDBConect.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.oBootDBConect.domain.Member1;
import com.oracle.oBootDBConect.repository.MemberRepository;

@Service
public class MemberService {
	private final MemberRepository memberRepository;
	@Autowired
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}	
	//회원가입
	public Long memberSave(Member1 member1) {
		memberRepository.save(member1);
		return member1.getId();
	}

		
	//전체회원 조회
	public List<Member1> findMembers() {
		System.out.println("MemberService findMembers start...");
//		List<Member1> memList = memberRepository.findAll();
		return memberRepository.findAll();
	}
	
}
