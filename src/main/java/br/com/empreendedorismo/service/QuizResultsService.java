package br.com.empreendedorismo.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.empreendedorismo.configuration.HibernateConfiguration;
import br.com.empreendedorismo.respository.QuizResultsRepository;
import br.com.empreendedorismo.utils.Constans;

@Service
public class QuizResultsService extends HibernateConfiguration{
	
	@SuppressWarnings("unused")
	@Autowired
	private QuizResultsRepository quizResultsRepository;
	
	@SuppressWarnings("unchecked")
	public HashMap<String, Double> findResultsById(Integer accountId) throws Exception {
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT c.description, a.value ");
			sql.append("FROM quiz_results qr ");
			sql.append("JOIN answer a ");
			sql.append("  ON qr.answer_id = a.answer_id ");
			sql.append("JOIN category c ");
			sql.append("  ON qr.category_id = c.category_id ");
			sql.append("WHERE qr.ACCOUNT_ID = :id ");
			javax.persistence.Query q = getSession().createNativeQuery(sql.toString());
			q.setParameter("id", accountId);
			return resultsByQuizCategory(q.getResultList());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public HashMap<String, Double> resultsByQuizCategory(List<Object[]> queryResult) throws Exception {
		DecimalFormat format = new DecimalFormat("#.##");
		Double allQuestionsByCategory = 3.0;
		HashMap<String, Double> resultsCategoryPercentage = new HashMap<String, Double>();
		try {
			for (Object[] category: queryResult) {
				Double categoryPercentageSingle = Double.parseDouble((String.valueOf(category[1]).toString()));
				Double maxPercentageByCategory = Double.parseDouble(format.parse(format.format((categoryPercentageSingle + categoryPercentageSingle) / allQuestionsByCategory)).toString());
				switch (category[0].toString()) {
					case Constans.BUSCA_DE_OPORTUNIDADES_E_INICIATIVA:
						resultsCategoryPercentage.put("Busca de oportunidades e iniciativa", maxPercentageByCategory);
						break;
					case Constans.PERSISTENCIA:
						resultsCategoryPercentage.put("Persistencia", maxPercentageByCategory);
						break;
					case Constans.COMPROMETIMENTO:
						resultsCategoryPercentage.put("Comprometimento", maxPercentageByCategory);
						break;
					case Constans.EXIGENCIA_DE_QUALIDADE_E_EFICIENCIA:
						resultsCategoryPercentage.put("Exigencia de qualidade e eficiencia", maxPercentageByCategory);
						break;
					case Constans.CORRER_RISCOS_CALCULADOS:
						resultsCategoryPercentage.put("Correr riscos calculados", maxPercentageByCategory);
						break;
					case Constans.ESTABELECIMENTO_DE_METAS:
						resultsCategoryPercentage.put("Estabelecimento de metas", maxPercentageByCategory);
						break;
					case Constans.BUSCA_DE_INFORMACAO:
						resultsCategoryPercentage.put("Busca de informacao", maxPercentageByCategory);
						break;
					case Constans.PLANEJAMENTO_E_MONITORAMENTO_SISTEMATICO:
						resultsCategoryPercentage.put("Planejamento e monitoramento sistematico", maxPercentageByCategory);
						break;
					case Constans.PERSUASAO_E_REDE_DE_CONTATOS:
						resultsCategoryPercentage.put("Persuasao e rede de contatos", maxPercentageByCategory);
						break;
					case Constans.INDEPENDENCIA_E_AUTOCONFIANCA:
						resultsCategoryPercentage.put("Independencia e autoconfianca", maxPercentageByCategory);
						break;
					}
				} return resultsCategoryPercentage;
		 }  catch (Exception e) {
			e.printStackTrace();
			throw e;		
		}
	}
}

