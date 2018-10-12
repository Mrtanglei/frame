package domain.user;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanglei
 * @date 18/10/12
 */
@Getter
@Setter
public class UserAccountBean {

    /**
     * 登录的账号
     */
    private String account;

    /**
     * 用于维持会话的token
     */
    private String token;
}
