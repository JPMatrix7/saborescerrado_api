package saborescerrado.jp.tp2.repository;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Telefone;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class TelefoneRepository implements PanacheRepository<Telefone> {

}
