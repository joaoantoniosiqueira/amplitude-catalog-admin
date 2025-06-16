package br.com.amplitude.catalog.admin.domain.category;

import br.com.amplitude.catalog.admin.domain.validation.Error;
import br.com.amplitude.catalog.admin.domain.validation.ValidationHandler;
import br.com.amplitude.catalog.admin.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category aCategory, final ValidationHandler aHandler) {
        super(aHandler);
        this.category = aCategory;
    }

    @Override
    public void validate() {
        if (category.getName() == null || category.getName().isBlank()) {
            validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
