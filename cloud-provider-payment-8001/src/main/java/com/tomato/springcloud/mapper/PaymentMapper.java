package com.tomato.springcloud.mapper;

import com.tomato.springcloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author : tomato<hechunhui_email@163.com>
 * @date : 2022/10/7 22:14
 * @className : PaymentDao
 * @description: TODO
 */
@Mapper
public interface PaymentMapper {

    int create(Payment payment);

    Payment getPaymentById(@Param("id") Long id);
}
