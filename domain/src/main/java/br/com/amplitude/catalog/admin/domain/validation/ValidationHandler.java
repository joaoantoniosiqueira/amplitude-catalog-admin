package br.com.amplitude.catalog.admin.domain.validation;

import java.util.List;

public interface ValidationHandler {

    ValidationHandler append(Error anError);

    ValidationHandler append(ValidationHandler aHandler);

    ValidationHandler validate(Validation validation);

    default boolean hasErrors() {
        return getErrors() != null && !getErrors().isEmpty();
    }

    default Error firstError() {
        return hasErrors() ? getErrors().getFirst() : null;
    }

    List<Error> getErrors();

    interface Validation {
        void validate();
    }
}
