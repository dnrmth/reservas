package br.com.fiap.reservas.usecases;

import br.com.fiap.reservas.entities.MesaEntity;
import br.com.fiap.reservas.entities.ReservaEntity;
import br.com.fiap.reservas.entities.ReservaVMesaEntity;
import br.com.fiap.reservas.entities.RestauranteEntity;
import br.com.fiap.reservas.enums.StatusReserva;
import br.com.fiap.reservas.infra.repository.mesa.MesaPK;
import br.com.fiap.reservas.infra.repository.reserva.ReservaVMesa;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CadastrarReservaUseCase {

    public static ReservaEntity cadastrarReserva(
            RestauranteEntity restaurante, String usuario, int qtdPessoas, List<MesaEntity> mesasLivres,
            LocalDateTime horaChegada) {

        if (restaurante.getCapacidade() < qtdPessoas) {
            throw new RuntimeException("Capacidade insuficiente");
        }

        int numeroMesas = (int) Math.ceil(qtdPessoas / 4.0);
        if (mesasLivres.size() < numeroMesas) {
            throw new RuntimeException("Número de mesas indisponível");
        }

        List<ReservaVMesaEntity> mesasParaReservar = new ArrayList<>();
        mesasLivres.stream()
                .limit(numeroMesas)
                .forEach(mesa -> {
                    ReservaVMesaEntity reservaVMesa = new ReservaVMesaEntity(StatusReserva.RESERVADA);
                    reservaVMesa.setIdMesa(new MesaPK(mesa.getRestauranteId(), mesa.getNumero()));
                    mesasParaReservar.add(reservaVMesa);
        });

        return new ReservaEntity(restaurante, usuario, mesasParaReservar, horaChegada);
    }
}
