package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.model.PessoaFisica;

public record PessoaFisicaResponseDTO(
    Long id,
    String nome,
    String sobrenome,
    String email,
    String cpf,
    LocalDate dataNascimento,
    Set<Perfil> perfis,
    Boolean ativo,
    LocalDateTime dataInclusao,
    List<EnderecoResponseDTO> enderecos,
    List<CartaoResponseDTO> cartoes,
    List<TelefoneResponseDTO> telefones,
    List<LicorResponseDTO> favoritos,
    List<CompraResponseDTO> compras
) {
    public static PessoaFisicaResponseDTO valueOf(PessoaFisica pessoaFisica) {
        return new PessoaFisicaResponseDTO(
            pessoaFisica.getId(),
            pessoaFisica.getNome(),
            pessoaFisica.getSobrenome(),
            pessoaFisica.getEmail(),
            pessoaFisica.getCpf(),
            pessoaFisica.getDataNascimento(),
            pessoaFisica.getPerfis(),
            pessoaFisica.getAtivo(),
            pessoaFisica.getDataInclusao(),
            null, // enderecos - simplificado para não quebrar
            null, // cartoes
            null, // telefones
            null, // favoritos
            null  // compras
        );
    }
}
