package ma.enset.hospital;

import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repository.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class Lab3SpringMvcDataJpaThymeleafApplication implements CommandLineRunner {

    final
    PatientRepository patientRepository;

    public Lab3SpringMvcDataJpaThymeleafApplication(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Lab3SpringMvcDataJpaThymeleafApplication.class, args);
    }

    @Override
    public void run(String... args) {
        patientRepository.save(Patient.builder()
                .name("Hassan")
                .birthDate(new Date())
                .sick(false)
                .score(32)
                .build());

        patientRepository.save(Patient.builder()
                .name("Rachid")
                .birthDate(new Date())
                .sick(true)
                .score(10)
                .build());

        patientRepository.save(Patient.builder()
                .name("Loubna")
                .birthDate(new Date())
                .sick(false)
                .score(3)
                .build());
    }
}
