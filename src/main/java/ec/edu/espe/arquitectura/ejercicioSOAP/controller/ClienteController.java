/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ec.edu.espe.arquitectura.ejercicioSOAP.controller;

import ec.edu.espe.arquitectura.ejercicioSOAP.exception.EntityExistException;
import ec.edu.espe.arquitectura.ejercicioSOAP.exception.EntityNotFoundException;
import ec.edu.espe.arquitectura.ejercicioSOAP.service.ClienteService;
import lombok.extern.slf4j.Slf4j;
import op.frexofa.cliente.ActualizarClienteRequest;
import op.frexofa.cliente.ActualizarClienteResponse;
import op.frexofa.cliente.BuscarClientePorCedulaRequest;
import op.frexofa.cliente.BuscarClientePorCedulaResponse;
import op.frexofa.cliente.ClienteActualizarRQ;
import op.frexofa.cliente.ClienteRQ;
import op.frexofa.cliente.CrearClienteRequest;
import op.frexofa.cliente.CrearClienteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 *
 * @author bran-
 */
@Endpoint
@Slf4j
public class ClienteController {

    private static final String NAMESPACE_URI = "http://frexofa.op/cliente";

    @Autowired
    private ClienteService clienteService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "crearClienteRequest")
    @ResponsePayload
    public CrearClienteResponse crearCliente(@RequestPayload CrearClienteRequest request) {
        log.info("Crear informacion recbida: {}",request.getClienteRQ());
        CrearClienteResponse response = new CrearClienteResponse();
        try {
            ClienteRQ clienteRQ = new ClienteRQ();
            clienteRQ.setCedula(request.getClienteRQ().getCedula());
            clienteRQ.setNombre(request.getClienteRQ().getNombre());
            clienteRQ.setApellido(request.getClienteRQ().getApellido());
            clienteRQ.setCorreo(request.getClienteRQ().getCorreo());
            clienteRQ.setDireccion(request.getClienteRQ().getDireccion());
                    /*ClienteRQ.builder()
                    .cedula(request.getClienteRQ().getCedula())
                    .apellido(request.getClienteRQ().getApellido())
                    .direccion(request.getClienteRQ().getDireccion())
                    .correo(request.getClienteRQ().getCorreo())
                    .nombre(request.getClienteRQ().getNombre())
                    .build();*/
            this.clienteService.crearCliente(clienteRQ);
            response.setStatus("Cliente creado exitosamente");
            return response;
        } catch (EntityNotFoundException | EntityExistException e) {
            log.error(e.getMessage());
            response.setStatus(e.getMessage());
            return response;
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus("A ocurrido un error durante el proceso");
            return response;
        }
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "actualizarClienteRequest")
    @ResponsePayload
    public ActualizarClienteResponse actualizarCliente(@RequestPayload ActualizarClienteRequest request) {
        log.info("Actualizar iformacion recbida: {}, {}",request.getCedula(),request.getClienteRQ());
        ActualizarClienteResponse response = new ActualizarClienteResponse();
        try {
            ClienteActualizarRQ actualizarRQ = new ClienteActualizarRQ();
            actualizarRQ.setNombre(request.getClienteRQ().getNombre());
            actualizarRQ.setApellido(request.getClienteRQ().getApellido());
            actualizarRQ.setDireccion(request.getClienteRQ().getDireccion());
                    
                    /*ClienteActualizarRQ.builder()
                    .nombre(request.getClienteRQ().getNombre())
                    .apellido(request.getClienteRQ().getApellido())
                    .direccion(request.getClienteRQ().getDireccion())
                    .build();*/
            this.clienteService.actualizarCliente(request.getCedula(), actualizarRQ);
            response.setStatus("Actualización Exitosa");
            return response;
        } catch (EntityNotFoundException e) {
            log.error(e.getMessage());
            response.setStatus(e.getMessage());
            return response;
        }catch (Exception e) {
            log.error(e.getMessage());
            response.setStatus("A ocurrido un error durante el proceso");
            return response;
        }
    }
    
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "buscarClientePorCedulaRequest")
    @ResponsePayload
    public BuscarClientePorCedulaResponse buscarClientePorCedula(@RequestPayload BuscarClientePorCedulaRequest request) {
        log.info("Buscar informacion recbida: {}",request.getCedula());
        BuscarClientePorCedulaResponse response = new BuscarClientePorCedulaResponse();
        ClienteRQ cliente = this.clienteService.buscarClientePorCedula(request.getCedula());
        response.setStatus(cliente);
        return response;
    }

}
