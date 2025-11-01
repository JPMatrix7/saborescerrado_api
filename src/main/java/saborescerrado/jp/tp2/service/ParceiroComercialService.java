package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.ParceiroComercialDTO;

public interface ParceiroComercialService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getCnpj(@PathParam("cnpj") String cnpj);

    public Response getNomeFantasia(@PathParam("nomeFantasia") String nomeFantasia);

    public Response insert(ParceiroComercialDTO parceiroComercial);

    public Response update(@PathParam("id") long id, ParceiroComercialDTO parceiroComercial);

    public Response delete(@PathParam("id") Long id);
}
