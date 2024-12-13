package acc.br.student_registration.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.ui.Model;

import org.springframework.security.core.Authentication;

import acc.br.student_registration.entity.Student;
import acc.br.student_registration.service.StudentService;
import acc.br.student_registration.dto.StudentRegistrationDTO;

@Controller
@SessionAttributes("registrationData")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @ModelAttribute("registrationData")
    public StudentRegistrationDTO registrationData() {
        return new StudentRegistrationDTO();
    }

    @GetMapping("/student/form")
    public String showForm(@ModelAttribute("registrationData") StudentRegistrationDTO registrationDto) {
        return "form";
    }

    @PostMapping("/student/submit")
    public String submitForm(@ModelAttribute("registrationData") StudentRegistrationDTO registrationDto, 
                             @RequestParam String name,
                             @RequestParam String course,
                             @RequestParam String cep,
                             @RequestParam("dateOfBirth") String dob) {
        registrationDto.setName(name);
        registrationDto.setCourse(course);
        registrationDto.setCep(cep);
        registrationDto.setDateOfBirth(LocalDate.parse(dob));
        return "redirect:/student/review";
    }

    @GetMapping("/student/review")
    public String reviewRegistration(@ModelAttribute("registrationData") StudentRegistrationDTO registrationDto, Model model) {
        model.addAttribute("registrationData", registrationDto);
        return "review";
    }

    @PostMapping("/student/confirm")
    public String confirmRegistration(@ModelAttribute("registrationData") StudentRegistrationDTO registrationDto, Model model) {
        Student saved = studentService.createStudent(registrationDto);
        model.addAttribute("registrationData", new StudentRegistrationDTO());
        return "success";
    }

    @PostMapping("/student/edit")
    public String editRegistration(@ModelAttribute("registrationData") StudentRegistrationDTO registrationDto, Model model) {
        return "form";
    }

    @PostMapping("/student/cancel")
    public String cancelRegistration(Model model) {
        model.addAttribute("registrationData", new StudentRegistrationDTO());
        return "form";
    }
}
