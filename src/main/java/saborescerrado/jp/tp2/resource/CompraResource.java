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
import saborescerrado.jp.tp2.dto.CompraDTO;
import saborescerrado.jp.tp2.service.CompraService;

@Path("/compra")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CompraResource {

    @Inject
    CompraService compraService;

    @GET
    public Response getAll() {
        return compraService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return compraService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return compraService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return compraService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return compraService.getAdminId(id);
    }

    @GET
    @Path("/status/{status}")
    public Response getStatus(@PathParam("status") String status) {
        return compraService.getStatus(status);
    }

    @GET
    @Path("/rastreio/{codigoRastreio}")
    public Response getCodigoRastreio(@PathParam("codigoRastreio") String codigoRastreio) {
        return compraService.getCodigoRastreio(codigoRastreio);
    }

    @POST
    public Response insert(CompraDTO compraDTO) {
        return compraService.insert(compraDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CompraDTO compraDTO) {
        return compraService.update(id, compraDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return compraService.delete(id);
    }
}
