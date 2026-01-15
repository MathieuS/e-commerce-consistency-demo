package com.github.mathieus.customerportal.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.mathieus.customerportal.dto.AccountDto;
import com.github.mathieus.customerportal.entity.Account;
import com.github.mathieus.customerportal.entity.Outbox;
import com.github.mathieus.customerportal.mapper.AccountMapper;
import com.github.mathieus.customerportal.repository.AccountRepository;
import com.github.mathieus.customerportal.repository.OutboxRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private OutboxRepository outboxRepository;

    @Autowired
    private ObjectMapper om;

    public Page<AccountDto> findAll(Pageable pageable) {
        return accountRepository
                .findAll(pageable)
                .map(AccountMapper::toDTO);
    }


    @Transactional
    public AccountDto create(@Valid AccountDto dto) {

        Account entity = AccountMapper.toEntity(dto);
        Account saved = accountRepository.save(entity);
        AccountDto savedDto = AccountMapper.toDTO(saved);

        Outbox accountCreated = new Outbox();
        accountCreated.setEventType("portal_account_created");
        try {
            accountCreated.setPayload(om.writeValueAsString(savedDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        outboxRepository.save(accountCreated);

        return savedDto;
    }
}
