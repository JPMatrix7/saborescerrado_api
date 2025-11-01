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
import saborescerrado.jp.tp2.dto.TelefoneDTO;
import saborescerrado.jp.tp2.dto.TelefoneResponseDTO;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Telefone;
import saborescerrado.jp.tp2.repository.TelefoneRepository;
import saborescerrado.jp.tp2.service.TelefoneService;

@ApplicationScoped
public class TelefoneServiceImpl implements TelefoneService {

    public static final Logger LOG = Logger.getLogger(TelefoneServiceImpl.class);

    @Inject
    TelefoneRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Telefone.getAll()");
            List<TelefoneResponseDTO> telefones = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(telefones).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Telefone.getAllAdmin()");
            List<TelefoneResponseDTO> telefones = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(telefones).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Telefone.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Telefone.getId()");
            Telefone telefone = repository.findById(id);
            if (telefone == null || !telefone.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(telefone)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Telefone.getAdminId()");
            Telefone telefone = repository.findById(id);
            if (telefone == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(telefone)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(TelefoneDTO telefoneDTO) {
        try {
            LOG.info("Requisição Telefone.insert()");
            Telefone telefone = new Telefone();
            telefone.setCodigoArea(telefoneDTO.codigoArea());
            telefone.setNumero(telefoneDTO.numero());
            repository.persist(telefone);
            return Response.status(Status.CREATED).entity(toResponseDTO(telefone)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, TelefoneDTO telefoneDTO) {
        try {
            LOG.info("Requisição Telefone.update()");
            Telefone telefone = repository.findById(id);
            if (telefone == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (telefoneDTO.codigoArea() != null && !telefoneDTO.codigoArea().isEmpty())
                telefone.setCodigoArea(telefoneDTO.codigoArea());
            if (telefoneDTO.numero() != null && !telefoneDTO.numero().isEmpty())
                telefone.setNumero(telefoneDTO.numero());
            
            return Response.ok(toResponseDTO(telefone)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Telefone.delete()");
            Telefone telefone = repository.findById(id);
            if (telefone == null)
                return Response.status(Status.NOT_FOUND).build();
            telefone.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Telefone.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private TelefoneResponseDTO toResponseDTO(Telefone telefone) {
        return new TelefoneResponseDTO(
            telefone.getId(),
            telefone.getCodigoArea(),
            telefone.getNumero()
        );
    }
}
