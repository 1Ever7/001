package com.emergentes.modelos;

import java.sql.Date;

public class Consulta {

    private int id;
   private int medicalConsultaId;
    private int detalleConsultaId;
    private String nombrePaciente;
    private String apellidoPaciente;
    private Date fechaConsulta;
    private String diagnostico;
    private String tratamiento;

    

    private String cel;
    private String gptcon;
 private int idoc;
 private String espe;
 
    public Consulta() {
    }

    public Consulta(int id, int medicalConsultaId, int detalleConsultaId, String nombrePaciente, String apellidoPaciente, Date fechaConsulta, String diagnostico, String tratamiento, String cel, String gptcon, int idoc, String espe) {
        this.id = id;
        this.medicalConsultaId = medicalConsultaId;
        this.detalleConsultaId = detalleConsultaId;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
        this.fechaConsulta = fechaConsulta;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
        this.cel = cel;
        this.gptcon = gptcon;
        this.idoc = idoc;
        this.espe= espe;
    }

    public String getEspe() {
        return espe;
    }

    public void setEspe(String espe) {
        this.espe = espe;
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

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }

    public Date getFechaConsulta() {
        return fechaConsulta;
    }

    public void setFechaConsulta(Date fechaConsulta) {
        this.fechaConsulta = fechaConsulta;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getGptcon() {
        return gptcon;
    }

    public void setGptcon(String gptcon) {
        this.gptcon = gptcon;
    }

    public int getIdoc() {
        return idoc;
    }

    public void setIdoc(int idoc) {
        this.idoc = idoc;
    }

}
