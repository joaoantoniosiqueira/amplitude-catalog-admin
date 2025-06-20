package br.com.amplitude.catalog.admin.domain.category;

import br.com.amplitude.catalog.admin.domain.AggregateRoot;
import br.com.amplitude.catalog.admin.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> {

    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(final CategoryID anId,
                     final String aName,
                     final String aDescription,
                     final boolean isActive,
                     final Instant aCreatedAt,
                     final Instant anUpdatedAt,
                     final Instant aDeletedAt) {
        super(anId);
        this.name = aName;
        this.description = aDescription;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = anUpdatedAt;
        this.deletedAt = aDeletedAt;
    }

    public static Category newCategory(final String aName, final String aDescription, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;
        return new Category(id, aName, aDescription, isActive, now, now, deletedAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate() {
        if (!this.active) {
            this.active = true;
            final var now = Instant.now();
            this.updatedAt = now;
            this.deletedAt = null; // Reset deletedAt when activating
        }
        return this;
    }

    public Category deactivate() {
        if (this.active) {
            this.active = false;
            final var now = Instant.now();
            this.updatedAt = now;
            this.deletedAt = now;
        }
        return this;
    }

    public Category update(final String aName, final String aDescription, final boolean isActive) {
        this.name = aName;
        this.description = aDescription;
        if (this.active != isActive) {
            if (isActive) {
                activate();
            } else {
                deactivate();
            }
        }
        this.updatedAt = Instant.now();
        return this;
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}