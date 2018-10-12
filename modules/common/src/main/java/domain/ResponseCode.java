package domain;

/**
 * @author tanglei
 * @date 18/10/11
 */
public class ResponseCode {

    //无效的参数
    public static final int INVALID_PARAM = 600;

    //会话已过期
    public static final int EXPIRED_SESSION = 402;

    //身份验证失败
    public static final int AUTH_FAILURE = 401;

    //没有权限
    public static final int NO_PERMISSION = 403;

    //内部错误
    public static final int INTERNAL_ERROR = 500;
}
