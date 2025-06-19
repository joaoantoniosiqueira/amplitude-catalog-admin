package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.domain.category.Category;
import br.com.amplitude.catalog.admin.domain.category.CategoryGateway;
import br.com.amplitude.catalog.admin.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway aCategoryGateway) {
        Objects.requireNonNull(aCategoryGateway);
        this.categoryGateway = aCategoryGateway;
    }

    @Override
    public CreateCategoryOutput execute(CreateCategoryCommand aCommand) {
        final var aName = aCommand.name();
        final var aDescription = aCommand.description();
        final var isActive = aCommand.isActive();

        final var aCategory = Category.newCategory(aName, aDescription, isActive);
        aCategory.validate(new ThrowsValidationHandler());

        this.categoryGateway.create(aCategory);

        return CreateCategoryOutput.from(aCategory.getId());
    }
}
