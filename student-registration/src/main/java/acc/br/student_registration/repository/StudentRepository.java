package acc.br.student_registration.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import acc.br.student_registration.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
