import main.java.entity.Group;
import main.java.entity.Student;
import main.java.entity.University;
import main.java.service.ConfDataSource;
import main.java.service.ServiceDatabase;
import main.java.web.config.SourceParameterWrapperStudent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@ComponentScan(basePackages={"main.java.*"})
@EnableWebMvc
public class testStudentController {


    private static final Logger log = LogManager.getLogger();

    private RestTemplate restTemplate = new RestTemplate();

    private GenericApplicationContext ctx;

    @Resource
    private ServiceDatabase service;

    private static final String url =      "http://localhost:8080/";
    private static final String getAll =   url+"student/all";
    private static final String filtr =    url+"student/filtr?name=nam&sname=surnam";
    private static final String filtr1 =   url+"student/filtr?name=name5&sname=surname5";
    private static final String groupId =  url+"student/group?id=10";
    private static final String post =     url+"student?id=10";
    private static final String del =      url+"student/delete?name=name9&sname=surname9";


    @Before
    public void testBefore() {

        ctx = new AnnotationConfigApplicationContext(ConfDataSource.class);
        service = ctx.getBean(ServiceDatabase.class);

        assertNotNull(service);

        service.clearTable();

    }

    @Test
    public void TestGetAll(){

        addStudent();

        List<Student> allStudent = restTemplate.getForObject(getAll, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent.size() == 10);
        System.out.println(allStudent);

    }

    @Test
    public void TestFiltr(){

        addStudent();

        //http://localhost:8080/student/filtr?name=name1&sname=surname1
        List<Student> allStudent = restTemplate.getForObject(filtr, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent.size() == 10);
        System.out.println(allStudent);

        List<Student> allStudent1 = restTemplate.getForObject(filtr1, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent1.size() == 1);
        System.out.println(allStudent1);

    }

    @Test
    public void TestGroupId(){

        addStudent();


        List<Student> allStudent = restTemplate.getForObject(groupId, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent.size() == 10);
        System.out.println(allStudent);


    }


    @Test
    public void TestAddNewStudent(){

        long id = 100L, groupIdn = 10;
        University un = new University(id,"Best university");

        Group gr = new Group(groupIdn,"group1");
        gr.setUniversity(un);
        un.getListGroup().add(gr);
        service.saveUniversity(un);


        for(long i = 0; i< 10; i++){
            Student s = new Student("name"+i,"surname"+i,new Date());
            ResponseEntity<Student> e = restTemplate.postForEntity(post,s,Student.class);
            assert(e.getStatusCode() ==  HttpStatus.CREATED);
            System.out.println(e.getStatusCode());

        }

        List<Student> allStudent = restTemplate.getForObject(groupId, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent.size() == 10);
        System.out.println(allStudent);


    }

    @Test
    public void TestDelete(){

        addStudent();
        restTemplate.delete(del);

        List<Student> allStudent = restTemplate.getForObject(groupId, SourceParameterWrapperStudent.ListWrapper.class);
        assert(allStudent.size() == 9);
        System.out.println(allStudent);

    }







    private void addStudent(){
        long id = 100L, groupIdn = 10;
        University un = new University(id,"Best university");

        Group gr = new Group(groupIdn,"group1");
        gr.setUniversity(un);

        for(long i = 0; i< 10; i++){
            Student s = new Student("name"+i,"surname"+i,new Date());
            s.setGroup(gr);
            gr.getListStudents().add(s);
        }

        un.getListGroup().add(gr);
        service.saveUniversity(un);
        List<Student> l = service.findStudentByGroupId(groupIdn);
        assert(l.size() == 10);

    }



}
