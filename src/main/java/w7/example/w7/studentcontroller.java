package w7.example.w7;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;


@Controller
@RequestMapping(path="/")
public class studentcontroller{
	
	@Autowired
	private studentRepository studentRepo;
	
	@Autowired
	private groupRepository groupRepo ;
	

	@GetMapping(path="/")
	public  String signin(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		model.addAttribute(new Student());
		
		return "logIn";
	}
	@GetMapping(path="/logOut")
	public  RedirectView logOut(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		return new RedirectView("/");
	}
	

	@GetMapping(path="/all")
	public  String getAllStudent(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		/*List<Student> l=studentRepo.findAll();
		for(Student c: l){
			System.out.println(c);
		}
		*/return "studentList";
	}
	
	
	
	//Add new Student!!!!!!!!!!!!
	
	@GetMapping(path="/addStudent")
    public String addieren(Model model){
		model.addAttribute(new Student());
		model.addAttribute("Liste2",groupRepo.findAll());
		
		return "add";
	}

	@PostMapping(path="/addform")
	public  String addNewStudent(Model model,@ModelAttribute @Valid Student student,Errors errors){
		if(errors.hasErrors() || findStudent(student.getMatnr())){
			model.addAttribute("errorNachricht","Der eingegebene Matrikelnummer schon existiert!!!");
			return "/add";
		}
		model.addAttribute("Liste2",groupRepo.findAll());
		//studentRepo.save(student);
		model.addAttribute("student",student);
	
		return "/joinGroup";
	}
	
	//Choose a Group

	@PostMapping(path="/joinGroup")
    public  String chooseGroup(@ModelAttribute Student student, Errors errors,Model model){
		if(student.getNameRelGroup()==null || groupRepo.count()==0){
			model.addAttribute("errorNachricht2","Bitte beachten die eingegebenen Daten");
			model.addAttribute("Liste2",groupRepo.findAll());
			return "/joinGroup";
		}
		if(!findGroup(student.getNameRelGroup())){
			model.addAttribute("errorNachricht1","There is no group with that name");
			model.addAttribute("Liste2",groupRepo.findAll());
			return "/joinGroup";
		}
		Group g=groupRepo.findOneByName(student.getNameRelGroup());
		student.setRelGroup(g);
		student.setInGroup(true);
		studentRepo.save(student);
		model.addAttribute("group",groupRepo.findOne(g.getId()));
		
		return ("/wellueberleitung") ;
	}


	@PostMapping(path="/studentsOfGroup")
	public  String studentsOfGroup(Model model,@ModelAttribute Student student){
		Student s=studentRepo.findOne(student.getId());
		Group g=groupRepo.findOneByName(s.getNameRelGroup());
		model.addAttribute("group",g);
	
		return "/studentsOfGroup";
	}
	
	//remove the Student!!!!!!!!!
	
	@GetMapping(path="/remove")
    public String removieren(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		model.addAttribute(new Student());
		return "remove";
	}
	
	@GetMapping(path="/removefrom")
	public  String removeStudent(@ModelAttribute @Valid Student student,Errors errors,Model model){
		if(!findStudent(student.getMatnr())){
			model.addAttribute("er","Bitte beachtem Sie die eingegebenen Daten!!!");
			model.addAttribute("Liste",studentRepo.findAll());
			return "/remove";
		}
		System.out.println("delete!");
		Student s= studentRepo.findOneByMatnr(student.getMatnr());
		Group g=s.getRelGroup();
		/*for( Student st: g.members){
			if(st.getId()==s.getId()){
				g.members.remove(st.getId());
			}
		}*/
		studentRepo.delete(s);
		model.addAttribute("Liste",studentRepo.findAll());
		return  "/remove";
		
	}
	
	//Add Group
	
	@GetMapping(path="/addGroup")
    public String groupAddieren(Model model){
		model.addAttribute("student",new Student());
		return "addGroup";
	}

	@GetMapping(path="/addform2")
    public String groupAddieren2(@ModelAttribute @Valid Student student,Errors errors,Model model){
		if(errors.hasErrors()){
			model.addAttribute("errorNachricht","Bitte beachtem Sie die eingegebenen Daten!!!");
			return "/addGroup";
		}
		if(!findStudent(student.getMatnr())){
			Group gra=new Group();
			gra.setName(student.getNameRelGroup());
			groupRepo.save(gra);
			
			Student s=new Student();
			s.setMatnr(student.getMatnr());
			s.setName(student.getName());
			s.setNameRelGroup(student.getNameRelGroup());
			s.setRelGroup(gra);
			s.setInGroup(true);
			studentRepo.save(s);
			gra.setAdmin(s);
		
			model.addAttribute("student",s);
			return "/addGroupUeberleitung";
		}
		if (isInGroup(student.getMatnr())){
				System.out.println(isInGroup(student.getMatnr()));
				model.addAttribute("errorNachricht","Sie sind schon in einer Gruppe!!!");
			return "/addGroup";
		}
		Student s=studentRepo.findOneByMatnr(student.getMatnr());
		Group gra=new Group();
		gra.setName(student.getNameRelGroup());
		gra.setAdmin(s);
		groupRepo.save(gra);
		model.addAttribute("student",s);
		return "/addGroupUeberleitung";
		}

	@GetMapping(path="/allGroup")
	public  String getAllGroup(Model model){
		model.addAttribute("Liste2",groupRepo.findAll());
		return "groupList";
	}

	//remove the Group!!!!!!!!!
	
	@GetMapping(path="/removeGroup")
    public String groupRemovieren(Model model){
		model.addAttribute("Liste2",groupRepo.findAll());
		return "removeGroup";
	}
	
	@GetMapping(path="/removeGroupFrom")
	public  RedirectView removeGroup(@RequestParam int id){
		groupRepo.delete((long) id);
		
		return  new RedirectView("/allGroup");
		
	}
	//Sign in!!!!!!!!!!!
	@GetMapping(path="/findeOne")
	public  String findStudent(@ModelAttribute @Valid Student student,Errors errors,Model model ){
		if(errors.hasErrors() || (!findStudent(student.getMatnr()))){
			model.addAttribute("errorNachricht","Diese Username and Passwort ist uns leider nicht bekannt!!!");
		return "/logIn";

		}
		Student s= studentRepo.findOneByMatnr(student.getMatnr());
		model.addAttribute(groupRepo.findOne(s.getRelGroup().getId()));
		
		return  "/studentsOfGroup" ;
		
	}
	public boolean findStudent(String matnr){
		if (studentRepo.findOneByMatnr(matnr)!=null){
			return true;
		}
		return false;
	}

	public boolean isInGroup(String matnr){
		Student s=studentRepo.findOneByMatnr(matnr);
		return s.isInGroup;
	}

	public boolean findGroup(String name){
		if (groupRepo.findOneByName(name)!=null){
			return true;
		}
		return false;
	}

}
