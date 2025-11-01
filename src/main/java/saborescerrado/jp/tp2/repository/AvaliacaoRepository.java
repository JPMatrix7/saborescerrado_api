package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Avaliacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AvaliacaoRepository implements PanacheRepository<Avaliacao> {

    public List<Avaliacao> findByEstrelas(Integer estrelas) {
        if (estrelas == null)
            return null;
        return find("estrelas = ?1", estrelas).list();
    }

    public List<Avaliacao> findByUsuario(Long usuarioId) {
        if (usuarioId == null)
            return null;
        return find("usuario.id = ?1", usuarioId).list();
    }
}
