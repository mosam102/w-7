package w7.example.w7.resources;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import w7.example.w7.Student;
@Transactional
@Repository
public interface studentRepository extends CrudRepository<Student,Long>{

	public List<Student> findAll();
	//public List<Student> delete(); 
	public Student findOneByName(String name);
	public Student findOneByMatnr(String matnr);

}
