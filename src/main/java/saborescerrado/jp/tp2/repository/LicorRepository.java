package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Licor;
import saborescerrado.jp.tp2.model.TipoLicor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class LicorRepository implements PanacheRepository<Licor> {

    public List<Licor> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }

    public List<Licor> findByVisivel(Boolean visivel) {
        if (visivel == null)
            return null;
        return find("visivel = ?1", visivel).list();
    }

    public List<Licor> findByTipo(TipoLicor tipo) {
        if (tipo == null)
            return null;
        return find("tipo = ?1", tipo).list();
    }

    public List<Licor> findByPrecoRange(Double precoMin, Double precoMax) {
        if (precoMin == null || precoMax == null)
            return null;
        return find("preco >= ?1 AND preco <= ?2", precoMin, precoMax).list();
    }

    public List<Licor> findByEstoqueDisponivel() {
        return find("estoque > 0").list();
    }
}
