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
import saborescerrado.jp.tp2.dto.EstadoDTO;
import saborescerrado.jp.tp2.service.EstadoService;

@Path("/estado")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class EstadoResource {

    @Inject
    EstadoService estadoService;

    @GET
    public Response getAll() {
        return estadoService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return estadoService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return estadoService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return estadoService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return estadoService.getAdminId(id);
    }

    @GET
    @Path("/nome/{nome}")
    public Response getNome(@PathParam("nome") String nome) {
        return estadoService.getNome(nome);
    }

    @GET
    @Path("/sigla/{sigla}")
    public Response getSigla(@PathParam("sigla") String sigla) {
        return estadoService.getSigla(sigla);
    }

    @POST
    public Response insert(EstadoDTO estadoDTO) {
        return estadoService.insert(estadoDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, EstadoDTO estadoDTO) {
        return estadoService.update(id, estadoDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return estadoService.delete(id);
    }
}
