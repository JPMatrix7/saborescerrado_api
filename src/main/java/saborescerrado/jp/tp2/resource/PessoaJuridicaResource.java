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
import saborescerrado.jp.tp2.dto.PessoaJuridicaDTO;
import saborescerrado.jp.tp2.service.PessoaJuridicaService;

@Path("/pessoas-juridicas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaJuridicaResource {

    @Inject
    PessoaJuridicaService pessoaJuridicaService;

    @GET
    public Response getAll() {
        return pessoaJuridicaService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return pessoaJuridicaService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return pessoaJuridicaService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return pessoaJuridicaService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return pessoaJuridicaService.getAdminId(id);
    }

    @GET
    @Path("/cnpj/{cnpj}")
    public Response getCnpj(@PathParam("cnpj") String cnpj) {
        return pessoaJuridicaService.getCnpj(cnpj);
    }

    @POST
    public Response insert(PessoaJuridicaDTO pessoaDTO) {
        return pessoaJuridicaService.insert(pessoaDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, PessoaJuridicaDTO pessoaDTO) {
        return pessoaJuridicaService.update(id, pessoaDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return pessoaJuridicaService.delete(id);
    }
}
