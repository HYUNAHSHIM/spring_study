package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemoryMemberRepository implements MemberRepository {
    // 만들었던 MemberRepository interface를 implements -> 아래에 MemberRepository의 메서드 틀들을 가져옴. 여기선 모두 가져옴

    // Member를 저장해주기 위해 Map 사용, 실무에서는 동시성 문제로 concurrent HashMap(?) 씀. 여기선 단순히 HashMap
    private static Map<Long, Member> store = new HashMap<>();
    // 0, 1, 2... 이렇게 key값 생성용, 실무에선 동시성 문제로 atom long..(?)해주긴함
    private static long sequence = 0L;

    @Override
    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {
        // 결과가 없는 경우 (null)을 위해 Optional로 감쌈 -> 클라이언트에서 어떻게 처리가 가능하게 됨.
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Member> findByName(String name) {
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny(); // findAny = 뤂 돌다가 하나라도 찾으면 반환, 결과가 Optinal로 반환됨
    }

    @Override
    public List<Member> findAll() {
        return new ArrayList<>(store.values()); // store에 있는 values(Member)를 전부 반환
    }

    // 테스트시 각자의 테스트가 서로에 영향을 끼치지 않게 하기 위해 메모리를 클리어해줌
    public void clearStore() {
        store.clear();
    }
}
