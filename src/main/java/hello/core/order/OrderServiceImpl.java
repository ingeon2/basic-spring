package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService{

    private final MemberRepository memberRepository;
    private DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //private final MemberRepository memberRepository = new MemoryMemberRepository();

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //private final DiscountPolicy discountPolicy = new RateDiscountPolicy();
    //위의 세줄처럼, 고정할인, 변동할인에 따라 주석처리 하면서 사용하는것 좋은 코드 아님.
    //위처럼 바꾼다 (DIP, OCP) 
    //OrderServiceLmpl는 사용하는 필드가 두개(멤버리파지토리, 디스카운트팔러시)이다.

    
    


    //아래는 오버라이딩
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        //받아온 id로 회원 객체 할당해줌(아래의 매서드에 할당한 객체가 필요하기 때문)
        int discountPrice = discountPolicy.discount(member, itemPrice);
        //createOrder에서 할인은 전적으로 OrderService와 관련없이 discountPolicy에만 의존함
        //discountPolicy 객체는 discount패키지 안의 로직에 따라 할인 가격을 내뱉고, 그 뱉은 가격으로 return할때 생성해서 사용.
        //★이게 바로 단일책임원칙 잘 지킨것!

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    //싱글톤 테스트옹 in AppConfig, README
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
