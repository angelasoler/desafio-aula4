package acc.br.student_registration.service;

import org.springframework.stereotype.Service;

import acc.br.student_registration.entity.Student;
import acc.br.student_registration.repository.StudentRepository;
import acc.br.student_registration.dto.StudentRegistrationDTO;
import acc.br.student_registration.dto.AddressDTO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AddressService addressService;

    public StudentService(StudentRepository studentRepository, AddressService addressService) {
        this.studentRepository = studentRepository;
        this.addressService = addressService;
    }

    @Transactional
    public Student createStudent(StudentRegistrationDTO dto) {
        AddressDTO AddressDTO = addressService.getAddress(dto.getCep());

        Student student = new Student();
        student.setName(dto.getName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setCourse(dto.getCourse());

        student.setCep(AddressDTO.getCep());
        student.setStreet(AddressDTO.getStreet());
        student.setNeighborhood(AddressDTO.getNeighborhood());
        student.setCity(AddressDTO.getCity());
        student.setState(AddressDTO.getState());

        return studentRepository.save(student);
    }

    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Transactional
    public Student updateStudent(Long id, StudentRegistrationDTO dto) {
        Student student = findById(id);
        if (student == null) {
            return null;
        }

        AddressDTO AddressDTO = addressService.getAddress(dto.getCep());

        student.setName(dto.getName());
        student.setDateOfBirth(dto.getDateOfBirth());
        student.setCourse(dto.getCourse());

        student.setCep(AddressDTO.getCep());
        student.setStreet(AddressDTO.getStreet());
        student.setNeighborhood(AddressDTO.getNeighborhood());
        student.setCity(AddressDTO.getCity());
        student.setState(AddressDTO.getState());

        return studentRepository.save(student);
    }
}
