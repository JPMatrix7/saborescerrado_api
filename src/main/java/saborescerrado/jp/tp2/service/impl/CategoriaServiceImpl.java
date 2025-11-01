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
import saborescerrado.jp.tp2.dto.CategoriaDTO;
import saborescerrado.jp.tp2.dto.CategoriaResponseDTO;
import saborescerrado.jp.tp2.model.Categoria;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.repository.CategoriaRepository;
import saborescerrado.jp.tp2.service.CategoriaService;

@ApplicationScoped
public class CategoriaServiceImpl implements CategoriaService {

    public static final Logger LOG = Logger.getLogger(CategoriaServiceImpl.class);

    @Inject
    CategoriaRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Categoria.getAll()");
            List<CategoriaResponseDTO> categorias = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(categorias).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Categoria.getAllAdmin()");
            List<CategoriaResponseDTO> categorias = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(categorias).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Categoria.count()");
            return repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Categoria.getId()");
            Categoria categoria = repository.findById(id);
            if (categoria == null || !categoria.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(categoria)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Categoria.getAdminId()");
            Categoria categoria = repository.findById(id);
            if (categoria == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(categoria)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Categoria.getNome()");
            List<CategoriaResponseDTO> categorias = repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(categorias).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.getNome()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(CategoriaDTO categoriaDTO) {
        try {
            LOG.info("Requisição Categoria.insert()");
            Categoria categoria = new Categoria();
            categoria.setNome(categoriaDTO.nome());
            categoria.setDescricao(categoriaDTO.descricao());
            repository.persist(categoria);
            return Response.status(Status.CREATED).entity(toResponseDTO(categoria)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, CategoriaDTO categoriaDTO) {
        try {
            LOG.info("Requisição Categoria.update()");
            Categoria categoria = repository.findById(id);
            if (categoria == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (categoriaDTO.nome() != null && !categoriaDTO.nome().isEmpty())
                categoria.setNome(categoriaDTO.nome());
            if (categoriaDTO.descricao() != null && !categoriaDTO.descricao().isEmpty())
                categoria.setDescricao(categoriaDTO.descricao());
            
            return Response.ok(toResponseDTO(categoria)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Categoria.delete()");
            Categoria categoria = repository.findById(id);
            if (categoria == null)
                return Response.status(Status.NOT_FOUND).build();
            categoria.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Categoria.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private CategoriaResponseDTO toResponseDTO(Categoria categoria) {
        return new CategoriaResponseDTO(
            categoria.getId(),
            categoria.getNome(),
            categoria.getDescricao()
        );
    }
}
