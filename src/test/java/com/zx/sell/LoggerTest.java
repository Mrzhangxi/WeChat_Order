package com.zx.sell;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Slf4j //lombok类提供的简化方法

public class LoggerTest {

    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
//        使用原生的slf4j的方法
        logger.debug("debug..........");
        logger.info("info...........");
        logger.error("error...........");

//        log.debug("debug..........");
//        log.info("info...........");
//        log.error("error...........");
    }
}
