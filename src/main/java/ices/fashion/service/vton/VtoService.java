package ices.fashion.service.vton;

import ices.fashion.constant.ApiResult;
import ices.fashion.service.vton.dto.VtoDto;
import ices.fashion.service.vton.dto.VtonInitDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VtoService {

    public ApiResult<VtoDto> virtualTryOn(String clothFileName, String modelFileName) throws IOException;

    // 上传衣服图像
    ApiResult uploadCloth(MultipartFile clothFile);

    // 上传人物图像
    ApiResult uploadImage(MultipartFile imageFile);

    // 进行衣服试穿
    String tryOn();

    public ApiResult<VtonInitDto> init();
}
