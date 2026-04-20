package com.project.ecommerce.exceptions;

public class ResourceAlreadyExistsException extends RuntimeException {
    String resourceName;
    String fieldName;
    String fieldValue;
    public ResourceAlreadyExistsException(String resourceName, String fieldName, String fieldValue) {
        super(String.format("%s already exists with %s : %s", resourceName, fieldName, fieldValue));
        this.resourceName = resourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
