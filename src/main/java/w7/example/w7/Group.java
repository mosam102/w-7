package w7.example.w7;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Data
@ToString(exclude="members")
@Entity
@Table(name="Groups")
public class Group {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	private String name;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Student admin;
	
	@OneToMany(mappedBy="relGroup",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	public List<Student> members;
	
	@OneToMany(mappedBy="relAppGroup",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Apply> applys;
	
	private long num;
	
	

	/*public long getNum() {
		
		return (long)members.size();
	}*/
	
}
