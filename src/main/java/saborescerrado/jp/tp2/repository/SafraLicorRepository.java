package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.SafraLicor;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class SafraLicorRepository implements PanacheRepository<SafraLicor> {

    public List<SafraLicor> findByFazenda(String fazenda) {
        if (fazenda == null)
            return null;
        return find("UPPER(fazenda) LIKE ?1", "%" + fazenda.toUpperCase() + "%").list();
    }

    public List<SafraLicor> findByQualidade(String qualidade) {
        if (qualidade == null)
            return null;
        return find("UPPER(qualidade) LIKE ?1", "%" + qualidade.toUpperCase() + "%").list();
    }
}
