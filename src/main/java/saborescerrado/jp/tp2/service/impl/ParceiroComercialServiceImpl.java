package saborescerrado.jp.tp2.service.impl;

import java.util.ArrayList;
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
import saborescerrado.jp.tp2.dto.EnderecoResponseDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.dto.ParceiroComercialDTO;
import saborescerrado.jp.tp2.dto.ParceiroComercialResponseDTO;
import saborescerrado.jp.tp2.model.Cidade;
import saborescerrado.jp.tp2.model.Endereco;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.ParceiroComercial;
import saborescerrado.jp.tp2.repository.EnderecoRepository;
import saborescerrado.jp.tp2.repository.ParceiroComercialRepository;
import saborescerrado.jp.tp2.service.ParceiroComercialService;

@ApplicationScoped
public class ParceiroComercialServiceImpl implements ParceiroComercialService {

    public static final Logger LOG = Logger.getLogger(ParceiroComercialServiceImpl.class);

    @Inject
    ParceiroComercialRepository repository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição ParceiroComercial.getAll()");
            List<ParceiroComercialResponseDTO> parceiros = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(parceiros).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição ParceiroComercial.getAllAdmin()");
            List<ParceiroComercialResponseDTO> parceiros = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(parceiros).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição ParceiroComercial.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.count()", e);
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição ParceiroComercial.getId()");
            ParceiroComercial parceiro = repository.findById(id);
            if (parceiro == null || !parceiro.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(parceiro)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição ParceiroComercial.getAdminId()");
            ParceiroComercial parceiro = repository.findById(id);
            if (parceiro == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(parceiro)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getCnpj(String cnpj) {
        try {
            LOG.info("Requisição ParceiroComercial.getCnpj()");
            ParceiroComercial parceiro = repository.findByCnpj(cnpj);
            if (parceiro == null || !parceiro.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(parceiro)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getCnpj()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNomeFantasia(String nomeFantasia) {
        try {
            LOG.info("Requisição ParceiroComercial.getNomeFantasia()");
            List<ParceiroComercialResponseDTO> parceiros = repository.findByNomeFantasia(nomeFantasia).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(parceiros).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.getNomeFantasia()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(ParceiroComercialDTO parceiroDTO) {
        try {
            LOG.info("Requisição ParceiroComercial.insert()");
            
            ParceiroComercial parceiro = new ParceiroComercial();
            parceiro.setCnpj(parceiroDTO.cnpj());
            parceiro.setRazaoSocial(parceiroDTO.razaoSocial());
            parceiro.setNomeFantasia(parceiroDTO.nomeFantasia());
            parceiro.setEmail(parceiroDTO.email());
            parceiro.setTelefone(parceiroDTO.telefone());
            
            if (parceiroDTO.enderecosIds() != null && !parceiroDTO.enderecosIds().isEmpty()) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : parceiroDTO.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                parceiro.setEnderecos(enderecos);
            }
            
            repository.persist(parceiro);
            return Response.status(Status.CREATED).entity(toResponseDTO(parceiro)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, ParceiroComercialDTO parceiroDTO) {
        try {
            LOG.info("Requisição ParceiroComercial.update()");
            ParceiroComercial parceiro = repository.findById(id);
            if (parceiro == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (parceiroDTO.cnpj() != null && !parceiroDTO.cnpj().isEmpty())
                parceiro.setCnpj(parceiroDTO.cnpj());
            if (parceiroDTO.razaoSocial() != null && !parceiroDTO.razaoSocial().isEmpty())
                parceiro.setRazaoSocial(parceiroDTO.razaoSocial());
            if (parceiroDTO.nomeFantasia() != null && !parceiroDTO.nomeFantasia().isEmpty())
                parceiro.setNomeFantasia(parceiroDTO.nomeFantasia());
            if (parceiroDTO.email() != null && !parceiroDTO.email().isEmpty())
                parceiro.setEmail(parceiroDTO.email());
            if (parceiroDTO.telefone() != null && !parceiroDTO.telefone().isEmpty())
                parceiro.setTelefone(parceiroDTO.telefone());
            
            if (parceiroDTO.enderecosIds() != null) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : parceiroDTO.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                parceiro.setEnderecos(enderecos);
            }
            
            return Response.ok(toResponseDTO(parceiro)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição ParceiroComercial.delete()");
            ParceiroComercial parceiro = repository.findById(id);
            if (parceiro == null)
                return Response.status(Status.NOT_FOUND).build();
            parceiro.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição ParceiroComercial.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private ParceiroComercialResponseDTO toResponseDTO(ParceiroComercial parceiro) {
        List<EnderecoResponseDTO> enderecosDTO = null;
        if (parceiro.getEnderecos() != null) {
            enderecosDTO = parceiro.getEnderecos().stream()
                    .map(this::enderecoToResponseDTO)
                    .collect(Collectors.toList());
        }
        
        return new ParceiroComercialResponseDTO(
            parceiro.getId(),
            parceiro.getCnpj(),
            parceiro.getRazaoSocial(),
            parceiro.getNomeFantasia(),
            parceiro.getEmail(),
            parceiro.getTelefone(),
            enderecosDTO
        );
    }

    private EnderecoResponseDTO enderecoToResponseDTO(Endereco endereco) {
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
