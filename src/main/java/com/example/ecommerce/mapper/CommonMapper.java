package com.example.ecommerce.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CommonMapper {

    private final ModelMapper mapper;

    <T, S> S convertToEntity(T data, Class<S> type){
        return mapper.map(data, type);
    }

    <T, S> S convertToResponse(T data, Class<S> type){
        return mapper.map(data, type);
    }

    <T, S> List<S> convertToResponseList(List<S> list, Class<S> type){
        return list.stream()
                .map(list1 -> convertToResponse(list, type))
                .collect(Collectors.toList());
    }
}
