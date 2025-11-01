package saborescerrado.jp.tp2.dto;

public record CartaoDTO(
    String numeroCartao,
    String nomeTitular,
    String validade,
    String cvv
) {}
