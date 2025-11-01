package saborescerrado.jp.tp2.dto;

public record EnderecoResponseDTO(
    Long id,
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep,
    CidadeResponseDTO cidade
) {}
