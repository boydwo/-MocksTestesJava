package br.com.caelum.leilao.servico;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import org.omg.CORBA.RepositoryIdHelper;

import br.com.caelum.leilao.builder.CriadorDeLeilao;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.infra.dao.LeilaoDao;

public class EncerradorDeLeilaoTest {
	
 @Test
 public void deveEncerrarLeiloesQueComecaramUmaSemanaAntes() {
	 
	Calendar antiga = Calendar.getInstance();
	antiga.set(1999,1,20);
	
	Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma")
			.naData(antiga).constroi();
	Leilao leilao2 = new CriadorDeLeilao().para("Geladeira")
			.naData(antiga).constroi();
	List<Leilao> leiloesAntigos = Arrays.asList(leilao1, leilao2);
	
	LeilaoDao daoFalso = mock(LeilaoDao.class);
	
	when(daoFalso.correntes()).thenReturn(leiloesAntigos);
	
	EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
	encerrador.encerra();
	 
	assertEquals(2, encerrador.getTotalEncerrados());
	assertTrue(leilao1.isEncerrado());
	assertTrue(leilao2.isEncerrado());
 }
 
 @Test
 public void deveAtualizarLeiloesEncerrados() {
	 Calendar antiga = Calendar.getInstance();
	 antiga.set(199,1,20);
	 
	 Leilao leilao1 = new CriadorDeLeilao().para("TV de plasma").naData(antiga)
			 .constroi();
	 
//	 RepositorioDeLeiloes daoFalso = mock(RepositorioDeLeiloes.class);
	 LeilaoDao daoFalso = mock(LeilaoDao.class);
	 when(daoFalso.correntes()).thenReturn(Arrays.asList(leilao1));
	
	 EncerradorDeLeilao encerrador = new EncerradorDeLeilao(daoFalso);
	 encerrador.encerra();
	 
	 verify(daoFalso, times(1)).atualiza(leilao1);
		
 }
}
