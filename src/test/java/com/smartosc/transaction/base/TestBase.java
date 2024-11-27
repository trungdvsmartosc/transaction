package com.smartosc.transaction.base;

import com.smartosc.transaction.TransactionApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public abstract class TestBase {

    @BeforeEach
    void init() {
        System.out.println("Base setup for integration test");
    }

    protected void logTestInfo(String message) {
        System.out.println("[TEST INFO]: " + message);
    }
}
