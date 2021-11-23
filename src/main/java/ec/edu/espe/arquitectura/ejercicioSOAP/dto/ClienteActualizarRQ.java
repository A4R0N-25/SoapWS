/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ejercicioSOAP.dto;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author bran-
 */
@Builder
@Data
public class ClienteActualizarRQ {
    
    private String nombre;
    private String apellido;
    private String direccion;
    
}
