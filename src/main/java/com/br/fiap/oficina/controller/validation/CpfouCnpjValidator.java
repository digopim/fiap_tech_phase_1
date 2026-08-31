package com.br.fiap.oficina.controller.validation;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class CpfouCnpjValidator implements ConstraintValidator<CpfouCnpj, String> {
    private final CPFValidator cpfValidator = new CPFValidator();
    private final CNPJValidator cnpjValidator = new CNPJValidator();

    @Override
    public boolean isValid(String valor, ConstraintValidatorContext contexto) {
        if (valor == null || valor.isBlank()) return false;
        try {
            cpfValidator.assertValid(valor);
        } catch (Exception _) {
            try {
                cnpjValidator.assertValid(valor);
            } catch (Exception _) { return false; }
        }
        return true;
    }

}
