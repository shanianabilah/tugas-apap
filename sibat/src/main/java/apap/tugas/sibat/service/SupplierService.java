package apap.tugas.sibat.service;

import apap.tugas.sibat.model.SupplierModel;

import java.util.List;
import java.util.Optional;

public interface SupplierService {
    List<SupplierModel> findAllSupplier();
    Optional<SupplierModel> getSupplierById(Long idSupplier);
    void addSupplier(SupplierModel supplier);
}
