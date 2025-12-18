package org.example.sistema_gestion_vitalexa.config;

import lombok.RequiredArgsConstructor;
import org.example.sistema_gestion_vitalexa.entity.User;
import org.example.sistema_gestion_vitalexa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PasswordMigrationRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(PasswordMigrationRunner.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) return;

        for (User u : users) {
            String pw = u.getPassword();
            if (pw == null) continue;

            // Detect simple bcrypt prefix ($2a$, $2b$, $2y$). If no prefix, asumimos texto plano y lo codificamos.
            if (!pw.startsWith("$2a$") && !pw.startsWith("$2b$") && !pw.startsWith("$2y$")) {
                log.info("Migrating password for user={} (detected non-bcrypt). Encoding now.", u.getUsername());
                u.setPassword(passwordEncoder.encode(pw));
                userRepository.save(u);
            } else {
                log.debug("Password for user={} already looks bcrypt, skipping.", u.getUsername());
            }
        }

        log.info("Password migration finished for {} users.", users.size());
    }
}

