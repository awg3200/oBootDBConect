package com.oracle.oBootDBConect;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.oracle.oBootDBConect.repository.JdbcMemberRepository;
import com.oracle.oBootDBConect.repository.MemberRepository;
import com.oracle.oBootDBConect.repository.MemoryMemberRepository;

//프로그램 실행시 환경파일 젤 먼저 뒤져서 세팅부터해줌
@Configuration //이것도 component
public class SpringConfig {
	private final DataSource dataSource;
	//application.properties에 정의된 데이터 소스를 의미
//	@Autowired //원래는 @Autowired 이거 써야하는데 하나로 결정되어있으면 생략해도 됨 
	public SpringConfig(DataSource dataSource) {
//		dataSource는 application.properties에 정의된 것이 실행
		this.dataSource = dataSource; 
	}
	
	@Bean //환경파일로 잡을 땐 빈으로 설정해줘야함
	//1018 10:04~13
	//수시로 변경되는건 해당 클래스에 어노테이션 넣지 않고 @Configuration파일 만들고 여기에 return new 를 어떤 걸로 할지만 변경해주면됨
	//그럼 JdbcMemberRepository파일을 쓸 수도 있고 MemoryMemberRepository 쓸 수도 있는데 
	//전자를 쓸땐 return new를 전자로 하고 후자 쓸 떈 후자로 리턴을 하면됨
	//어떤 저장소를 쓸지 자주 변경되는 경우 해당 Repository클래스 파일 위에 어노테이션을 설정하면 그걸로 고정돼서 일일히 변경될 때마다 변경된 Repository로 가서 어노테이션 달아야 하지만
	//이렇게 @Configuration만들면 여기서 아래처럼 리턴값만 변경하면 되니까 유지보수가 더 쉬움
	public MemberRepository memberRepository() {
		//MemberRepository 이 인터페이스 (디비에 연결하기 위한거?) 빈을 리턴 값으로 new연산자를 통해 
		//이 인터페이스를 구체화한 클래스 객체를 생성해서 만들어줌?
		//따라서 이 인터페이스는 해당 객체로 연결됨? 어떤 객체를 쓰냐에 따라 오라클로 연결이 되기도하고 메모리 쓰는 방식으로 연결되기도 하고
//		return new JdbcMemberRepository(dataSource);
		//MemberRepository에 대한 빈을 오라클에 연결하는 레파지토리를 빈으로 설정함 따라서 실행하면 이게 세팅돼서 오라클에 저장된 회원 목록 나옴
		return new MemoryMemberRepository();
//		MemberRepository에 다한 빈을 메모리 레파지토리로 설정 
	}
	
}
