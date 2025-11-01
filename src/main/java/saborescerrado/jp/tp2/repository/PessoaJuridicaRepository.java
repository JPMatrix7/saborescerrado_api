package saborescerrado.jp.tp2.repository;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.PessoaJuridica;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class PessoaJuridicaRepository implements PanacheRepository<PessoaJuridica> {

    public PessoaJuridica findByCnpj(String cnpj) {
        if (cnpj == null)
            return null;
        return find("cnpj = ?1", cnpj).firstResult();
    }

    public PessoaJuridica findByRazaoSocial(String razaoSocial) {
        if (razaoSocial == null)
            return null;
        return find("UPPER(razaoSocial) LIKE ?1", "%" + razaoSocial.toUpperCase() + "%").firstResult();
    }
}
