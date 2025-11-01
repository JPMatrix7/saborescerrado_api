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
import saborescerrado.jp.tp2.dto.CartaoDTO;
import saborescerrado.jp.tp2.service.CartaoService;

@Path("/cartoes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CartaoResource {

    @Inject
    CartaoService cartaoService;

    @GET
    public Response getAll() {
        return cartaoService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return cartaoService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return cartaoService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return cartaoService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return cartaoService.getAdminId(id);
    }

    @POST
    public Response insert(CartaoDTO cartaoDTO) {
        return cartaoService.insert(cartaoDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, CartaoDTO cartaoDTO) {
        return cartaoService.update(id, cartaoDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return cartaoService.delete(id);
    }
}
