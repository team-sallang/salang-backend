package com.salang.backend;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.salang.backend.member.Member;
import com.salang.backend.member.MemberRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class DatabaseConnectionTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void testDatabaseConnection() {
        // 1. 엔티티 저장
        Member member = new Member();
        member.setName("테스트유저");

        memberRepository.save(member);

        // 2. 저장된 엔티티 조회
        Optional<Member> found = memberRepository.findById(member.getId());
        assertTrue(found.isPresent(), "DB 연결 실패: 저장한 Member를 찾을 수 없습니다.");

        System.out.println("조회된 Member 이름: " + found.get().getName());
        System.out.println("조회된 Member id: " + found.get().getId());

        // 3. 삭제
//        memberRepository.delete(member);
//        assertFalse(memberRepository.findById(member.getId()).isPresent(), "삭제 실패");
    }
}
