package saborescerrado.jp.tp2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record TelefoneDTO(
    @NotBlank(message = "Código de área é obrigatório")
    @Pattern(regexp = "^\\d{2}$", message = "Código de área deve ter exatamente 2 dígitos")
    String codigoArea,
    
    @NotBlank(message = "Número de telefone é obrigatório")
    @Pattern(regexp = "^9\\d{8}$", message = "Número deve ter 9 dígitos e começar com 9")
    String numero
) {}
