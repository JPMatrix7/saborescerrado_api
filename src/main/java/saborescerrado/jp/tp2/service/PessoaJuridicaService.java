package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.PessoaJuridicaDTO;

public interface PessoaJuridicaService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getCnpj(@PathParam("cnpj") String cnpj);

    public Response insert(PessoaJuridicaDTO pessoaJuridica);

    public Response update(@PathParam("id") long id, PessoaJuridicaDTO pessoaJuridica);

    public Response delete(@PathParam("id") Long id);
}
