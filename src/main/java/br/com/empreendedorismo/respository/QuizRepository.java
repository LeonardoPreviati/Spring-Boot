package br.com.empreendedorismo.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.empreendedorismo.entity.Quiz;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Integer>{

}
