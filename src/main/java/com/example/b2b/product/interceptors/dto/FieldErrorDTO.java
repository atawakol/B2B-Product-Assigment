package com.example.b2b.product.interceptors.dto;

/**
 * Data value object holder for rest errors.
 *
 * @author atawakol
 */
public class FieldErrorDTO {

    private String field;

    private String error;

    public FieldErrorDTO(String field, String error) {
        this.field = field;
        this.error = error;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
