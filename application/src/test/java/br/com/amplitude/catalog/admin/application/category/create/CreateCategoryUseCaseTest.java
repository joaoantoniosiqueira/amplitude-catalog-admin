package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

public class CreateCategoryUseCaseTest {

    @Test
    public void shouldReturnCreatedCategoryWhenGivenValidCommand() {
        final var expectedName = "Movies";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var categoryGateway = Mockito.mock(CategoryGateway.class);
        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).create(
                argThat(aCategory ->
                        aCategory.getId() != null &&
                                expectedName.equals(aCategory.getName()) &&
                                expectedDescription.equals(aCategory.getDescription()) &&
                                expectedIsActive == aCategory.isActive() &&
                                aCategory.getCreatedAt() != null &&
                                aCategory.getUpdatedAt() != null &&
                                aCategory.getDeletedAt() == null
                ));
    }
}
