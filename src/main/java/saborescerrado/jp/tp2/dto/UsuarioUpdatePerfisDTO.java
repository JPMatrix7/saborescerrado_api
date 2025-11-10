package saborescerrado.jp.tp2.dto;

import java.util.Set;

import saborescerrado.jp.tp2.model.Perfil;

public record UsuarioUpdatePerfisDTO(
    Set<Perfil> perfis
) {}
