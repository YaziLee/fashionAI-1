package ices.fashion;

import ices.fashion.service.VtoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.UnsupportedEncodingException;

@SpringBootTest
class FashionApplicationTests {

    @Autowired
    private VtoService vtoService;

    @Test
    void contextLoads() throws UnsupportedEncodingException {
        vtoService.virtualTryOn();
    }

}
