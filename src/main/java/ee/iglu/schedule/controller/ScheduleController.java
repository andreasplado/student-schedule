package ee.iglu.schedule.controller;

import ee.iglu.schedule.model.Class;
import ee.iglu.schedule.model.Student;
import ee.iglu.schedule.service.ClassService;
import ee.iglu.schedule.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class ScheduleController {

    @Autowired
    private ClassService classService;
    @Autowired
    private StudentService studentService;
    
    @GetMapping("/")
    public String welcome(Model model) {
        List<Class> classes = classService.getAllClasses();

        model.addAttribute("scheduleClasses", classes );
        model.addAttribute("allStudents", studentService.getAllStudents());
        model.addAttribute("studentClasses", studentService.getAllStudentsWithClasses( classes ) );

        return "main";
    }

    @GetMapping("/edit-class/{id}")
    public String edit(Model model, @PathVariable("id") long id){
        model.addAttribute("scheduleClass", classService.getAClass(id));

        return "class_edit";
    }

    @PostMapping("/edit-class/{id}")
    public String edit(@PathVariable("id") long id,
                       @Valid Class aClass,
                       BindingResult bindingResult,
                       Model model){
        classService.updateClass(aClass);

        return "class_edited_successfully";
    }

    @GetMapping("/class-details/{id}")
    public String classDetails(Model model, @PathVariable("id") long id){
        Class aClass = classService.getAClass(id);
        if(aClass != null){
            model.addAttribute("scheduleClass", aClass);
            return "class_details";
        }else {
            return "404";
        }
    }

    @GetMapping("/add-class")
    public String addClass(){
        return "class_add";
    }

    @PostMapping("/add-class")
    public String classSubmit(@ModelAttribute Class aClass){
        classService.saveClass(aClass);

        return "class_submitted_successfully";
    }

    @PostMapping("delete-class/{id}")
    public String deleteClass(Model model, @PathVariable("id") long id,
                              @Valid Class aClass){
        if(classService.deleteClass(id)){
            return "class_deleted_successfully";
        }else{
            return "class_not_found";
        }
    }

    @GetMapping("/query-result-student-classes/{studentName}")
    public String studentClasses(Model model, @PathVariable("studentName") String studentName){
        List<Class> classes = classService.getAllClasses();

        model.addAttribute("scheduleClasses", classes );
        model.addAttribute("students", studentService.getAllStudentsWithClasses( classes ) );
        model.addAttribute("studentClasses", studentService.getAllClassesByStudentName(studentName));
        model.addAttribute("searchString", studentName);
        model.addAttribute("allStudents", studentService.getAllStudents());
        model.addAttribute("studentClassesDistinctTime", studentService.getStudentClassesWithDistinctTime(studentName));

        return "student_classes_query_result";
    }

    @GetMapping("/student-details/{id}")
    public String studentDetails(Model model, @PathVariable("id") long id){
        List<Class> classes = classService.getAllClasses();

        model.addAttribute("scheduleClasses", classes );
        model.addAttribute("studentWithClasses", studentService.getStudentWithClasses( classes, id ) );

        return "student_details";
    }
}
