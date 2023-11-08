package com.devsuperior.dsmeta.dto;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ErrorValidation extends ErrorCustom {

    private List<ExtremeMessage> errors = new ArrayList<>();

    public ErrorValidation(Instant timestamp, Integer status, String error, String path) {
        super(timestamp, status, error, path);
    }

    public void addError(String exceptionName, String message) {
        errors.add(new ExtremeMessage() {
        });
    }
}

