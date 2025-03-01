package br.com.fiap.reservas.infra.repository.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {

    @Query(value = """
            select * from restaurante
            where nome = :nome and endereco = :endereco and tipo = :tipo
            """, nativeQuery = true)
    Restaurante findByNomeAndEnderecoAndTipo(String nome, String endereco, String tipo);

    List<Restaurante> findByNome(String nome);
}
