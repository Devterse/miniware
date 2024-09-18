package com.miniware.blog.payment.dto;

import com.miniware.blog.payment.constants.PayType;
import lombok.Getter;

@Getter
public class ReqPayment {

    private PayType payType;
}
