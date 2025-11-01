package saborescerrado.jp.tp2.dto;

import java.util.List;

import saborescerrado.jp.tp2.model.TipoLicor;

public record LicorDTO(
    String nome,
    String descricao,
    Double preco,
    Integer estoque,
    List<String> imagens,
    Boolean visivel,
    Double teorAlcoolico,
    TipoLicor tipo,
    Long safraId,
    Long saborId,
    Long embalagemId,
    Long parceiroComercialId,
    List<Long> categoriasIds,
    List<Long> ingredientesIds,
    List<Long> premiacoesIds
) {}
