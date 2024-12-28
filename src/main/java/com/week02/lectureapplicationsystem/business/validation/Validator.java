package com.week02.lectureapplicationsystem.business.validation;

public interface Validator<T, E> {
    void validate(T t, E e);
}
