package saborescerrado.jp.tp2.repository;

import java.util.List;

import jakarta.enterprise.context.ApplicationScoped;

import saborescerrado.jp.tp2.model.Compra;
import saborescerrado.jp.tp2.model.StatusPedido;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class CompraRepository implements PanacheRepository<Compra> {

    public List<Compra> findByStatus(StatusPedido status) {
        if (status == null)
            return null;
        return find("status = ?1", status).list();
    }

    public Compra findByCodigoRastreio(String codigoRastreio) {
        if (codigoRastreio == null)
            return null;
        return find("codigoRastreio = ?1", codigoRastreio).firstResult();
    }

    public List<Compra> findByPago(Boolean pago) {
        if (pago == null)
            return null;
        return find("pago = ?1", pago).list();
    }
}
