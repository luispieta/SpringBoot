package med.voll.api.domain.paciente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

    @Query("""
            SELECT m.ativo FROM medico m WHERE m.id = :id
            """)
    Boolean findAtivoById(Long idPaciente);

}
