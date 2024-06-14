package com.emergentes.dao;

import com.emergentes.modelos.DetalleConsulta;
import com.emergentes.modelos.Paciente;
import java.util.List;

public interface pacienteDAO {
        public void insert(Paciente paciente) throws Exception;

    public void update(Paciente paciente) throws Exception;

    public void delete(int id) throws Exception;

    public Paciente getById(int id) throws Exception;

    public List<Paciente> getAll() throws Exception;
    
    public List<DetalleConsulta> getAnte(int id) throws Exception;
    
    

}
