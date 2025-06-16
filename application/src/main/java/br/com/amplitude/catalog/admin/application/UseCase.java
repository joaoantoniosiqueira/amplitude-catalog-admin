package br.com.amplitude.catalog.admin.application;

import br.com.amplitude.catalog.admin.domain.category.Category;

public class UseCase {

    public Category execute() {
        return Category.newCategory("Movies", "The category with the best movies", true);
    }
}