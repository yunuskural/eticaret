package com.metric.eticaret.authentication.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class HttpResponse {

   // @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy hh.mm.ss")
    private Long timestamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;
    private List<?> data = new ArrayList<>();//tip bağımsız her şey olabilir data : string,int vs

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message, List<?> data) {
        this.timestamp = new Date().getTime();
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
        this.data = data;
    }
}
