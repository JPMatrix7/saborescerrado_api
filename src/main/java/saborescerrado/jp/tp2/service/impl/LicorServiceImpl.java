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
import saborescerrado.jp.tp2.dto.LicorDTO;
import saborescerrado.jp.tp2.dto.LicorResponseDTO;
import saborescerrado.jp.tp2.model.Categoria;
import saborescerrado.jp.tp2.model.Embalagem;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.model.Ingrediente;
import saborescerrado.jp.tp2.model.Licor;
import saborescerrado.jp.tp2.model.ParceiroComercial;
import saborescerrado.jp.tp2.model.Premiacao;
import saborescerrado.jp.tp2.model.Sabor;
import saborescerrado.jp.tp2.model.SafraLicor;
import saborescerrado.jp.tp2.model.TipoLicor;
import saborescerrado.jp.tp2.repository.CategoriaRepository;
import saborescerrado.jp.tp2.repository.EmbalagemRepository;
import saborescerrado.jp.tp2.repository.IngredienteRepository;
import saborescerrado.jp.tp2.repository.LicorRepository;
import saborescerrado.jp.tp2.repository.ParceiroComercialRepository;
import saborescerrado.jp.tp2.repository.PremiacaoRepository;
import saborescerrado.jp.tp2.repository.SaborRepository;
import saborescerrado.jp.tp2.repository.SafraLicorRepository;
import saborescerrado.jp.tp2.service.LicorService;

@ApplicationScoped
public class LicorServiceImpl implements LicorService {

    public static final Logger LOG = Logger.getLogger(LicorServiceImpl.class);

    @Inject
    LicorRepository repository;

    @Inject
    CategoriaRepository categoriaRepository;

    @Inject
    SafraLicorRepository safraLicorRepository;

    @Inject
    PremiacaoRepository premiacaoRepository;

    @Inject
    SaborRepository saborRepository;

    @Inject
    IngredienteRepository ingredienteRepository;

    @Inject
    EmbalagemRepository embalagemRepository;

    @Inject
    ParceiroComercialRepository parceiroComercialRepository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Licor.getAll()");
            List<LicorResponseDTO> licores = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .filter(l -> l.getVisivel() != null && l.getVisivel())
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(licores).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Licor.getAllAdmin()");
            List<LicorResponseDTO> licores = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(licores).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Licor.count()");
            return repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .filter(l -> l.getVisivel() != null && l.getVisivel())
                    .count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Licor.getId()");
            Licor licor = repository.findById(id);
            if (licor == null || !licor.getAtivo() || !licor.getVisivel())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(licor).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getId()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Licor.getAdminId()");
            Licor licor = repository.findById(id);
            if (licor == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(licor).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getAdminId()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getNome(String nome) {
        try {
            LOG.info("Requisição Licor.getNome()");
            return Response.ok(repository.findByNome(nome).stream()
                    .filter(EntityClass::getAtivo)
                    .filter(l -> l.getVisivel() != null && l.getVisivel())
                    .collect(Collectors.toList())).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getNome()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getTipo(String tipo) {
        try {
            LOG.info("Requisição Licor.getTipo()");
            return Response.ok(repository.findByTipo(TipoLicor.valueOf(tipo.toUpperCase())).stream()
                    .filter(EntityClass::getAtivo)
                    .filter(l -> l.getVisivel() != null && l.getVisivel())
                    .collect(Collectors.toList())).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getTipo()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getVisivel() {
        try {
            LOG.info("Requisição Licor.getVisivel()");
            return Response.ok(repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .filter(l -> l.getVisivel() != null && l.getVisivel())
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .collect(Collectors.toList())).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.getVisivel()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(LicorDTO licorDTO) {
        try {
            LOG.info("Requisição Licor.insert()");
            
            Licor licor = new Licor();
            licor.setNome(licorDTO.nome());
            licor.setDescricao(licorDTO.descricao());
            licor.setPreco(licorDTO.preco());
            licor.setEstoque(licorDTO.estoque());
            licor.setImagens(licorDTO.imagens());
            licor.setVisivel(licorDTO.visivel());
            licor.setTeorAlcoolico(licorDTO.teorAlcoolico());
            licor.setTipo(licorDTO.tipo());
            
            // Categorias
            if (licorDTO.categoriasIds() != null && !licorDTO.categoriasIds().isEmpty()) {
                List<Categoria> categorias = new ArrayList<>();
                for (Long categoriaId : licorDTO.categoriasIds()) {
                    Categoria categoria = categoriaRepository.findById(categoriaId);
                    if (categoria != null)
                        categorias.add(categoria);
                }
                licor.setCategorias(categorias);
            }
            
            // Safra
            if (licorDTO.safraId() != null) {
                SafraLicor safra = safraLicorRepository.findById(licorDTO.safraId());
                if (safra != null)
                    licor.setSafra(safra);
            }
            
            // Premiações
            if (licorDTO.premiacoesIds() != null && !licorDTO.premiacoesIds().isEmpty()) {
                List<Premiacao> premiacoes = new ArrayList<>();
                for (Long premiacaoId : licorDTO.premiacoesIds()) {
                    Premiacao premiacao = premiacaoRepository.findById(premiacaoId);
                    if (premiacao != null)
                        premiacoes.add(premiacao);
                }
                licor.setPremiacoes(premiacoes);
            }
            
            // Sabor
            if (licorDTO.saborId() != null) {
                Sabor sabor = saborRepository.findById(licorDTO.saborId());
                if (sabor != null)
                    licor.setSabor(sabor);
            }
            
            // Ingredientes
            if (licorDTO.ingredientesIds() != null && !licorDTO.ingredientesIds().isEmpty()) {
                List<Ingrediente> ingredientes = new ArrayList<>();
                for (Long ingredienteId : licorDTO.ingredientesIds()) {
                    Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId);
                    if (ingrediente != null)
                        ingredientes.add(ingrediente);
                }
                licor.setIngredientes(ingredientes);
            }
            
            // Embalagem
            if (licorDTO.embalagemId() != null) {
                Embalagem embalagem = embalagemRepository.findById(licorDTO.embalagemId());
                if (embalagem != null)
                    licor.setEmbalagem(embalagem);
            }
            
            // Parceiro Comercial
            if (licorDTO.parceiroComercialId() != null) {
                ParceiroComercial parceiro = parceiroComercialRepository.findById(licorDTO.parceiroComercialId());
                if (parceiro != null)
                    licor.setParceiroComercial(parceiro);
            }
            
            repository.persist(licor);
            return Response.status(Status.CREATED).entity(licor).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.insert()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, LicorDTO licorDTO) {
        try {
            LOG.info("Requisição Licor.update()");
            Licor licor = repository.findById(id);
            if (licor == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (licorDTO.nome() != null && !licorDTO.nome().isEmpty())
                licor.setNome(licorDTO.nome());
            if (licorDTO.descricao() != null && !licorDTO.descricao().isEmpty())
                licor.setDescricao(licorDTO.descricao());
            if (licorDTO.preco() != null)
                licor.setPreco(licorDTO.preco());
            if (licorDTO.estoque() != null)
                licor.setEstoque(licorDTO.estoque());
            if (licorDTO.imagens() != null)
                licor.setImagens(licorDTO.imagens());
            if (licorDTO.visivel() != null)
                licor.setVisivel(licorDTO.visivel());
            if (licorDTO.teorAlcoolico() != null)
                licor.setTeorAlcoolico(licorDTO.teorAlcoolico());
            if (licorDTO.tipo() != null)
                licor.setTipo(licorDTO.tipo());
            
            // Categorias
            if (licorDTO.categoriasIds() != null) {
                List<Categoria> categorias = new ArrayList<>();
                for (Long categoriaId : licorDTO.categoriasIds()) {
                    Categoria categoria = categoriaRepository.findById(categoriaId);
                    if (categoria != null)
                        categorias.add(categoria);
                }
                licor.setCategorias(categorias);
            }
            
            // Safra
            if (licorDTO.safraId() != null) {
                SafraLicor safra = safraLicorRepository.findById(licorDTO.safraId());
                if (safra != null)
                    licor.setSafra(safra);
            }
            
            // Premiações
            if (licorDTO.premiacoesIds() != null) {
                List<Premiacao> premiacoes = new ArrayList<>();
                for (Long premiacaoId : licorDTO.premiacoesIds()) {
                    Premiacao premiacao = premiacaoRepository.findById(premiacaoId);
                    if (premiacao != null)
                        premiacoes.add(premiacao);
                }
                licor.setPremiacoes(premiacoes);
            }
            
            // Sabor
            if (licorDTO.saborId() != null) {
                Sabor sabor = saborRepository.findById(licorDTO.saborId());
                if (sabor != null)
                    licor.setSabor(sabor);
            }
            
            // Ingredientes
            if (licorDTO.ingredientesIds() != null) {
                List<Ingrediente> ingredientes = new ArrayList<>();
                for (Long ingredienteId : licorDTO.ingredientesIds()) {
                    Ingrediente ingrediente = ingredienteRepository.findById(ingredienteId);
                    if (ingrediente != null)
                        ingredientes.add(ingrediente);
                }
                licor.setIngredientes(ingredientes);
            }
            
            // Embalagem
            if (licorDTO.embalagemId() != null) {
                Embalagem embalagem = embalagemRepository.findById(licorDTO.embalagemId());
                if (embalagem != null)
                    licor.setEmbalagem(embalagem);
            }
            
            // Parceiro Comercial
            if (licorDTO.parceiroComercialId() != null) {
                ParceiroComercial parceiro = parceiroComercialRepository.findById(licorDTO.parceiroComercialId());
                if (parceiro != null)
                    licor.setParceiroComercial(parceiro);
            }
            
            return Response.ok(licor).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.update()");
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Licor.delete()");
            Licor licor = repository.findById(id);
            if (licor == null)
                return Response.status(Status.NOT_FOUND).build();
            licor.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Licor.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private LicorResponseDTO toResponseDTO(Licor licor) {
        // Retornando DTO super simplificado para evitar dependências circulares
        // Todas as relações complexas retornam null
        return new LicorResponseDTO(
            licor.getId(),
            licor.getNome(),
            licor.getDescricao(),
            licor.getPreco(),
            licor.getEstoque(),
            licor.getImagens(),
            licor.getEstrelas(),
            licor.getVisivel(),
            licor.getTeorAlcoolico(),
            licor.getTipo(),
            null, // safra - evita circular
            null, // sabor - evita circular
            null, // embalagem - evita circular
            null, // parceiroComercial - evita circular
            null, // categorias - evita circular
            null, // ingredientes - evita circular
            null, // premiacoes - evita circular
            null  // avaliacoes - evita circular
        );
    }
}
