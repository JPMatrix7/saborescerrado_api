package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record PessoaFisicaDTO(
    String nome,
    String email,
    String senha,
    Set<Perfil> perfis,
    String cpf,
    LocalDate dataNascimento,
    String sobrenome,
    List<Long> enderecosIds,
    List<Long> cartoesIds,
    List<Long> telefonesIds,
    List<Long> favoritosIds
) {}
