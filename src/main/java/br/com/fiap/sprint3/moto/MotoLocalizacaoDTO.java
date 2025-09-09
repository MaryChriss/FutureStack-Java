package br.com.fiap.sprint3.moto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MotoLocalizacaoDTO {
    public Long motoId;
    public String modelo;
    public String placa;
    public Long zonaId;
    public String zonaNome;
    public String tipoZona;   // A/B
    public Long patioId;
    public String patioNome;
}
