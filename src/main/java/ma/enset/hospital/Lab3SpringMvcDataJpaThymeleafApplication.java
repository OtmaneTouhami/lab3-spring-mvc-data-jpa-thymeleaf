package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
}
