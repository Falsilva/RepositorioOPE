package br.com.mdjpapeis.teste;

import java.util.List;

import br.com.mdjpapeis.dao.PerfilUsuarioDAO;
import br.com.mdjpapeis.dao.TipoFornecedorDAO;
import br.com.mdjpapeis.dao.UsuarioDAO;
import br.com.mdjpapeis.entity.PerfilUsuario;
import br.com.mdjpapeis.entity.TipoFornecedor;
import br.com.mdjpapeis.entity.Usuario;

public class PopulaBanco {

	public static void main(String[] args) {
		
		new PerfilUsuarioDAO().inserir(new PerfilUsuario("Administrador"));
		new PerfilUsuarioDAO().inserir(new PerfilUsuario("Comprador"));
		new PerfilUsuarioDAO().inserir(new PerfilUsuario("Vendedor"));
		
		new TipoFornecedorDAO().inserir(new TipoFornecedor("Empresa"));
		new TipoFornecedorDAO().inserir(new TipoFornecedor("Catador"));
		
		List<PerfilUsuario> perfis = new PerfilUsuarioDAO().listar();
		for(PerfilUsuario perfil : perfis){
			if(perfil.getPerfil().equals("Administrador")){
				new UsuarioDAO().inserir(new Usuario("admin", "1234", "Fulano", "fulano@gmail.com", perfil));
			}
		}
	}

}
