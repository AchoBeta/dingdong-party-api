package com.dingdong.party.serviceBase.exception;

import com.dingdong.party.serviceBase.common.api.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author retraci
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class PartyException extends RuntimeException {

    private Integer code;

    public PartyException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public PartyException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
    }

    @Override
    public String toString() {
        return "DingDong-Party-Exception{" +
                "code=" + code +
                ", message=" + this.getMessage() +
                '}';
    }
}
