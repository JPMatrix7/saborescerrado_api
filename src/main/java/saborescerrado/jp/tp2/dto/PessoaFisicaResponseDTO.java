package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record PessoaFisicaResponseDTO(
    Long id,
    String nome,
    String email,
    Set<Perfil> perfis,
    String cpf,
    LocalDate dataNascimento,
    String sobrenome,
    List<EnderecoResponseDTO> enderecos,
    List<CartaoResponseDTO> cartoes,
    List<TelefoneResponseDTO> telefones,
    List<LicorResponseDTO> favoritos,
    List<CompraResponseDTO> compras
) {}
