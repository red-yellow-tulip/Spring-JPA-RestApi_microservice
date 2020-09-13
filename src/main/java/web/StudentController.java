package main.java.web;

import main.java.entity.Student;
import main.java.service.ServiceDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ServiceDatabase serviceDatabase;

    // RequestMethod.GET
    // http://localhost:8080/student/all
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Student>> getAllProduct() {

        List<Student> listStudent = new ArrayList<>(serviceDatabase.findAllStudent());
        if (listStudent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listStudent, HttpStatus.OK);
    }

    // RequestMethod.GET
    // http://localhost:8080/student/filtr?name=name1&sname=surname1
    @RequestMapping(value = "/filtr", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Student>> getFilterProduct(@RequestParam(value="name", required=false, defaultValue="")  String name,
                                                          @RequestParam(value="sname", required=false, defaultValue="")  String sname) {

        List<Student> listProduct  = serviceDatabase.findAllStudentByNameLikeOrSurnameLike(name,sname);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    // RequestMethod.GET
    // http://localhost:8080/student/group?id=10
    @RequestMapping(value = "/group", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Student>> getFilterProduct(@RequestParam(value="id", required=false, defaultValue="")  long groupId) {

        List<Student> listProduct  = serviceDatabase.findStudentByGroupId(groupId);
        return new ResponseEntity<>(listProduct, HttpStatus.OK);
    }

    // RequestMethod.POST
    // http://localhost:8080/student?id=11   + object
    @RequestMapping(value = "", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Student> saveProduct(@RequestBody @Valid Student student,
                                               @RequestParam(value="id", required=false, defaultValue="")  long groupId) {

        if (!serviceDatabase.isExistsGroupById(groupId))
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (serviceDatabase.isExistsStudent(student.getName(),student.getSurname()))
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        if (student == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Student temp = serviceDatabase.addStudentByGroupId(student, groupId);

        if (temp != null)
               return new ResponseEntity<>(temp, new HttpHeaders(), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(temp, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    // RequestMethod.DELETE
    // http://localhost:8080/student/delete?name=name1&sname=surname1
    @RequestMapping(value = "delete", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Student> deleteProduct(       @RequestParam(value="name", required=false, defaultValue="")  String name,
                                                        @RequestParam(value="sname", required=false, defaultValue="") String sname)  {

        if (!serviceDatabase.isExistsStudent(name,sname))
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        serviceDatabase.deleteStudent(name,sname);

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }
}
