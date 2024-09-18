package com.miniware.blog.payment.process;

import com.miniware.blog.payment.constants.PayType;

public interface PaymentProcess {
    PayType getType();

    void approve();

    void cancel();

    void refund();
}