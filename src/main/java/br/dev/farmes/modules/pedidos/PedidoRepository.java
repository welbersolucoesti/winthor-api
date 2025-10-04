package br.dev.farmes.modules.pedidos;

import java.math.BigDecimal;
import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PedidoRepository implements PanacheRepositoryBase<PedidoEntity, Integer> {

	public List<PedidoEntity> findAllPedidos() {
		
		String sql = """
				SELECT
					p.NUMPED AS "NUMPED",
					p.NUMNOTA AS "NUMNOTA",
					TO_CHAR(p.DATA, 'DD/MM/YYYY') AS "DATA",
					p.VLATEND AS "VALOR",
					p.POSICAO AS "POSICAO",
					p.CODCOB || ' - ' || cb.COBRANCA AS "COBRANCA",
					plp.DESCRICAO AS "PLANO_PAGAMENTO",
					c.CLIENTE AS "CLIENTE"
				FROM
					PCPEDC p
				INNER JOIN PCCLIENT c ON c.CODCLI = p.CODCLI
				INNER JOIN PCCOB cb ON cb.CODCOB = p.CODCOB
				INNER JOIN PCPLPAG plp ON plp.CODPLPAG = p.CODPLPAG
				WHERE
					p.POSICAO NOT IN ('C')
				ORDER BY
					c.CLIENTE ASC
				""";
		
		List<Object[]> results = getEntityManager().createNativeQuery(sql).getResultList();
		
		return results.stream().map(r -> new PedidoEntity(
				(Integer) r[0],
				(Integer) r[1],
				(String) r[2],
				(BigDecimal) r[3],
				(String) r[4],
				(String) r[5],
				(String) r[6],
				(String) r[7]
		)).toList();
	}
	
	public PedidoEntity findByNumPed(Integer numPed) {
		Object[] result = (Object[]) getEntityManager().createNativeQuery("""
				SELECT
					p.NUMPED AS "NUMPED",
					p.NUMNOTA AS "NUMNOTA",
					TO_CHAR(p.DATA, 'DD/MM/YYYY') AS "DATA",
					p.VLATEND AS "VALOR",
					p.POSICAO AS "POSICAO",
					p.CODCOB || ' - ' || cb.COBRANCA AS "COBRANCA",
					plp.DESCRICAO AS "PLANO_PAGAMENTO",
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
				""")
				.setParameter("numPed", numPed)
				.getSingleResult();
		
		if (result == null) {
			return null;
		}
		
		return new PedidoEntity(
				(Integer) result[0],
				(Integer) result[1],
				(String) result[2],
				(BigDecimal) result[3],
				(String) result[4],
				(String) result[5],
				(String) result[6],
				(String) result[7]
		);
	}
}
