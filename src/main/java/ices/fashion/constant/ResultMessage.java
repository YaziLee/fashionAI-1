package ices.fashion.constant;

public enum ResultMessage {
    /**
     * 操作成功
     */
    RESULT_SUCCESS_1(1, "操作成功"),
    /**
     * 操作失败
     */
    RESULT_ERROR_0(0, "操作失败"),
    /**
     * 操作成功
     */
    RESULT_ERROR_STATUS_1(1),
    /**
     * 操作失败
     */
    RESULT_ERROR_STATUS_0(0),
    /**
     * 重新登录
     */
    LOGOUT_LOGIN(-1, "重新登录"),
    /**
     * 用户不存在
     */
    ACCOUNT_NOT_FOUND_1000(1000, "用户不存在"),
    /**
     * 账户已被锁定
     */
    DISABLEDA_CCOUNT_1001(1001, "账户已被锁定"),
    /**
     * 密码错误
     */
    INCORRECT_CREDENTIALS_1002(1002, "密码错误"),
    /**
     * 没有对应的权限，请联系管理员!
     */
    NO_PERMISSION_1003(1003, "没有对应的权限，请联系管理员!"),
    /**
     * 用户名已存在
     */
    USER_NAME_EXISTS(1004, "用户名已存在"),
    /** 角色 *********************************************************************/
    /**
     * 该角色已存在
     */
    ROLE_NAME_EXISTS(2001, "该角色已存在"),
    /** 前端页面 *********************************************************************/
    /** 会员 ******************************************************************/
    /**
     * 会员不存在
     */
    API_CUSTOMER_NOT_EXISTS(3001, "账号不存在"),
    /**
     * 邮箱地址不能为空
     */
    API_EMAIL_IS_NULL(3002, "账号不能为空"),
    /**
     * 瞄瞄不能为空
     */
    API_PASSWORD_IS_NULL(3003, "密码不能为空"),
    /**
     * 用户名或密码错误
     */
    API_EMAIL_PWD_ERROR(3004, "用户名或密码错误"),
    /**
     * 两次密码不一致
     */
    API_CONFIRM_PWD_NOT_EQUAL(3005, "两次密码不一致"),
    /**
     * 该用户已存在
     */
    API_CUSTOMER_EXISTS(3006, "该用户已存在"),
    /**
     * 原密码错误
     */
    API_OLD_PASSWORD_ERROR(3007, "原密码错误"),
    /**
     * 验证码无效或已过期
     */
    API_AUTH_CODE_EXPIRE(3008, "验证码无效或已过期，请点击重新发送"),
    /**
     * 验证码错误
     */
    API_AUTH_CODE_ERROR(3009, "验证码错误"),
    /**
     * 套装信息不存在
     */
    SUIT_NOT_EXISTS(4001, "套装信息不存在"),
    /**
     * 评论不存在
     */
    SUIT_COMMENT_NOT_EXISTS(4101, "评论不存在"),
    /**
     * 用户已收藏过该作品
     */
    SUIT_RECORD_IS_COLLECTED(4102, "您已收藏过该作品"),
    /**
     * 前端最多可显示三条关于我们
     */
    ABOUT_US_EXCEED(5001, "前端最多可显示三条关于我们");


    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;

    ResultMessage(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultMessage(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
