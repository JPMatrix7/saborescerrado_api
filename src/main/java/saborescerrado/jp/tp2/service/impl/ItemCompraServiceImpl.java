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
import saborescerrado.jp.tp2.dto.ItemCompraDTO;
import saborescerrado.jp.tp2.dto.ItemCompraResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.ItemCompra;
import saborescerrado.jp.tp2.model.Licor;
import saborescerrado.jp.tp2.repository.ItemCompraRepository;
import saborescerrado.jp.tp2.repository.LicorRepository;
import saborescerrado.jp.tp2.service.ItemCompraService;
import saborescerrado.jp.tp2.service.LicorService;

@ApplicationScoped
public class ItemCompraServiceImpl implements ItemCompraService {

    public static final Logger LOG = Logger.getLogger(ItemCompraServiceImpl.class);

    @Inject
    ItemCompraRepository repository;

    @Inject
    LicorRepository licorRepository;

    @Inject
    LicorService licorService;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição ItemCompra.getAll()");
            List<ItemCompraResponseDTO> itens = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(itens).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição ItemCompra.getAllAdmin()");
            List<ItemCompraResponseDTO> itens = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(itens).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição ItemCompra.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição ItemCompra.getId()");
            ItemCompra itemCompra = repository.findById(id);
            if (itemCompra == null || !itemCompra.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(itemCompra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição ItemCompra.getAdminId()");
            ItemCompra itemCompra = repository.findById(id);
            if (itemCompra == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(itemCompra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(ItemCompraDTO itemCompraDTO) {
        try {
            LOG.info("Requisição ItemCompra.insert()");
            
            Licor licor = licorRepository.findById(itemCompraDTO.licorId());
            if (licor == null)
                return Response.status(Status.NOT_FOUND).entity("Licor não encontrado").build();
            
            ItemCompra itemCompra = new ItemCompra();
            itemCompra.setQuantidade(itemCompraDTO.quantidade());
            itemCompra.setPrecoUnitario(itemCompraDTO.precoUnitario());
            itemCompra.setLicor(licor);
            
            repository.persist(itemCompra);
            return Response.status(Status.CREATED).entity(toResponseDTO(itemCompra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, ItemCompraDTO itemCompraDTO) {
        try {
            LOG.info("Requisição ItemCompra.update()");
            ItemCompra itemCompra = repository.findById(id);
            if (itemCompra == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (itemCompraDTO.quantidade() != null)
                itemCompra.setQuantidade(itemCompraDTO.quantidade());
            if (itemCompraDTO.precoUnitario() != null)
                itemCompra.setPrecoUnitario(itemCompraDTO.precoUnitario());
            
            if (itemCompraDTO.licorId() != null) {
                Licor licor = licorRepository.findById(itemCompraDTO.licorId());
                if (licor != null)
                    itemCompra.setLicor(licor);
            }
            
            return Response.ok(toResponseDTO(itemCompra)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição ItemCompra.delete()");
            ItemCompra itemCompra = repository.findById(id);
            if (itemCompra == null)
                return Response.status(Status.NOT_FOUND).build();
            itemCompra.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ItemCompra.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private ItemCompraResponseDTO toResponseDTO(ItemCompra itemCompra) {
        // Para evitar dependência circular, retornamos null para o licor por enquanto
        // O licor completo será carregado quando necessário através do LicorService
        return new ItemCompraResponseDTO(
            itemCompra.getId(),
            itemCompra.getQuantidade(),
            itemCompra.getPrecoUnitario(),
            null  // LicorResponseDTO será carregado quando necessário
        );
    }
}
