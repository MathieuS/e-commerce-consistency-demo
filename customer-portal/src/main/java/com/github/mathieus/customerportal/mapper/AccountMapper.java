package com.github.mathieus.customerportal.mapper;


import com.github.mathieus.customerportal.dto.AccountDto;
import com.github.mathieus.customerportal.entity.Account;

public class AccountMapper {

    public static AccountDto toDTO(Account entity) {
        if (entity == null) {
            return null;
        }

        AccountDto dto = new AccountDto();
        dto.setEmail(entity.getEmail());
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setErpId(entity.getErpId());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setUpdatedAt(entity.getUpdatedAt());

        return dto;
    }

    public static void updateEntity(Account entity, AccountDto dto) {
        if (entity == null || dto == null) {
            return;
        }

        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getFirstName() != null) {
            entity.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            entity.setLastName(dto.getLastName());
        }
        entity.setErpId(dto.getErpId());
    }

    public static Account toEntity(AccountDto dto) {
        if (dto == null) {
            return null;
        }

        Account entity = new Account();
        entity.setEmail(dto.getEmail());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setErpId(dto.getErpId());

        return entity;
    }
}