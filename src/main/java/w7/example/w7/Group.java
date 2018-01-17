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
	
	private long num;
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student getAdmin() {
		return admin;
	}

	public void setAdmin(Student admin) {
		this.admin = admin;
	}
	
	public List<Student> getMembers(){
		return members;
		
	}

	public void setMembers(List<Student> members){
		this.members=members;
		
	}

	public long getNum() {
		num=0;
		for(Student s : members){
			num++;
		}
	
		return num;
	}

	public void setNum(long num) {
		this.num = num;
	}
	
	
}
