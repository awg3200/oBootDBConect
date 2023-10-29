package com.oracle.oBootDBConect.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConect.domain.Member1;

//@Repository
//여긴 멥을 이용해 메모리에 저장하는 dao
//JdbcMemberRepository는 오라클을 이용해 저장하는 dao 근데 둘 다@Repository 있으면 component가 두개이고?
	//빈이 두개라고 에러 뜸 따라서 둘 중 하나만 @Repository 붙여서 다오를 정해줘야함
public class MemoryMemberRepository implements MemberRepository {
	private static Map<Long, Member1> store = new HashMap<>();
	private static long sequence = 0L;
	
	//oBootHello가 메모리(Map방식)에 저장한 거랑 다르게 오라클 사용하는 예
	
	@Override
	public Member1 save(Member1 member1) {
		System.out.println("MemoryMemberRepository save start..");
		member1.setId(++sequence); //시퀀스가 멤버 변수니까 하나씩 증가시킨 값을 member1에 세터로 저장
		//오라클 테이블에서 시퀀스처럼 하나씩 증가시키면서 사용
		store.put(member1.getId(), member1);
		System.out.println("MemoryMemberRepository sequence -> " + sequence);
		System.out.println("MemoryMemberRepository member1.getName() -> " + member1.getName());
		
		return member1;
	}

	@Override
	public List<Member1> findAll() {
		System.out.println("MemoryMemberRepository findAll start...");
		return new ArrayList<>(store.values());
	}

}
