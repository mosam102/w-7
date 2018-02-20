package w7.example.w7;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;

import w7.example.w7.controllers.Studentcontroller;
import w7.example.w7.resources.studentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class W7ApplicationTests {

	@Mock
	Model m;
	@Mock
	Errors error;
	@Mock
	Student student;
	@Rule public MockitoRule rule=MockitoJUnit.rule();
	@Mock
	studentRepository sRepo;
	@InjectMocks
	Studentcontroller sController;
	
	@Test
	public void contextLoads() {
		
		Studentcontroller s= new Studentcontroller();
		s.signin(m).equals("/logIn");
		Mockito.verify(m).addAttribute(new Student());
		
	}
	@Test
	public void StudentNameHasError() {
		
		Student student=Mockito.mock(Student.class);
		//Studentcontroller s= new Studentcontroller();
		Mockito.when(error.hasErrors()).thenReturn(true);
		sController.findStudent(student, error, m).equals("/logIn");
		Mockito.verify(error).hasErrors();
		
	}
	@Test
	public void StudentNameHasNoError() {
		
		Mockito.when(error.hasErrors()).thenReturn(false);
		sController.findStudent(student, error, m).equals("/student/studentsOfGroup");
		Mockito.verify(error).hasErrors();
		
	}
	@Test
	public void StudentMatnrHasError() {
		
		//Mockito.when(error.hasErrors()).thenReturn(false);
		Student st=Mockito.mock(Student.class);
		st.setMatnr("000000000");
		sController.findStudent(st, error, m).equals("/logIn");
		Mockito.verify(st).setMatnr("000000000");

	}
	@Test
	public void StudentMatnrHasnoError() {
		
		//Mockito.when(error.hasErrors()).thenReturn(false);
		Student st=Mockito.mock(Student.class);
		st.setMatnr("0000000");
		Mockito.when(sRepo.findOneByMatnr("0000000")).thenReturn(new Student());
		sController.findStudent(st, error, m).equals("/student/studentsOfGroup");
		Mockito.verify(st).setMatnr("0000000");

	}

	@Test
	public void StudentIsInGroup() {
		Mockito.when(sRepo.findOneByMatnr("00011")).thenReturn(new Student());
		assertEquals(sController.findStudent("00011"),true);
		//Mockito.verify(sRepo.findOneByMatnr("000")).equals(new Student());
		
	}
	@Test
	public void StudentIsNotInGroup() {
		//studentRepository sRepo=Mockito.mock(studentRepository.class);
		//Student student=Mockito.mock(Student.class);
		//Studentcontroller s= new Studentcontroller();
		Mockito.when(sRepo.findOneByMatnr("00011")).thenReturn(null);
		assertEquals(sController.findStudent("00011"),false);
		//Mockito.verify(sRepo.findOneByMatnr("000")).equals(new Student());
		
	}
	
	/*@Test
	public void contextInhaltLoad() {
		
		Model m= Mockito.mock(Model.class);
		//Errors e= Mockito.mock(Errors.class);
		Studentcontroller s= new Studentcontroller();
		Mockito.verify(m).addAttribute("msg","SUCCESS");
		m.toString().equals("SUCCESS");
	}*/
	

	/*@RunWith(MockitoJUnitRunner.class) 
	public StudentcontrollerTest {

	    //private Studentcontroller s;
	    @Mock
	    private Model model;

	    @Before
	    public void before(){
	    	Studentcontroller studentcontroller = new Studentcontroller();
	    }

	    public void testSomething(){
	        String returnValue = studentcontroller.home(model);
	        verify(model, times(1)).addAttribute("msg", "SUCCESS");
	        assertEquals("hello", returnValue);
	    }

	}
*/	
}
