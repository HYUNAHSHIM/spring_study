package hello.hellospring.repository;

import static org.assertj.core.api.Assertions.assertThat;

import hello.hellospring.domain.Member;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;


// repository가 잘 동작하는지 test하는 테스트 코드
// 테스트 코드를 만들때 보통 똑같은 패키지 명으로 만듬
// 클래스 이름은 클래스명 + Test 붙여서!
// 굳이 public으로 안해도 됨. 다른 곳에서 가져다 쓸게 아니라서
// 테스트하는 코드를 먼저 만들고 구현을 하는걸 TDD라고 함
class MemoryMemberRepositoryTest {

    // 테스트 하기 위해 생성
    MemoryMemberRepository repository = new MemoryMemberRepository();

    // 모든 테스트는 순서가 보장이 안됨. 순서에 의존하지 않게 설계되어야 함.
    // 여러 test를 함께 돌리면 이전 테스트에서 저장된게 안없어져서 오류가남
    // 테스트 끝나면 데이터를 클리어해줘야함
    // 클리어하는 함수는 memoryRepository에 clearStore만듬
    // @AfterEach = 테스트 메서드들이 끝날때마다 할 동작을 정의
    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void save() {
        Member member = new Member();
        member.setName("spring");

        repository.save(member); // 저장

        // 저장한게 잘 들어갔는지 확인, 참고로 저장할때 id가 세팅됨
        // 반환타입이 Optional인데 Optional에서는 get으로 값을 꺼낼 수 있음, 좋은 방법은 아니지만 테스트 코드에선 이렇게 해도 괜춘
        Member result = repository.findById(member.getId()).get();

        // 검증 단계
        // 둘이 같은지 확인
        assertThat(member).isEqualTo(result);

    }

    @Test
    public void findByName() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        Member result = repository.findByName("spring1").get();

        assertThat(result).isEqualTo(member1);
    }

    @Test
    public void findAll() {
        Member member1 = new Member();
        member1.setName("spring1");
        repository.save(member1);

        Member member2 = new Member();
        member2.setName("spring2");
        repository.save(member2);

        List<Member> result = repository.findAll();
        assertThat(result.size()).isEqualTo(2);
    }

}
