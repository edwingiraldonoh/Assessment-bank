package com.bank.management.exceptions;

import lombok.Getter;

@Getter
public class DataNotFountException extends RuntimeException {
    //Users with the id %d not found
    private final Long id;
    private final String entity;

    public DataNotFountException(Long id, String entity) {
        super(String.format("%s with id %d not found", entity,id));
        this.id = id;
        this.entity = entity;
    }
}
