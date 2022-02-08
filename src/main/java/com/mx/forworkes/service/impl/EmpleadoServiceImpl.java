package com.mx.forworkes.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.mx.forworkes.dto.empleadoDto;
import com.mx.forworkes.entity.EmpleadoEntity;
import com.mx.forworkes.exception.ExceptionGlobal;
import com.mx.forworkes.repository.IEmpleadoRepository;
import com.mx.forworkes.service.IEmpleadoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author IgnacioCisnerosJuare
 *
 */
@Service
public class EmpleadoServiceImpl implements IEmpleadoService {

    private static final Logger LOGGER = LogManager.getLogger(EmpleadoServiceImpl.class);
    @Autowired
    private IEmpleadoRepository empleadoRepository;

    @Override
    public List<empleadoDto> obtenerEmpleados() throws ExceptionGlobal {
        LOGGER.info("::OBTENIENDO EMPLEADOS DE LA BASE DE DATOS::");
        try {
            List<empleadoDto> listaEmpleados = new ArrayList<>();
            List<EmpleadoEntity> empleados = empleadoRepository.findAll();
            for (EmpleadoEntity i : empleados) {
                empleadoDto empl = new empleadoDto();
                empl.setEmpleadoId(i.getEmpleadoId());
                empl.setNombre(i.getNombre());
                empl.setApellidoPaterno(i.getApellidoPaterno());
                empl.setApellidoMaterno(i.getApellidoMaterno());
                empl.setFechaNacimiento(i.getFechaNacimiento());
                empl.setFechaIngreso(i.getFechaIngreso());
                empl.setDomicilio(i.getDomicilio());
                empl.setCorreo(i.getCorreo());
                empl.setSueldoMensual(i.getSueldoMensual());
                listaEmpleados.add(empl);
                return listaEmpleados;
            }
        } catch (Exception e) {
            LOGGER.info("Ocurrio un error al obtener los empleado ===> " + e.getMessage());
            throw new ExceptionGlobal("Ocurrio un error al obtener los empleado.", 500);
        }

        return null;
    }

}
