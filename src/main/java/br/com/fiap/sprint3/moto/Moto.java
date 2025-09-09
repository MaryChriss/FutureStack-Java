package br.com.fiap.sprint3.moto;

import br.com.fiap.sprint3.patio.Patio;
import br.com.fiap.sprint3.zona.Zona;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Moto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String modelo;

    @Column(unique = true)
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zona zona;

    private StatusMoto status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patio_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_MOTO_PATIO"))
    private Patio patio;
}
