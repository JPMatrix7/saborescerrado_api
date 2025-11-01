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
import saborescerrado.jp.tp2.dto.EnderecoDTO;
import saborescerrado.jp.tp2.dto.EnderecoResponseDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.model.Cidade;
import saborescerrado.jp.tp2.model.Endereco;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.repository.CidadeRepository;
import saborescerrado.jp.tp2.repository.EnderecoRepository;
import saborescerrado.jp.tp2.service.EnderecoService;

@ApplicationScoped
public class EnderecoServiceImpl implements EnderecoService {

    public static final Logger LOG = Logger.getLogger(EnderecoServiceImpl.class);

    @Inject
    EnderecoRepository repository;

    @Inject
    CidadeRepository cidadeRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Endereco.getAll()");
            List<EnderecoResponseDTO> enderecos = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(enderecos).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Endereco.getAllAdmin()");
            List<EnderecoResponseDTO> enderecos = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(enderecos).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Endereco.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Endereco.getId()");
            Endereco endereco = repository.findById(id);
            if (endereco == null || !endereco.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(endereco)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Endereco.getAdminId()");
            Endereco endereco = repository.findById(id);
            if (endereco == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(endereco)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getCep(String cep) {
        try {
            LOG.info("Requisição Endereco.getCep()");
            List<EnderecoResponseDTO> enderecos = repository.findByCep(cep).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(enderecos).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.getCep()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(EnderecoDTO enderecoDTO) {
        try {
            LOG.info("Requisição Endereco.insert()");
            
            Cidade cidade = cidadeRepository.findById(enderecoDTO.cidadeId());
            if (cidade == null)
                return Response.status(Status.NOT_FOUND).entity("Cidade não encontrada").build();
            
            Endereco endereco = new Endereco();
            endereco.setLogradouro(enderecoDTO.logradouro());
            endereco.setNumero(enderecoDTO.numero());
            endereco.setComplemento(enderecoDTO.complemento());
            endereco.setBairro(enderecoDTO.bairro());
            endereco.setCep(enderecoDTO.cep());
            endereco.setCidade(cidade);
            
            repository.persist(endereco);
            return Response.status(Status.CREATED).entity(toResponseDTO(endereco)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, EnderecoDTO enderecoDTO) {
        try {
            LOG.info("Requisição Endereco.update()");
            Endereco endereco = repository.findById(id);
            if (endereco == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (enderecoDTO.logradouro() != null && !enderecoDTO.logradouro().isEmpty())
                endereco.setLogradouro(enderecoDTO.logradouro());
            if (enderecoDTO.numero() != null && !enderecoDTO.numero().isEmpty())
                endereco.setNumero(enderecoDTO.numero());
            if (enderecoDTO.complemento() != null && !enderecoDTO.complemento().isEmpty())
                endereco.setComplemento(enderecoDTO.complemento());
            if (enderecoDTO.bairro() != null && !enderecoDTO.bairro().isEmpty())
                endereco.setBairro(enderecoDTO.bairro());
            if (enderecoDTO.cep() != null && !enderecoDTO.cep().isEmpty())
                endereco.setCep(enderecoDTO.cep());
            
            if (enderecoDTO.cidadeId() != null) {
                Cidade cidade = cidadeRepository.findById(enderecoDTO.cidadeId());
                if (cidade != null)
                    endereco.setCidade(cidade);
            }
            
            return Response.ok(toResponseDTO(endereco)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Endereco.delete()");
            Endereco endereco = repository.findById(id);
            if (endereco == null)
                return Response.status(Status.NOT_FOUND).build();
            endereco.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Endereco.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private EnderecoResponseDTO toResponseDTO(Endereco endereco) {
        CidadeResponseDTO cidadeDTO = null;
        if (endereco.getCidade() != null) {
            Cidade cidade = endereco.getCidade();
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
        
        return new EnderecoResponseDTO(
            endereco.getId(),
            endereco.getLogradouro(),
            endereco.getNumero(),
            endereco.getComplemento(),
            endereco.getBairro(),
            endereco.getCep(),
            cidadeDTO
        );
    }
}
