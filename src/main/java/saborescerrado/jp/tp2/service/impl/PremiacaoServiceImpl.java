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
import saborescerrado.jp.tp2.dto.PremiacaoDTO;
import saborescerrado.jp.tp2.dto.PremiacaoResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Premiacao;
import saborescerrado.jp.tp2.repository.PremiacaoRepository;
import saborescerrado.jp.tp2.service.PremiacaoService;

@ApplicationScoped
public class PremiacaoServiceImpl implements PremiacaoService {

    public static final Logger LOG = Logger.getLogger(PremiacaoServiceImpl.class);

    @Inject
    PremiacaoRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Premiacao.getAll()");
            List<PremiacaoResponseDTO> premiacoes = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(premiacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Premiacao.getAllAdmin()");
            List<PremiacaoResponseDTO> premiacoes = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(premiacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Premiacao.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Premiacao.getId()");
            Premiacao premiacao = repository.findById(id);
            if (premiacao == null || !premiacao.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(premiacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Premiacao.getAdminId()");
            Premiacao premiacao = repository.findById(id);
            if (premiacao == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(premiacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getEvento(String evento) {
        try {
            LOG.info("Requisição Premiacao.getEvento()");
            List<PremiacaoResponseDTO> premiacoes = repository.findByEvento(evento).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(premiacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getEvento()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAno(Integer ano) {
        try {
            LOG.info("Requisição Premiacao.getAno()");
            List<PremiacaoResponseDTO> premiacoes = repository.findByAno(ano).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(premiacoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.getAno()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(PremiacaoDTO premiacaoDTO) {
        try {
            LOG.info("Requisição Premiacao.insert()");
            Premiacao premiacao = new Premiacao();
            premiacao.setEvento(premiacaoDTO.evento());
            premiacao.setAno(premiacaoDTO.ano());
            premiacao.setMedalha(premiacaoDTO.medalha());
            repository.persist(premiacao);
            return Response.status(Status.CREATED).entity(toResponseDTO(premiacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, PremiacaoDTO premiacaoDTO) {
        try {
            LOG.info("Requisição Premiacao.update()");
            Premiacao premiacao = repository.findById(id);
            if (premiacao == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (premiacaoDTO.evento() != null && !premiacaoDTO.evento().isEmpty())
                premiacao.setEvento(premiacaoDTO.evento());
            if (premiacaoDTO.ano() != null)
                premiacao.setAno(premiacaoDTO.ano());
            if (premiacaoDTO.medalha() != null && !premiacaoDTO.medalha().isEmpty())
                premiacao.setMedalha(premiacaoDTO.medalha());
            
            return Response.ok(toResponseDTO(premiacao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Premiacao.delete()");
            Premiacao premiacao = repository.findById(id);
            if (premiacao == null)
                return Response.status(Status.NOT_FOUND).build();
            premiacao.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Premiacao.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private PremiacaoResponseDTO toResponseDTO(Premiacao premiacao) {
        return new PremiacaoResponseDTO(
            premiacao.getId(),
            premiacao.getEvento(),
            premiacao.getAno(),
            premiacao.getMedalha()
        );
    }
}
