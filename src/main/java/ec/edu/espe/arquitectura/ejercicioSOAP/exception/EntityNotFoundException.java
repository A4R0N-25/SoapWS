/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ejercicioSOAP.exception;

/**
 *
 * @author bran-
 */
public class EntityNotFoundException extends RuntimeException{
    
    public EntityNotFoundException(String message) {
        super(message);
    }
    
}
