package com.example.hr_project.exception;

public class InvalidCredentialsException extends Exception{


    public InvalidCredentialsException(String message){
        super(String.format(message));

    }
}
