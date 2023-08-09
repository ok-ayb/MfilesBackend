package io.xhub.smwall.service;

import io.xhub.smwall.domains.Authority;
import io.xhub.smwall.exceptions.BusinessException;
import io.xhub.smwall.repositories.AuthorityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class AuthorityService {
    private final AuthorityRepository authorityRepository;

    public List<Authority> getAllAuthorities() throws BusinessException {
        log.info("Start getting all authorities");
        return authorityRepository.findAll();
    }
}
