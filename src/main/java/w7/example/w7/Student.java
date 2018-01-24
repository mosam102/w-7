package w7.example.w7;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name="Students")
public class Student {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@NotNull
	@Size(min=1,message="musst not be empty")
	private String name;
	
	@NotNull
	@Size(min=1,max=7)
	private String matnr;
	
	@ManyToOne
	@JoinColumn(name="relGroup")
	private Group relGroup;
	
	@ManyToOne
	@JoinColumn(name="relApply")
	private Apply relApply;
	
	private String nameRelGroup;
	
	public String getNameRelGroup() {
		return nameRelGroup;
	}
	public void setNameRelGroup(String nameRelGroup) {
		this.nameRelGroup = nameRelGroup;
	}
	@OneToOne(mappedBy="admin", cascade = CascadeType.ALL)
	private Group adminForGroup;
	
	public boolean isInGroup;
	
	
	
		
	
}
