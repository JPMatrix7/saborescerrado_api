package saborescerrado.jp.tp2.resource;

import java.util.List;
import java.util.Set;

import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import saborescerrado.jp.tp2.dto.*;
import saborescerrado.jp.tp2.model.Perfil;
import saborescerrado.jp.tp2.service.UsuarioService;

@Path("/usuario")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioResource {

    @Inject
    UsuarioService usuarioService;

    @GET
    @PermitAll
    @Path("/{page}/{pageSize}")
    public List<UsuarioResponseDTO> getAll(@PathParam("page") int page, @PathParam("pageSize") int pageSize){
        return usuarioService.getAll(page, pageSize);
        
    }

    @GET
    @PermitAll
    @Path("/count")
    public long count(){
        return usuarioService.count();
    }

    @GET
    @PermitAll
    @Path("/nome/{nome}")
    public List<UsuarioResponseDTO> getNome(@PathParam("nome") String nome){
        return usuarioService.getNome(nome);
        
    }

    @GET
    @PermitAll
    @Path("/id/{id}")
    public Response getId(@PathParam("id") long id){
        return usuarioService.getId(id);
        
    }

    @POST
    @PermitAll
    @Transactional
    public Response insert(PessoaFisicaDTO p){
        return usuarioService.insert(p);
    }

    @PATCH
    @PermitAll
    @Path("/senha/{id}")
    @Transactional
    public UsuarioResponseDTO updateSenha(@PathParam("id") Long id, UsuarioUpdateSenhaDTO senha){
        return usuarioService.updateSenha(id, senha);
    }

    @PATCH
    @PermitAll
    @Path("/email/{id}")
    @Transactional
    public UsuarioResponseDTO updateEmail(@PathParam("id") Long id, UsuarioUpdateEmailDTO email){
        return usuarioService.updateEmail(id, email);
    }

    @PATCH
    @PermitAll
    @Path("/login/{id}")
    @Transactional
    public UsuarioResponseDTO updateLogin(@PathParam("id") Long id, UsuarioUpdateLoginDTO login){
        return usuarioService.updateLogin(id, login);
    }

    @PATCH
    @PermitAll
    @Path("/nome/{id}")
    @Transactional
    public UsuarioResponseDTO updateNome(@PathParam("id") Long id, UsuarioUpdateNomeDTO nome){
        return usuarioService.updateNome(id, nome);
    }

    @PATCH
    @PermitAll
    @Path("/perfis/{id}")
    @Transactional
    public UsuarioResponseDTO updatePerfis(@PathParam("id") Long id, Set<Perfil> perfis){
        return usuarioService.updatePerfis(id, new UsuarioUpdatePerfisDTO(perfis));
    }

    @PUT
    @PermitAll
    @Path("/{id}")
    @Transactional
    public Response update(@PathParam("id") Long id, PessoaFisicaUpdateDTO dto){
        return usuarioService.update(id, dto);
    }

    @PATCH
    @PermitAll
    @Path("/delete/{id}")
    @Transactional
    public Response delete(@PathParam("id") Long id) {
        return usuarioService.delete(id);
    }

    @PATCH
    @PermitAll
    @Path("/resetarsenha/{id}")
    @Transactional
    public Response resetarSenha(@PathParam("id") Long id) {
        return usuarioService.resetarSenha(id);
    }
    
}
