package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class JpaMemberRepository implements MemberRepository {

    // jpa는 entitymanager로 모든게 동작
    // build.gradle에서 jpa설정 해둠으로써 spring boot에서 entityManager를 자동 생성해줌
    // 여기선 그렇게 만들어진 것을 injection 받음
    private final EntityManager em;

    public JpaMemberRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Member save(Member member) {
        em.persist(member); // jpa가 insert query 다 만들어서 디비에 넣어줌
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        Member member = em.find(Member.class, id); // 조회할 타입과 식별자를 인자로
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name) {
        // pk가 아닌 값으로 조회할 때는 jpql이라는 query언어를 써야함
        List<Member> result = em.createQuery("select m from Member m where m.name = :name",
            Member.class).setParameter("name", name).getResultList();
        
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }
}
