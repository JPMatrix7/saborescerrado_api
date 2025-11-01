package saborescerrado.jp.tp2.dto;

import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record PessoaJuridicaDTO(
    String nome,
    String email,
    String senha,
    Set<Perfil> perfis,
    String cnpj,
    String razaoSocial,
    String nomeFantasia,
    List<Long> enderecosIds,
    List<Long> cartoesIds,
    List<Long> telefonesIds,
    List<Long> favoritosIds
) {}
