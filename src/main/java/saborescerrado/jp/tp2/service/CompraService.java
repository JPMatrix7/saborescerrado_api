package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.CompraDTO;

public interface CompraService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response getStatus(@PathParam("status") String status);

    public Response getCodigoRastreio(@PathParam("codigoRastreio") String codigoRastreio);

    public Response insert(CompraDTO compra);

    public Response update(@PathParam("id") long id, CompraDTO compra);

    public Response delete(@PathParam("id") Long id);
}
