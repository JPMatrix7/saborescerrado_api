package saborescerrado.jp.tp2.dto;

public record EnderecoDTO(
    String logradouro,
    String numero,
    String complemento,
    String bairro,
    String cep,
    Long cidadeId
) {}
