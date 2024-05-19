/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.modelos;

/**
 *
 * @author ALVARO
 */
public class DetalleConsulta {
    private int id;
    private int idMedCon;
    private String diagnostic;
    private String treatment;

    public DetalleConsulta() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMedCon() {
        return idMedCon;
    }

    public void setIdMedCon(int idMedCon) {
        this.idMedCon = idMedCon;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    
}
