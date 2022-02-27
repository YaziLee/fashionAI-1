package ices.fashion;

import ices.fashion.service.*;
import ices.fashion.service.dto.MMCGANCriteria;
import ices.fashion.service.dto.OutfitGANCriteria;
import ices.fashion.service.dto.RenderGANCriteria;
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
    private OutfitGANService outfitGANService;

    @Autowired
    private RenderGANService renderGANService;

    @Autowired
    private VtoService vtoService;

    @Autowired
    private UploadTokenService uploadTokenService;

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

    @Test
    void testOutfitGAN() throws IOException {
        OutfitGANCriteria outfitGANCriteria = new OutfitGANCriteria();
        outfitGANCriteria.setUpperFileName("001aeb1dc1adbcb6a36060961f92843e.jpg");
        outfitGANCriteria.setShoesFileName("001aeb1dc1adbcb6a36060961f92843e_shoes.jpg");
//        outfitGANCriteria.setLowerFileName("001aeb1dc1adbcb6a36060961f92843e_lower.jpg");
        outfitGANCriteria.setBagFileName("001aeb1dc1adbcb6a36060961f92843e_bag.jpg");
        outfitGANService.doOutfitGAN(outfitGANCriteria);
    }

    @Test
    void testRender() throws IOException {
        RenderGANCriteria renderGANCriteria = new RenderGANCriteria();
        renderGANCriteria.setColorFileName("render_1.jpg");
        renderGANCriteria.setSketchFileName("000000221f00000b30e99368f4b72eed.jpg");
        renderGANService.doRenderGenerate(renderGANCriteria);
    }

    @Test
    void testUploadToken() {
        String token = uploadTokenService.getUploadToken();
        System.out.println(token);
    }

}
