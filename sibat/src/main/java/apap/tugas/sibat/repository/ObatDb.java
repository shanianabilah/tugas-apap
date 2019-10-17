package apap.tugas.sibat.repository;

import apap.tugas.sibat.model.ObatModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ObatDb extends JpaRepository<ObatModel,Long> {
    List<ObatModel> findAll();
    Optional<ObatModel> findByNomorRegistrasi(String nomor);
}
