/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ejercicioSOAP.dao;

import ec.edu.espe.arquitectura.ejercicioSOAP.model.Cliente;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 *
 * @author bran-
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{
    
    Optional<Cliente> findByCedula(String cedula);
    Optional<Cliente> findByCorreo(String correo);
    
}
