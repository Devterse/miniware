package com.miniware.blog.payment.service;

import com.miniware.blog.payment.constants.PayType;
import com.miniware.blog.payment.dto.ReqPayment;
import com.miniware.blog.payment.process.PaymentProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final Map<PayType, PaymentProcess> map = new EnumMap<>(PayType.class);;
    public PaymentServiceImpl(List<PaymentProcess> processList) {
        for (PaymentProcess paymentProcess : processList) {
            map.put(paymentProcess.getType(), paymentProcess);
        }

        /*this.map = processList.stream()
            .collect (
                Collectors.toMap(PaymentProcess::getType, Function.identity(),
                    (existing, replacement) -> existing, () -> new EnumMap<>(PayType.class)
                )
            );*/
    }

    /*결제 승인*/
    @Override
    public void paymentApprove(ReqPayment reqPayment) {
        PaymentProcess paymentProcess = map.get(reqPayment.getPayType());
        paymentProcess.approve();
    }

    /*결제 취소*/
    @Override
    public void paymentCancel(ReqPayment reqPayment) {
        PaymentProcess paymentProcess = map.get(reqPayment.getPayType());
        paymentProcess.cancel();
    }

    /*결제 부분결제/환불*/
    @Override
    public void paymentRefund(ReqPayment reqPayment) {
        PaymentProcess paymentProcess = map.get(reqPayment.getPayType());
        paymentProcess.refund();
    }

    /*결제 프로세스 리턴*/
    public PaymentProcess PaymentProcess(ReqPayment reqPayment) {
        return map.get(reqPayment.getPayType());
    }

}