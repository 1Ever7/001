/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.dao;

import com.emergentes.modelos.login;
import java.util.List;

/**
 *
 * @author ALVARO
 */
public interface loginDAO {
        login getUser(String username, String password);
        
        public login  getById(int id) throws Exception;
         public void update(login log) throws Exception;
         public void delete(int id) throws Exception;
        login insert(login log) throws Exception;
        public List<login> getAll() throws Exception;
        
        
        
}
