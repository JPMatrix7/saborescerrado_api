package saborescerrado.jp.tp2.service;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.CartaoDTO;

public interface CartaoService {

    public Response getAll();

    public Response getAllAdmin(int page, int pageSize);

    public long count();

    public Response getId(@PathParam("id") long id);

    public Response getAdminId(@PathParam("id") long id);

    public Response insert(CartaoDTO cartao);

    public Response update(@PathParam("id") long id, CartaoDTO cartao);

    public Response delete(@PathParam("id") Long id);
}
