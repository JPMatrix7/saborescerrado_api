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
import saborescerrado.jp.tp2.dto.EmbalagemDTO;
import saborescerrado.jp.tp2.dto.EmbalagemResponseDTO;
import saborescerrado.jp.tp2.model.Embalagem;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.repository.EmbalagemRepository;
import saborescerrado.jp.tp2.service.EmbalagemService;

@ApplicationScoped
public class EmbalagemServiceImpl implements EmbalagemService {

    public static final Logger LOG = Logger.getLogger(EmbalagemServiceImpl.class);

    @Inject
    EmbalagemRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Embalagem.getAll()");
            List<EmbalagemResponseDTO> embalagens = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(embalagens).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Embalagem.getAllAdmin()");
            List<EmbalagemResponseDTO> embalagens = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(embalagens).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Embalagem.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Embalagem.getId()");
            Embalagem embalagem = repository.findById(id);
            if (embalagem == null || !embalagem.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(embalagem)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Embalagem.getAdminId()");
            Embalagem embalagem = repository.findById(id);
            if (embalagem == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(embalagem)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getVolume(Integer volume) {
        try {
            LOG.info("Requisição Embalagem.getVolume()");
            List<EmbalagemResponseDTO> embalagens = repository.findByVolume(volume).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(embalagens).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.getVolume()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(EmbalagemDTO embalagemDTO) {
        try {
            LOG.info("Requisição Embalagem.insert()");
            Embalagem embalagem = new Embalagem();
            embalagem.setVolume(embalagemDTO.volume());
            embalagem.setMaterial(embalagemDTO.material());
            repository.persist(embalagem);
            return Response.status(Status.CREATED).entity(toResponseDTO(embalagem)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, EmbalagemDTO embalagemDTO) {
        try {
            LOG.info("Requisição Embalagem.update()");
            Embalagem embalagem = repository.findById(id);
            if (embalagem == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (embalagemDTO.volume() != null)
                embalagem.setVolume(embalagemDTO.volume());
            if (embalagemDTO.material() != null && !embalagemDTO.material().isEmpty())
                embalagem.setMaterial(embalagemDTO.material());
            
            return Response.ok(toResponseDTO(embalagem)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Embalagem.delete()");
            Embalagem embalagem = repository.findById(id);
            if (embalagem == null)
                return Response.status(Status.NOT_FOUND).build();
            embalagem.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Embalagem.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private EmbalagemResponseDTO toResponseDTO(Embalagem embalagem) {
        return new EmbalagemResponseDTO(
            embalagem.getId(),
            embalagem.getVolume(),
            embalagem.getMaterial()
        );
    }
}
