package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Sabor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SaborRepository implements PanacheRepository<Sabor> {

    public List<Sabor> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
