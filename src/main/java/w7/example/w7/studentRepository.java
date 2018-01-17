package w7.example.w7;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public interface studentRepository extends CrudRepository<Student,Long>{

	public List<Student> findAll();
	//public List<Student> delete(); 
	public Student findOneByName(String name);
	public Student findOneByMatnr(String matnr);

}
