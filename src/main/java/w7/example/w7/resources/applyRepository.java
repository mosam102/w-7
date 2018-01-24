package w7.example.w7.resources;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import w7.example.w7.Apply;

@Transactional
@Repository
public interface applyRepository extends CrudRepository<Apply, Long>{

	public List<Apply> findAll();
}
