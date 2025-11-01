package saborescerrado.jp.tp2.model;

import jakarta.persistence.Entity;

@Entity
public class Categoria extends EntityClass {

    private String nome;
    
    private String descricao;

    // Getters and Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
