package apap.tugas.sibat.service;

import apap.tugas.sibat.model.ObatModel;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ObatService {
    List<ObatModel> findAllObat();
    void addObat(ObatModel obat);
    Optional<ObatModel> getObatByNomorRegistrasi(String nomor);
    Optional<ObatModel> getObatById(Long id);
    ObatModel changeObat(ObatModel obat);
    String generateKode(ObatModel obat);
    void deleteObat(ObatModel obat);
}
