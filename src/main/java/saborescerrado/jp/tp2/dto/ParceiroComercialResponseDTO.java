package saborescerrado.jp.tp2.dto;

import java.util.List;

public record ParceiroComercialResponseDTO(
    Long id,
    String cnpj,
    String razaoSocial,
    String nomeFantasia,
    String email,
    String telefone,
    List<EnderecoResponseDTO> enderecos
) {}
