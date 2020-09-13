package main.java.web;

import main.java.entity.Group;
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
@RequestMapping("/group")
public class GroupController {


    @Autowired
    private ServiceDatabase serviceDatabase;

    // RequestMethod.GET
    // http://localhost:8080/group/all
    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Group>> getAllProduct() {

        List<Group> listGroup = serviceDatabase.findAllGroup();
        return new ResponseEntity<>(listGroup, HttpStatus.OK);
    }

    // RequestMethod.GET
    // http://localhost:8080/group/filtr?name=name1
    @RequestMapping(value = "/filtr", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<List<Group>> getFilterProduct(@RequestParam(value="name", required=false, defaultValue="")  String groupName) {

        List<Group> listGroup  = serviceDatabase.findAllGroupByName(groupName);
        return new ResponseEntity<>(listGroup, HttpStatus.OK);
    }

    // RequestMethod.GET
    // http://localhost:8080/group?id=10
    @RequestMapping(value = "", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseBody
    @CrossOrigin
    public ResponseEntity<Group> getFilterProduct(@RequestParam(value="id", required=false, defaultValue="")  long groupId) {

        Group g  = serviceDatabase.findGroupById(groupId);
        if (g == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(g, HttpStatus.OK);
    }


    // RequestMethod.POST
    // http://localhost:8080/group/add  + object
    @RequestMapping(value = "add", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Group> saveProduct(@RequestBody @Valid Group gr) {

        if (gr == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        if (serviceDatabase.isExistsGroupById(gr.getGroupId()))
            return new ResponseEntity<>(HttpStatus.CONFLICT);

        Group temp = serviceDatabase.addNewGroup(gr);

        if (temp != null)
            return new ResponseEntity<>(temp, new HttpHeaders(), HttpStatus.CREATED);
        else
            return new ResponseEntity<>(temp, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    // RequestMethod.DELETE
    // http://localhost:8080/group/delete?id=10
    @RequestMapping(value = "delete", method = RequestMethod.DELETE, produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<Group> deleteProduct(       @RequestParam(value="id", required=false, defaultValue="")  long groupId) {

        if (!serviceDatabase.isExistsGroupById(groupId))
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);

        if ( 0 != serviceDatabase.getCountStudentByGroupId(groupId))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        serviceDatabase.deleteGroup(groupId);
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.OK);
    }



}
