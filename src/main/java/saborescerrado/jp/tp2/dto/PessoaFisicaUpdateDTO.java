package saborescerrado.jp.tp2.dto;

import java.time.LocalDate;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.validation.ValidCPF;

public record PessoaFisicaUpdateDTO(
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    String nome,

    @NotBlank(message = "Sobrenome é obrigatório")
    @Size(min = 2, max = 100, message = "Sobrenome deve ter entre 2 e 100 caracteres")
    String sobrenome,

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    String email,

    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    String senha,  // Opcional no update

    @NotBlank(message = "CPF é obrigatório")
    @ValidCPF
    String cpf,

    @NotNull(message = "Data de nascimento é obrigatória")
    @Past(message = "Data de nascimento deve ser no passado")
    LocalDate dataNascimento,

    @NotNull(message = "Perfil é obrigatório")
    Set<Perfil> perfis
) {}
