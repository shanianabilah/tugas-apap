package apap.tugas.sibat.service;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.ObatModel;
import apap.tugas.sibat.repository.GudangDb;
import apap.tugas.sibat.repository.ObatDb;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GudangServiceImpl implements GudangService {
    @Autowired
    private GudangDb gudangDb;

    @Autowired
    private ObatDb obatDb;

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

    @Override
    public List<ObatModel> obatExpired(List<ObatModel> listObat) {
        List<ObatModel> expired = new ArrayList<ObatModel>();
        for (ObatModel obat : listObat){
            if (obat.getTanggalTerbit().plusYears(5).isBefore(LocalDate.now())){
                expired.add(obat);
            }
        }
        return expired;
    }

    @Override
    public void assignObat(Long idGudang, Long idObat){
        GudangModel gudang = gudangDb.findByIdGudang(idGudang).get();
        ObatModel obat = obatDb.findById(idObat).get();
        gudangDb.save(gudang);
        obatDb.save(obat);
    }


}
