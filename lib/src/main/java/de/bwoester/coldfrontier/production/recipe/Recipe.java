package de.bwoester.coldfrontier.production.recipe;

import de.bwoester.coldfrontier.resources.ResourceAmounts;

import java.util.Objects;

/** Inputs and outputs for one completed operation. Processing capacity is supplied separately. */
public record Recipe(ResourceAmounts inputs, ResourceAmounts outputs) {

    public Recipe {
        Objects.requireNonNull(inputs);
        if (outputs.amounts().isEmpty()) {
            throw new IllegalArgumentException("A recipe must produce at least one resource");
        }
    }
}
