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
import saborescerrado.jp.tp2.dto.IngredienteDTO;
import saborescerrado.jp.tp2.dto.IngredienteResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Ingrediente;
import saborescerrado.jp.tp2.repository.IngredienteRepository;
import saborescerrado.jp.tp2.service.IngredienteService;

@ApplicationScoped
public class IngredienteServiceImpl implements IngredienteService {

    public static final Logger LOG = Logger.getLogger(IngredienteServiceImpl.class);

    @Inject
    IngredienteRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Ingrediente.getAll()");
            List<IngredienteResponseDTO> ingredientes = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(ingredientes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Ingrediente.getAllAdmin()");
            List<IngredienteResponseDTO> ingredientes = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(ingredientes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Ingrediente.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Ingrediente.getId()");
            Ingrediente ingrediente = repository.findById(id);
            if (ingrediente == null || !ingrediente.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(ingrediente)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Ingrediente.getAdminId()");
            Ingrediente ingrediente = repository.findById(id);
            if (ingrediente == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(ingrediente)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Ingrediente.getNome()");
            List<IngredienteResponseDTO> ingredientes = repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(ingredientes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.getNome()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(IngredienteDTO ingredienteDTO) {
        try {
            LOG.info("Requisição Ingrediente.insert()");
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(ingredienteDTO.nome());
            ingrediente.setQuantidade(ingredienteDTO.quantidade());
            ingrediente.setUnidadeMedida(ingredienteDTO.unidadeMedida());
            repository.persist(ingrediente);
            return Response.status(Status.CREATED).entity(toResponseDTO(ingrediente)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, IngredienteDTO ingredienteDTO) {
        try {
            LOG.info("Requisição Ingrediente.update()");
            Ingrediente ingrediente = repository.findById(id);
            if (ingrediente == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (ingredienteDTO.nome() != null && !ingredienteDTO.nome().isEmpty())
                ingrediente.setNome(ingredienteDTO.nome());
            if (ingredienteDTO.quantidade() != null)
                ingrediente.setQuantidade(ingredienteDTO.quantidade());
            if (ingredienteDTO.unidadeMedida() != null && !ingredienteDTO.unidadeMedida().isEmpty())
                ingrediente.setUnidadeMedida(ingredienteDTO.unidadeMedida());
            
            return Response.ok(toResponseDTO(ingrediente)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Ingrediente.delete()");
            Ingrediente ingrediente = repository.findById(id);
            if (ingrediente == null)
                return Response.status(Status.NOT_FOUND).build();
            ingrediente.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Ingrediente.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private IngredienteResponseDTO toResponseDTO(Ingrediente ingrediente) {
        return new IngredienteResponseDTO(
            ingrediente.getId(),
            ingrediente.getNome(),
            ingrediente.getQuantidade(),
            ingrediente.getUnidadeMedida()
        );
    }
}
