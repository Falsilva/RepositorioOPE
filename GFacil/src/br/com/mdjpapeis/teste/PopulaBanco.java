package br.com.mdjpapeis.teste;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.MonthDay;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.PersistenceException;

import br.com.mdjpapeis.dao.CaixaDAO;
import br.com.mdjpapeis.dao.ClienteDAO;
import br.com.mdjpapeis.dao.FornecedorDAO;
import br.com.mdjpapeis.dao.MovimentacaoDAO;
import br.com.mdjpapeis.dao.PedidoCompraDAO;
import br.com.mdjpapeis.dao.PedidoVendaDAO;
import br.com.mdjpapeis.dao.ProdutoDAO;
import br.com.mdjpapeis.entity.Caixa;
import br.com.mdjpapeis.entity.Cliente;
import br.com.mdjpapeis.entity.Fornecedor;
import br.com.mdjpapeis.entity.ItemPedido;
import br.com.mdjpapeis.entity.Movimentacao;
import br.com.mdjpapeis.entity.PedidoCompra;
import br.com.mdjpapeis.entity.PedidoCompra.StatusCompra;
import br.com.mdjpapeis.entity.PedidoVenda;
import br.com.mdjpapeis.entity.PedidoVenda.StatusVenda;
import br.com.mdjpapeis.entity.Produto;

public class PopulaBanco {

	public static void main(String[] args) {
				
		//new UsuarioDAO().inserir(new Usuario("admin", "1234", "Fulano", "fulano@gmail.com", Usuario.Perfil.ADMINISTRADOR));
		
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
		
		//------------------- MONTANDO DADOS PARA PEDIDO DE COMPRA --------------------------
		//Fornecedor fornecedor = new FornecedorDAO().listar().get(4);
		/*
		Produto produto1 = new Produto(14);
		produto1 = new ProdutoDAO().buscaProdutoPorCodigo(produto1);
		
		Produto produto2 = new Produto(15);
		produto2 = new ProdutoDAO().buscaProdutoPorCodigo(produto2);
		
		Produto produto3 = new Produto(16);
		produto3 = new ProdutoDAO().buscaProdutoPorCodigo(produto3);
		
		ItemPedido item1 = new ItemPedido(produto1, new BigDecimal(150), produto1.getPrecoCompra());
		ItemPedido item2 = new ItemPedido(produto2, new BigDecimal(150), produto2.getPrecoCompra());
		ItemPedido item3 = new ItemPedido(produto3, new BigDecimal(150), produto3.getPrecoCompra());
		
		List<ItemPedido> itens = new ArrayList<ItemPedido>();
		/*itens.add(item1);
		itens.add(item2);
		itens.add(item3);
		
		BigDecimal valorPedido = item1.getValorItem().add(item2.getValorItem().add(item3.getValorItem()));
		
		PedidoCompra pedido = new PedidoCompra();
		pedido.setDataAbertura(Calendar.getInstance());
		pedido.setDataPagamento(Calendar.getInstance());
		pedido.setFornecedor(fornecedor);		
		pedido.setStatusCompra(StatusCompra.PENDENTE);
		pedido.setValorTotal(valorPedido);
		
		pedido.setItensPedidoCompra(itens);		
		
		//------------------- MONTANDO DADOS PARA PEDIDO DE VENDA --------------------------
		Cliente cliente = new ClienteDAO().listar().get(4);		
		
		ItemPedido item1 = new ItemPedido(produto1, new BigDecimal(150), produto1.getPrecoVenda());
		ItemPedido item2 = new ItemPedido(produto2, new BigDecimal(150), produto2.getPrecoVenda());
		ItemPedido item3 = new ItemPedido(produto3, new BigDecimal(150), produto3.getPrecoVenda());
		
		itens = null;
		itens = new ArrayList<ItemPedido>();
		itens.add(item1);
		itens.add(item2);
		itens.add(item3);
		
		BigDecimal valorPedido = item1.getValorItem().add(item2.getValorItem().add(item3.getValorItem()));
		
		PedidoVenda pedidoVenda = new PedidoVenda();
		pedidoVenda.setDataAbertura(Calendar.getInstance());
		pedidoVenda.setDataPagamento(Calendar.getInstance());
		pedidoVenda.setCliente(cliente);		
		pedidoVenda.setStatusVenda(StatusVenda.PENDENTE);
		pedidoVenda.setValorTotal(valorPedido);
		
		pedidoVenda.setItensPedidoVenda(itens);			
		
		//Instant inicio = Instant.now();
		/*
		//------------------- CADASTRANDO E EXIBINDO PEDIDO DE COMPRA --------------------------
		try{
			new PedidoCompraDAO().inserir(pedido);
			System.out.println("CADASTRADO:");								
			
			for(PedidoCompra ped : new PedidoCompraDAO().listar()){
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("\nPEDIDO COMPRA Nº " + ped.getnPedido() +
						" DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(ped.getDataAbertura().getTime()) + "\nSTATUS: " + 
						ped.getStatusCompra().toString() + "\n" +
						"EMPRESA: " + ped.getFornecedor().getEmpresa() + "\n" + 
						"CNPJ: " + ped.getFornecedor().getCnpj() + " INSC. ESTADUAL: " + ped.getFornecedor().getInscEstadual() + "\n" +
						"ENDERECO: " + ped.getFornecedor().getEndereco() + "\n" +
						"CONTATO: " + ped.getFornecedor().getContato() + " TELEFONE: " + ped.getFornecedor().getTelefone() + " E-MAIL: " + ped.getFornecedor().getEmail() + "\n\n" +
						ped.getItensPedidoCompra().size() + " ITENS:");
				for(ItemPedido item : ped.getItensPedidoCompra()){
					System.out.println(ped.getItensPedidoCompra().indexOf(item)+1 + " - "
							+ item.getProduto().getProduto() + " - " 
							+ item.getPeso() + "Kg - R$" 
							+ item.getValorItem());
				}				
				System.out.println("VLR. TOTAL: R$" + ped.getValorTotal());
			}
			
		}catch(PersistenceException e){
			System.out.println("XIIIIIIIII - NAO CADASTROU");
		}
		*/
		//------------------- CADASTRANDO E EXIBINDO PEDIDO DE VENDA --------------------------
		/*try{
			new PedidoVendaDAO().inserir(pedidoVenda);
			/*System.out.println("CADASTRADO:");							
			
			for(PedidoVenda ped : new PedidoVendaDAO().listar()){
				System.out.println("---------------------------------------------------------------------------");
				System.out.println("\nPEDIDO VENDA Nº " + ped.getnPedido() +
						" DATA: " + new SimpleDateFormat("dd/MM/yyyy").format(ped.getDataAbertura().getTime()) + "\nSTATUS: " + 
						ped.getStatusVenda().toString() + "\n" +
						"EMPRESA: " + ped.getCliente().getEmpresa() + "\n" + 
						"CNPJ: " + ped.getCliente().getCnpj() + " INSC. ESTADUAL: " + ped.getCliente().getInscEstadual() + "\n" +
						"ENDERECO: " + ped.getCliente().getEndereco() + "\n" +
						"CONTATO: " + ped.getCliente().getContato() + " TELEFONE: " + ped.getCliente().getTelefone() + " E-MAIL: " + ped.getCliente().getEmail() + "\n\n" +
						ped.getItensPedidoVenda().size() + " ITENS:");
				for(ItemPedido item : ped.getItensPedidoVenda()){
					System.out.println(ped.getItensPedidoVenda().indexOf(item)+1 + " - "
							+ item.getProduto().getProduto() + " - " 
							+ item.getPeso() + "Kg - R$" 
							+ item.getValorItem());
				}				
				System.out.println("VLR. TOTAL: R$" + ped.getValorTotal());
			}
			
		}catch(PersistenceException e){
			System.out.println("XIIIIIIIII - NAO CADASTROU");
		}
		
		
		//--------------------------------------- EXIBINDO DADOS DOS PEDIDOS -----------------------------------		
		//pedido = new PedidoCompraDAO().buscaPedidoCompraPorCodigo(3);	
		pedidoVenda = new PedidoVendaDAO().buscaPedidoVendaPorCodigo(4);
		/*
		System.out.println("\nPEDIDO COMPRA..: " + pedido.getnPedido() + 
				"\nDATA ABERTURA..: " + new SimpleDateFormat("dd/MM/yyyy").format(pedido.getDataAbertura().getTime()) + 
				"\nDATA PAGAMENTO.: " + new SimpleDateFormat("dd/MM/yyyy").format(pedido.getDataPagamento().getTime()) +
				"\nFORNECEDOR.....: " + pedido.getFornecedor().getEmpresa() +
				"\nVALOR DO PEDIDO: R$ " + pedido.getValorTotal() +
				"\nSTATUS.........: " + pedido.getStatusCompra().toString());
		
		System.out.println("\nPEDIDO VENDA...: " + pedidoVenda.getnPedido() + 
				"\nDATA ABERTURA..: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataAbertura().getTime()) + 
				"\nDATA PAGAMENTO.: " + new SimpleDateFormat("dd/MM/yyyy").format(pedidoVenda.getDataPagamento().getTime()) +
				"\nCLIENTE........: " + pedidoVenda.getCliente().getEmpresa() +
				"\nVALOR DO PEDIDO: R$ " + pedidoVenda.getValorTotal() +
				"\nSTATUS.........: " + pedidoVenda.getStatusVenda().toString());		
			
		//-------------------------- MOVIMENTAÇÕES --- CADASTRANDO MOVIMENTAÇÕES DE ENTRADA E SAÍDA -------------------
		Movimentacao movimentacaoCompra = new Movimentacao();
		movimentacaoCompra.setPedidoCompra(pedido);
		new MovimentacaoDAO().inserir(movimentacaoCompra);
		
		Movimentacao movimentacaoVenda = new Movimentacao();
		movimentacaoVenda.setPedidoVenda(pedidoVenda);
		new MovimentacaoDAO().inserir(movimentacaoVenda);		
		
		//List<Movimentacao> movimentacoes = new MovimentacaoDAO().listar();
		/*
		for(Movimentacao mov : movimentacoes){
			System.out.println("ITEM " + mov.getnLancamento() +
					": " + new SimpleDateFormat("dd/MM/yyyy").format(mov.getData().getTime()) +
					" " + mov.getTipoLancamento().toString() +
					" R$ " + mov.getValorLancamento());			
		}
		
		
		//------------------------------------------------- CAIXA ---------------------------------------------
		Caixa caixa = new CaixaDAO().buscaCaixaPorCodigo(1);
		List<Movimentacao> movimentacoes = caixa.getMovimentacoes();
		movimentacoes.add(movimentacaoVenda);		
		
		if(caixa.getSaldo() == null) caixa.setSaldo(new BigDecimal(0));
		if(caixa.getTotalEntrada() == null) caixa.setTotalEntrada(new BigDecimal(0));
		if(caixa.getTotalSaida() == null) caixa.setTotalSaida(new BigDecimal(0));
		
		
		if(movimentacoes.get(movimentacoes.size()-1).getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
			caixa.setTotalEntrada(caixa.getTotalEntrada().add(movimentacaoVenda.getValorLancamento()));
			caixa.setSaldo(caixa.getSaldo().add(caixa.getTotalEntrada()).subtract(caixa.getTotalSaida()));
			System.out.println("ENTRADA: R$ " + caixa.getTotalEntrada());
		}else{
			//caixa.setTotalSaida(caixa.getTotalSaida().add(movimentacaoCompra.getValorLancamento()));
			System.out.println("SAÍDA: R$ " + caixa.getTotalSaida());
		}
		/*
		for(Movimentacao mov : movimentacoes){
			if(mov.getTipoLancamento().equals(Movimentacao.TipoLancamento.ENTRADA)){
				caixa.setTotalEntrada(caixa.getTotalEntrada().add(mov.getValorLancamento()));
				System.out.println("ENTRADA: R$ " + caixa.getTotalEntrada());
			}else{
				caixa.setTotalSaida(caixa.getTotalSaida().add(mov.getValorLancamento()));
				System.out.println("SAÍDA: R$ " + caixa.getTotalSaida());
			}					
		}
		//caixa.setSaldo(caixa.getSaldo().add(caixa.getTotalEntrada()).subtract(caixa.getTotalSaida()));
		caixa.setMovimentacoes(movimentacoes);
		new CaixaDAO().atualizar(caixa);
		
		//System.out.println("SALDO: " + new CaixaDAO().buscaCaixaPorCodigo(1).getSaldo());		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		//Instant fim = Instant.now();
		/*
		// Data para Computadores
		Duration duracao = Duration.between(inicio, fim);
		long duracaoEmMilissegundos = duracao.toMillis();
		System.out.println("DURAÇÃO EM MILLIS: " + duracaoEmMilissegundos);
		
		// Data para Humanos - LocalDate pega o dia todo, não só a data
		LocalDate hoje = LocalDate.now();
		System.out.println("DATA: " + hoje); //2014-04-08 (formato ISO-8601)
		
		// Especificando uma Data
		LocalDate emissaoRG = LocalDate.of(2000, 1, 15);
		
		// Especificando uma Data com o enum Month
		LocalDate emissaoRGEnum = LocalDate.of(2000, Month.JANUARY, 15);
		
		// Cálculo de um Período entre dois LocalDate 
		LocalDate homemNoEspaco = LocalDate.of(1961, Month.APRIL, 12);
		LocalDate homemNaLua = LocalDate.of(1969, Month.MAY, 25);
		
		// Period já trata anos bissextos
		Period periodo = Period.between(homemNoEspaco, homemNaLua);
		 
		System.out.printf("%s anos, %s mês e %s dias", 
		  periodo.getYears() , periodo.getMonths(), periodo.getDays());	//8 anos, 1 mês e 13 dias
		
		// LocalTime - especifica um horário sem data específica
		LocalTime horarioDeEntrada = LocalTime.of(9, 0);
		System.out.println(horarioDeEntrada); //09:00
		
		// LocalDateTime especifica uma data e hora
		LocalDateTime agora = LocalDateTime.now();
		System.out.println(agora); //2014-06-12T17:00 (formato ISO-8601)
		
		LocalDateTime aberturaDaCopa = LocalDateTime.of(2014, Month.JUNE, 12, 17, 0);
		System.out.println(aberturaDaCopa); //2014-06-12T17:00 (formato ISO-8601)
		
		// Classes:
		// MonthDay, para datas importantes que se repetem todos os anos, e 
		// YearMonth, para representar um mês inteiro de um ano específico.
		
		MonthDay natal = MonthDay.of(Month.DECEMBER, 25);
		YearMonth copaDoMundo2014 = YearMonth.of(2014, Month.JUNE);
		
		// Formatando Datas
		LocalDate hojeHoje = LocalDate.now();
		DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		hojeHoje.format(formatador); //08/04/2014
		
		// Manipulando Datas
		LocalDate hojeManipula = LocalDate.now();
		LocalDate amanhaManipula = hojeManipula.plusDays(1);
		
		// Cronômetro - Mede a quantidade de tempo
		MonthDay natalMede = MonthDay.of(Month.DECEMBER, 25);
		LocalDate natalDesseAno = natalMede.atYear(Year.now().getValue());
		long diasAteONatal = LocalDate.now().until(natalDesseAno, ChronoUnit.DAYS);
		*/
		
		// SEGUNDO TESTE CAIXA ZUADO
		Caixa caixa = new CaixaDAO().buscaCaixaPorCodigo(1);
		
		
		System.out.println("\nINICIO\n\nCAIXA:\nENTRADAS: " + caixa.getTotalEntrada() + "\nSAÍDAS: " + caixa.getTotalSaida() + "\nSALDO: " + caixa.getSaldo());
		/* ENTRADA: 1306,50
		 * SAIDA  :  544,55
		 * SALDO  :  761,95
		 */
		
		System.out.println("\n\n1.ENTRADA de Venda: 500,00");
		caixa.setTotalEntrada(caixa.getTotalEntrada().add(new BigDecimal(500.00)));
		caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
		/* ENTRADA: 1806,50
		 * SAIDA  :  544,55
		 * SALDO  : 1261,95
		 */
		System.out.println("\n1.CAIXA:\nENTRADAS: " + caixa.getTotalEntrada() + "\nSAÍDAS: " + caixa.getTotalSaida() + "\nSALDO: " + caixa.getSaldo());
		
		
		System.out.println("\n\n2.SAIDA de Compra: 100,00");
		caixa.setTotalSaida(caixa.getTotalSaida().add(new BigDecimal(100.00)));
		caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
		/* ENTRADA: 1806,50
		 * SAIDA  :  644,55
		 * SALDO  : 1161,95
		 */
		System.out.println("\n2.CAIXA:\nENTRADAS: " + caixa.getTotalEntrada() + "\nSAÍDAS: " + caixa.getTotalSaida() + "\nSALDO: " + caixa.getSaldo());
		
		
		System.out.println("\n\n3.ENTRADA Excluida: 500,00");
		caixa.setTotalEntrada(caixa.getTotalEntrada().subtract(new BigDecimal(500.00)));
		caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
		/* ENTRADA: 1306,50
		 * SAIDA  :  644,55
		 * SALDO  :  661,95
		 */
		System.out.println("\n3.CAIXA:\nENTRADAS: " + caixa.getTotalEntrada() + "\nSAÍDAS: " + caixa.getTotalSaida() + "\nSALDO: " + caixa.getSaldo());
		
		
		System.out.println("\n\n4.SAIDA Excluida: 100,00");
		caixa.setTotalSaida(caixa.getTotalSaida().subtract(new BigDecimal(100.00)));
		caixa.setSaldo(caixa.getTotalEntrada().subtract(caixa.getTotalSaida()));
		/* ENTRADA: 1306,50
		 * SAIDA  :  544,55
		 * SALDO  :  761,95
		 */
		System.out.println("\n4.CAIXA:\nENTRADAS: " + caixa.getTotalEntrada() + "\nSAÍDAS: " + caixa.getTotalSaida() + "\nSALDO: " + caixa.getSaldo());
		
		System.out.println("\n\nFIM");
	}

}
