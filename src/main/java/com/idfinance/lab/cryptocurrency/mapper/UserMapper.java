package com.idfinance.lab.cryptocurrency.mapper;

import com.idfinance.lab.cryptocurrency.dto.UserCreateDto;
import com.idfinance.lab.cryptocurrency.dto.UserDto;
import com.idfinance.lab.cryptocurrency.model.User;
import org.mapstruct.Mapper;

/**
 * The {@link UserMapper} interface provides mapping operations between {@link UserCreateDto} and {@link User},
 * as well as between {@link User} and {@link UserDto}.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Maps the {@link UserCreateDto} object to the {@link User} object.
     * @param userCreateDto The {@link UserCreateDto} object to map.
     * @return The mapped {@link User} entity.
     */
    User mapFromDto(UserCreateDto userCreateDto);

    /**
     * Maps a {@link User} object to a {@link UserDto} object.
     * @param user The {@link User} entity to map.
     * @return The mapped {@link UserDto}.
     */
    UserDto mapToDto(User user);
}
