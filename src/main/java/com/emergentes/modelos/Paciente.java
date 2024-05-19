
package com.emergentes.modelos;
public class Paciente {
  
    private int id;
    private String firstname;
    private String lastname;
    private String dni;
    private String numberClinicalHistory;

    public Paciente() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getNumberClinicalHistory() {
        return numberClinicalHistory;
    }

    public void setNumberClinicalHistory(String numberClinicalHistory) {
        this.numberClinicalHistory = numberClinicalHistory;
    }
    
    
}
