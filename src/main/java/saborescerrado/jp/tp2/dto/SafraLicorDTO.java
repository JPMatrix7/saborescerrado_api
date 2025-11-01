package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;

public record SafraLicorDTO(
    LocalDate anoSafra,
    String fazenda,
    String qualidade,
    Long cidadeId
) {}
