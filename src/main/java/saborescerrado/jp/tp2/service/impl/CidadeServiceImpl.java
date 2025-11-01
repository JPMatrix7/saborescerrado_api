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
import saborescerrado.jp.tp2.dto.CidadeDTO;
import saborescerrado.jp.tp2.dto.CidadeResponseDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.model.Cidade;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Estado;
import saborescerrado.jp.tp2.repository.CidadeRepository;
import saborescerrado.jp.tp2.repository.EstadoRepository;
import saborescerrado.jp.tp2.service.CidadeService;

@ApplicationScoped
public class CidadeServiceImpl implements CidadeService {

    public static final Logger LOG = Logger.getLogger(CidadeServiceImpl.class);

    @Inject
    CidadeRepository repository;

    @Inject
    EstadoRepository estadoRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Cidade.getAll()");
            List<CidadeResponseDTO> cidades = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(cidades).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Cidade.getAllAdmin()");
            List<CidadeResponseDTO> cidades = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(cidades).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Cidade.count()");
            return repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Cidade.getId()");
            Cidade cidade = repository.findById(id);
            if (cidade == null || !cidade.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(cidade)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Cidade.getAdminId()");
            Cidade cidade = repository.findById(id);
            if (cidade == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(cidade)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Cidade.getNome()");
            List<CidadeResponseDTO> cidades = repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(cidades).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.getNome()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(CidadeDTO cidadeDTO) {
        try {
            LOG.info("Requisição Cidade.insert()");
            
            Estado estado = estadoRepository.findById(cidadeDTO.estadoId());
            if (estado == null)
                return Response.status(Status.NOT_FOUND).entity("Estado não encontrado").build();
            
            Cidade cidade = new Cidade();
            cidade.setNome(cidadeDTO.nome());
            cidade.setEstado(estado);
            
            repository.persist(cidade);
            return Response.status(Status.CREATED).entity(toResponseDTO(cidade)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, CidadeDTO cidadeDTO) {
        try {
            LOG.info("Requisição Cidade.update()");
            Cidade cidade = repository.findById(id);
            if (cidade == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (cidadeDTO.nome() != null && !cidadeDTO.nome().isEmpty())
                cidade.setNome(cidadeDTO.nome());
            
            if (cidadeDTO.estadoId() != null) {
                Estado estado = estadoRepository.findById(cidadeDTO.estadoId());
                if (estado != null)
                    cidade.setEstado(estado);
            }
            
            return Response.ok(toResponseDTO(cidade)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Cidade.delete()");
            Cidade cidade = repository.findById(id);
            if (cidade == null)
                return Response.status(Status.NOT_FOUND).build();
            cidade.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cidade.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private CidadeResponseDTO toResponseDTO(Cidade cidade) {
        EstadoResponseDTO estadoDTO = null;
        if (cidade.getEstado() != null) {
            estadoDTO = new EstadoResponseDTO(
                cidade.getEstado().getId(),
                cidade.getEstado().getNome(),
                cidade.getEstado().getSigla()
            );
        }
        
        return new CidadeResponseDTO(
            cidade.getId(),
            cidade.getNome(),
            estadoDTO
        );
    }
}
