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
import saborescerrado.jp.tp2.dto.EstadoDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Estado;
import saborescerrado.jp.tp2.repository.EstadoRepository;
import saborescerrado.jp.tp2.service.EstadoService;

@ApplicationScoped
public class EstadoServiceImpl implements EstadoService {

    public static final Logger LOG = Logger.getLogger(EstadoServiceImpl.class);

    @Inject
    EstadoRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Estado.getAll()");
            List<EstadoResponseDTO> estados = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(estados).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Estado.getAllAdmin()");
            List<EstadoResponseDTO> estados = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(estados).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Estado.count()");
            return repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Estado.getId()");
            Estado estado = repository.findById(id);
            if (estado == null || !estado.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(estado)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Estado.getAdminId()");
            Estado estado = repository.findById(id);
            if (estado == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(estado)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Estado.getNome()");
            List<EstadoResponseDTO> estados = repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(estados).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getNome()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getSigla(String sigla) {
        try {
            LOG.info("Requisição Estado.getSigla()");
            Estado estado = repository.findBySigla(sigla);
            if (estado == null || !estado.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(estado)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.getSigla()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(EstadoDTO estadoDTO) {
        try {
            LOG.info("Requisição Estado.insert()");
            Estado estado = new Estado();
            estado.setNome(estadoDTO.nome());
            estado.setSigla(estadoDTO.sigla());
            repository.persist(estado);
            return Response.status(Status.CREATED).entity(toResponseDTO(estado)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, EstadoDTO estadoDTO) {
        try {
            LOG.info("Requisição Estado.update()");
            Estado estado = repository.findById(id);
            if (estado == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (estadoDTO.nome() != null && !estadoDTO.nome().isEmpty())
                estado.setNome(estadoDTO.nome());
            if (estadoDTO.sigla() != null && !estadoDTO.sigla().isEmpty())
                estado.setSigla(estadoDTO.sigla());
            
            return Response.ok(toResponseDTO(estado)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Estado.delete()");
            Estado estado = repository.findById(id);
            if (estado == null)
                return Response.status(Status.NOT_FOUND).build();
            estado.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Estado.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private EstadoResponseDTO toResponseDTO(Estado estado) {
        return new EstadoResponseDTO(
            estado.getId(),
            estado.getNome(),
            estado.getSigla()
        );
    }
}
