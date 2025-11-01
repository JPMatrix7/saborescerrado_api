package saborescerrado.jp.tp2.dto;

public record CartaoResponseDTO(
    Long id,
    String numeroCartao,
    String nomeTitular,
    String validade
) {}
