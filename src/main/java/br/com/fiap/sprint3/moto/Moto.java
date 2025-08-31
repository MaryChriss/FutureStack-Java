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

    @NotBlank(message = "{moto.modelo.notblank}")
    private String modelo;

    @NotBlank
    @Column(unique = true)
    @Size(min = 7, max = 7, message = "{moto.placa.size}")
    private String placa;

    @ManyToOne(fetch = FetchType.LAZY)
    private Zona zona;

    private StatusMoto status;

    @ManyToOne
    private Patio patio;
}
