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
import saborescerrado.jp.tp2.dto.CidadeDTO;
import saborescerrado.jp.tp2.service.CidadeService;

@Path("/cidade")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CidadeResource {

    @Inject
    CidadeService cidadeService;

    @GET
    public Response getAll() {
        return cidadeService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return cidadeService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return cidadeService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return cidadeService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return cidadeService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return cidadeService.getNome(nome);
    }

    @POST
    public Response insert(CidadeDTO cidadeDTO) {
        return cidadeService.insert(cidadeDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CidadeDTO cidadeDTO) {
        return cidadeService.update(id, cidadeDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return cidadeService.delete(id);
    }
}
