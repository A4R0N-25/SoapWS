/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ejercicioSOAP.service;

import ec.edu.espe.arquitectura.ejercicioSOAP.dao.ClienteRepository;
import ec.edu.espe.arquitectura.ejercicioSOAP.exception.EntityExistException;
import ec.edu.espe.arquitectura.ejercicioSOAP.exception.EntityNotFoundException;
import ec.edu.espe.arquitectura.ejercicioSOAP.model.Cliente;
import java.util.Optional;
import op.frexofa.cliente.ClienteActualizarRQ;
import op.frexofa.cliente.ClienteRQ;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bran-
 */
@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void crearCliente(ClienteRQ clienteRQ) {

        if (!this.verificarCedula(clienteRQ.getCedula())) {
            throw new EntityNotFoundException("Cedula Invalida");
        }
        Optional<Cliente> clienteOptional = this.clienteRepository.findByCedula(clienteRQ.getCedula());
        if (clienteOptional.isPresent()) {
            throw new EntityExistException("Ya existe un cliente con esta cedula");
        }
        clienteOptional = this.clienteRepository.findByCorreo(clienteRQ.getCorreo());
        if (clienteOptional.isPresent()) {
            throw new EntityExistException("Ya existe un cliente con este correo");
        }
        Cliente cliente = new Cliente();
        cliente.setCedula(clienteRQ.getCedula());
        cliente.setNombre(clienteRQ.getNombre());
        cliente.setApellido(clienteRQ.getApellido());
        cliente.setCorreo(clienteRQ.getCorreo());
        cliente.setDireccion(clienteRQ.getDireccion());
        this.clienteRepository.save(cliente);

    }

    public void actualizarCliente(String cedula, ClienteActualizarRQ clienteActualizarRQ) {
        Optional<Cliente> clienteOptional = this.clienteRepository.findByCedula(cedula);
        if (!clienteOptional.isPresent()) {
            throw new EntityNotFoundException("No se a encontrado al cliente solicitado");
        }
        Cliente cliente = clienteOptional.get();

        cliente.setNombre(clienteActualizarRQ.getNombre());
        cliente.setApellido(clienteActualizarRQ.getApellido());
        cliente.setDireccion(clienteActualizarRQ.getDireccion());
        this.clienteRepository.save(cliente);
    }

    public ClienteRQ buscarClientePorCedula(String cedula) {
        Optional<Cliente> clienteOptional = this.clienteRepository.findByCedula(cedula);
        if (!clienteOptional.isPresent()) {
            throw new EntityNotFoundException("No se a encontrado al cliente solicitado");
        }
        ClienteRQ cliente = new ClienteRQ();
        cliente.setCedula(clienteOptional.get().getCedula());
        cliente.setNombre(clienteOptional.get().getNombre());
        cliente.setApellido(clienteOptional.get().getApellido());
        cliente.setCorreo(clienteOptional.get().getCorreo());
        cliente.setDireccion(clienteOptional.get().getDireccion() );
        /*ClienteRQ.builder()
                .cedula(clienteOptional.get().getCedula())
                .nombre(clienteOptional.get().getNombre())
                .apellido(clienteOptional.get().getApellido())
                .correo(clienteOptional.get().getCorreo())
                .direccion(clienteOptional.get().getDireccion())
                .build();*/
        return cliente;
    }

    private boolean verificarCedula(String document) {
        byte sum = 0;
        try {
            if (document.trim().length() != 10) {
                return false;
            }
            String[] data = document.split("");
            byte verifier = Byte.parseByte(data[0] + data[1]);
            if (verifier < 1 || verifier > 24) {
                return false;
            }
            byte[] digits = new byte[data.length];
            for (byte i = 0; i < digits.length; i++) {
                digits[i] = Byte.parseByte(data[i]);
            }
            if (digits[2] > 6) {
                return false;
            }
            for (byte i = 0; i < digits.length - 1; i++) {
                if (i % 2 == 0) {
                    verifier = (byte) (digits[i] * 2);
                    if (verifier > 9) {
                        verifier = (byte) (verifier - 9);
                    }
                } else {
                    verifier = (byte) (digits[i] * 1);
                }
                sum = (byte) (sum + verifier);
            }
            if ((sum - (sum % 10) + 10 - sum) == digits[9]) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
