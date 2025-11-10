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
import saborescerrado.jp.tp2.dto.IngredienteDTO;
import saborescerrado.jp.tp2.service.IngredienteService;

@Path("/ingrediente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class IngredienteResource {

    @Inject
    IngredienteService ingredienteService;

    @GET
    public Response getAll() {
        return ingredienteService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return ingredienteService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return ingredienteService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return ingredienteService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return ingredienteService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return ingredienteService.getNome(nome);
    }

    @POST
    public Response insert(IngredienteDTO ingredienteDTO) {
        return ingredienteService.insert(ingredienteDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, IngredienteDTO ingredienteDTO) {
        return ingredienteService.update(id, ingredienteDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return ingredienteService.delete(id);
    }
}
