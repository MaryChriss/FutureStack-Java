package br.com.fiap.sprint3.patio;

import br.com.fiap.sprint3.moto.Moto;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{patio.nome.notblank}")
    @Column(unique = true)
    private String nome;

    @NotNull(message = "{patio.qntvagas.notnull}")
    @Min(value = 60,   message = "{patio.qntvagas.min}")
    @Max(value = 255, message = "{patio.qntvagas.max}")
    private Integer quantidadeVagas;

    @DecimalMin(value = "350.0", inclusive = true, message = "{patio.qntvagasA.min}")
    @DecimalMax(value = "1400.0", inclusive = true, message = "{patio.qntvagasA.max}")
    private Double metragemZonaA;

    @DecimalMin(value = "350.0", inclusive = true, message = "{patio.qntvagasB.min}")
    @DecimalMax(value = "1400.0", inclusive = true, message = "{patio.qntvagasB.max}")
    private Double metragemZonaB;

    @OneToMany(mappedBy = "patio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Moto> motos = new ArrayList<>();

}
