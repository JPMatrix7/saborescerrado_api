package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Premiacao;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PremiacaoRepository implements PanacheRepository<Premiacao> {

    public List<Premiacao> findByEvento(String evento) {
        if (evento == null)
            return null;
        return find("UPPER(evento) LIKE ?1", "%" + evento.toUpperCase() + "%").list();
    }

    public List<Premiacao> findByAno(Integer ano) {
        if (ano == null)
            return null;
        return find("ano = ?1", ano).list();
    }

    public List<Premiacao> findByMedalha(String medalha) {
        if (medalha == null)
            return null;
        return find("UPPER(medalha) LIKE ?1", "%" + medalha.toUpperCase() + "%").list();
    }
}
