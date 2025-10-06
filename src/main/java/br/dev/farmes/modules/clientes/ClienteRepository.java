package br.dev.farmes.modules.clientes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import br.dev.farmes.modules.clientes.entities.ClienteEntity;
import br.dev.farmes.modules.clientes.entities.ClienteResumeEntity;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ClienteRepository implements PanacheRepositoryBase<ClienteEntity, Integer> {

	public Optional<List<ClienteEntity>> findAllClientes(String bloqueados, Integer page, Integer size) {
		
		String bloqueioCondition = bloqueados.equals("A") 
				? "" 
				: bloqueados.equals("S") 
					? "AND c.BLOQUEIO = 'S'" 
					: "AND c.BLOQUEIO = 'N'";
		
		String sql = """
				SELECT
					c.CODCLI,
					C.CGCENT AS "CNPJ",
					c.CLIENTE,
					c.BLOQUEIO,
					SUM(p.VALOR) AS "TOTAL_TITULOS",
					SUM(CASE 
						WHEN p.VPAGO IS NULL THEN p.VALOR
					END) AS "TITULOS_ABERTOS",
					SUM(CASE 
						WHEN p.VPAGO IS NOT NULL THEN p.VALOR
					END) AS "TITULOS_PAGOS",
						SUM(CASE 
						WHEN p.VPAGO IS NULL AND p.DTVENC < SYSDATE THEN p.VALOR
					END) AS "TITULOS_VENCIDOS"
				FROM
					PCCLIENT c
				LEFT JOIN PCPREST p ON
					p.CODCLI = c.CODCLI
					AND DTVENC >= TRUNC(SYSDATE, 'MM')
					AND DTVENC <= LAST_DAY(SYSDATE)
				WHERE 
					c.CODATV1 = 6
					""" + bloqueioCondition + """
				GROUP BY
					c.CODCLI,
					C.CGCENT,
					c.CLIENTE,
					c.BLOQUEIO
				ORDER BY CLIENTE ASC
				OFFSET :offset ROWS
				FETCH NEXT :fetch ROWS ONLY
				""";
		
		List<Object[]> results = getEntityManager()
				.createNativeQuery(sql)
				.setParameter("offset", (page - 1) * size)
				.setParameter("fetch", size)
				.getResultList();
		
		if (results.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(
				results.stream().map(result -> 
					new ClienteEntity(
						(Integer) result[0], 
						(String) result[1], 
						(String) result[2], 
						(BigDecimal) result[4], 
						(BigDecimal) result[5], 
						(BigDecimal) result[6], 
						(BigDecimal) result[7],
						((Character) result[3]).equals('S')
					)
				).toList()
			);
	}
	
	public Optional<ClienteResumeEntity> findResumeClientes() {
		
		String sql = """
				WITH TITULOS_DO_MES AS (
					SELECT
						p.CODCLI,
						SUM(p.VALOR) AS "TOTAL_TITULOS_MES",
				        SUM(CASE 
				            WHEN p.VPAGO IS NULL THEN p.VALOR
				        END) AS "TITULOS_ABERTOS_MES",
				        SUM(CASE 
				            WHEN p.VPAGO IS NOT NULL THEN p.VALOR
				        END) AS "TITULOS_PAGOS_MES",
				        SUM(CASE 
				            WHEN p.VPAGO IS NULL AND p.DTVENC < SYSDATE THEN p.VALOR
				        END) AS "TITULOS_VENCIDOS_MES"
					FROM
						PCPREST p
					WHERE
						p.DTVENC >= TRUNC(SYSDATE, 'MM')
				        AND p.DTVENC <= LAST_DAY(SYSDATE)
				    GROUP BY p.CODCLI
				)
				SELECT
					COUNT(c.CODCLI) AS "TOTAL_CLIENTES",
					COUNT(CASE WHEN c.BLOQUEIO = 'S' THEN c.CODCLI END) AS "TOTAL_BLOQUEADOS",
					SUM(t."TOTAL_TITULOS_MES") AS "TOTAL_TITULOS",
					SUM(t."TITULOS_ABERTOS_MES") AS "TITULOS_ABERTOS",
					SUM(t."TITULOS_PAGOS_MES") AS "TITULOS_PAGOS",
					SUM(t."TITULOS_VENCIDOS_MES") AS "TITULOS_VENCIDOS"
				FROM PCCLIENT c
				LEFT JOIN
					TITULOS_DO_MES t ON t.CODCLI = c.CODCLI
				WHERE c.CODATV1 = 6
				""";
		
		Object[] result = (Object[]) getEntityManager()
				.createNativeQuery(sql)
				.getSingleResult();
		
		if (result == null) {
			return Optional.empty();
		}
		
		return Optional.of(
			new ClienteResumeEntity(
				((BigDecimal) result[0]).intValue(),
				(BigDecimal) result[2],
				(BigDecimal) result[3],
				(BigDecimal) result[4],
				(BigDecimal) result[5],
				((BigDecimal) result[1]).intValue()
			)
		);
	}
}
