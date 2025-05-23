package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repository.PatientRepository;
import ma.enset.hospital.security.service.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

import java.util.Date;

@SpringBootApplication
public class Lab3SpringMvcDataJpaThymeleafApplication {

    final
    PatientRepository patientRepository;

    public Lab3SpringMvcDataJpaThymeleafApplication(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab3SpringMvcDataJpaThymeleafApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // @Bean
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(Patient.builder()
                    .name("Hassan")
                    .birthDate(new Date())
                    .sick(false)
                    .score(132)
                    .build());

            patientRepository.save(Patient.builder()
                    .name("Rachid")
                    .birthDate(new Date())
                    .sick(true)
                    .score(110)
                    .build());

            patientRepository.save(Patient.builder()
                    .name("Loubna")
                    .birthDate(new Date())
                    .sick(false)
                    .score(123)
                    .build());
        };
    }

    // @Bean
    CommandLineRunner start(JdbcUserDetailsManager jdbcUserDetailsManager) {
        return args -> {
            String encodedPassword = passwordEncoder().encode("1234");

            if (!jdbcUserDetailsManager.userExists("user1")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user1").password(encodedPassword).roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("user2")) {
                jdbcUserDetailsManager.createUser(User.withUsername("user2").password(encodedPassword).roles("USER").build());
            }

            if (!jdbcUserDetailsManager.userExists("admin")) {
                jdbcUserDetailsManager.createUser(User.withUsername("admin").password(encodedPassword).roles("USER", "ADMIN").build());
            }
        };
    }

    @Bean
    CommandLineRunner CommandLineRunnerUserDetails(AccountService accountService) {
        return args -> {
            if (accountService.loadRole("USER") == null) {
                accountService.addNewRole("USER");
            }
            if (accountService.loadRole("ADMIN") == null) {
                accountService.addNewRole("ADMIN");
            }
            if (accountService.loadUserByUsername("user1") == null) {
                accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
                accountService.addRoleToUser("user1", "USER");
            }

            if (accountService.loadUserByUsername("user2") == null) {
                accountService.addNewUser("user2", "1234", "user2@gmail.com", "1234");
                accountService.addRoleToUser("user2", "USER");
            }

            if (accountService.loadUserByUsername("admin") == null) {
                accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");
                accountService.addRoleToUser("admin", "USER");
                accountService.addRoleToUser("admin", "ADMIN");
            }
        };
    }
}

