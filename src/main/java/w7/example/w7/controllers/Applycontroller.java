package w7.example.w7.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

	
	@GetMapping(path="/addApply")
	public  String addApply(Model model){
		
		Student s=new Student();
		Group g=new Group();
		g.setName("applyment");
		groupRepo.save(g);
		s.setName("apply");
		s.setMatnr("0000");
		s.setRelGroup(g);
		studentRepo.save(s);
		Apply app=new Apply();
		app.setCandidate(s);
		app.setRelAppGroup(g);
		applyRepo.save(app);
		model.addAttribute("applyListe",applyRepo.findAll());
		System.out.println(app.getCandidate().getName());
		System.out.println("222");
		
		return "/apply/applyList";
	}

}
