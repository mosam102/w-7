package w7.example.w7;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.boot.autoconfigure.web.ResourceProperties.Strategy;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude="voters")
@Entity
@Table(name="Applys")
public class Apply {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Student candidate;
	
	@ManyToOne
	@JoinColumn(name="relAppGroup")
	private Group relAppGroup;
	
	@OneToMany(mappedBy="relApply",fetch=FetchType.EAGER,cascade=CascadeType.ALL)
	private List<Student> voters;
	
	private LocalDateTime deadline;
}
