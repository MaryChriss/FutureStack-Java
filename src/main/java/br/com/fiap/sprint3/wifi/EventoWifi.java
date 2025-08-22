package br.com.fiap.sprint3.wifi;

import br.com.fiap.sprint3.gateway.Gateway;
import br.com.fiap.sprint3.moto.Moto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoWifi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_evento_wifi;

    @ManyToOne
    private Moto moto;

    @ManyToOne
    private Gateway gateway;

    private Integer rssits_evento;

}
