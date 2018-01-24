package w7.example.w7.resources;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import w7.example.w7.Group;
@Transactional
@Repository
public interface groupRepository extends CrudRepository<Group,Long> {

	public List<Group> findAll();
	public Group findOneByName(String group);
}
