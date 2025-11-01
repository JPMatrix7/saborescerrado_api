package saborescerrado.jp.tp2.dto;

public record IngredienteResponseDTO(
    Long id,
    String nome,
    Double quantidade,
    String unidadeMedida
) {}
