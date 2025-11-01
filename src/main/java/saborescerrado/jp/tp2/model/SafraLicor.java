package saborescerrado.jp.tp2.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SafraLicor extends EntityClass {

    private LocalDate anoSafra;
    
    private String fazenda;
    
    private String qualidade;

    @ManyToOne
    @JoinColumn(name = "cidade")
    private Cidade cidade;

    // Getters and Setters
    public LocalDate getAnoSafra() {
        return anoSafra;
    }

    public void setAnoSafra(LocalDate anoSafra) {
        this.anoSafra = anoSafra;
    }

    public String getFazenda() {
        return fazenda;
    }

    public void setFazenda(String fazenda) {
        this.fazenda = fazenda;
    }

    public String getQualidade() {
        return qualidade;
    }

    public void setQualidade(String qualidade) {
        this.qualidade = qualidade;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }
}
