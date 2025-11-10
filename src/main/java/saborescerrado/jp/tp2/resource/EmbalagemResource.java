package saborescerrado.jp.tp2.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.EmbalagemDTO;
import saborescerrado.jp.tp2.service.EmbalagemService;

@Path("/embalagem")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EmbalagemResource {

    @Inject
    EmbalagemService embalagemService;

    @GET
    public Response getAll() {
        return embalagemService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return embalagemService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return embalagemService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return embalagemService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return embalagemService.getAdminId(id);
    }

    @POST
    public Response insert(EmbalagemDTO embalagemDTO) {
        return embalagemService.insert(embalagemDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EmbalagemDTO embalagemDTO) {
        return embalagemService.update(id, embalagemDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return embalagemService.delete(id);
    }
}
