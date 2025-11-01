package saborescerrado.jp.tp2.model;

import jakarta.persistence.Entity;

@Entity
public class Sabor extends EntityClass {

    private String nome;

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
