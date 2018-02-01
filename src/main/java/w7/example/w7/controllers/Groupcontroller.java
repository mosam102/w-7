package w7.example.w7.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import w7.example.w7.Apply;
import w7.example.w7.Group;
import w7.example.w7.Student;
import w7.example.w7.resources.applyRepository;
import w7.example.w7.resources.groupRepository;
import w7.example.w7.resources.studentRepository;

@Controller
@RequestMapping(path="/group/")
public class Groupcontroller {
	
	@Autowired
	private studentRepository studentRepo;
	
	@Autowired
	private groupRepository groupRepo ;
	
	@Autowired
	private applyRepository applyRepo;

	@GetMapping(path="/allGroup")
	public  String getAllGroup(Model model){
		//groupRepo.deleteAll();
		model.addAttribute("Liste2",groupRepo.findAll());
		model.addAttribute("applyList",applyRepo.findAll());
		
		return "/group/groupList";
	}
	
	
	@GetMapping(path="/addGroup")
    public String groupAddieren(Model model){
		model.addAttribute("student",new Student());
		return "/group/addGroup";
	}

	
	@PostMapping(path="/addGroup2")
    public String groupAddieren2(@ModelAttribute @Valid Student student,Errors errors,Model model){
		if(errors.hasErrors()){
			model.addAttribute("errorNachricht","Bitte beachtem Sie die eingegebenen Daten!!!");
			return "/group/addGroup";
		}
	
		if(!findStudent(student.getMatnr())){
			creatNewStudentAndGroup(student);
			model.addAttribute("student",student);
			return "/group/addGroupUeberleitung";
		}
		if (isInGroup(student.getMatnr())){
			System.out.println(isInGroup(student.getMatnr()));
			System.out.println("000");
			model.addAttribute("errorNachricht","Sie sind schon in einer Gruppe!!!");
		return "/group/addGroup";
		}
		createJustGroup(student);
		model.addAttribute("student",student);
		return "/group/addGroupUeberleitung";
		}
	
		private void createJustGroup(Student student){
			Student s=studentRepo.findOneByMatnr(student.getMatnr());
			Group gra=new Group();
			gra.setName(student.getNameRelGroup());
			//gra.setAdmin(s);
			groupRepo.save(gra);			
			s.setInGroup(true);
			s.setRelGroup(gra);
			studentRepo.save(s);
			
		}
		
		private void creatNewStudentAndGroup(Student student){
			System.out.println("gefunden");
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
			//gra.setAdmin(s);
		}
	@GetMapping(path="/removeGroup")
    public String groupRemovieren(Model model){
		model.addAttribute("Liste2",groupRepo.findAll());
		return "/group/removeGroup";
	}
	
	@GetMapping(path="/removeGroupFrom")
	public  RedirectView removeGroup(@RequestParam String name1){
		groupRepo.delete(groupRepo.findOneByName(name1));
		
		return  new RedirectView("/group/allGroup");
		
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

	public boolean findStudent(String matnr){
		if (studentRepo.findOneByMatnr(matnr)!=null){
			return true;
		}
		return false;
	}
	
}
