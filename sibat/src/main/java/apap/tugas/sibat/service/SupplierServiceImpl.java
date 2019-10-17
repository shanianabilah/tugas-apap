package apap.tugas.sibat.service;

import apap.tugas.sibat.model.SupplierModel;
import apap.tugas.sibat.repository.SupplierDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    private SupplierDb supplierDb;

    @Override
    public List<SupplierModel> findAllSuuplier() {
        return supplierDb.findAll();
    }
}
