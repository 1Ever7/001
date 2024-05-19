package com.emergentes.dao;

import com.emergentes.modelos.Doctor;
import java.util.List;

public interface docDAO {
    public void insert(Doctor doctor) throws Exception;

    public void update(Doctor doctor) throws Exception;

    public void delete(int id) throws Exception;

    public Doctor  getById(int id) throws Exception;

    public List<Object> getAll() throws Exception;
    
}
