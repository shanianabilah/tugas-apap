package apap.tugas.sibat.service;

import apap.tugas.sibat.model.JenisModel;
import apap.tugas.sibat.repository.JenisDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JenisServiceImpl implements JenisService {
    @Autowired
    private JenisDb jenisDb;

    @Override
    public List<JenisModel> findAllJenis() {
        return jenisDb.findAll();
    }
}
