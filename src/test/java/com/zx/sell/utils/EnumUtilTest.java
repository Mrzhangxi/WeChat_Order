package com.zx.sell.utils;

import com.zx.sell.enums.OrderStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static java.awt.Cursor.log;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class EnumUtilTest {

    @Test
    public void getByCode() {
//        log.info("dd");
        System.out.println(EnumUtil.getByCode(1, OrderStatusEnum.class).getMessage());
    }
}