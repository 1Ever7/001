/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.dao;


import com.emergentes.modelos.gptmod;
import java.util.List;

/**
 *
 * @author ALVARO
 */
public interface gptDAO {
    public gptmod getBynomape(int id) throws Exception;
    
    public void update(gptmod gptmod) throws Exception;
    
    public List<gptmod> getAll() throws Exception;
    
}
