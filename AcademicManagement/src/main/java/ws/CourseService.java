package ws;

import dtos.CourseDTO;
import ejb.CourseBean;
import entity.Course;

import javax.ejb.EJB;
import javax.ws.rs.*;
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

    @POST
    @Path("/")
    public Response createNewCourse(CourseDTO courseDTO){
        courseBean.create(courseDTO.getCode(),
                courseDTO.getName(),
                courseDTO.getStudents());

        Course course = courseBean.findCourse(courseDTO.getCode());
        if (course == null){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

        return Response.status(Response.Status.CREATED).entity(toDTO(course)).build();
    }
}
