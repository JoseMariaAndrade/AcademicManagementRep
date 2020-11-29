package ws;

import dtos.DocumentDTO;
import ejb.DocumentBean;
import ejb.StudentBean;
import entity.Document;
import entity.Student;
import exceptions.MyEntityNotFoundException;
import org.apache.commons.io.IOUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Path("/file")
public class DocumentService {

    @EJB
    private StudentBean studentBean;

    @EJB
    private DocumentBean documentBean;

    @POST
    @Path("{username}/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@PathParam("username") String username, MultipartFormDataInput multipartFormDataInput)
            throws MyEntityNotFoundException, IOException {

        Student student = studentBean.findStudent(username);

        if (student == null) {
            throw new MyEntityNotFoundException(String.format("Student with username %s not found", username));
        }

        Map<String, List<InputPart>> uploadForm = multipartFormDataInput.getFormDataMap();

        //Get file data to save
        List<InputPart> inputParts = uploadForm.get("attachment");

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                String fileName = getFileName(header);

                // Convert the uploaded file to inputstream
                InputStream inputStream = inputPart.getBody(InputStream.class, null);

                byte[] bytes = IOUtils.toByteArray(inputStream);

                String path = System.getProperty("user.home") + File.separator + "uploads";

                File customDir = new File(path);

                if (!customDir.exists())
                    customDir.mkdir();

                fileName = customDir.getCanonicalPath() + File.separator + fileName;

                writeFile(bytes, fileName);

                documentBean.create(username, path, fileName);

                return Response.status(Response.Status.OK).entity(String.format("Upload file name: %s",fileName)).build();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        return null;
    }

    @POST
    @Path("{username}/documents/")
    public List<DocumentDTO> getDocuments(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        Student student = studentBean.findStudent(username);

        if (student == null)
            throw new MyEntityNotFoundException(String.format("Student with username %s not found.", username));

        return documentsToDTOs(documentBean.getStudentDocuments(username));
    }

    @GET
    @Path("{username}/hasDocuments/")
    public Response hasDocuments(@PathParam("username") String username)
            throws MyEntityNotFoundException {

        Student student = studentBean.findStudent(username);

        if (student == null)
            throw new MyEntityNotFoundException(String.format("Student with username %s not found.", username));

        return Response.status(Response.Status.OK).entity(new Boolean(!student.getDocuments().isEmpty())).build();
    }

    private List<DocumentDTO> documentsToDTOs(List<Document> documents) {
        return documents.stream().map(this::toDTO).collect(Collectors.toList());
    }

    private DocumentDTO toDTO(Document document) {
        return new DocumentDTO(
                document.getId(),
                document.getFilepath(),
                document.getFilename()
        );
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");

        for (String fileName : contentDisposition) {
            if ((fileName.trim()).startsWith("filename")){

                String[] name = fileName.split("=");

                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }

    private void writeFile(byte[] content, String filename)
            throws IOException {

        File file = new File(filename);

        if (!file.exists())
            file.createNewFile();

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(content);
        fileOutputStream.flush();
        fileOutputStream.close();

        System.out.println(String.format("Written: %s",filename));
    }
}
