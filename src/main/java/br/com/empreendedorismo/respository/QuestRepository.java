package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.empreendedorismo.entity.Quest;

public interface QuestRepository extends JpaRepository<Quest, Integer>{

}
