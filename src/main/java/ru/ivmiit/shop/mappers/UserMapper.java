package ru.ivmiit.shop.mappers;

import ru.ivmiit.shop.dto.request.UserRequest;
import ru.ivmiit.shop.dto.response.UserResponse;
import ru.ivmiit.shop.entities.UserEntity;

public interface UserMapper extends BaseMapper<UserEntity, UserRequest, UserResponse> {
}
