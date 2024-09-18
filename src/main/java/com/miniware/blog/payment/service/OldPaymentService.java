package com.miniware.blog.payment.service;

import com.miniware.blog.payment.constants.PayType;
import com.miniware.blog.payment.dto.ReqPayment;

public interface OldPaymentService  {

    void apprve(ReqPayment reqPayment);

    void cancel(ReqPayment reqPayment);

    void refund(ReqPayment reqPayment);

}
