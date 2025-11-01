package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.PessoaFisicaDTO;

public interface PessoaFisicaService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getCpf(@PathParam("cpf") String cpf);

    public Response insert(PessoaFisicaDTO pessoaFisica);

    public Response update(@PathParam("id") long id, PessoaFisicaDTO pessoaFisica);

    public Response delete(@PathParam("id") Long id);
}
