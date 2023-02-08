package hello.core.discount;

import hello.core.member.Member;

public interface DiscountPolicy {
    
    //return할인 대상 금액 (정액이든 정률이든 최후엔 금액이 나오는 매서드)
    int discount(Member member, int price);
}
