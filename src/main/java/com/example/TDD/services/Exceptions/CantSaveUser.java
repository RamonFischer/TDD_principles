package com.example.TDD.services.Exceptions;

public class CantSaveUser extends RuntimeException{
    public CantSaveUser(String message) {
        super(message);
    }
}
