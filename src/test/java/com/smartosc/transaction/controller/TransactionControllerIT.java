package com.smartosc.transaction.controller;

import com.smartosc.transaction.base.TestBase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(TestBase.class)
public class TransactionControllerIT {

    @Test
    void contextLoads() {
        log.debug("test123");
    }
}
