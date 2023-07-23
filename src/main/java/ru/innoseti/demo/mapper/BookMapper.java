package ru.innoseti.demo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ru.innoseti.demo.dto.BookInput;
import ru.innoseti.demo.model.Book;


@Component
@Mapper
public interface BookMapper {

    BookMapper INSTANCE = Mappers.getMapper(BookMapper.class);

    @Mapping(target = "authors", ignore = true)
    Book toEntity(BookInput book);

}
