package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import java.util.List;
import java.util.Optional;

// Member 객체를 저장하는 repository (interface)
public interface MemberRepository {
    // Repository에 4가지 기능을 만듬
    Member save(Member member); // 회원이 저장소에 저장
    // Optional: null로 반환될 수 있으니까 Optional로 감싸서 반환
    Optional<Member> findById(Long id); // 이 저장소에서 findById로 찾아올 수 있음
    Optional<Member> findByName(String name); // 마찬가지
    List<Member> findAll(); // 지금까지 저장된 모든 회원을 가져옴
}
