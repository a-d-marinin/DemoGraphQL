package ru.innoseti.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.innoseti.demo.dto.AuthorInput;
import ru.innoseti.demo.model.Author;

@Component
@Mapper
public interface AuthorMapper {

    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    @Mapping(target = "books", ignore = true)
    Author toEntity(AuthorInput author);

}
