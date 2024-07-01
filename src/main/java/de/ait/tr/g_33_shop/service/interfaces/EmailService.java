package de.ait.tr.g_33_shop.service.interfaces;

import de.ait.tr.g_33_shop.domain.entity.User;

public interface EmailService {

    void sendConfirmationEmail(User user);
}
