package com.metric.eticaret.authentication.config;

import com.metric.eticaret.authentication.model.HttpResponse;
import com.metric.eticaret.user.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

@Service
public class HttpResponseService {

    public ResponseEntity<HttpResponse> response(Object object, String message, HttpStatus httpStatus) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),
                                                    httpStatus,
                                                    httpStatus.getReasonPhrase().toUpperCase(Locale.ROOT),
                                                    message.toUpperCase(),
                                                    Collections.singletonList(object)),httpStatus);
    }
}
