package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

// service -> 비즈니스 로직을 작성하는 부분
@Transactional
public class MemberService {

    // final = 처음 초기화 할 때와 생성자에서만 값을 할당할 수 있음. 그 외의 경우 값 수정 불가
    private final MemberRepository memberRepository;

    /*
    final 변수 초기화를 생성자로 외부에서 넣어주도록 해줌.
    여기서는 spring Container에서 MemberRepository를 주입해줌
    이런걸 Dependency Injection이라고 함
    */
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /* 회원 가입 */
    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원 x
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> { // 값이 있으면, ifPresent는 Optional로 한번 감싸서 return해줬기 때문에 쓸 수 있는 것
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }

    /* 전체 회원 조회 */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /* 특정 회원 아이디로 조회 */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}
