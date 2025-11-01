package saborescerrado.jp.tp2.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import saborescerrado.jp.tp2.dto.PessoaFisicaDTO;
import saborescerrado.jp.tp2.dto.UsuarioResponseDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateEmailDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateLoginDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateNomeDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateSenhaDTO;
import saborescerrado.jp.tp2.model.Cartao;
import saborescerrado.jp.tp2.model.Endereco;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Licor;
import saborescerrado.jp.tp2.model.PessoaFisica;
import saborescerrado.jp.tp2.model.Telefone;
import saborescerrado.jp.tp2.model.Usuario;
import saborescerrado.jp.tp2.repository.CartaoRepository;
import saborescerrado.jp.tp2.repository.EnderecoRepository;
import saborescerrado.jp.tp2.repository.LicorRepository;
import saborescerrado.jp.tp2.repository.TelefoneRepository;
import saborescerrado.jp.tp2.repository.UsuarioRepository;
import saborescerrado.jp.tp2.service.UsuarioService;

@ApplicationScoped
public class UsuarioServiceImpl implements UsuarioService {

    public static final Logger LOG = Logger.getLogger(UsuarioServiceImpl.class);

    @Inject
    UsuarioRepository repository;

    @Inject
    EnderecoRepository enderecoRepository;

    @Inject
    CartaoRepository cartaoRepository;

    @Inject
    TelefoneRepository telefoneRepository;

    @Inject
    LicorRepository licorRepository;

    @Override
    public List<UsuarioResponseDTO> getAll(int page, int pageSize) {
        try {
            LOG.info("Requisição Usuario.getAll()");
            return repository.findAll().page(page, pageSize).stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.getAll(): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Usuario.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.count()");
            return 0;
        }
    }

    @Override
    public List<UsuarioResponseDTO> getNome(String nome) {
        try {
            LOG.info("Requisição Usuario.getNome()");
            return repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.getNome(): " + e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Usuario.getId()");
            Usuario usuario = repository.findById(id);
            if (usuario == null || !usuario.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(usuario)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.getId()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(PessoaFisicaDTO p) {
        try {
            LOG.info("Requisição Usuario.insert()");
            
            PessoaFisica pessoaFisica = new PessoaFisica();
            pessoaFisica.setNome(p.nome());
            pessoaFisica.setEmail(p.email());
            pessoaFisica.setSenha(p.senha()); // Em produção, fazer hash da senha
            pessoaFisica.setCpf(p.cpf());
            pessoaFisica.setDataNascimento(p.dataNascimento());
            pessoaFisica.setSobrenome(p.sobrenome());
            
            if (p.perfis() != null && !p.perfis().isEmpty()) {
                pessoaFisica.setPerfis(new HashSet<>(p.perfis()));
            }
            
            if (p.enderecosIds() != null) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : p.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                pessoaFisica.setEnderecos(enderecos);
            }
            
            if (p.cartoesIds() != null) {
                List<Cartao> cartoes = new ArrayList<>();
                for (Long cartaoId : p.cartoesIds()) {
                    Cartao cartao = cartaoRepository.findById(cartaoId);
                    if (cartao != null)
                        cartoes.add(cartao);
                }
                pessoaFisica.setCartoes(cartoes);
            }
            
            if (p.telefonesIds() != null) {
                List<Telefone> telefones = new ArrayList<>();
                for (Long telefoneId : p.telefonesIds()) {
                    Telefone telefone = telefoneRepository.findById(telefoneId);
                    if (telefone != null)
                        telefones.add(telefone);
                }
                pessoaFisica.setTelefones(telefones);
            }
            
            if (p.favoritosIds() != null) {
                List<Licor> favoritos = new ArrayList<>();
                for (Long licorId : p.favoritosIds()) {
                    Licor licor = licorRepository.findById(licorId);
                    if (licor != null)
                        favoritos.add(licor);
                }
                pessoaFisica.setFavoritos(favoritos);
            }
            
            repository.persist(pessoaFisica);
            return Response.status(Status.CREATED).entity(toResponseDTO(pessoaFisica)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.insert(): " + e.getMessage());
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateSenha(Long id, UsuarioUpdateSenhaDTO senha) {
        LOG.info("Requisição Usuario.updateSenha()");
        Usuario usuario = repository.findById(id);
        if (usuario == null)
            throw new RuntimeException("Usuário não encontrado");
        
        // Em produção, verificar a senha atual e fazer hash da nova senha
        usuario.setSenha(senha.novaSenha());
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateEmail(Long id, UsuarioUpdateEmailDTO email) {
        LOG.info("Requisição Usuario.updateEmail()");
        Usuario usuario = repository.findById(id);
        if (usuario == null)
            throw new RuntimeException("Usuário não encontrado");
        
        usuario.setEmail(email.email());
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateLogin(Long id, UsuarioUpdateLoginDTO login) {
        LOG.info("Requisição Usuario.updateLogin()");
        Usuario usuario = repository.findById(id);
        if (usuario == null)
            throw new RuntimeException("Usuário não encontrado");
        
        usuario.setNome(login.login()); // Assumindo que login é o nome
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioResponseDTO updateNome(Long id, UsuarioUpdateNomeDTO nome) {
        LOG.info("Requisição Usuario.updateNome()");
        Usuario usuario = repository.findById(id);
        if (usuario == null)
            throw new RuntimeException("Usuário não encontrado");
        
        usuario.setNome(nome.nome());
        return toResponseDTO(usuario);
    }

    @Override
    @Transactional
    public Response update(Long id, PessoaFisicaDTO dto) {
        try {
            LOG.info("Requisição Usuario.update()");
            Usuario usuario = repository.findById(id);
            if (usuario == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (!(usuario instanceof PessoaFisica))
                return Response.status(Status.BAD_REQUEST)
                        .entity("Usuário não é uma pessoa física").build();
            
            PessoaFisica pessoaFisica = (PessoaFisica) usuario;
            
            if (dto.nome() != null && !dto.nome().isEmpty())
                pessoaFisica.setNome(dto.nome());
            if (dto.email() != null && !dto.email().isEmpty())
                pessoaFisica.setEmail(dto.email());
            if (dto.senha() != null && !dto.senha().isEmpty())
                pessoaFisica.setSenha(dto.senha()); // Em produção, fazer hash da senha
            if (dto.cpf() != null && !dto.cpf().isEmpty())
                pessoaFisica.setCpf(dto.cpf());
            if (dto.dataNascimento() != null)
                pessoaFisica.setDataNascimento(dto.dataNascimento());
            if (dto.sobrenome() != null && !dto.sobrenome().isEmpty())
                pessoaFisica.setSobrenome(dto.sobrenome());
            
            if (dto.perfis() != null && !dto.perfis().isEmpty()) {
                pessoaFisica.setPerfis(new HashSet<>(dto.perfis()));
            }
            
            if (dto.enderecosIds() != null) {
                List<Endereco> enderecos = new ArrayList<>();
                for (Long enderecoId : dto.enderecosIds()) {
                    Endereco endereco = enderecoRepository.findById(enderecoId);
                    if (endereco != null)
                        enderecos.add(endereco);
                }
                pessoaFisica.setEnderecos(enderecos);
            }
            
            if (dto.cartoesIds() != null) {
                List<Cartao> cartoes = new ArrayList<>();
                for (Long cartaoId : dto.cartoesIds()) {
                    Cartao cartao = cartaoRepository.findById(cartaoId);
                    if (cartao != null)
                        cartoes.add(cartao);
                }
                pessoaFisica.setCartoes(cartoes);
            }
            
            if (dto.telefonesIds() != null) {
                List<Telefone> telefones = new ArrayList<>();
                for (Long telefoneId : dto.telefonesIds()) {
                    Telefone telefone = telefoneRepository.findById(telefoneId);
                    if (telefone != null)
                        telefones.add(telefone);
                }
                pessoaFisica.setTelefones(telefones);
            }
            
            if (dto.favoritosIds() != null) {
                List<Licor> favoritos = new ArrayList<>();
                for (Long licorId : dto.favoritosIds()) {
                    Licor licor = licorRepository.findById(licorId);
                    if (licor != null)
                        favoritos.add(licor);
                }
                pessoaFisica.setFavoritos(favoritos);
            }
            
            return Response.ok(toResponseDTO(pessoaFisica)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.update(): " + e.getMessage());
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Usuario.delete()");
            Usuario usuario = repository.findById(id);
            if (usuario == null)
                return Response.status(Status.NOT_FOUND).build();
            usuario.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.delete()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response resetarSenha(Long id) {
        try {
            LOG.info("Requisição Usuario.resetarSenha()");
            Usuario usuario = repository.findById(id);
            if (usuario == null)
                return Response.status(Status.NOT_FOUND).build();
            
            // Resetar senha para um valor padrão
            usuario.setSenha("123456"); // Em produção, gerar senha aleatória e enviar por email
            return Response.ok().entity("Senha resetada com sucesso").build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Usuario.resetarSenha(): " + e.getMessage());
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private UsuarioResponseDTO toResponseDTO(Usuario usuario) {
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getPerfis(),
                usuario.getEnderecos() != null ? usuario.getEnderecos().stream()
                        .map(e -> new saborescerrado.jp.tp2.dto.EnderecoResponseDTO(
                                e.getId(),
                                e.getLogradouro(),
                                e.getNumero(),
                                e.getComplemento(),
                                e.getBairro(),
                                e.getCep(),
                                e.getCidade() != null ? new saborescerrado.jp.tp2.dto.CidadeResponseDTO(
                                        e.getCidade().getId(),
                                        e.getCidade().getNome(),
                                        e.getCidade().getEstado() != null ? new saborescerrado.jp.tp2.dto.EstadoResponseDTO(
                                                e.getCidade().getEstado().getId(),
                                                e.getCidade().getEstado().getNome(),
                                                e.getCidade().getEstado().getSigla()
                                        ) : null
                                ) : null
                        ))
                        .collect(Collectors.toList()) : new ArrayList<>(),
                usuario.getCartoes() != null ? usuario.getCartoes().stream()
                        .map(c -> new saborescerrado.jp.tp2.dto.CartaoResponseDTO(
                                c.getId(),
                                c.getNumeroCartao(),
                                c.getNomeTitular(),
                                c.getValidade()
                        ))
                        .collect(Collectors.toList()) : new ArrayList<>(),
                usuario.getTelefones() != null ? usuario.getTelefones().stream()
                        .map(t -> new saborescerrado.jp.tp2.dto.TelefoneResponseDTO(
                                t.getId(),
                                t.getCodigoArea(),
                                t.getNumero()
                        ))
                        .collect(Collectors.toList()) : new ArrayList<>(),
                usuario.getFavoritos() != null ? usuario.getFavoritos().stream()
                        .map(l -> new saborescerrado.jp.tp2.dto.LicorResponseDTO(
                                l.getId(),
                                l.getNome(),
                                l.getDescricao(),
                                l.getPreco(),
                                l.getEstoque(),
                                l.getImagens(),
                                l.getEstrelas(),
                                l.getVisivel(),
                                l.getTeorAlcoolico(),
                                l.getTipo(),
                                null, // safra
                                null, // sabor
                                null, // embalagem
                                null, // parceiroComercial
                                null, // categorias
                                null, // ingredientes
                                null, // premiacoes
                                null  // avaliacoes
                        ))
                        .collect(Collectors.toList()) : new ArrayList<>(),
                usuario.getCompras() != null ? usuario.getCompras().stream()
                        .map(c -> new saborescerrado.jp.tp2.dto.CompraResponseDTO(
                                c.getId(),
                                c.getDataCompra(),
                                c.getStatus(),
                                c.getFormaPagamento(),
                                c.getValorTotal(),
                                c.getCodigoRastreio(),
                                c.getDataPrevista(),
                                c.getDataEntrega(),
                                c.getPago(),
                                null, // usuario
                                null  // itens
                        ))
                        .collect(Collectors.toList()) : new ArrayList<>()
        );
    }
}
