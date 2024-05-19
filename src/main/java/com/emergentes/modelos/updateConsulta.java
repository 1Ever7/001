/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.emergentes.modelos;

import java.sql.Date;

public class updateConsulta {

    private int id;
    private int medicalConsultaId;
    private int detalleConsultaId;

    public updateConsulta() {
    }

    public updateConsulta(int id, int medicalConsultaId, int detalleConsultaId) {
        this.id = id;
        this.medicalConsultaId = medicalConsultaId;
        this.detalleConsultaId = detalleConsultaId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMedicalConsultaId() {
        return medicalConsultaId;
    }

    public void setMedicalConsultaId(int medicalConsultaId) {
        this.medicalConsultaId = medicalConsultaId;
    }

    public int getDetalleConsultaId() {
        return detalleConsultaId;
    }

    public void setDetalleConsultaId(int detalleConsultaId) {
        this.detalleConsultaId = detalleConsultaId;
    }
    
}
