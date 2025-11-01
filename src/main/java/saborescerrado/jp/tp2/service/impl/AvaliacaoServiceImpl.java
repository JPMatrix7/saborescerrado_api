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
import saborescerrado.jp.tp2.dto.AvaliacaoDTO;
import saborescerrado.jp.tp2.dto.AvaliacaoResponseDTO;
import saborescerrado.jp.tp2.dto.UsuarioResponseDTO;
import saborescerrado.jp.tp2.model.Avaliacao;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.repository.AvaliacaoRepository;
import saborescerrado.jp.tp2.service.AvaliacaoService;
import saborescerrado.jp.tp2.service.UsuarioService;

@ApplicationScoped
public class AvaliacaoServiceImpl implements AvaliacaoService {

    public static final Logger LOG = Logger.getLogger(AvaliacaoServiceImpl.class);

    @Inject
    AvaliacaoRepository repository;

    @Inject
    UsuarioService usuarioService;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Avaliacao.getAll()");
            List<AvaliacaoResponseDTO> avaliacoes = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(avaliacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Avaliacao.getAllAdmin()");
            List<AvaliacaoResponseDTO> avaliacoes = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(avaliacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Avaliacao.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Avaliacao.getId()");
            Avaliacao avaliacao = repository.findById(id);
            if (avaliacao == null || !avaliacao.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(avaliacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Avaliacao.getAdminId()");
            Avaliacao avaliacao = repository.findById(id);
            if (avaliacao == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(avaliacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getEstrelas(Integer estrelas) {
        try {
            LOG.info("Requisição Avaliacao.getEstrelas()");
            List<AvaliacaoResponseDTO> avaliacoes = repository.findByEstrelas(estrelas).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(avaliacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.getEstrelas()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(AvaliacaoDTO avaliacaoDTO) {
        try {
            LOG.info("Requisição Avaliacao.insert()");
            
            Avaliacao avaliacao = new Avaliacao();
            avaliacao.setEstrelas(avaliacaoDTO.estrelas());
            avaliacao.setComentario(avaliacaoDTO.comentario());
            
            repository.persist(avaliacao);
            return Response.status(Status.CREATED).entity(toResponseDTO(avaliacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, AvaliacaoDTO avaliacaoDTO) {
        try {
            LOG.info("Requisição Avaliacao.update()");
            Avaliacao avaliacao = repository.findById(id);
            if (avaliacao == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (avaliacaoDTO.estrelas() != null)
                avaliacao.setEstrelas(avaliacaoDTO.estrelas());
            if (avaliacaoDTO.comentario() != null && !avaliacaoDTO.comentario().isEmpty())
                avaliacao.setComentario(avaliacaoDTO.comentario());
            
            return Response.ok(toResponseDTO(avaliacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Avaliacao.delete()");
            Avaliacao avaliacao = repository.findById(id);
            if (avaliacao == null)
                return Response.status(Status.NOT_FOUND).build();
            avaliacao.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Avaliacao.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private AvaliacaoResponseDTO toResponseDTO(Avaliacao avaliacao) {
        UsuarioResponseDTO usuarioDTO = null;
        if (avaliacao.getUsuario() != null) {
            usuarioDTO = (UsuarioResponseDTO) usuarioService.getId(avaliacao.getUsuario().getId()).getEntity();
        }
        
        return new AvaliacaoResponseDTO(
            avaliacao.getId(),
            avaliacao.getEstrelas(),
            avaliacao.getComentario(),
            usuarioDTO
        );
    }
}
