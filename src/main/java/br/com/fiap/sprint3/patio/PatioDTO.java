package br.com.fiap.sprint3.patio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PatioDTO {
    public Long id;
    public String nome;
    public Integer quantidadeVagas;
    public Double metragemZonaA;
    public Double metragemZonaB;
}
