package saborescerrado.jp.tp2.model;

import jakarta.persistence.Entity;

@Entity
public class Telefone extends EntityClass {

    private String codigoArea;
    
    private String numero;

    // Getters and Setters
    public String getCodigoArea() {
        return codigoArea;
    }

    public void setCodigoArea(String codigoArea) {
        this.codigoArea = codigoArea;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
