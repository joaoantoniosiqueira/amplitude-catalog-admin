package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.application.UseCase;
import br.com.amplitude.catalog.admin.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
