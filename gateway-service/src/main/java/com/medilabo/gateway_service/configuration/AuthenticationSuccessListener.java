package com.medilabo.gateway_service.configuration;

import com.medilabo.gateway_service.model.UserCredential;
import com.medilabo.gateway_service.service.UserCredentialService;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserCredentialService userCredentialService;

    public AuthenticationSuccessListener(UserCredentialService userCredentialService) {
        this.userCredentialService = userCredentialService;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        if (authentication.getPrincipal() instanceof OAuth2User oauth2User) {
            String email = oauth2User.getAttribute("email");

            userCredentialService.findUser(email)
                    .switchIfEmpty(userCredentialService.saveUser(new UserCredential(email)))
                    .subscribe();
        }
    }
}
