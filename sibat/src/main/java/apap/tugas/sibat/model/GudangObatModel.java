package apap.tugas.sibat.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

@Entity
@Table(name = "gudangObat")
public class GudangObatModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idGudangObat;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "gudangId", referencedColumnName = "idGudang", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private GudangModel gudang;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "obatId", referencedColumnName = "idObat", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private ObatModel obat;

}
