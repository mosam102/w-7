package w7.example.w7;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class studentcontroller{
	

	@GetMapping(path="/")
    public String homepage(Model model){
		model.addAttribute("alk",new String[] {"a","b","c"});
		return "student";
	}
	


}
