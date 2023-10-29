package com.oracle.oBootDBConect.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import com.oracle.oBootDBConect.domain.Member1;

//@Repository
//MemoryMemberRepository에 @Repository를 두거나 여기 두거나 둘 중 하나에 둬서 dao를 선택해야함
//만약 여기로 연결해서 작억하다 중간에 메모리로 바꿔서 다시 실행해서 회원목록 보면 목록 안나옴
//왜냐면 디비에 연결한 게 아니라 메모리에 연결한거고 메모리는 일시적인거라
//근데 SpringConfig클래스에서 @Configuration로 환경파일 만들고 빈 설정하려면 
//MemoryMemberRepository나 여기나 둘다 @Repository없어야 함
public class JdbcMemberRepository implements MemberRepository {
//1010 10:45?
	//JDBC 사용
	private final DataSource dataSource;
	
	public JdbcMemberRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private Connection getConnection() {
		return DataSourceUtils.getConnection(dataSource);
		//스프링에서 jdbc쓸 때 DataSourceUtils사용할 것을 권장
		//콘텍스트에 필요한 것을 application.properties에 정의해둠? 1010 10:20
		//이걸 사용해서 연결하고 릴리즈(맨 아래 있음)하면 메모리최적화까지 가능
		//기존에 jdbc연결하는 방식으로 해도되지만 그럼 메모리 최적화는 안됨
	}
	
	@Override
	public Member1 save(Member1 member1)  {
		String sql = "INSERT INTO member1(id, name) VALUES(member_seq.nextval, ?)";
		System.out.println("JdbcMemberRepository sql -> " + sql);		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, member1.getName());
			pstmt.executeUpdate();
			System.out.println("JdbcMemberRepository pstmt.executeUpdate After");
			return member1;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}

	@Override
	public List<Member1> findAll() {
		String sql = "SELECT * FROM member1";
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			List<Member1> members = new ArrayList<>();
			while (rs.next()) {
				Member1 member = new Member1();
				member.setId(rs.getLong("id"));
				member.setName(rs.getString("name"));
				members.add(member);
			}
			return members;
		} catch (Exception e) {
			throw new IllegalStateException(e);
		} finally {
			close(conn, pstmt, rs);
		}
	}
	
	private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		try {
			if(rs != null) rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(pstmt != null) pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn != null) close(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void close(Connection conn) throws SQLException {
		DataSourceUtils.releaseConnection(conn, dataSource);
		//releaseConnection는 메모리를 놓아줘라 메모리 최적화 해주는 것 이렇게 연결하면 메모리 최적화 까지 해주겠다는 것
		//throws SQLException 없으면 
	}

}
