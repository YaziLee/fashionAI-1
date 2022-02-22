package ices.fashion;

import ices.fashion.service.MMCGANService;
import ices.fashion.service.VtoService;
import ices.fashion.service.dto.MMCGANCriteria;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@SpringBootTest
class FashionApplicationTests {

    @Autowired
    private MMCGANService mmcganService;

    @Autowired
    private VtoService vtoService;

    @Test
    void contextLoads() throws UnsupportedEncodingException {
//        vtoService.virtualTryOn();
    }

    @Test
    void testGson() throws IOException {
        MMCGANCriteria mmcganCriteria = new MMCGANCriteria();
        mmcganCriteria.setId(1);
        mmcganCriteria.setOriginalText("Womens Fashion Outerwear Jackets Balenciaga jackets Balenciaga Cropped " +
                "embellished woolblend tweed jacket");
        mmcganCriteria.setTargetCategory("Skirts");
        mmcganCriteria.setTargetText("Womens Fashion Skirts Mini Skirts Helmut Lang mini skirts Helmut Lang " +
                "Layered Skirt");
        mmcganCriteria.setFileName("e8fd2028d0462196fdd3fbc9b27bbbcc.jpg");
        mmcganService.doMMCGAN(mmcganCriteria);
    }

}
