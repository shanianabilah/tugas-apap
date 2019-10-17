package apap.tugas.sibat.controller;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.service.GudangService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    // URL mapping view
    @RequestMapping(path = "/gudang", method = RequestMethod.GET)
    public String view(Model model){
        List<GudangModel> allGudang = gudangService.findAllGudang();
        // Add model restoran ke "resto" untuk dirender
        model.addAttribute("listGudang", allGudang);
        // Return view template
        return "daftar-gudang";
    }
    
    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.GET)
    public String addObatFormPage(Model model){
        GudangModel newGudang = new GudangModel();
        model.addAttribute("gudang", newGudang);
        return "form-tambah-gudang";
    }

    @RequestMapping(value = "/gudang/tambah", method = RequestMethod.POST)
    public String addObatSubmit(@ModelAttribute GudangModel gudang, Model model) {
        gudangService.addGudang(gudang);
        model.addAttribute("gudang", gudang);
        return "tambah-gudang";
    }

    @RequestMapping(path = "/gudang/view/{idGudang}", method = RequestMethod.GET)
    public String view(
            // Request Parameter untuk dipass
            @PathVariable Long idGudang, Model model
    ){
        GudangModel gudang = gudangService.getGudangById(idGudang).get();
        model.addAttribute("gudang", gudang);
        return "lihat-gudang";
    }

    // API yang digunakan untuk menuju halaman form ubah obat
    @RequestMapping(value = "gudang/ubah/{idGudang}", method = RequestMethod.GET)
    public String changeGudangFormPage(@PathVariable Long idGudang, Model model){
        // Mengambil existing data restoran
        GudangModel existingGudang = gudangService.getGudangById(idGudang).get();
        model.addAttribute("gudang", existingGudang);
        return "form-ubah-gudang";
    }

    // API yang digunakan untuk submit form change restoran
    @RequestMapping(value = "gudang/ubah/{idGudang}", method = RequestMethod.POST)
    public String changeGudangFormSubmit(@PathVariable Long idGudang, @ModelAttribute GudangModel gudang, Model model){
        GudangModel newGudangData = gudangService.changeGudang(gudang);
        model.addAttribute("gudang", newGudangData);
        return "ubah-gudang";
    }

    @RequestMapping("gudang/hapus/{idGudang}")
    public String deleteGudang(@PathVariable("idGudang") Long idGudang, Model model){
        GudangModel gudang = gudangService.getGudangById(idGudang).get();
        if(gudang == null || idGudang.equals("")){
            return "error";
        }
        else{
            // Add model restoran ke "resto" untuk dirender
            if (gudang.getListObat().isEmpty()) {
                model.addAttribute("gudang", gudang);

                gudangService.deleteGudang(gudang);

                // Return view template
                return "hapus-gudang";
            }
            else{
                return "error/hapus-gudang-error";
            }
        }
    }
}
