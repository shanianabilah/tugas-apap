package apap.tugas.sibat.service;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.repository.GudangDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GudangServiceImpl implements GudangService {
    @Autowired
    private GudangDb gudangDb;

    @Override
    public List<GudangModel> findAllGudang() {
        return gudangDb.findAll();
    }

    @Override
    public void addGudang(GudangModel gudang) {
        gudangDb.save(gudang);
    }

    @Override
    public Optional<GudangModel> getGudangById(Long id) {
        return gudangDb.findByIdGudang(id);
    }

    @Override
    public GudangModel changeGudang(GudangModel gudang) {
        GudangModel targetGudang = gudangDb.findById(gudang.getIdGudang()).get();

        try{
            targetGudang.setNama(gudang.getNama());
            targetGudang.setAlamat(gudang.getAlamat());
            gudangDb.save(targetGudang);
            return targetGudang;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    @Override
    public void deleteGudang(GudangModel gudang) {
        gudangDb.delete(gudang);
    }


}
