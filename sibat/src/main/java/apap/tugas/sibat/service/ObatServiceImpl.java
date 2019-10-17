package apap.tugas.sibat.service;

import apap.tugas.sibat.model.ObatModel;
import apap.tugas.sibat.repository.ObatDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ObatServiceImpl implements ObatService {
    @Autowired
    private ObatDb obatDb;

    @Override
    public List<ObatModel> findAllObat() {
        return obatDb.findAll();
    }

    @Override
    public void addObat(ObatModel obat) {
        obatDb.save(obat);
    }

    @Override
    public Optional<ObatModel> getObatByNomorRegistrasi(String nomor) {
        return obatDb.findByNomorRegistrasi(nomor);
    }

    @Override
    public Optional<ObatModel> getObatById(Long id) {
        return obatDb.findById(id);
    }

    @Override
    public ObatModel changeObat(ObatModel obat) {
        ObatModel targetObat = obatDb.findById(obat.getIdObat()).get();

        try{
            targetObat.setNama(obat.getNama());
            targetObat.setTanggalTerbit(obat.getTanggalTerbit());
            targetObat.setHarga(obat.getHarga());
            targetObat.setBentuk(obat.getBentuk());
            targetObat.setKode(generateKode(targetObat));
            obatDb.save(targetObat);
            return targetObat;
        } catch (NullPointerException nullException){
            return null;
        }
    }

    public String generateKode(ObatModel obat){
        String kode = "";
        if (obat.getJenis().getNama().equals("Generik")){
            kode += "1";
        }
        else if(obat.getJenis().getNama().equals("Paten")){
            kode += "2";
        }
        if(obat.getBentuk().equals("Cairan")){
            kode += "01";
        }
        else if (obat.getBentuk().equals("Kapsul")){
            kode += "02";
        }
        else if (obat.getBentuk().equals("Tablet")){
            kode += "03";
        }
        String[] currYear = LocalDate.now().toString().split("-");
        kode += currYear[0];
        String[] yearString = obat.getTanggalTerbit().toString().split(" ");
        int year = Integer.parseInt(yearString[yearString.length-1]) + 5;
        kode += String.valueOf(year);
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'A');
        char a = (char)(r.nextInt(26) + 'A');
        kode = kode + c + a;
        return kode;
    }

    @Override
    public void deleteObat(ObatModel obat) {
        obatDb.delete(obat);
    }

}
