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
import saborescerrado.jp.tp2.dto.PremiacaoDTO;
import saborescerrado.jp.tp2.service.PremiacaoService;

@Path("/premiacao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PremiacaoResource {

    @Inject
    PremiacaoService premiacaoService;

    @GET
    public Response getAll() {
        return premiacaoService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return premiacaoService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return premiacaoService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return premiacaoService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return premiacaoService.getAdminId(id);
    }

    @GET
    @Path("/evento/{evento}")
    public Response getEvento(@PathParam("evento") String evento) {
        return premiacaoService.getEvento(evento);
    }

    @GET
    @Path("/ano/{ano}")
    public Response getAno(@PathParam("ano") Integer ano) {
        return premiacaoService.getAno(ano);
    }

    @POST
    public Response insert(PremiacaoDTO premiacaoDTO) {
        return premiacaoService.insert(premiacaoDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PremiacaoDTO premiacaoDTO) {
        return premiacaoService.update(id, premiacaoDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return premiacaoService.delete(id);
    }
}
