package br.com.fiap.sprint3.users;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class UserService {

    public void registerOAuth(String provider, Map<String, Object> attrs) {
        if (attrs == null) return;

        String email = str(attrs, "email");
        String name;
        String avatar;

        if ("github".equalsIgnoreCase(provider)) {
            name = coalesce(str(attrs, "name"), str(attrs, "login"));
            avatar = str(attrs, "avatar_url");

            if (email == null) {
                String login = str(attrs, "login");
                if (login != null) email = login + "@users.noreply.github.com";
            }
        } else if ("google".equalsIgnoreCase(provider)) {
            name = coalesce(str(attrs, "name"), str(attrs, "given_name"));
            avatar = str(attrs, "picture");
        } else {
            name = coalesce(str(attrs, "name"), str(attrs, "login"));
            avatar = str(attrs, "avatar_url");
        }
    }

    private String str(Map<String, Object> m, String k) {
        Object v = m.get(k);
        return v != null ? v.toString() : null;
    }

    private String coalesce(String... vals) {
        for (String v : vals) if (v != null && !v.isBlank()) return v;
        return null;
    }
}
