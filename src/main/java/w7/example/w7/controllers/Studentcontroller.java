package w7.example.w7.controllers;


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

import w7.example.w7.Apply;
import w7.example.w7.Group;
import w7.example.w7.Student;
import w7.example.w7.resources.applyRepository;
import w7.example.w7.resources.groupRepository;
import w7.example.w7.resources.studentRepository;


@Controller
@RequestMapping(path="/")
public class Studentcontroller{
	
	@Autowired
	private studentRepository studentRepo;
	
	@Autowired
	private groupRepository groupRepo ;
	
	@Autowired
	private applyRepository applyRepo;

	@GetMapping(path="/")
	public  String signin(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		model.addAttribute(new Student());
		
		return "/logIn";
	}
	@GetMapping(path="/logOut")
	public  RedirectView logOut(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		return new RedirectView("/");
	}
	
	@GetMapping(path="/all")
	public  String getAllStudent(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		return "/student/studentList";
	}
	
	
	
	//Add new Student!!!!!!!!!!!!
	
	@GetMapping(path="/addStudent")
    public String addStudent(Model model){
		model.addAttribute(new Student());
		model.addAttribute("Liste2",groupRepo.findAll());
		
		return "/student/addstudent";
	}

	@PostMapping(path="/addform")
	public  String addStudent2(Model model,@ModelAttribute @Valid Student student,Errors errors){
		if(errors.hasErrors() || findStudent(student.getMatnr())){
			model.addAttribute("errorNachricht","Der eingegebene Matrikelnummer schon existiert!!!");
			return "/student/addstudent";
		}
		model.addAttribute("Liste2",groupRepo.findAll());
		//studentRepo.save(student);
		model.addAttribute("student",student);
	
		return "/group/joinGroup";
	}
		@PostMapping(path="/joinGroup")
		public  String chooseGroup(@ModelAttribute Student student,@RequestParam String name1, Errors errors,Model model){
			Group g=groupRepo.findOneByName(name1);
			student.setRelGroup(g);
			student.setNameRelGroup(name1);
			student.setInGroup(true);
			studentRepo.save(student);
			model.addAttribute("student",student);
			return ("/student/wellueberleitung") ;
		}
		
		@PostMapping(path="/studentsOfGroup")
		public  String studentsOfGroup(Model model,@ModelAttribute Student student){
			System.out.println(student.getName());
			System.out.println(student.getMatnr());
			Student s=studentRepo.findOneByMatnr(student.getMatnr());
			Group g=groupRepo.findOneByName(s.getNameRelGroup());
			model.addAttribute("group",g);
		
		return "/student/studentsOfGroup";
	}
	
	//remove the Student!!!!!!!!!
	
	@GetMapping(path="/removeStudent")
    public String removeStudent(Model model){
		model.addAttribute("Liste",studentRepo.findAll());
		model.addAttribute(new Student());
		return "/student/removeStudent";
	}
	
	@GetMapping(path="/removefrom")
	public  String removeStudent2(@ModelAttribute @Valid Student student,Errors errors,Model model){
		if(!findStudent(student.getMatnr())){
			model.addAttribute("er","Bitte beachtem Sie die eingegebenen Daten!!!");
			model.addAttribute("Liste",studentRepo.findAll());
			return "/student/removeStudent";
		}
		System.out.println("delete!");
		Student s= studentRepo.findOneByMatnr(student.getMatnr());
		s.setRelGroup(null);
		//Group g=s.getRelGroup();
		/*for( Student st: g.members){
			if(st.getId()==s.getId()){
				g.members.remove(st.getId());
			}
		}*/
		studentRepo.delete(s);
		model.addAttribute("Liste",studentRepo.findAll());
		return  "/student/removeStudent";
		
	}
	
	@GetMapping(path="/findeOne")
	public  String findStudent(@ModelAttribute @Valid Student student,Errors errors,Model model ){
		if(errors.hasErrors() || (!findStudent(student.getMatnr()))){
			model.addAttribute("errorNachricht","Diese Username and Passwort ist uns leider nicht bekannt!!!");
		return "/student/logIn";

		}
		Student s= studentRepo.findOneByMatnr(student.getMatnr());
		model.addAttribute(groupRepo.findOne(s.getRelGroup().getId()));
		
		return  "/student/studentsOfGroup" ;
		
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
	public void deleteStudent(Student s){
		studentRepo.delete(s);
			
	}
}
