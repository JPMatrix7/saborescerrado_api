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
import saborescerrado.jp.tp2.dto.SaborDTO;
import saborescerrado.jp.tp2.dto.SaborResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Sabor;
import saborescerrado.jp.tp2.repository.SaborRepository;
import saborescerrado.jp.tp2.service.SaborService;

@ApplicationScoped
public class SaborServiceImpl implements SaborService {

    public static final Logger LOG = Logger.getLogger(SaborServiceImpl.class);

    @Inject
    SaborRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Sabor.getAll()");
            List<SaborResponseDTO> sabores = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(sabores).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Sabor.getAllAdmin()");
            List<SaborResponseDTO> sabores = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(sabores).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Sabor.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Sabor.getId()");
            Sabor sabor = repository.findById(id);
            if (sabor == null || !sabor.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(sabor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Sabor.getAdminId()");
            Sabor sabor = repository.findById(id);
            if (sabor == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(sabor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Sabor.getNome()");
            List<SaborResponseDTO> sabores = repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(sabores).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.getNome()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(SaborDTO saborDTO) {
        try {
            LOG.info("Requisição Sabor.insert()");
            Sabor sabor = new Sabor();
            sabor.setNome(saborDTO.nome());
            repository.persist(sabor);
            return Response.status(Status.CREATED).entity(toResponseDTO(sabor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, SaborDTO saborDTO) {
        try {
            LOG.info("Requisição Sabor.update()");
            Sabor sabor = repository.findById(id);
            if (sabor == null)
                return Response.status(Status.NOT_FOUND).build();
            if (saborDTO.nome() != null && !saborDTO.nome().isEmpty())
                sabor.setNome(saborDTO.nome());
            return Response.ok(toResponseDTO(sabor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Sabor.delete()");
            Sabor sabor = repository.findById(id);
            if (sabor == null)
                return Response.status(Status.NOT_FOUND).build();
            sabor.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Sabor.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private SaborResponseDTO toResponseDTO(Sabor sabor) {
        return new SaborResponseDTO(
            sabor.getId(),
            sabor.getNome()
        );
    }
}
