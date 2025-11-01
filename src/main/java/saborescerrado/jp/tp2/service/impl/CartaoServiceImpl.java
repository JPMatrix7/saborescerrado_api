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
import saborescerrado.jp.tp2.dto.CartaoDTO;
import saborescerrado.jp.tp2.dto.CartaoResponseDTO;
import saborescerrado.jp.tp2.model.Cartao;
import saborescerrado.jp.tp2.model.EntityClass;
import saborescerrado.jp.tp2.repository.CartaoRepository;
import saborescerrado.jp.tp2.service.CartaoService;

@ApplicationScoped
public class CartaoServiceImpl implements CartaoService {

    public static final Logger LOG = Logger.getLogger(CartaoServiceImpl.class);

    @Inject
    CartaoRepository repository;

    @Override
    public Response getAll() {
        try {
            LOG.info("Requisição Cartao.getAll()");
            List<CartaoResponseDTO> cartoes = repository.findAll().stream()
                    .filter(EntityClass::getAtivo)
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(cartoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.getAll()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAllAdmin(int page, int pageSize) {
        try {
            LOG.info("Requisição Cartao.getAllAdmin()");
            List<CartaoResponseDTO> cartoes = repository.findAll().page(page, pageSize).stream()
                    .sorted(Comparator.comparing(EntityClass::getId).reversed())
                    .map(this::toResponseDTO)
                    .collect(Collectors.toList());
            return Response.ok(cartoes).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.getAllAdmin()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public long count() {
        try {
            LOG.info("Requisição Cartao.count()");
            return repository.findAll().stream().filter(EntityClass::getAtivo).count();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.count()");
            return 0;
        }
    }

    @Override
    public Response getId(long id) {
        try {
            LOG.info("Requisição Cartao.getId()");
            Cartao cartao = repository.findById(id);
            if (cartao == null || !cartao.getAtivo())
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(cartao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.getId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    public Response getAdminId(long id) {
        try {
            LOG.info("Requisição Cartao.getAdminId()");
            Cartao cartao = repository.findById(id);
            if (cartao == null)
                return Response.status(Status.NOT_FOUND).build();
            return Response.ok(toResponseDTO(cartao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.getAdminId()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response insert(CartaoDTO cartaoDTO) {
        try {
            LOG.info("Requisição Cartao.insert()");
            Cartao cartao = new Cartao();
            cartao.setNumeroCartao(cartaoDTO.numeroCartao());
            cartao.setNomeTitular(cartaoDTO.nomeTitular());
            cartao.setValidade(cartaoDTO.validade());
            cartao.setCvv(cartaoDTO.cvv());
            repository.persist(cartao);
            return Response.status(Status.CREATED).entity(toResponseDTO(cartao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.insert()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response update(long id, CartaoDTO cartaoDTO) {
        try {
            LOG.info("Requisição Cartao.update()");
            Cartao cartao = repository.findById(id);
            if (cartao == null)
                return Response.status(Status.NOT_FOUND).build();
            
            if (cartaoDTO.numeroCartao() != null && !cartaoDTO.numeroCartao().isEmpty())
                cartao.setNumeroCartao(cartaoDTO.numeroCartao());
            if (cartaoDTO.nomeTitular() != null && !cartaoDTO.nomeTitular().isEmpty())
                cartao.setNomeTitular(cartaoDTO.nomeTitular());
            if (cartaoDTO.validade() != null && !cartaoDTO.validade().isEmpty())
                cartao.setValidade(cartaoDTO.validade());
            if (cartaoDTO.cvv() != null && !cartaoDTO.cvv().isEmpty())
                cartao.setCvv(cartaoDTO.cvv());
            
            return Response.ok(toResponseDTO(cartao)).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.update()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    @Override
    @Transactional
    public Response delete(Long id) {
        try {
            LOG.info("Requisição Cartao.delete()");
            Cartao cartao = repository.findById(id);
            if (cartao == null)
                return Response.status(Status.NOT_FOUND).build();
            cartao.setAtivo(false);
            return Response.status(Status.NO_CONTENT).build();
        } catch (Exception e) {
            LOG.error("Erro ao rodar Requisição Cartao.delete()", e);
            return Response.status(400).entity(e.getMessage()).build();
        }
    }

    private CartaoResponseDTO toResponseDTO(Cartao cartao) {
        return new CartaoResponseDTO(
            cartao.getId(),
            cartao.getNumeroCartao(),
            cartao.getNomeTitular(),
            cartao.getValidade()
        );
    }
}
