package com.miniware.blog.payment.service;

import com.miniware.blog.payment.constants.PayType;
import com.miniware.blog.payment.dto.ReqPayment;

public class OldPaymentServiceImpl implements OldPaymentService {

    @Override
    public void apprve(ReqPayment reqPayment) {
        if (PayType.APAY == reqPayment.getPayType()) {
            //APay 승인처리
        } else if (PayType.BPAY == reqPayment.getPayType()) {
            //BPay 승인처리
        } else if (PayType.CPAY == reqPayment.getPayType()) {
            //CPay 승인처리
        }
    }

    @Override
    public void cancel(ReqPayment reqPayment) {
        if (PayType.APAY == reqPayment.getPayType()) {
            //APay 취소처리
        } else if (PayType.BPAY == reqPayment.getPayType()) {
            //BPay 취소처리
        } else if (PayType.CPAY == reqPayment.getPayType()) {
            //CPay 취소처리
        }
    }

    @Override
    public void refund(ReqPayment reqPayment) {
        if (PayType.APAY == reqPayment.getPayType()) {
            //APay 부분결제/환불처리
        } else if (PayType.BPAY == reqPayment.getPayType()) {
            //BPay 부분결제/환불처리
        } else if (PayType.CPAY == reqPayment.getPayType()) {
            //CPay 부분결제/환불처리
        }
    }
}
