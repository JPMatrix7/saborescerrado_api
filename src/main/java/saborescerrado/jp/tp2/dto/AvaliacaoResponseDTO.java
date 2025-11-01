package saborescerrado.jp.tp2.dto;

public record AvaliacaoResponseDTO(
    Long id,
    Integer estrelas,
    String comentario,
    UsuarioResponseDTO usuario
) {}
