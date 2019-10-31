package apap.tugas.sibat.controller;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.JenisModel;
import apap.tugas.sibat.model.ObatModel;
import apap.tugas.sibat.model.SupplierModel;
import apap.tugas.sibat.service.GudangService;
import apap.tugas.sibat.service.JenisService;
import apap.tugas.sibat.service.ObatService;
import apap.tugas.sibat.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    // URL mapping view
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String view(Model model){
        List<ObatModel> allObat = obatService.findAllObat();
        model.addAttribute("listObat", allObat);
        // Return view template
        return "daftar-obat";
    }

    // URL mapping yang digunakan untuk mengakses halaman add obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.GET)
    public String addObatFormPage(Model model){
        ObatModel newObat = new ObatModel();
        List<SupplierModel> listSupplier = supplierSerivce.findAllSupplier();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        ArrayList<SupplierModel> newSupplierList = new ArrayList<SupplierModel>();
        newSupplierList.add(new SupplierModel());
        newObat.setListSupplier(newSupplierList);
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("supplierList", listSupplier);
        model.addAttribute("obat", newObat);
        return "form-tambah-obat";
    }

    // URL mapping yang digunakan untuk submit form yang telah anda masukkan pada halaman add obat
    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute ObatModel obat, Model model) {
        obat.setKode(obatService.generateKode(obat));
        obatService.addObat(obat);
        ObatModel targetObat = obatService.getObatById(obat.getIdObat()).get();
        for (int i=0; i < targetObat.getListSupplier().size(); i++){
            SupplierModel supplier = targetObat.getListSupplier().get(i);
            if (supplier.getListObat() == null){
                supplier.setListObat(new ArrayList<ObatModel>());
            }
            supplier.getListObat().add(targetObat);
            supplierSerivce.addSupplier(supplier);
        }
        model.addAttribute("obat", obat);
        return "tambah-obat";
    }

    @RequestMapping(value = "/obat/tambah", method = RequestMethod.POST, params = {"addRow"})
    public String addRowSupplier(@ModelAttribute ObatModel obat, BindingResult bindingResult, Model model){
        if(obat.getListSupplier() == null){
            obat.setListSupplier(new ArrayList<SupplierModel>());
        }
        obat.getListSupplier().add(new SupplierModel());
        model.addAttribute("obat", obat);
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<SupplierModel> listSupplier = supplierSerivce.findAllSupplier();
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("supplierList", listSupplier);
        return "form-tambah-obat";
    }

    @RequestMapping(path = "/obat/view", method = RequestMethod.GET)
    public String view(@RequestParam(value = "noReg") String noReg, Model model){
        ObatModel obat = obatService.getObatByNomorRegistrasi(noReg).get();
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
    public String delete(@PathVariable Long idObat, Model model){
        ObatModel obat = obatService.getObatById(idObat).get();
        model.addAttribute("nama", obat.getNama());
        obatService.deleteObat(obat);
        return "hapus-obat";
    }

    @RequestMapping(value = "/obat/filter", method = RequestMethod.GET)
    public String filter(@RequestParam(value = "idGudang", required = false) Long idGudang, @RequestParam(value = "idSupplier", required = false) Long idSupplier, @RequestParam(value = "idJenis", required = false) Long idJenis,
                         Model model){
        List<GudangModel> listGudang = gudangService.findAllGudang();
        List<JenisModel> listJenis = jenisService.findAllJenis();
        List<SupplierModel> listSupplier = supplierSerivce.findAllSupplier();
        model.addAttribute("listGudang", listGudang);
        model.addAttribute("listJenis", listJenis);
        model.addAttribute("listSupplier", listSupplier);
        if (!(idGudang==null || idSupplier==null || idJenis==null)){
            List<ObatModel> listObat = new ArrayList<ObatModel>();
            GudangModel gudang = gudangService.getGudangById(idGudang).get();
            SupplierModel supplier = supplierSerivce.getSupplierById(idSupplier).get();
            JenisModel jenis = jenisService.getJenisById(idJenis).get();
            List<ObatModel> data1 = gudang.getListObat();
            List<ObatModel> data2 = supplier.getListObat();
            List<ObatModel> data3 = jenis.getListObat();
            for (ObatModel obat : data1){
                if (data2.contains(obat) && data3.contains(obat)){
                    listObat.add(obat);
                }
            }
            model.addAttribute("listObat", listObat);
            model.addAttribute("gudang",gudang.getNama());
            model.addAttribute("supplier", supplier.getNama());
            model.addAttribute("jenis", jenis.getNama());
        }
        return "filter";
    }
}

