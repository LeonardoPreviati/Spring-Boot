package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreendedorismo.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer>{

}
