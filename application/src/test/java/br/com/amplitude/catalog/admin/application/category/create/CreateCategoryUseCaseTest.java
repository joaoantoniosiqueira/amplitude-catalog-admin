package br.com.amplitude.catalog.admin.application.category.create;

import br.com.amplitude.catalog.admin.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.AdditionalAnswers.*;
import static org.mockito.ArgumentMatchers.*;
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

        final var actualOutput = useCase.execute(aCommand).get();

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

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

        Mockito.verify(categoryGateway, times(0)).create(any());
    }

    @Test
    public void shouldReturnCreatedDeactivateCategoryWhenGivenValidCommandWithIsActivateFalse() {
        final var expectedName = "Movies";
        final var expectedDescription = "All about movies";
        final var expectedIsActive = false;

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand).get();

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

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "Gateway error";

        final var aCommand = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(categoryGateway.create(any())).thenThrow(new IllegalStateException("Gateway error"));

        final var notification = useCase.execute(aCommand).getLeft();

        Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

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
