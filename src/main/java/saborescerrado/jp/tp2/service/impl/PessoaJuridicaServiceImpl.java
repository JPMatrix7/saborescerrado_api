package saborescerrado.jp.tp2.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import saborescerrado.jp.tp2.dto.CartaoResponseDTO;
import saborescerrado.jp.tp2.dto.EnderecoResponseDTO;
import saborescerrado.jp.tp2.dto.EstadoResponseDTO;
import saborescerrado.jp.tp2.dto.CidadeResponseDTO;
import saborescerrado.jp.tp2.dto.TelefoneResponseDTO;
import saborescerrado.jp.tp2.dto.PessoaJuridicaDTO;
import saborescerrado.jp.tp2.dto.PessoaJuridicaResponseDTO;
import saborescerrado.jp.tp2.model.Cartao;
import saborescerrado.jp.tp2.model.Endereco;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Licor;
import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.model.PessoaJuridica;
import saborescerrado.jp.tp2.model.Telefone;
import saborescerrado.jp.tp2.repository.CartaoRepository;
import saborescerrado.jp.tp2.repository.EnderecoRepository;
import saborescerrado.jp.tp2.repository.LicorRepository;
import saborescerrado.jp.tp2.repository.PessoaJuridicaRepository;
import saborescerrado.jp.tp2.repository.TelefoneRepository;
import saborescerrado.jp.tp2.service.PessoaJuridicaService;

@ApplicationScoped
public class PessoaJuridicaServiceImpl implements PessoaJuridicaService {

    public static final Logger LOG = Logger.getLogger(PessoaJuridicaServiceImpl.class);

    @Inject
    PessoaJuridicaRepository repository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    CartaoRepository cartaoRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    LicorRepository licorRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição PessoaJuridica.getAll()");
            List<PessoaJuridicaResponseDTO> pessoas = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(pessoas).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição PessoaJuridica.getAllAdmin()");
            List<PessoaJuridicaResponseDTO> pessoas = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(pessoas).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição PessoaJuridica.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição PessoaJuridica.getId()");
            PessoaJuridica pessoa = repository.findById(id);
            if (pessoa == null || !pessoa.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(pessoa)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição PessoaJuridica.getAdminId()");
            PessoaJuridica pessoa = repository.findById(id);
            if (pessoa == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(pessoa)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getCnpj(String cnpj) {
        try {
            LOG.info("Requisição PessoaJuridica.getCnpj()");
            PessoaJuridica pessoa = repository.findByCnpj(cnpj);
            if (pessoa == null || !pessoa.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(pessoa)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.getCnpj()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(PessoaJuridicaDTO pessoaDTO) {
        try {
            LOG.info("Requisição PessoaJuridica.insert()");
            
            PessoaJuridica pessoa = new PessoaJuridica();
            pessoa.setNome(pessoaDTO.nome());
            pessoa.setEmail(pessoaDTO.email());
            // Nota: senha deve ser tratada com hash em produção
            pessoa.setSenha(pessoaDTO.senha());
            pessoa.setCnpj(pessoaDTO.cnpj());
            pessoa.setRazaoSocial(pessoaDTO.razaoSocial());
            pessoa.setNomeFantasia(pessoaDTO.nomeFantasia());
            
            if (pessoaDTO.perfis() != null && !pessoaDTO.perfis().isEmpty()) {
                Set<Perfil> perfis = new HashSet<>(pessoaDTO.perfis());
                pessoa.setPerfis(perfis);
            }
            
            if (pessoaDTO.enderecosIds() != null) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : pessoaDTO.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                pessoa.setEnderecos(enderecos);
            }
            
            if (pessoaDTO.cartoesIds() != null) {
                List<Cartao> cartoes = new ArrayList<>();
                for (Long cartaoId : pessoaDTO.cartoesIds()) {
                    Cartao cartao = cartaoRepository.findById(cartaoId);
                    if (cartao != null)
                        cartoes.add(cartao);
                }
                pessoa.setCartoes(cartoes);
            }
            
            if (pessoaDTO.telefonesIds() != null) {
                List<Telefone> telefones = new ArrayList<>();
                for (Long telefoneId : pessoaDTO.telefonesIds()) {
                    Telefone telefone = telefoneRepository.findById(telefoneId);
                    if (telefone != null)
                        telefones.add(telefone);
                }
                pessoa.setTelefones(telefones);
            }
            
            if (pessoaDTO.favoritosIds() != null) {
                List<Licor> favoritos = new ArrayList<>();
                for (Long licorId : pessoaDTO.favoritosIds()) {
                    Licor licor = licorRepository.findById(licorId);
                    if (licor != null)
                        favoritos.add(licor);
                }
                pessoa.setFavoritos(favoritos);
            }
            
            repository.persist(pessoa);
            return Response.status(Status.CREATED).entity(toResponseDTO(pessoa)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, PessoaJuridicaDTO pessoaDTO) {
        try {
            LOG.info("Requisição PessoaJuridica.update()");
            PessoaJuridica pessoa = repository.findById(id);
            if (pessoa == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (pessoaDTO.nome() != null && !pessoaDTO.nome().isEmpty())
                pessoa.setNome(pessoaDTO.nome());
            if (pessoaDTO.email() != null && !pessoaDTO.email().isEmpty())
                pessoa.setEmail(pessoaDTO.email());
            // Nota: senha deve ser tratada com hash em produção
            if (pessoaDTO.senha() != null && !pessoaDTO.senha().isEmpty())
                pessoa.setSenha(pessoaDTO.senha());
            if (pessoaDTO.cnpj() != null && !pessoaDTO.cnpj().isEmpty())
                pessoa.setCnpj(pessoaDTO.cnpj());
            if (pessoaDTO.razaoSocial() != null && !pessoaDTO.razaoSocial().isEmpty())
                pessoa.setRazaoSocial(pessoaDTO.razaoSocial());
            if (pessoaDTO.nomeFantasia() != null && !pessoaDTO.nomeFantasia().isEmpty())
                pessoa.setNomeFantasia(pessoaDTO.nomeFantasia());
            
            if (pessoaDTO.perfis() != null && !pessoaDTO.perfis().isEmpty()) {
                Set<Perfil> perfis = new HashSet<>(pessoaDTO.perfis());
                pessoa.setPerfis(perfis);
            }
            
            if (pessoaDTO.enderecosIds() != null) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : pessoaDTO.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                pessoa.setEnderecos(enderecos);
            }
            
            if (pessoaDTO.cartoesIds() != null) {
                List<Cartao> cartoes = new ArrayList<>();
                for (Long cartaoId : pessoaDTO.cartoesIds()) {
                    Cartao cartao = cartaoRepository.findById(cartaoId);
                    if (cartao != null)
                        cartoes.add(cartao);
                }
                pessoa.setCartoes(cartoes);
            }
            
            if (pessoaDTO.telefonesIds() != null) {
                List<Telefone> telefones = new ArrayList<>();
                for (Long telefoneId : pessoaDTO.telefonesIds()) {
                    Telefone telefone = telefoneRepository.findById(telefoneId);
                    if (telefone != null)
                        telefones.add(telefone);
                }
                pessoa.setTelefones(telefones);
            }
            
            if (pessoaDTO.favoritosIds() != null) {
                List<Licor> favoritos = new ArrayList<>();
                for (Long licorId : pessoaDTO.favoritosIds()) {
                    Licor licor = licorRepository.findById(licorId);
                    if (licor != null)
                        favoritos.add(licor);
                }
                pessoa.setFavoritos(favoritos);
            }
            
            return Response.ok(toResponseDTO(pessoa)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição PessoaJuridica.delete()");
            PessoaJuridica pessoa = repository.findById(id);
            if (pessoa == null)
                return Response.status(Status.NOT_FOUND).build();
            pessoa.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição PessoaJuridica.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private PessoaJuridicaResponseDTO toResponseDTO(PessoaJuridica pessoa) {
        List<EnderecoResponseDTO> enderecosDTO = null;
        if (pessoa.getEnderecos() != null) {
            enderecosDTO = pessoa.getEnderecos().stream().map(endereco -> {
                CidadeResponseDTO cidadeDTO = null;
                if (endereco.getCidade() != null) {
                    EstadoResponseDTO estadoDTO = null;
                    if (endereco.getCidade().getEstado() != null) {
                        estadoDTO = new EstadoResponseDTO(
                            endereco.getCidade().getEstado().getId(),
                            endereco.getCidade().getEstado().getNome(),
                            endereco.getCidade().getEstado().getSigla()
                        );
                    }
                    cidadeDTO = new CidadeResponseDTO(
                        endereco.getCidade().getId(),
                        endereco.getCidade().getNome(),
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
            }).collect(Collectors.toList());
        }

        List<CartaoResponseDTO> cartoesDTO = null;
        if (pessoa.getCartoes() != null) {
            cartoesDTO = pessoa.getCartoes().stream()
                .map(cartao -> new CartaoResponseDTO(
                    cartao.getId(),
                    cartao.getNumeroCartao(),
                    cartao.getNomeTitular(),
                    cartao.getValidade()
                ))
                .collect(Collectors.toList());
        }

        List<TelefoneResponseDTO> telefonesDTO = null;
        if (pessoa.getTelefones() != null) {
            telefonesDTO = pessoa.getTelefones().stream()
                .map(telefone -> new TelefoneResponseDTO(
                    telefone.getId(),
                    telefone.getCodigoArea(),
                    telefone.getNumero()
                ))
                .collect(Collectors.toList());
        }

        // Retornando null para evitar dependências circulares
        return new PessoaJuridicaResponseDTO(
            pessoa.getId(),
            pessoa.getNome(),
            pessoa.getEmail(),
            pessoa.getPerfis(),
            pessoa.getCnpj(),
            pessoa.getRazaoSocial(),
            pessoa.getNomeFantasia(),
            enderecosDTO,
            cartoesDTO,
            telefonesDTO,
            null, // favoritos - evita circular
            null  // compras - evita circular
        );
    }
}
