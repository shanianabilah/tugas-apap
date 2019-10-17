package apap.tugas.sibat.controller;

import apap.tugas.sibat.model.JenisModel;
import apap.tugas.sibat.model.ObatModel;
import apap.tugas.sibat.model.SupplierModel;
import apap.tugas.sibat.service.JenisService;
import apap.tugas.sibat.service.ObatService;
import apap.tugas.sibat.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Controller
public class ObatController {
    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    @Qualifier("supplierServiceImpl")
    @Autowired
    private SupplierService supplierSerivce;

    @Qualifier("jenisServiceImpl")
    @Autowired
    private JenisService jenisService;

    // URL mapping view
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String view(Model model){
        List<ObatModel> allObat = obatService.findAllObat();
        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("listObat", allObat);
        // Return view template
        return "daftar-obat";
    }

    // URL mapping yang digunakan untuk mengakses halaman add obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String addObatFormPage(Model model){
        ObatModel newObat = new ObatModel();
        List<SupplierModel> listSupplier = supplierSerivce.findAllSuuplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("listSupplier", listSupplier);
        model.addAttribute("obat", newObat);
        return "form-tambah-obat";
    }

    // URL mapping yang digunakan untuk submit form yang telah anda masukkan pada halaman add obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute ObatModel obat, Model model) {
        obat.setKode(obatService.generateKode(obat));
        obatService.addObat(obat);
        model.addAttribute("obat", obat);
        return "tambah-obat";
    }

    @RequestMapping(path = "/obat/view/{nomorRegistrasi}", method = RequestMethod.GET)
    public String view(
            // Request Parameter untuk dipass
            @PathVariable String nomorRegistrasi, Model model
    ){
        ObatModel obat = obatService.getObatByNomorRegistrasi(nomorRegistrasi).get();
        model.addAttribute("obat", obat);
        return "lihat-obat";
    }

    // API yang digunakan untuk menuju halaman form ubah obat
    @RequestMapping(value = "obat/ubah/{idObat}", method = RequestMethod.GET)
    public String changeObatFormPage(@PathVariable Long idObat, Model model){
        ObatModel existingObat = obatService.getObatById(idObat).get();
        model.addAttribute("obat", existingObat);
        return "form-ubah-obat";
    }

    // API yang digunakan untuk submit form change restoran
    @RequestMapping(value = "obat/ubah/{idObat}", method = RequestMethod.POST)
    public String changeRestoranFormSubmit(@PathVariable Long idObat, @ModelAttribute ObatModel obat, Model model){
        ObatModel newObatData = obatService.changeObat(obat);
        model.addAttribute("obat", newObatData);
        return "ubah-obat";
    }

    @RequestMapping(value = "/obat/hapus/{idObat}", method = RequestMethod.GET)
    private String delete(@ModelAttribute ObatModel obat, @PathVariable Long idObat, Model model){
        model.addAttribute("nama", obat.getNama());
        obatService.deleteObat(obat);
        return "hapus-obat";
    }
}
