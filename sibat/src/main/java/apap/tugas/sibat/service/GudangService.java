package apap.tugas.sibat.service;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.ObatModel;

import java.util.List;
import java.util.Optional;

public interface GudangService {
    List<GudangModel> findAllGudang();
    void addGudang(GudangModel gudang);
    Optional<GudangModel> getGudangById(Long id);
    GudangModel changeGudang(GudangModel Gudang);
    void deleteGudang(GudangModel gudang);
    List<ObatModel> obatExpired(List<ObatModel> listObat);
    void assignObat(Long idGudang, Long idObat);
}
