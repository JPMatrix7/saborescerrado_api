package saborescerrado.jp.tp2.dto;

public record ItemCompraResponseDTO(
    Long id,
    Integer quantidade,
    Double precoUnitario,
    LicorResponseDTO licor
) {}
