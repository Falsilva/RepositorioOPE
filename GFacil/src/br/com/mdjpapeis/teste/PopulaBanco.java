package br.com.mdjpapeis.teste;

import br.com.mdjpapeis.dao.UsuarioDAO;
import br.com.mdjpapeis.entity.Usuario;

public class PopulaBanco {

	public static void main(String[] args) {
				
		new UsuarioDAO().inserir(new Usuario("admin", "1234", "Fulano", "fulano@gmail.com", Usuario.Perfil.ADMINISTRADOR));
		
		/*new PerfilUsuarioDAO().inserir(new PerfilUsuario("Administrador"));
		new PerfilUsuarioDAO().inserir(new PerfilUsuario("Comprador"));
		new PerfilUsuarioDAO().inserir(new PerfilUsuario("Vendedor"));
		
		new TipoFornecedorDAO().inserir(new TipoFornecedor("Empresa"));
		new TipoFornecedorDAO().inserir(new TipoFornecedor("Catador"));
		
		List<PerfilUsuario> perfis = new PerfilUsuarioDAO().listar();
		for(PerfilUsuario perfil : perfis){
			if(perfil.getPerfil().equals("Administrador")){
				new UsuarioDAO().inserir(new Usuario("admin", "1234", "Fulano", "fulano@gmail.com", perfil));
			}
		}*/
	}

}
