package com.miniware.blog.payment.process;

import com.miniware.blog.payment.constants.PayType;
import org.springframework.stereotype.Component;

@Component
public class APay implements PaymentProcess {
    @Override
    public PayType getType() {
        return PayType.APAY;
    }

    @Override
    public void approve() {
        System.out.println("A페이 결제 승인");
    }

    @Override
    public void cancel() {
        System.out.println("A페이 결제 실패");
    }

    @Override
    public void refund() {
        System.out.println("A페이 결제 수정");
    }

}
