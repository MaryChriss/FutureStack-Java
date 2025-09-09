package br.com.fiap.sprint3.auth;

import br.com.fiap.sprint3.users.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final UserService userService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();

        if (auth instanceof OAuth2AuthenticationToken token && auth.getPrincipal() instanceof OAuth2User oUser) {
            String provider = token.getAuthorizedClientRegistrationId(); // "github" | "google"
            userService.registerOAuth(provider, oUser.getAttributes());
        }
        // se não for OAuth2, ignore
    }
}