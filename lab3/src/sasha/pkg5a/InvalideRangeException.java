/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sasha.pkg5a;

public class InvalideRangeException extends Exception {
    private double invalidValue;
    
    public InvalideRangeException(String message, double invalidValue) {
        super(message);
        this.invalidValue = invalidValue;
    }
    
    public double getInvalidValue() {
        return invalidValue;
    }
}
