package com.yupi.springbootinit.common;

/**
 * 自定义错误码
 *
 * @author <a href="https://github.com/liyupi">程序员鱼皮</a>
 * @from <a href="https://yupi.icu">编程导航知识星球</a>
 */
public enum ErrorCode {
    //定义枚举常量，用构造方法初始化枚举常量的成员
    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    //定义成员变量
    /**
     * 状态码
     */
    private final int code;

    /**
     * 信息
     */
    private final String message;

    //构造方法，用于创建枚举实例
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 用于获取枚举常量的code和message的成员
     * @return
     */
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
