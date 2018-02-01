package w7.example.w7.controllers;

import java.util.HashMap;

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

import w7.example.w7.Apply;
import w7.example.w7.Group;
import w7.example.w7.Student;
import w7.example.w7.resources.applyRepository;
import w7.example.w7.resources.groupRepository;
import w7.example.w7.resources.studentRepository;

@Controller
@RequestMapping(path="/apply/")
public class Applycontroller {
	
	@Autowired
	private studentRepository studentRepo;
	
	@Autowired
	private groupRepository groupRepo ;
	
	@Autowired
	private applyRepository applyRepo;


	@GetMapping(path="/applyList")
	public  String getAllGroup(Model model){
		//groupRepo.deleteAll();
		//studentRepo.deleteAll();
		//applyRepo.deleteAll();
		model.addAttribute("Liste2",groupRepo.findAll());
		model.addAttribute("applyList",applyRepo.findAll());
		return "/apply/applyList";
	}

	@GetMapping(path="/addApply")
	public  String addApply(Model model,@ModelAttribute Student student){
		
		model.addAttribute("applyListe",applyRepo.findAll());
		model.addAttribute("student",new Student());
		model.addAttribute("Liste2",groupRepo.findAll());
		return "/apply/addNewApply";
	}
	@GetMapping(path="/addApply2")
	public String groupAddieren2(@ModelAttribute @Valid Student student,String name1,Errors errors,Model model){
		System.out.println("geht!");
		if(errors.hasErrors()){
			model.addAttribute("errorNachricht","Bitte beachtem Sie die eingegebenen Daten!!!");
			return "/apply/addNewApply";
		}
	
		if(!findStudent(student.getMatnr())){
			System.out.println("findone");
			createStudent(student);
			}
		if (isInGroup(student.getMatnr())){
			System.out.println(isInGroup(student.getMatnr()));
			System.out.println("000");
			model.addAttribute("Liste2",groupRepo.findAll());
			model.addAttribute("errorNachricht","Sie sind schon in einer Gruppe!!!");
		return "/apply/addNewApply";
		}
		Student s=studentRepo.findOneByMatnr(student.getMatnr());
		Apply app=new Apply();
		app.setCandidate(s);
		app.setRelAppGroup(groupRepo.findOneByName(name1));
		applyRepo.save(app);
		model.addAttribute("applyList",applyRepo.findAll());
		model.addAttribute("student",student);
		model.addAttribute("Liste2",groupRepo.findAll());
		return "/apply/applyList";
		}
		@GetMapping(path="/wahlApp")
		public  String wahlApp(@ModelAttribute Group group,Model model){
			System.out.println("da!");
			model.addAttribute("group",groupRepo.findOneByName(group.getName()));
			model.addAttribute("applyList",applyRepo.findAll());
			return "/apply/wahlApp";
		}
		@GetMapping(path="/bestimmen")
		public  String bestimmen(@ModelAttribute Group group,@RequestParam long name1,@RequestParam String option,@RequestParam String matnr,Model model){
			if(option.equals("yes")){
				Student s=studentRepo.findOneByMatnr(matnr);
				Apply apply=applyRepo.findOne(name1);
				s.setRelApply(apply);
				HashMap <Student,Boolean> m=new HashMap<>();
				m.put(s, true);
				
				//apply.setMap(m);
				//s.setRelApply(apply);
				studentRepo.save(s);
				applyRepo.save(apply);
			}
			model.addAttribute("group",groupRepo.findOneByName(group.getName()));
			model.addAttribute("applyList",applyRepo.findAll());
			return "/student/studentsOfGroup";
		}
		private void createStudent(Student student){
			studentRepo.save(student);
			
		}
		public boolean isInGroup(String matnr){
			Student s=studentRepo.findOneByMatnr(matnr);
			return s.isInGroup;
		}

		public boolean findStudent(String matnr){
			if (studentRepo.findOneByMatnr(matnr)!=null){
				return true;
			}
			return false;
		}

}
