package saborescerrado.jp.tp2.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import saborescerrado.jp.tp2.dto.CompraDTO;
import saborescerrado.jp.tp2.dto.CompraResponseDTO;
import saborescerrado.jp.tp2.model.Compra;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.StatusPedido;
import saborescerrado.jp.tp2.repository.CompraRepository;
import saborescerrado.jp.tp2.service.CompraService;

@ApplicationScoped
public class CompraServiceImpl implements CompraService {

    public static final Logger LOG = Logger.getLogger(CompraServiceImpl.class);

    @Inject
    CompraRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Compra.getAll()");
            List<CompraResponseDTO> compras = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(compras).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Compra.getAllAdmin()");
            List<CompraResponseDTO> compras = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(compras).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Compra.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Compra.getId()");
            Compra compra = repository.findById(id);
            if (compra == null || !compra.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(compra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Compra.getAdminId()");
            Compra compra = repository.findById(id);
            if (compra == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(compra).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getAdminId()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getStatus(String status) {
        try {
            LOG.info("Requisição Compra.getStatus()");
            return Response.ok(repository.findByStatus(StatusPedido.valueOf(status.toUpperCase())).stream()
                    .filter(EntityClass::getAtivo)
                    .collect(Collectors.toList())).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getStatus()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getCodigoRastreio(String codigoRastreio) {
        try {
            LOG.info("Requisição Compra.getCodigoRastreio()");
            Compra compra = repository.findByCodigoRastreio(codigoRastreio);
            if (compra == null || !compra.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(compra).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.getCodigoRastreio()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(CompraDTO compraDTO) {
        try {
            LOG.info("Requisição Compra.insert()");
            
            Compra compra = new Compra();
            compra.setDataCompra(compraDTO.dataCompra());
            compra.setStatus(compraDTO.status());
            compra.setFormaPagamento(compraDTO.formaPagamento());
            compra.setValorTotal(compraDTO.valorTotal());
            compra.setCodigoRastreio(compraDTO.codigoRastreio());
            compra.setDataPrevista(compraDTO.dataPrevista());
            compra.setDataEntrega(compraDTO.dataEntrega());
            compra.setPago(compraDTO.pago());
            
            repository.persist(compra);
            return Response.status(Status.CREATED).entity(compra).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.insert()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, CompraDTO compraDTO) {
        try {
            LOG.info("Requisição Compra.update()");
            Compra compra = repository.findById(id);
            if (compra == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (compraDTO.dataCompra() != null)
                compra.setDataCompra(compraDTO.dataCompra());
            if (compraDTO.status() != null)
                compra.setStatus(compraDTO.status());
            if (compraDTO.formaPagamento() != null)
                compra.setFormaPagamento(compraDTO.formaPagamento());
            if (compraDTO.valorTotal() != null)
                compra.setValorTotal(compraDTO.valorTotal());
            if (compraDTO.codigoRastreio() != null && !compraDTO.codigoRastreio().isEmpty())
                compra.setCodigoRastreio(compraDTO.codigoRastreio());
            if (compraDTO.dataPrevista() != null)
                compra.setDataPrevista(compraDTO.dataPrevista());
            if (compraDTO.dataEntrega() != null)
                compra.setDataEntrega(compraDTO.dataEntrega());
            if (compraDTO.pago() != null)
                compra.setPago(compraDTO.pago());
            
            return Response.ok(toResponseDTO(compra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Compra.delete()");
            Compra compra = repository.findById(id);
            if (compra == null)
                return Response.status(Status.NOT_FOUND).build();
            compra.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Compra.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private CompraResponseDTO toResponseDTO(Compra compra) {
        // Compra não tem relação direta com Usuario, retornando null
        // Para evitar dependências circulares complexas com ItemCompra
        return new CompraResponseDTO(
            compra.getId(),
            compra.getDataCompra(),
            compra.getStatus(),
            compra.getFormaPagamento(),
            compra.getValorTotal(),
            compra.getCodigoRastreio(),
            compra.getDataPrevista(),
            compra.getDataEntrega(),
            compra.getPago(),
            null, // usuario - não existe relação direta
            null  // itens - evita circular
        );
    }
}
