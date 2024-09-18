package com.miniware.blog.payment.controller;

import com.miniware.blog.payment.dto.ReqPayment;
import com.miniware.blog.payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/payment/approve")
    public void PaymentApprove(@RequestBody ReqPayment reqPayment) {
        paymentService.paymentApprove(reqPayment);
    }

    @GetMapping("/payment/cancel")
    public void PaymentCancel(ReqPayment reqPayment) {
        paymentService.paymentCancel(reqPayment);
    }

    @GetMapping("/payment/refund")
    public void PaymentRefund(ReqPayment reqPayment) {
        paymentService.paymentRefund(reqPayment);
    }
}
