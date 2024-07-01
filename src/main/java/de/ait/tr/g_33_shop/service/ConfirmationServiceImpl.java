package de.ait.tr.g_33_shop.service;

import de.ait.tr.g_33_shop.domain.entity.ConfirmationCode;
import de.ait.tr.g_33_shop.domain.entity.User;
import de.ait.tr.g_33_shop.repository.ConfirmationCodeRepository;
import de.ait.tr.g_33_shop.service.interfaces.ConfirmationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ConfirmationServiceImpl implements ConfirmationService {

    private final ConfirmationCodeRepository repository;

    public ConfirmationServiceImpl(ConfirmationCodeRepository repository) {
        this.repository = repository;
    }

    @Override
    public String generateConfirmationCode(User user) {
//        LocalDateTime expired = LocalDateTime.now().plusDays(1);

        // Для целей тестирования:
        LocalDateTime expired = LocalDateTime.now().plusMinutes(10);
        String code = UUID.randomUUID().toString();
        ConfirmationCode entity = new ConfirmationCode(code, expired, user);
        repository.save(entity);
        return code;
    }
}