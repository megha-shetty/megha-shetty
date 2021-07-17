package org.example.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BusinessException extends RuntimeException {

    Integer status;

    int code;

    String link;

    String developerMessage;

    public BusinessException(int status, int code, String message, String developerMessage, String link) {
        super(message);
        this.status = status;
        this.code = code;
        this.developerMessage = developerMessage;
        this.link = link;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
        this.developerMessage = message;
    }

}
