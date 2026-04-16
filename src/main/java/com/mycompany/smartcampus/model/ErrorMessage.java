/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.smartcampus.model;

/**
 *
 * @author karandeep Singh Jalf
 */

public class ErrorMessage {
    private String message;
    private int code;
    private String documentation;

    public ErrorMessage(String message, int code, String documentation) {
        this.message = message;
        this.code = code;
        this.documentation = documentation;
    }

    public String getMessage() { return message; }
    public int getCode() { return code; }
    public String getDocumentation() { return documentation; }
}