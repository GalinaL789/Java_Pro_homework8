package de.ait.tr.g_33_shop.service.interfaces;

import de.ait.tr.g_33_shop.domain.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void register(User user);

    void validateCode(String code);
}
