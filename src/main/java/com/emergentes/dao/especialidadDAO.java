package com.emergentes.dao;

import com.emergentes.modelos.Especialidad;
import java.util.List;

public interface especialidadDAO {

    public void insert(Especialidad especie) throws Exception;

    public void update(Especialidad especie) throws Exception;

    public void delete(int id) throws Exception;

    public Especialidad getById(int id) throws Exception;

    public List<Especialidad> getAll() throws Exception;
}
