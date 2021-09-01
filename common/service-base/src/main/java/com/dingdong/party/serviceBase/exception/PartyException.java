package com.dingdong.party.serviceBase.exception;

import com.dingdong.party.commonUtils.result.ResultCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
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
