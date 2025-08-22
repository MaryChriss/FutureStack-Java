package br.com.fiap.sprint3.patio;

import lombok.Builder;

@Builder
public class PatioDTO {
    public Long id;
    public String nome;
    public Integer quantidadeVagas;
    public Double metragemZonaA;
    public Double metragemZonaB;
}
