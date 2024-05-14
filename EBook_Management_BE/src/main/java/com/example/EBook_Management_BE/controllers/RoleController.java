package com.example.EBook_Management_BE.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.example.EBook_Management_BE.components.LocalizationUtils;
import com.example.EBook_Management_BE.dtos.RoleDTO;
import com.example.EBook_Management_BE.entity.Role;
import com.example.EBook_Management_BE.constants.Uri;
import com.example.EBook_Management_BE.mappers.RoleMapper;
import com.example.EBook_Management_BE.responses.RoleResponse;
import com.example.EBook_Management_BE.services.role.IRoleRedisService;
import com.example.EBook_Management_BE.services.role.IRoleService;
import com.example.EBook_Management_BE.utils.MessageKeys;
import com.example.EBook_Management_BE.utils.ResponseObject;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = Uri.ROLE)
@Validated
@RequiredArgsConstructor
public class RoleController {
    private final IRoleService roleService;
    private final IRoleRedisService roleRedisService;
    private final LocalizationUtils localizationUtils;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping()
    public ResponseEntity<ResponseObject> getRoleById(@RequestHeader(name = "role_id") Long roleId) throws Exception {
        Role existingRole = roleService.getRoleById(roleId);

        RoleResponse roleResponse = roleMapper.mapToRoleResponse(existingRole);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_GET_BY_ID_SUCCESSFULLY))
                .data(roleResponse)
                .build());
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_SYS-ADMIN')")
    public ResponseEntity<ResponseObject> createRole(@Valid @RequestBody RoleDTO roleDTO) throws Exception {
        Role role = roleMapper.mapToRoleEntity(roleDTO);

        Role newRole = roleService.createRole(role);
        roleRedisService.saveRoleById(newRole.getId(), newRole);

        RoleResponse roleResponse = roleMapper.mapToRoleResponse(newRole);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.CREATED)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_CREATE_SUCCESSFULLY))
                .data(roleResponse)
                .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYS-ADMIN')")
    public ResponseEntity<ResponseObject> updateRole(@PathVariable Long id, @Valid @RequestBody RoleDTO roleDTO) throws Exception {
        Role role = roleMapper.mapToRoleEntity(roleDTO);

        roleService.updateRole(id, role);
        roleRedisService.saveRoleById(id, role);

        RoleResponse roleResponse = roleMapper.mapToRoleResponse(role);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_UPDATE_SUCCESSFULLY))
                .data(roleResponse)
                .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_SYS-ADMIN')")
    public ResponseEntity<ResponseObject> deleteRole(@PathVariable Long id) throws Exception {
        roleService.deleteRoleById(id);

        return ResponseEntity.ok(ResponseObject.builder()
                .status(HttpStatus.OK)
                .message(localizationUtils.getLocalizedMessage(MessageKeys.ROLE_DELETE_SUCCESSFULLY))
                .build());
    }
}
