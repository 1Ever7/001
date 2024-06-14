package com.emergentes.modelos;
public class Doctor {
     private int id;
    private int idEspecialidad;
    private String firstname;
    private String lastname;
    private String dni;
    private String codi;
    private String nombreEspecialidad;

    public Doctor() {
    }

    public Doctor(int id, int idEspecialidad, String firstname, String lastname, String dni, String codi, String nombreEspecialidad) {
        this.id = id;
        this.idEspecialidad = idEspecialidad;
        this.firstname = firstname;
        this.lastname = lastname;
        this.dni = dni;
        this.codi = codi;
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public String getNombreEspecialidad() {
        return nombreEspecialidad;
    }

    public void setNombreEspecialidad(String nombreEspecialidad) {
        this.nombreEspecialidad = nombreEspecialidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdEspecialidad() {
        return idEspecialidad;
    }

    public void setIdEspecialidad(int idEspecialidad) {
        this.idEspecialidad = idEspecialidad;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }
    
    
}
