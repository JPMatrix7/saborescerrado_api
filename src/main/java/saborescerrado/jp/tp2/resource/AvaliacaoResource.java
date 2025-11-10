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
import saborescerrado.jp.tp2.dto.AvaliacaoDTO;
import saborescerrado.jp.tp2.service.AvaliacaoService;

@Path("/avaliacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AvaliacaoResource {

    @Inject
    AvaliacaoService avaliacaoService;

    @GET
    public Response getAll() {
        return avaliacaoService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return avaliacaoService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return avaliacaoService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return avaliacaoService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return avaliacaoService.getAdminId(id);
    }

    @GET
    @Path("/estrelas/{estrelas}")
    public Response getEstrelas(@PathParam("estrelas") Integer estrelas) {
        return avaliacaoService.getEstrelas(estrelas);
    }

    @POST
    public Response insert(AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.insert(avaliacaoDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.update(id, avaliacaoDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return avaliacaoService.delete(id);
    }
}
