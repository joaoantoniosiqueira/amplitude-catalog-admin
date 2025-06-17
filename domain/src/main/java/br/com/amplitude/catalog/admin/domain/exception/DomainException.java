package br.com.amplitude.catalog.admin.domain.exception;

import br.com.amplitude.catalog.admin.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {

    private static final long serialVersionUID = 1L;

    private final List<Error> errors;

    public DomainException(final String aMessage, final List<Error> anErrors) {
        super(aMessage);
        this.errors = anErrors;
    }

    public static DomainException with(final Error anError) {
        return new DomainException(anError.message(), List.of(anError));
    }

    public static DomainException with(final List<Error> anErrors) {
        return new DomainException("", anErrors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
