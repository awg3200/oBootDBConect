package com.oracle.oBootDBConect.repository;

import java.util.List;

import com.oracle.oBootDBConect.domain.Member1;

public interface MemberRepository {
	Member1 save(Member1 member1);
	List<Member1> findAll();
	//인터페이스 한 이유는 나중에 dbms가 오라클에서 Mysql이나 다른걸로 바뀔 수는 경우 
	//인터페이스로 해놓는 것 이러면 설정만 좀 바꿔주면 나머진 거의 그대로라서 1010 11:10
	//디비 자체도 통채로 바꾸기 위해 인터페이스로 만든것
}
