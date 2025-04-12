package ma.enset.hospital.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.hospital.entities.Patient;
import ma.enset.hospital.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class PatientController {
    private PatientRepository patientRepository;

    @GetMapping(path = "/user/index")
    public String index(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "4") int size,
            @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        Page<Patient> patientsPage = patientRepository.findByNameContains(keyword, PageRequest.of(page, size));
        model.addAttribute("patients", patientsPage.getContent());
        model.addAttribute("pages", new int[patientsPage.getTotalPages()]);
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping(path = "/admin/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(Long id, String keyword, int page) {
        patientRepository.deleteById(id);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/")
    public String home() {
        return "redirect:/user/index";
    }

    @GetMapping(path = "/admin/patientForm")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String patientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patientForm";
    }

    @PostMapping(path = "/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String save(
            Model model,
            @Valid Patient patient,
            BindingResult bindingResult,
            @RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page
    ) {
        if (bindingResult.hasErrors()) {
            return "patientForm";
        }
        patientRepository.save(patient);
        return "redirect:/user/index?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping(path = "/admin/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String edit(Model model, @PathVariable("id") Long id, String keyword, int page) {
        Patient patient = patientRepository.findById(id).orElse(null);
        if (patient == null) {
            return "redirect:/user/index";
        }
        model.addAttribute("patient", patient);
        model.addAttribute("keyword", keyword);
        model.addAttribute("page", page);
        return "patientUpdateForm";
    }

    @GetMapping(path = "/login")
    public String login() {
        return "login";
    }

    @GetMapping(path = "/notAuthorized")
    public String notAuthorized() {
        return "notAuthorized";
    }

}
