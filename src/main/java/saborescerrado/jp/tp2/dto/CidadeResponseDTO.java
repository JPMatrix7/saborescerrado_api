package saborescerrado.jp.tp2.dto;

public record CidadeResponseDTO(
    Long id,
    String nome,
    EstadoResponseDTO estado
) {}
