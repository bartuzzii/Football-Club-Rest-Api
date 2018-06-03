package org.bj.footballclubrestapi.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClubNotFoundException extends RuntimeException {
    public ClubNotFoundException(String s) {
        super(s);
    }
}
