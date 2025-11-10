package saborescerrado.jp.tp2.dto;

import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.validation.ValidCNPJ;

public record PessoaJuridicaDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    String nome,
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    String email,
    
    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    String senha,
    
    Set<Perfil> perfis,
    
    @NotBlank(message = "CNPJ é obrigatório")
    @ValidCNPJ
    String cnpj,
    
    @NotBlank(message = "Razão social é obrigatória")
    @Size(min = 3, max = 150, message = "Razão social deve ter entre 3 e 150 caracteres")
    String razaoSocial,
    
    @NotBlank(message = "Nome fantasia é obrigatório")
    @Size(min = 3, max = 150, message = "Nome fantasia deve ter entre 3 e 150 caracteres")
    String nomeFantasia,
    
    List<Long> enderecosIds,
    List<Long> cartoesIds,
    List<Long> telefonesIds,
    List<Long> favoritosIds
) {}
