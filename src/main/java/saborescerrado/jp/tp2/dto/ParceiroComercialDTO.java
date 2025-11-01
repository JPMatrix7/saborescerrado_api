package saborescerrado.jp.tp2.dto;

import java.util.List;

public record ParceiroComercialDTO(
    String cnpj,
    String razaoSocial,
    String nomeFantasia,
    String email,
    String telefone,
    List<Long> enderecosIds
) {}
