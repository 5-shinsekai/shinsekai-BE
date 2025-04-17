package com.example.shinsekai.util;

import org.springframework.core.MethodParameter;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.Optional;

public class OneBasedPageableResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OneBasedPageable.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        int page = Integer.parseInt(Optional.ofNullable(webRequest.getParameter("page")).orElse("1"));
        int size = Integer.parseInt(Optional.ofNullable(webRequest.getParameter("size")).orElse("10"));
        return PageRequest.of(page - 1, size);
    }
}