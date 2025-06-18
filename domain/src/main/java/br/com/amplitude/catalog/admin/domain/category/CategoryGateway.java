package br.com.amplitude.catalog.admin.domain.category;

import br.com.amplitude.catalog.admin.domain.pagination.Pagination;

import java.util.Optional;

public interface CategoryGateway {

    Category create(Category aCategory);

    Optional<Category> findById(CategoryID anId);

    Category update(Category aCategory);

    void deleteById(CategoryID anId);

    Pagination<Category> findAll(CategorySearchQuery aQuery);
}
