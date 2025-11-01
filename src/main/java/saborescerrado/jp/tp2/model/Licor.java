package saborescerrado.jp.tp2.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Licor extends EntityClass {

    private String nome;

    @Size(max = 3000)
    private String descricao;

    private Double preco;

    private Integer estoque;

    @CollectionTable
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "url_imagens_licor")
    private List<String> imagens;

    @OneToMany
    @JoinColumn(name = "lista_avaliacao")
    private List<Avaliacao> avaliacoes; 

    @ManyToMany
    @JoinTable(
        name = "licor_categoria",
        joinColumns = @JoinColumn(name = "licor_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id")
    )
    private List<Categoria> categorias;

    private Double estrelas;

    private Boolean visivel;

    private Double teorAlcoolico;

    @ManyToOne
    @JoinColumn(name = "safra")
    private SafraLicor safra;

    @ManyToMany
    @JoinTable(
        name = "licor_premiacao",
        joinColumns = @JoinColumn(name = "licor_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "premiacao_id", referencedColumnName = "id")
    )
    private List<Premiacao> premiacoes;

    @ManyToOne
    @JoinColumn(name = "sabor")
    private Sabor sabor;

    @ManyToMany
    @JoinTable(
        name = "licor_ingrediente",
        joinColumns = @JoinColumn(name = "licor_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id", referencedColumnName = "id")
    )
    private List<Ingrediente> ingredientes;

    @ManyToOne
    @JoinColumn(name = "embalagem")
    private Embalagem embalagem;

    @Enumerated(EnumType.ORDINAL)
    private TipoLicor tipo;

    @ManyToOne
    @JoinColumn(name = "parceiro_comercial")
    private ParceiroComercial parceiroComercial;

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

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Integer getEstoque() {
        return estoque;
    }

    public void setEstoque(Integer estoque) {
        this.estoque = estoque;
    }

    public List<String> getImagens() {
        return imagens;
    }

    public void setImagens(List<String> imagens) {
        this.imagens = imagens;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Double getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(Double estrelas) {
        this.estrelas = estrelas;
    }

    public Boolean getVisivel() {
        return visivel;
    }

    public void setVisivel(Boolean visivel) {
        this.visivel = visivel;
    }

    public Double getTeorAlcoolico() {
        return teorAlcoolico;
    }

    public void setTeorAlcoolico(Double teorAlcoolico) {
        this.teorAlcoolico = teorAlcoolico;
    }

    public SafraLicor getSafra() {
        return safra;
    }

    public void setSafra(SafraLicor safra) {
        this.safra = safra;
    }

    public List<Premiacao> getPremiacoes() {
        return premiacoes;
    }

    public void setPremiacoes(List<Premiacao> premiacoes) {
        this.premiacoes = premiacoes;
    }

    public Sabor getSabor() {
        return sabor;
    }

    public void setSabor(Sabor sabor) {
        this.sabor = sabor;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public Embalagem getEmbalagem() {
        return embalagem;
    }

    public void setEmbalagem(Embalagem embalagem) {
        this.embalagem = embalagem;
    }

    public TipoLicor getTipo() {
        return tipo;
    }

    public void setTipo(TipoLicor tipo) {
        this.tipo = tipo;
    }

    public ParceiroComercial getParceiroComercial() {
        return parceiroComercial;
    }

    public void setParceiroComercial(ParceiroComercial parceiroComercial) {
        this.parceiroComercial = parceiroComercial;
    }
}
