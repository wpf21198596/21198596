package com.fh.shop.api;

import com.fh.shop.api.order.mapper.IOrderMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopApiSpringbootApplicationTests {

    @Autowired
    private IOrderMapper orderMapper;

    @Test
    public void contextLoads() {
        System.out.println(123);

    }

}
