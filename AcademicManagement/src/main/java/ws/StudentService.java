package ws;

import dtos.StudentDTO;
import ejb.StudentBean;
import entity.Student;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/students")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StudentService {

    @EJB
    private StudentBean studentBean;

    //Converts an entity Student to a DTO Student class
    private StudentDTO toDTO(Student student){
        return new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getCode(),
                student.getCourse().getName()
        );
    }

    //Converts an entire list of entities into a list of DTOs
    private List<StudentDTO> toDTOs(List<Student> students){
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<StudentDTO> getAllStudentsWS(){
        return toDTOs(studentBean.getAllStudents());
    }

    @POST
    @Path("/")
    public Response createNewStudent(StudentDTO studentDTO){
        studentBean.create(studentDTO.getUsername(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getPassword(),
                studentDTO.getCourseCode());

        Student student = studentBean.findStudent(studentDTO.getUsername());
        if (student == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(student))
                .build();
    }
}
