package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.ParceiroComercial;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class ParceiroComercialRepository implements PanacheRepository<ParceiroComercial> {

    public ParceiroComercial findByCnpj(String cnpj) {
        if (cnpj == null)
            return null;
        return find("cnpj = ?1", cnpj).firstResult();
    }

    public List<ParceiroComercial> findByRazaoSocial(String razaoSocial) {
        if (razaoSocial == null)
            return null;
        return find("UPPER(razaoSocial) LIKE ?1", "%" + razaoSocial.toUpperCase() + "%").list();
    }

    public List<ParceiroComercial> findByNomeFantasia(String nomeFantasia) {
        if (nomeFantasia == null)
            return null;
        return find("UPPER(nomeFantasia) LIKE ?1", "%" + nomeFantasia.toUpperCase() + "%").list();
    }
}
