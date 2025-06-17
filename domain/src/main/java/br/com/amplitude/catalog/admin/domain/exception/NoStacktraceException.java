package br.com.amplitude.catalog.admin.domain.exception;

public class NoStacktraceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public NoStacktraceException(final String message) {
        super(message, null);
    }

    public NoStacktraceException(final String message, final Throwable cause) {
        super(message, cause, false, false);
    }
}
