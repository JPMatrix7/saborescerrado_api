package saborescerrado.jp.tp2.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TelefoneValidator implements ConstraintValidator<ValidTelefone, String> {

    @Override
    public void initialize(ValidTelefone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String telefone, ConstraintValidatorContext context) {
        if (telefone == null || telefone.isEmpty()) {
            return true; // Use @NotNull ou @NotBlank para validar nulo
        }

        // Remove caracteres não numéricos
        String numeros = telefone.replaceAll("[^0-9]", "");

        // Verifica se tem exatamente 11 dígitos (2 DDD + 9 número)
        if (numeros.length() != 11) {
            return false;
        }

        // Verifica se o terceiro dígito é 9 (celular)
        // Formato: (DD) 9XXXX-XXXX
        if (numeros.charAt(2) != '9') {
            return false;
        }

        // Verifica se o DDD é válido (11 a 99)
        try {
            int ddd = Integer.parseInt(numeros.substring(0, 2));
            if (ddd < 11 || ddd > 99) {
                return false;
            }
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
