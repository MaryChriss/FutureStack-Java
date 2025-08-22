package br.com.fiap.sprint3.moto;

import lombok.Builder;

@Builder
public class MotoDTO {
    public Long id;

    public String modelo;

    public String placa;

    public Long zonaId;

    public StatusMoto status;
}
