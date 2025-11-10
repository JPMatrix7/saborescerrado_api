package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.validation.ValidCPF;

public record PessoaFisicaDTO(
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
    
    @NotBlank(message = "CPF é obrigatório")
    @ValidCPF
    String cpf,
    
    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    LocalDate dataNascimento,
    
    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(min = 2, max = 100, message = "Sobrenome deve ter entre 2 e 100 caracteres")
    String sobrenome,
    
    List<Long> enderecosIds,
    List<Long> cartoesIds,
    List<Long> telefonesIds,
    List<Long> favoritosIds
) {}
