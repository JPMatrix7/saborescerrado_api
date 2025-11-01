package saborescerrado.jp.tp2.dto;

import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record PessoaJuridicaResponseDTO(
    Long id,
    String nome,
    String email,
    Set<Perfil> perfis,
    String cnpj,
    String razaoSocial,
    String nomeFantasia,
    List<EnderecoResponseDTO> enderecos,
    List<CartaoResponseDTO> cartoes,
    List<TelefoneResponseDTO> telefones,
    List<LicorResponseDTO> favoritos,
    List<CompraResponseDTO> compras
) {}
