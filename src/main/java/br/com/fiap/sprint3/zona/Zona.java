package br.com.fiap.sprint3.zona;


import br.com.fiap.sprint3.moto.Moto;
import br.com.fiap.sprint3.patio.Patio;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoZona tipo;

    private Double metragem;

    @OneToMany(mappedBy = "zona")
    private List<Moto> motos;

    @Column(nullable = false, unique = true)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patio_id", nullable = false)
    private Patio patio;

}
