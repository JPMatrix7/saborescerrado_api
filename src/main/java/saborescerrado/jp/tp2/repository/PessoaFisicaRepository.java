package saborescerrado.jp.tp2.repository;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.PessoaFisica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PessoaFisicaRepository implements PanacheRepository<PessoaFisica> {

    public PessoaFisica findByCpf(String cpf) {
        if (cpf == null)
            return null;
        return find("cpf = ?1", cpf).firstResult();
    }
}
