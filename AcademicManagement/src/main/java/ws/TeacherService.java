package ws;

import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejb.TeacherBean;
import entity.Subject;
import entity.Teacher;
import exceptions.MyConstraintViolationException;
import exceptions.MyEntityExistsException;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/teachers")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeacherService {

    @EJB
    private TeacherBean teacherBean;

    private TeacherDTO toDTO(Teacher teacher){
        return new TeacherDTO(
                teacher.getUsername(),
                teacher.getPassword(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getOffice()
        );
    }

    private List<TeacherDTO> toDTOS(List<Teacher> teachers){
        return teachers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<TeacherDTO> getAllTeachers(){
        return toDTOS(teacherBean.getAllTeachers());
    }

    @GET
    @Path("{username}")
    public Response getTeacherDetails(@PathParam("username") String username){
        Teacher teacher = teacherBean.findTeacher(username);
        if (teacher!=null){
            return Response.status(Response.Status.OK).entity(toDTO(teacher)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_TEACHER").build();
    }

    @GET
    @Path("{username}/subjects")
    public Response getTeacherSubjects(@PathParam("username") String username){
        Teacher teacher =  teacherBean.findTeacher(username);
        System.out.println(teacher);
        if (teacher != null){
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<List<SubjectDTO>>(subjectsToDTOs(teacher.getSubjects())){

            };

            return Response.status(Response.Status.OK).entity(entity).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_TEACHER").build();
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

    @POST
    @Path("/")
    public Response createNewTeacher(TeacherDTO teacherDTO)
            throws MyEntityExistsException, MyConstraintViolationException {

        teacherBean.create(
                teacherDTO.getUsername(),
                teacherDTO.getPassword(),
                teacherDTO.getName(),
                teacherDTO.getEmail(),
                teacherDTO.getOffice()
        );

        return Response.status(Response.Status.CREATED).build();
    }
}
