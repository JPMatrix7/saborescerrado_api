package saborescerrado.jp.tp2.resource;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
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
import saborescerrado.jp.tp2.dto.PessoaFisicaDTO;
import saborescerrado.jp.tp2.service.PessoaFisicaService;

@Path("/pessoafisica")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaFisicaResource {

    @Inject
    PessoaFisicaService pessoaFisicaService;

    @GET
    public Response getAll() {
        return pessoaFisicaService.getAll();
    }

    @GET
    @Path("/admin/{page}/{pageSize}")
    public Response getAllAdmin(@PathParam("page") int page, @PathParam("pageSize") int pageSize) {
        return pessoaFisicaService.getAllAdmin(page, pageSize);
    }

    @GET
    @Path("/count")
    public long count() {
        return pessoaFisicaService.count();
    }

    @GET
    @Path("/{id}")
    public Response getId(@PathParam("id") long id) {
        return pessoaFisicaService.getId(id);
    }

    @GET
    @Path("/admin/id/{id}")
    public Response getAdminId(@PathParam("id") long id) {
        return pessoaFisicaService.getAdminId(id);
    }

    @GET
    @Path("/cpf/{cpf}")
    public Response getCpf(@PathParam("cpf") String cpf) {
        return pessoaFisicaService.getCpf(cpf);
    }

    @POST
    public Response insert(@Valid PessoaFisicaDTO pessoaDTO) {
        return pessoaFisicaService.insert(pessoaDTO);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, @Valid PessoaFisicaDTO pessoaDTO) {
        return pessoaFisicaService.update(id, pessoaDTO);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        return pessoaFisicaService.delete(id);
    }
}
