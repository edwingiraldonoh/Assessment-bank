package com.bank.management.exceptions;

import lombok.Getter;

@Getter
public class DuplicatedDataException extends RuntimeException {
    private final String entity;
    private final String dni;
    public DuplicatedDataException(String entity, String dni) {

        super(String.format("%s con DNI %s ya existe",entity, dni  ));
        this.entity = entity;
        this.dni = dni;
    }
}
