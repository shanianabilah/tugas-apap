package apap.tugas.sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.tomcat.jni.Local;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "obat")
public class ObatModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idObat;

    @NotNull
    @Size(max = 255)
    @Column(name = "bentuk", nullable = false)
    private String bentuk;

    @NotNull
    @Column(name = "harga", nullable = false)
    private Double harga;

    @NotNull
    @Size(max = 255)
    @Column(name = "kode", nullable = false, unique = true)
    private String kode;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "nomorRegistrasi", nullable = false, unique = true)
    private String nomorRegistrasi;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "tanggalTerbit", nullable = false)
    private LocalDate tanggalTerbit;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "jenisId", referencedColumnName = "idJenis", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private JenisModel jenis;

    @ManyToMany
    @JoinTable(
            name = "daftarGudang",
            joinColumns = @JoinColumn(name = "idObat"),
            inverseJoinColumns = @JoinColumn(name = "idGudang"))
    List<GudangModel> listGudang;

    @ManyToMany
    @JoinTable(
            name = "daftarSupplier",
            joinColumns = @JoinColumn(name = "idObat"),
            inverseJoinColumns = @JoinColumn(name = "idSupplier"))
    List<SupplierModel> listSupplier;

    public Long getIdObat() {
        return idObat;
    }

    public void setIdObat(Long idObat) {
        this.idObat = idObat;
    }

    public String getBentuk() {
        return bentuk;
    }

    public void setBentuk(String bentuk) {
        this.bentuk = bentuk;
    }

    public Double getHarga() { return harga; }

    public String getHargaString(){ return "Rp " + Math.round(harga);}

    public void setHarga(Double harga) {
        this.harga = harga;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNomorRegistrasi() {
        return nomorRegistrasi;
    }

    public void setNomorRegistrasi(String nomorRegistrasi) {
        this.nomorRegistrasi = nomorRegistrasi;
    }

    public LocalDate getTanggalTerbit() {
        return tanggalTerbit;
    }

    public String getTanggalTerbitString(){
        String[] datetime = tanggalTerbit.toString().split(" ");
        return datetime[0];
    }

    public void setTanggalTerbit(LocalDate tanggalTerbit) {
        this.tanggalTerbit = tanggalTerbit;
    }

    public JenisModel getJenis() {
        return jenis;
    }

    public void setJenis(JenisModel jenis) {
        this.jenis = jenis;
    }

    public List<GudangModel> getListGudang() {
        return listGudang;
    }

    public void setListGudang(List<GudangModel> listGudang) {
        this.listGudang = listGudang;
    }

    public List<SupplierModel> getListSupplier() {
        return listSupplier;
    }

    public void setListSupplier(List<SupplierModel> lsitSupplier) {
        this.listSupplier = lsitSupplier;
    }

}
