package br.com.amplitude.catalog.admin.domain.category;

import br.com.amplitude.catalog.admin.domain.validation.Error;
import br.com.amplitude.catalog.admin.domain.validation.ValidationHandler;
import br.com.amplitude.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 255;

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = category.getName();
        if (name == null) {
            validationHandler().append(new Error("'name' should not be null"));
            return;
        }

        if (name.trim().isEmpty()) {
            validationHandler().append(new Error("'name' should not be empty"));
            return;
        }

        int length = name.trim().length();
        if (length < NAME_MIN_LENGTH || length > NAME_MAX_LENGTH) {
            validationHandler().append(new Error("'name' must be between 3 and 255 characters"));
        }
    }
}
