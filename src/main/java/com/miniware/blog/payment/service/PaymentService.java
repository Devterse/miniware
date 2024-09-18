package com.miniware.blog.payment.service;

import com.miniware.blog.payment.constants.PayType;
import com.miniware.blog.payment.dto.ReqPayment;

public interface PaymentService {
    void paymentApprove(ReqPayment reqPayment);

    void paymentCancel(ReqPayment reqPayment);

    void paymentRefund(ReqPayment reqPayment);

}
