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
    private String idMedCon;
    private String diagnostic;
    private String treatment;
    private String date;

    public DetalleConsulta() {
    }

    public DetalleConsulta(int id, String idMedCon, String diagnostic, String treatment, String date) {
        this.id = id;
        this.idMedCon = idMedCon;
        this.diagnostic = diagnostic;
        this.treatment = treatment;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdMedCon() {
        return idMedCon;
    }

    public void setIdMedCon(String idMedCon) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
    
    
}
