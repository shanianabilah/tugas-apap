package apap.tugas.sibat.service;

import apap.tugas.sibat.model.SupplierModel;
import apap.tugas.sibat.repository.SupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDb supplierDb;

    @Override
    public List<SupplierModel> findAllSupplier() {
        return supplierDb.findAll();
    }

    @Override
    public Optional<SupplierModel> getSupplierById(Long idSupplier) {
        return supplierDb.findById(idSupplier);
    }

    @Override
    public void addSupplier(SupplierModel supplier) {
        supplierDb.save(supplier);
    }
}
