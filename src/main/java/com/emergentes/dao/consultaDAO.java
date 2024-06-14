package com.emergentes.dao;

import com.emergentes.modelos.Consulta;
import com.emergentes.modelos.Especialidad;
import com.emergentes.modelos.Paciente;
import com.emergentes.modelos.contarconsultas;

import java.sql.Date;
import java.util.List;

public interface consultaDAO {
    public Consulta getBynomape(int id,int medicalConsultaId,int detalleConsultaId,String firstname,String lastname) throws Exception;
    
    public void insert(Consulta detalles) throws Exception;

    public void update(Consulta detalles) throws Exception;

    public void delete(int medicalConsultaId,int detalleConsultaId) throws Exception;

    public List<Consulta> getByDate(Date fech) throws Exception;

    public List<Consulta> getAll() throws Exception;
    
    public List<Object> getAll1() throws Exception;
    
    public List<contarconsultas> getReporteConsultas() throws Exception ;
    
    public List<Paciente> getAllp()throws Exception;
    
}
