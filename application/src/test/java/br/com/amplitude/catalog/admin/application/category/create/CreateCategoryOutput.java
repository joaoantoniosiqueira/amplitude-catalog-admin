package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.domain.category.CategoryID;

public record CreateCategoryOutput(CategoryID id) {

    public static CreateCategoryOutput from(final CategoryID anId) {
        return new CreateCategoryOutput(anId);
    }
}
