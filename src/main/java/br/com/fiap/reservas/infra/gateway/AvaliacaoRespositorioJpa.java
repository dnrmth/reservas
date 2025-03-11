package br.com.fiap.reservas.infra.gateway;

import br.com.fiap.reservas.controller.dto.AvaliacaoDto;
import br.com.fiap.reservas.entities.AvaliacaoEntity;
import br.com.fiap.reservas.entities.RestauranteEntity;
import br.com.fiap.reservas.entities.UsuarioEntity;
import br.com.fiap.reservas.infra.repository.avaliacao.Avaliacao;
import br.com.fiap.reservas.infra.repository.avaliacao.AvaliacaoRepository;
import br.com.fiap.reservas.interfaces.IAvaliacaoGateway;
import jakarta.transaction.Transactional;

public class AvaliacaoRespositorioJpa implements IAvaliacaoGateway {

    private final AvaliacaoRepository avaliacaoRepository;
    private final RestauranteRepositorioJpa restauranteRepositorioJpa;
    private final UsuarioRepositorioJpa usuarioRepositorioJpa;

    public AvaliacaoRespositorioJpa(AvaliacaoRepository avaliacaoRepository, RestauranteRepositorioJpa restauranteRepositorioJpa, UsuarioRepositorioJpa usuarioRepositorioJpa) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.restauranteRepositorioJpa = restauranteRepositorioJpa;
        this.usuarioRepositorioJpa = usuarioRepositorioJpa;
    }

    @Override
    @Transactional
    public AvaliacaoEntity avaliarRestaurante(AvaliacaoDto avaliacaoDto) {
        Avaliacao avaliacao = new Avaliacao(avaliacaoDto.restauranteId(), avaliacaoDto.nota(), avaliacaoDto.comentario(),
                avaliacaoDto.usuarioId());

        avaliacaoRepository.save(avaliacao);

        RestauranteEntity restauranteEntity = restauranteRepositorioJpa.buscarRestaurantePorId(avaliacaoDto.restauranteId());
        UsuarioEntity usuarioEntity = usuarioRepositorioJpa.buscarUsuarioPorId(avaliacaoDto.usuarioId());

        return new AvaliacaoEntity(avaliacao.getNota(), avaliacao.getComentario(), usuarioEntity, restauranteEntity);
    }

    @Override
    public AvaliacaoEntity buscarAvaliacaoPorRestaurante(Long id) {
        Avaliacao avaliacao = avaliacaoRepository.findAvaliacaoByRestaurante(id);
        RestauranteEntity restauranteEntity = restauranteRepositorioJpa.buscarRestaurantePorId(avaliacao.getRestauranteId());
        UsuarioEntity usuarioEntity = usuarioRepositorioJpa.buscarUsuarioPorId(avaliacao.getUsuarioId());

        return new AvaliacaoEntity(avaliacao.getNota(), avaliacao.getComentario(), usuarioEntity, restauranteEntity);
    }
}
