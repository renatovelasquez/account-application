package dev.renvl.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumListValidator implements ConstraintValidator<ListMatchesEnum, List<String>> {
    private Set<String> acceptedValues;

    @Override
    public void initialize(ListMatchesEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(List<String> values, ConstraintValidatorContext context) {
        if (values == null) {
            return true;
        }
        return acceptedValues.stream().anyMatch(values::contains);
    }
}
