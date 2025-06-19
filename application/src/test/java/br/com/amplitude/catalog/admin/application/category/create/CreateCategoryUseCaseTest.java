package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.domain.category.CategoryGateway;
import br.com.amplitude.catalog.admin.domain.exception.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    public void shouldReturnCreatedCategoryWhenGivenValidCommand() {
        final var expectedName = "Movies";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = true;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

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

    @Test
    public void shouldThrowDomainExceptionWhenGivenCommandWithInvalidName() {
        final var expectedName = "Mo";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";
        final var expectedErrorCount = 1;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        final var actualException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void shouldReturnCreatedDeactivateCategoryWhenGivenValidCommandWithIsActivateFalse() {
        final var expectedName = "Movies";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

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
                                aCategory.getDeletedAt() != null
                ));
    }

    @Test
    public void shouldReturnRandomExceptionWhenThrowsRandomException() {
        final var expectedName = "Movies";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(any())).thenThrow(new IllegalStateException("Gateway error"));

        final var actualException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aCommand));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

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
