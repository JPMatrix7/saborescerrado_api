package saborescerrado.jp.tp2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Avaliacao extends EntityClass {

    @Min(1)
    @Max(5)
    private Integer estrelas;
    
    private String comentario;

    @ManyToOne
    @JoinColumn(name = "usuario")
    private Usuario usuario;

    // Getters and Setters
    public Integer getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Integer estrelas) {
        this.estrelas = estrelas;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
