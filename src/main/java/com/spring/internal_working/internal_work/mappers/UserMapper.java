package com.spring.internal_working.internal_work.mappers;


import com.spring.internal_working.internal_work.dtos.RegisterUserRequest;
import com.spring.internal_working.internal_work.dtos.UpdateUserRequest;
import com.spring.internal_working.internal_work.dtos.UserDto;
import com.spring.internal_working.internal_work.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(RegisterUserRequest request);
    void updateEntity(UpdateUserRequest userDto, @MappingTarget User user);

}
