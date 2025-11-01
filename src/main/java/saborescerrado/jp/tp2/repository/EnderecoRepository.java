package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Endereco;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EnderecoRepository implements PanacheRepository<Endereco> {

    public List<Endereco> findByCep(String cep) {
        if (cep == null)
            return null;
        return find("cep = ?1", cep).list();
    }

    public List<Endereco> findByBairro(String bairro) {
        if (bairro == null)
            return null;
        return find("UPPER(bairro) LIKE ?1", "%" + bairro.toUpperCase() + "%").list();
    }
}
