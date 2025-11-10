package saborescerrado.jp.tp2.service;

import java.util.List;

import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.core.Response;
import saborescerrado.jp.tp2.dto.PessoaFisicaDTO;
import saborescerrado.jp.tp2.dto.UsuarioResponseDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateEmailDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateLoginDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateNomeDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdatePerfisDTO;
import saborescerrado.jp.tp2.dto.UsuarioUpdateSenhaDTO;

public interface UsuarioService {

    public List<UsuarioResponseDTO> getAll(int page, int pageSize);

    public long count();

    public List<UsuarioResponseDTO> getNome(@PathParam("nome") String nome);

    public Response getId(@PathParam("id") long id);

    public Response insert(PessoaFisicaDTO p);

    public UsuarioResponseDTO updateSenha(Long id, UsuarioUpdateSenhaDTO senha);

    public UsuarioResponseDTO updateEmail(Long id, UsuarioUpdateEmailDTO email);

    public UsuarioResponseDTO updateLogin(Long id, UsuarioUpdateLoginDTO login);

    public UsuarioResponseDTO updateNome(Long id, UsuarioUpdateNomeDTO nome);

    public UsuarioResponseDTO updatePerfis(Long id, UsuarioUpdatePerfisDTO perfis);

    public Response update(Long id, PessoaFisicaDTO dto);

    public Response delete(Long id);

    public Response resetarSenha(Long id);
}
