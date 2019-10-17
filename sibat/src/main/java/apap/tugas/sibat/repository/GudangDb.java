package apap.tugas.sibat.repository;

import apap.tugas.sibat.model.GudangModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GudangDb extends JpaRepository<GudangModel,Long>{
    List<GudangModel> findAll();
    Optional<GudangModel> findByIdGudang(long nomor);
}
