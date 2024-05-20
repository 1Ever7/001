/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.modelos;

import java.sql.Date;

/**
 *
 * @author ALVARO
 */
public class contarconsultas {
    private Date fecha;
    private String especie;
    private int Total;

    public contarconsultas() {
    }

    public contarconsultas(Date fecha, String especie, int Total) {
        this.fecha = fecha;
        this.especie = especie;
        this.Total = Total;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int Total) {
        this.Total = Total;
    }

  
    
    
}
