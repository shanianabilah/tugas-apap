package apap.tugas.sibat.repository;

import apap.tugas.sibat.model.JenisModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JenisDb extends JpaRepository<JenisModel,Long> {
    List<JenisModel> findAll();
}
