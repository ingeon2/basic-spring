package hello.core.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MemberServiceImpl implements MemberService{

    //private final MemberRepository memberRepository = new MemoryMemberRepository();
    //바로 위는 추상화(MemberRepository 인터페이스)와 구체화(MemoryMemberRepository 클래스) 둘다 의존중(DIP위반)
    //그래서 아래처럼 바꿈(with AppConfig) 
    //아래는 MemoryMemberRepository  코드를 찾아볼 수 없음 , 추상화에만 의존
    //이전의 구체화는 AppConfig에서 생성해서 넣어줘서 없엘 수 있었음.

    private final MemberRepository memberRepository;
    @Autowired
    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }



    @Override
    public void join(Member member) {
        memberRepository.save(member);
    }

    @Override
    public Member findMember(Long memberId) {
        return memberRepository.findById(memberId);
    }


    //싱글톤 테스트옹 in AppConfig, README
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}

//설계 변경으로 MemberServiceImpl 은 MemoryMemberRepository 를 의존하지 않는다!
//단지 MemberRepository 인터페이스만 의존한다.
//MemberServiceImpl 입장에서 생성자를 통해 어떤 구현 객체가 들어올지(주입될지)는 알 수 없다.
//★★★★★MemberServiceImpl 의 생성자를 통해서 어떤 구현 객체를 주입할지는 오직 외부( AppConfig )에서 결정된다.★★★★★
//MemberServiceImpl 은 이제부터 의존관계에 대한 고민은 외부에 맡기고 실행에만 집중하면 된다.