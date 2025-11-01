package saborescerrado.jp.tp2.dto;

import java.util.List;

import saborescerrado.jp.tp2.model.TipoLicor;

public record LicorResponseDTO(
    Long id,
    String nome,
    String descricao,
    Double preco,
    Integer estoque,
    List<String> imagens,
    Double estrelas,
    Boolean visivel,
    Double teorAlcoolico,
    TipoLicor tipo,
    SafraLicorResponseDTO safra,
    SaborResponseDTO sabor,
    EmbalagemResponseDTO embalagem,
    ParceiroComercialResponseDTO parceiroComercial,
    List<CategoriaResponseDTO> categorias,
    List<IngredienteResponseDTO> ingredientes,
    List<PremiacaoResponseDTO> premiacoes,
    List<AvaliacaoResponseDTO> avaliacoes
) {}
