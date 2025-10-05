package br.dev.farmes.modules.pedidos;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.dev.farmes.modules.pedidos.entities.PedidoEntity;
import br.dev.farmes.modules.pedidos.entities.PedidoResumeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.NoResultException;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<PedidoEntity, Integer> {

	public Optional<List<PedidoEntity>> findAllPedidos(LocalDate dataInicial, LocalDate dataFinal, Integer page, Integer size) {
		String sql = """
				SELECT
					p.NUMPED AS "NUMPED",
					p.NUMNOTA AS "NUMNOTA",
					TO_CHAR(p.DATA, 'DD/MM/YYYY') AS "DATA",
					p.VLATEND AS "VALOR",
					p.POSICAO AS "POSICAO",
					p.CODCOB || ' - ' || cb.COBRANCA AS "COBRANCA",
					plp.DESCRICAO AS "PLANO_PAGAMENTO",
					c.CODCLI AS "CODCLI",
					c.CLIENTE AS "CLIENTE"
				FROM
					PCPEDC p
				INNER JOIN PCCLIENT c ON c.CODCLI = p.CODCLI
				INNER JOIN PCCOB cb ON cb.CODCOB = p.CODCOB
				INNER JOIN PCPLPAG plp ON plp.CODPLPAG = p.CODPLPAG
				WHERE
					p.POSICAO NOT IN ('C')
					AND p.DATA >= TO_DATE(:dataInicial, 'YYYY-MM-DD') 
					AND p.DATA <= TO_DATE(:dataFinal, 'YYYY-MM-DD')
				ORDER BY
					p.NUMPED DESC
				OFFSET :offset ROWS
				FETCH NEXT :fetch ROWS ONLY
				""";
		
		List<Object[]> results = getEntityManager()
				.createNativeQuery(sql)
				.setParameter("dataInicial", dataInicial.toString())
				.setParameter("dataFinal", dataFinal.toString())
				.setParameter("offset", (page - 1) * size)
				.setParameter("fetch", size)
				.getResultList();
		
		if (results.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(
				results.stream().map(result -> 
					new PedidoEntity(
						(Integer) result[0], 
						(Integer) result[1], 
						(String) result[2], 
						(BigDecimal) result[3],
						(String) result[4], 
						(String) result[5], 
						(String) result[6], 
						(Integer) result[7],
						(String) result[8]
					)
				).toList()
		);
	}
	
	public Optional<PedidoResumeEntity> findResumePedidos(LocalDate dataInicial, LocalDate dataFinal) {
		
		String sql = """
				SELECT
					COUNT(p.NUMPED) AS "QTD_PEDIDOS",
					COUNT(DISTINCT c.CLIENTE) AS "CLIENTES",
					SUM(p.VLATEND) AS "VALOR"
				FROM
					PCPEDC p
				INNER JOIN PCCLIENT c ON c.CODCLI = p.CODCLI
				WHERE
					p.POSICAO NOT IN ('C')
					AND p.DATA >= TO_DATE(:dataInicial, 'YYYY-MM-DD')
					AND p.DATA <= TO_DATE(:dataFinal, 'YYYY-MM-DD')
				""";
		
		Object[] result = (Object[]) getEntityManager()
				.createNativeQuery(sql)
				.setParameter("dataInicial", dataInicial.toString())
				.setParameter("dataFinal", dataFinal.toString())
				.getSingleResult();
		
		if (result == null) {
			return Optional.empty();
		}
		
		return Optional.of(
				new PedidoResumeEntity(
					((BigDecimal) result[0]).intValue(), 
			        ((BigDecimal) result[1]).intValue(),
					(BigDecimal) result[2]
				)
		);
	}

	public Optional<PedidoEntity> findByNumPed(Integer numPed) {
		Object[] result = (Object[]) getEntityManager().createNativeQuery("""
				SELECT
					p.NUMPED AS "NUMPED",
					p.NUMNOTA AS "NUMNOTA",
					TO_CHAR(p.DATA, 'DD/MM/YYYY') AS "DATA",
					p.VLATEND AS "VALOR",
					p.POSICAO AS "POSICAO",
					p.CODCOB || ' - ' || cb.COBRANCA AS "COBRANCA",
					plp.DESCRICAO AS "PLANO_PAGAMENTO",
					c.CODCLI AS "CODCLI",
					c.CLIENTE AS "CLIENTE"
				FROM
					PCPEDC p
				INNER JOIN PCCLIENT c ON c.CODCLI = p.CODCLI
				INNER JOIN PCCOB cb ON cb.CODCOB = p.CODCOB
				INNER JOIN PCPLPAG plp ON plp.CODPLPAG = p.CODPLPAG
				WHERE
					p.POSICAO NOT IN ('C')
					AND p.NUMPED = :numPed
				ORDER BY
					c.CLIENTE ASC
				""").setParameter("numPed", numPed).getSingleResult();

		if (result == null) {
			return Optional.empty();
		}

		return Optional.of(
				new PedidoEntity(
						(Integer) result[0], 
						(Integer) result[1], 
						(String) result[2], 
						(BigDecimal) result[3],
						(String) result[4], 
						(String) result[5], 
						(String) result[6],
						(Integer) result[7],
						(String) result[8]
				)
		);
	}
}
