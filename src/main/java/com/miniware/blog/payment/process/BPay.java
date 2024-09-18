package com.miniware.blog.payment.process;

import com.miniware.blog.payment.constants.PayType;
import org.springframework.stereotype.Component;

@Component
public class BPay implements PaymentProcess {
    @Override
    public PayType getType() {
        return PayType.BPAY;
    }

    @Override
    public void approve() {
        System.out.println("B페이 결제 승인");
    }

    @Override
    public void cancel() {
        System.out.println("B페이 결제 취소");
    }

    @Override
    public void refund() {
        System.out.println("B페이 결제 수정");
    }

}