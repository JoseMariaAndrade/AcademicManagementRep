package ws;

import dtos.StudentDTO;
import dtos.SubjectDTO;
import dtos.TeacherDTO;
import ejb.SubjectBean;
import entity.Student;
import entity.Subject;
import entity.Teacher;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Path("/subjects")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SubjectService {

    @EJB
    private SubjectBean subjectBean;

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

    private List<SubjectDTO> toDTOs(List<Subject> subjects){
        return subjects.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<SubjectDTO> getAllSubjectsWS(){
        return toDTOs(subjectBean.getAllSubjects());
    }

    @GET
    @Path("{subjectCode}")
    public Response getSubjectDetails(@PathParam("subjectCode") int subjectCode){
        Subject subject = subjectBean.findSubject(subjectCode);
        if (subject!=null){
            return Response.status(Response.Status.OK).entity(toDTO(subject)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_SUBJECT").build();
    }

    @GET
    @Path("{subjectCode}/teachers")
    public Response getTeacherSubjects(@PathParam("subjectCode") int subjectCode){
        Subject subject = subjectBean.findSubject(subjectCode);
        if (subject != null){
            GenericEntity<List<TeacherDTO>> entity = new GenericEntity<List<TeacherDTO>>(teachersToDTOs(subject.getTeachers())){

            };

            return Response.status(Response.Status.OK).entity(entity).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_TEACHER").build();
    }

    @GET
    @Path("{subjectCode}/students")
    public Response getStudentsSubjects(@PathParam("subjectCode") int subjectCode){
        Subject subject = subjectBean.findSubject(subjectCode);
        if (subject != null){
            GenericEntity<List<StudentDTO>> entity = new GenericEntity<List<StudentDTO>>(studentsToDTOs(subject.getStudents())){

            };

            return Response.status(Response.Status.OK).entity(entity).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENTS").build();
    }

    @POST
    @Path("/")
    public Response createNewSubject(SubjectDTO subjectDTO){
        subjectBean.create(
                subjectDTO.getCode(),
                subjectDTO.getName(),
                subjectDTO.getCourseCode(),
                subjectDTO.getCourseYear(),
                subjectDTO.getSchoolYear()
        );
        Subject subject = subjectBean.findSubject(subjectDTO.getCode());
        if (subject == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        return Response.status(Response.Status.CREATED)
                .entity(toDTO(subject))
                .build();
    }

    private List<StudentDTO> studentsToDTOs(Set<Student> students) {
        return students.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private List<TeacherDTO> teachersToDTOs(Set<Teacher> teachers) {
        return teachers.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private TeacherDTO toDTO(Teacher teacher){
        return new TeacherDTO(
                teacher.getUsername(),
                teacher.getPassword(),
                teacher.getName(),
                teacher.getEmail(),
                teacher.getOffice()
        );
    }

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
}
