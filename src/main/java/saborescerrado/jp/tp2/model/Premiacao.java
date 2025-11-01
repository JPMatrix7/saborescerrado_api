package saborescerrado.jp.tp2.model;

import jakarta.persistence.Entity;

@Entity
public class Premiacao extends EntityClass {

    private String evento;
    
    private Integer ano;
    
    private String medalha;

    // Getters and Setters
    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getMedalha() {
        return medalha;
    }

    public void setMedalha(String medalha) {
        this.medalha = medalha;
    }
}
