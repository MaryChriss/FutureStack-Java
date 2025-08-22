package br.com.fiap.sprint3.alpr;

import br.com.fiap.sprint3.moto.Moto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoAlpr {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_alpr;

    @ManyToOne
    private Moto moto;

    @Column(nullable = false)
    private String placa_lida;

    private String url_imagem;

    private LocalDateTime ts_alpr;

}
