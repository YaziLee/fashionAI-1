package ices.fashion.constant;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public class ApiResult<T> {

    @ApiModelProperty(value = "编码", required = true)
    private Integer code;
    @ApiModelProperty("信息")
    private String message;
    @ApiModelProperty("数据")
    private T data;

    public ApiResult() {
    }

    public ApiResult(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResult(ResultMessage resultMessage) {
        this.code = resultMessage.getCode();
        this.message = resultMessage.getMessage();
    }

    public ApiResult(ResultMessage resultMessage, T data) {
        this.code = resultMessage.getCode();
        this.message = resultMessage.getMessage();
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}