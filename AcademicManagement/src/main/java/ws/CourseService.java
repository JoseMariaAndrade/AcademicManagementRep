package ws;

import dtos.CourseDTO;
import dtos.SubjectDTO;
import ejb.CourseBean;
import entity.Course;
import entity.Subject;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/courses")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class CourseService {

    @EJB
    private CourseBean courseBean;

    private CourseDTO toDTO(Course course){
        return new CourseDTO(
                course.getCode(),
                course.getName()
        );
    }

    private List<CourseDTO> toDTOs(List<Course> courses){
        return courses.stream().map(this::toDTO).collect(Collectors.toList());
    }

    @GET
    @Path("/")
    public List<CourseDTO> getAllCoursesWS(){
        return toDTOs(courseBean.getAllCourses());
    }

    @GET
    @Path("{courseCode}")
    public Response getCourseDetails(@PathParam("courseCode") int courseCode){
        Course course = courseBean.findCourse(courseCode);
        if (course!=null){
            return Response.status(Response.Status.OK).entity(toDTO(course)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }

    @GET
    @Path("{courseCode}/subjects")
    public Response getStudentsSubjects(@PathParam("courseCode") int courseCode){
        Course course = courseBean.findCourse(courseCode);
        if (course != null){
            GenericEntity<List<SubjectDTO>> entity = new GenericEntity<List<SubjectDTO>>(subjectsToDTOs(course.getSubjects())){

            };

            return Response.status(Response.Status.OK).entity(entity).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }

    private List<SubjectDTO> subjectsToDTOs(List<Subject> subjects) {
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
    public Response createNewCourse(CourseDTO courseDTO) {
        courseBean.create(courseDTO.getCode(),
                courseDTO.getName()
        );

        return Response.status(Response.Status.CREATED).build();
    }

    @DELETE
    @Path("{courseCode}")
    public Response deleteCourse(@PathParam("courseCode") int courseCode){
        Course course = courseBean.removeCourse(courseCode);
        if (course!=null){
            return Response.status(Response.Status.OK).entity(toDTO(course)).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("ERROR_FINDING_STUDENT").build();
    }
}
