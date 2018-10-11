package domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author tanglei
 * @date 18/10/11
 */
@Getter
@Setter
public class CommonResponse implements Serializable {

    private int retCode;
    private String retMsg;
    private Object result;

    public CommonResponse() {
        this((Object)null);
    }

    public CommonResponse(Object result) {
        this("SUCCESS", result);
    }

    public CommonResponse(int retCode, String retMsg) {
        this(retCode, retMsg, (Object)null);
    }

    public CommonResponse(String retMsg, Object result) {
        this(0, retMsg, result);
    }

    public CommonResponse(int retCode, String retMsg, Object result) {
        this.retCode = retCode;
        this.retMsg = retMsg;
        this.result = result;
    }

}
