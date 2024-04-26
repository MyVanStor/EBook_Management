package com.example.EBook_Management_BE.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.factory.Mappers;

import com.example.EBook_Management_BE.dtos.FollowDTO;
import com.example.EBook_Management_BE.entity.Follow;
import com.example.EBook_Management_BE.responses.FollowResponse;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface FollowMapper {
	FollowMapper INSTANCE = Mappers.getMapper(FollowMapper.class);

	Follow mapToFollowEntity(FollowDTO followDTO);

	FollowResponse mapToFollowResponse(Follow follow);
}
