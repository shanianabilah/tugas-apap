package apap.tugas.sibat.controller;

import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.GudangModel;
import apap.tugas.sibat.model.ObatModel;
import apap.tugas.sibat.service.GudangService;
import apap.tugas.sibat.service.ObatService;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.print.attribute.standard.MediaSize;
import java.time.LocalTime;
import java.util.List;

@Controller
public class GudangController {
    @Qualifier("gudangServiceImpl")
    @Autowired
    private GudangService gudangService;

    @Qualifier("obatServiceImpl")
    @Autowired
    private ObatService obatService;

    // URL mapping view
    @RequestMapping(path = "/gudang", method = RequestMethod.GET)
    public String view(Model model){
        List<GudangModel> allGudang = gudangService.findAllGudang();
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

    @RequestMapping(path = "/gudang/view", method = RequestMethod.GET)
    public String view(
            // Request Parameter untuk dipass
            @RequestParam(value = "idGudang") Long idGudang, Model model
    ){
        GudangModel gudang = gudangService.getGudangById(idGudang).get();
        model.addAttribute("gudang", gudang);
        List<ObatModel> allObat = obatService.findAllObat();
        model.addAttribute("listObat", allObat);
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
            if (gudang.getListObat().isEmpty()) {
                model.addAttribute("gudang", gudang);
                gudangService.deleteGudang(gudang);
                return "hapus-gudang";
            }
            else{
                return "error/hapus-gudang-error";
            }
        }
    }

    @RequestMapping(value = "/gudang/tambah-obat", method = RequestMethod.POST)
    public String tambahObat(
            @RequestParam(value = "idObat") Long idObat, @ModelAttribute GudangModel gudang, RedirectAttributes redirectAttributes){
        GudangModel gudangSave = gudangService.getGudangById(gudang.getIdGudang()).get();
        ObatModel obat = obatService.getObatById(idObat).get();
        gudangSave.getListObat().add(obat);
        obat.getListGudang().add(gudangSave);
        gudangService.assignObat(gudang.getIdGudang(), idObat);
        LocalTime time = LocalTime.now();
        redirectAttributes.addFlashAttribute("gudang", gudangSave);
        redirectAttributes.addFlashAttribute("obat", obat);
        redirectAttributes.addFlashAttribute("time", time);
        redirectAttributes.addFlashAttribute("success", true);
        return "redirect:/gudang/view?idGudang="+ gudangSave.getIdGudang();
    }


    @RequestMapping(value="/gudang/expired-obat", method=RequestMethod.GET)
    public String obatExpired(Model model){
        List<GudangModel> allGudang = gudangService.findAllGudang();
        model.addAttribute("listGudang", allGudang);
        return "form-expired";
    }

    @RequestMapping(value = "/gudang/expired-obat", method = RequestMethod.POST)
    public String viewObatExpired(
            // Request Parameter untuk dipass
            @RequestParam(value = "idGudang") Long idGudang, Model model
    ){
        GudangModel gudang = gudangService.getGudangById(idGudang).get();
        List<ObatModel> listObat = gudang.getListObat();
        List<ObatModel> listExpired = gudangService.obatExpired(listObat);
        model.addAttribute("gudang", gudang);
        model.addAttribute("listExpired", listExpired);
        return "lihat-expired";
    }
}
