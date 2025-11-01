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
import saborescerrado.jp.tp2.dto.SaborDTO;
import saborescerrado.jp.tp2.service.SaborService;

@Path("/sabores")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SaborResource {

    @Inject
    SaborService saborService;

    @GET
    public Response getAll() {
        return saborService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return saborService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return saborService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return saborService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return saborService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return saborService.getNome(nome);
    }

    @POST
    public Response insert(SaborDTO saborDTO) {
        return saborService.insert(saborDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, SaborDTO saborDTO) {
        return saborService.update(id, saborDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return saborService.delete(id);
    }
}
