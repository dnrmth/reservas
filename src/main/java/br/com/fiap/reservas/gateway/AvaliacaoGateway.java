package br.com.fiap.reservas.gateway;

import br.com.fiap.reservas.entities.AvaliacaoEntity;
import br.com.fiap.reservas.entities.RestauranteEntity;
import br.com.fiap.reservas.entities.UsuarioEntity;
import br.com.fiap.reservas.interfaces.IAvaliacaoGateway;

public class AvaliacaoGateway implements IAvaliacaoGateway {

    private final IAvaliacaoGateway avaliacaoDatabaseGateway;

    public AvaliacaoGateway(IAvaliacaoGateway avaliacaoDatabaseGateway){
        this.avaliacaoDatabaseGateway = avaliacaoDatabaseGateway;
    }

    @Override
    public AvaliacaoEntity avaliarRestaurante(RestauranteEntity restaurante, int nota, String comentario, UsuarioEntity usuario) {
        return avaliacaoDatabaseGateway.avaliarRestaurante(restaurante, nota, comentario, usuario);
    }

    @Override
    public AvaliacaoEntity buscarAvaliacaoPorRestaurante(String nome) {
        return avaliacaoDatabaseGateway.buscarAvaliacaoPorRestaurante(nome);
    }
}
