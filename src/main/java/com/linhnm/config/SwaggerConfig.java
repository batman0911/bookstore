package com.linhnm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(info = @Info(title = "bookstore", version = "v1"))
class SwaggerConfig implements OperationCustomizer {
    @Override
    public Operation customize(Operation operation, HandlerMethod handlerMethod) {
        Parameter parameterHeader = new Parameter()
                .in(ParameterIn.QUERY.toString())
                .description("Enter media type: json or hal. (Default: json, hal = \"application/hal+json\" with link)")
                .required(false)
                .name("mediaType")
                .example("hal")
                .schema(new StringSchema()
                        .addEnumItem("hal")
                        .addEnumItem("json")
                        .addEnumItem("xml")
                        ._default("json"));

        operation.addParametersItem(parameterHeader);
        return operation;
    }
}
