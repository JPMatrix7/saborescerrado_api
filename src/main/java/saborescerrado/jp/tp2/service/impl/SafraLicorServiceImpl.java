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
import saborescerrado.jp.tp2.dto.CidadeResponseDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.dto.SafraLicorDTO;
import saborescerrado.jp.tp2.dto.SafraLicorResponseDTO;
import saborescerrado.jp.tp2.model.Cidade;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.SafraLicor;
import saborescerrado.jp.tp2.repository.CidadeRepository;
import saborescerrado.jp.tp2.repository.SafraLicorRepository;
import saborescerrado.jp.tp2.service.SafraLicorService;

@ApplicationScoped
public class SafraLicorServiceImpl implements SafraLicorService {

    public static final Logger LOG = Logger.getLogger(SafraLicorServiceImpl.class);

    @Inject
    SafraLicorRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição SafraLicor.getAll()");
            List<SafraLicorResponseDTO> safras = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(safras).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição SafraLicor.getAllAdmin()");
            List<SafraLicorResponseDTO> safras = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(safras).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição SafraLicor.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição SafraLicor.getId()");
            SafraLicor safraLicor = repository.findById(id);
            if (safraLicor == null || !safraLicor.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(safraLicor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição SafraLicor.getAdminId()");
            SafraLicor safraLicor = repository.findById(id);
            if (safraLicor == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(safraLicor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getFazenda(String fazenda) {
        try {
            LOG.info("Requisição SafraLicor.getFazenda()");
            List<SafraLicorResponseDTO> safras = repository.findByFazenda(fazenda).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(safras).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.getFazenda()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(SafraLicorDTO safraLicorDTO) {
        try {
            LOG.info("Requisição SafraLicor.insert()");
            
            Cidade cidade = cidadeRepository.findById(safraLicorDTO.cidadeId());
            if (cidade == null)
                return Response.status(Status.NOT_FOUND).entity("Cidade não encontrada").build();
            
            SafraLicor safraLicor = new SafraLicor();
            safraLicor.setAnoSafra(safraLicorDTO.anoSafra());
            safraLicor.setFazenda(safraLicorDTO.fazenda());
            safraLicor.setQualidade(safraLicorDTO.qualidade());
            safraLicor.setCidade(cidade);
            
            repository.persist(safraLicor);
            return Response.status(Status.CREATED).entity(toResponseDTO(safraLicor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, SafraLicorDTO safraLicorDTO) {
        try {
            LOG.info("Requisição SafraLicor.update()");
            SafraLicor safraLicor = repository.findById(id);
            if (safraLicor == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (safraLicorDTO.anoSafra() != null)
                safraLicor.setAnoSafra(safraLicorDTO.anoSafra());
            if (safraLicorDTO.fazenda() != null && !safraLicorDTO.fazenda().isEmpty())
                safraLicor.setFazenda(safraLicorDTO.fazenda());
            if (safraLicorDTO.qualidade() != null && !safraLicorDTO.qualidade().isEmpty())
                safraLicor.setQualidade(safraLicorDTO.qualidade());
            
            if (safraLicorDTO.cidadeId() != null) {
                Cidade cidade = cidadeRepository.findById(safraLicorDTO.cidadeId());
                if (cidade != null)
                    safraLicor.setCidade(cidade);
            }
            
            return Response.ok(toResponseDTO(safraLicor)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição SafraLicor.delete()");
            SafraLicor safraLicor = repository.findById(id);
            if (safraLicor == null)
                return Response.status(Status.NOT_FOUND).build();
            safraLicor.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição SafraLicor.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private SafraLicorResponseDTO toResponseDTO(SafraLicor safraLicor) {
        CidadeResponseDTO cidadeDTO = null;
        if (safraLicor.getCidade() != null) {
            Cidade cidade = safraLicor.getCidade();
            EstadoResponseDTO estadoDTO = null;
            if (cidade.getEstado() != null) {
                estadoDTO = new EstadoResponseDTO(
                    cidade.getEstado().getId(),
                    cidade.getEstado().getNome(),
                    cidade.getEstado().getSigla()
                );
            }
            cidadeDTO = new CidadeResponseDTO(
                cidade.getId(),
                cidade.getNome(),
                estadoDTO
            );
        }
        
        return new SafraLicorResponseDTO(
            safraLicor.getId(),
            safraLicor.getAnoSafra(),
            safraLicor.getFazenda(),
            safraLicor.getQualidade(),
            cidadeDTO
        );
    }
}
