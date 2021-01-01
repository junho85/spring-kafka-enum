package kr.pe.junho85.demo.model;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum TestType {
    ALPHA,
    BETA,
    @JsonEnumDefaultValue UNKNOWN;
}
