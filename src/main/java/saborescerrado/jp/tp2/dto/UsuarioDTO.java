package saborescerrado.jp.tp2.dto;

import java.util.List;
import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record UsuarioDTO(
    String nome,
    String email,
    String senha,
    Set<Perfil> perfis,
    List<Long> enderecosIds,
    List<Long> cartoesIds,
    List<Long> telefonesIds,
    List<Long> favoritosIds
) {}
