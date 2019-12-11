package apap.tugas.sibat.repository;

import apap.tugas.sibat.model.SupplierModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupplierDb extends JpaRepository<SupplierModel, Long> {
    List<SupplierModel> findAll();
}
