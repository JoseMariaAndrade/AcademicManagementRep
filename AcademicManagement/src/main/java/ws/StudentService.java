package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import ejb.StudentBean;
import entity.Student;
import entity.Subject;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;
import exceptions.MyEntityNotFoundException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/students")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class StudentService {

    @EJB
    private StudentBean studentBean;

    //Converts an entity Student to a DTO Student class
    private StudentDTO toDTONoSubjects(Student student){
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
    private List<StudentDTO> toDTOsNoSubjects(List<Student> students){
        return students.stream().map(this::toDTONoSubjects).collect(Collectors.toList());
    }

    private StudentDTO toDTO(Student student){
        StudentDTO studentDTO = new StudentDTO(
                student.getUsername(),
                student.getPassword(),
                student.getName(),
                student.getEmail(),
                student.getCourse().getCode(),
                student.getCourse().getName()
        );

        List<SubjectDTO> subjectDTOS = subjectsToDTOs(student.getSubjects());
        studentDTO.setSubjects(subjectDTOS);

        return studentDTO;
    }

    //Converts an entire list of entities into a list of DTOs
    private List<StudentDTO> toDTOs(List<Student> students){
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }



    private List<SubjectDTO> subjectsToDTOs(Set<Subject> subjects) {
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private SubjectDTO toDTO(Subject subject){
        return new SubjectDTO(
                subject.getCode(),
                subject.getName(),
                subject.getCourse().getCode(),
                subject.getCourse().getName(),
                subject.getCourseYear(),
                subject.getSchoolarYear()
        );
    }

    @GET
    @Path("/")
    public List<StudentDTO> getAllStudentsWS(){
        return toDTOsNoSubjects(studentBean.getAllStudents());
    }

    @GET
    @Path("{username}")
    public Response getStudentDetails(@PathParam("username") String username){
        Student student = studentBean.findStudent(username);
        if (student!=null){
            return Response.status(Response.Status.OK).entity(toDTO(student)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }

    @GET
    @Path("{username}/subjects")
    public Response getStudentsSubjects(@PathParam("username") String username){
        Student student = studentBean.findStudent(username);
        if (student != null){
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<List<SubjectDTO>>(subjectsToDTOs(student.getSubjects())){

            };

            return Response.status(Response.Status.OK).entity(entity).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }

    @POST
    @Path("/")
    public Response createNewStudent(StudentDTO studentDTO)
            throws MyEntityExistsException, MyEntityNotFoundException, MyConstraintViolationException {
        studentBean.create(studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());

        return Response.status(Response.Status.CREATED)
                .build();
    }

    /*@POST
    @Path("/")
    public Response updateStudent(StudentDTO studentDTO){
        studentBean.create(studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());
        Student student = studentBean.findStudent(studentDTO.getUsername());
        if (student == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity("toDTO(student)")
                .build();
    }*/

    @POST
    @Path("{username}/{subject}")
    public Response subjectStudent(@PathParam("username") String username, @PathParam("subject") int subjectCode){
        /*studentBean.create(studentDTO.getUsername(),
                studentDTO.getPassword(),
                studentDTO.getName(),
                studentDTO.getEmail(),
                studentDTO.getCourseCode());
        Student student = studentBean.findStudent(studentDTO.getUsername());*/

        studentBean.enrollStudentInSubject(subjectCode, username);

        System.out.println("ASDASD");
//        if (student == null){
            return Response.status(Response.Status.OK).build();
//        }
//        return Response.status(Response.Status.CREATED)
//                .entity(toDTO(student))
//                .build();
    }

    @DELETE
    @Path("{username}")
    public Response deleteStudent(@PathParam("username") String username){
        Student student = studentBean.findStudent(username);
        if (student!=null) {
            //studentBean.delete(student);
            return Response.status(Response.Status.OK).entity(student).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }
}
