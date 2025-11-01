package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Ingrediente;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class IngredienteRepository implements PanacheRepository<Ingrediente> {

    public List<Ingrediente> findByNome(String nome) {
        if (nome == null)
            return null;
        return find("UPPER(nome) LIKE ?1", "%" + nome.toUpperCase() + "%").list();
    }
}
