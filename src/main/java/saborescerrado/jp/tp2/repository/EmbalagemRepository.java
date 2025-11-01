package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Embalagem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class EmbalagemRepository implements PanacheRepository<Embalagem> {

    public List<Embalagem> findByVolume(Integer volume) {
        if (volume == null)
            return null;
        return find("volume = ?1", volume).list();
    }

    public List<Embalagem> findByMaterial(String material) {
        if (material == null)
            return null;
        return find("UPPER(material) LIKE ?1", "%" + material.toUpperCase() + "%").list();
    }
}
