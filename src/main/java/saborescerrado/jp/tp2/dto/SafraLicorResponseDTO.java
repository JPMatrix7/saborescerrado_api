package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;

public record SafraLicorResponseDTO(
    Long id,
    LocalDate anoSafra,
    String fazenda,
    String qualidade,
    CidadeResponseDTO cidade
) {}
