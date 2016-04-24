package ohtu;

import ohtu.verkkokauppa.*;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class KauppaTest {


    @Test
    public void yhdenOstoksenPaatyttyaPankinMetodiaTilisiirtoKutsutaanOikeillaParametreilla() {
        // luodaan ensin mock-oliot
        Pankki pankki = mock(Pankki.class);

        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        // määritellään että viitegeneraattori palauttaa viitten 42
        when(viite.uusi()).thenReturn(42);

        Varasto varasto = mock(Varasto.class);
        // määritellään että tuote numero 1 on maito jonka hinta on 5 ja saldo 1
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1, "maito", 5));

        // sitten testattava kauppa 
        Kauppa k = new Kauppa(varasto, pankki, viite);

        // tehdään ostokset
        k.aloitaAsiointi();
        k.lisaaKoriin(1);     // ostetaan tuotetta numero 1 eli maitoa
        k.tilimaksu("pekka", "12345");

        // sitten suoritetaan varmistus, että pankin metodia tilisiirto on kutsuttu
        verify(pankki).tilisiirto(eq("pekka"), anyInt(), eq("12345"), anyString(), eq(5));
        // toistaiseksi ei välitetty kutsussa käytetyistä parametreista
    }
    
    @Test
    public void kaksiEriOstostaSuoritetaanOikeillaTilisiirtoMetodinParametreilla() {
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2,"mehu",7));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("kuha", "54321");
        
        verify(pankki).tilisiirto(eq("kuha"), anyInt(), eq("54321"), anyString(), eq(12));
    }
    
    @Test
    public void kaksiSamaaOstostaKunSaldoOnTarpeeksiSuoritetaanOikeillaParametreilla(){
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(1);
        k.tilimaksu("kuha", "12345");
        
        verify(pankki).tilisiirto(eq("kuha"), anyInt(), eq("12345"), anyString(), eq(10));
    }
    
    @Test
    public void kaksiEriOstostaJoistaToinenLoppuSuoritetaanOikeillaParametreilla(){
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2,"mehu",7));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("kuha", "54321");
        
        verify(pankki).tilisiirto(eq("kuha"), anyInt(), eq("54321"), anyString(), eq(5));
    }
    
    @Test
    public void metodiaAloitaAsiointiKutsuttaessaEdellisetOstoksetOnNollattu(){
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(0);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2,"mehu",7));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("kuha", "54321");
        
        verify(pankki).tilisiirto(eq("kuha"), anyInt(), eq("54321"), anyString(), eq(5));
    }
    
    @Test
    public void kauppaPyytaaUudenViitteenJokaiselleMaksutapahtumalle(){
        Pankki pankki = mock(Pankki.class);
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2,"mehu",7));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
         
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.tilimaksu("kuha1", "12345");
        
        verify(viite, times(1)).uusi();
        
        k.aloitaAsiointi();
        k.lisaaKoriin(2);
        k.tilimaksu("kuha2", "23456");
        
        verify(viite, times(2)).uusi();
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.tilimaksu("kuha3", "34567");
        
        verify(viite, times(3)).uusi();
    }
    
    @Test
    public void metodiaPoistaKoristaKutsuttaessaOikeaTuotePoistetaan(){
        Pankki pankki = mock(Pankki.class);
        
        Viitegeneraattori viite = mock(Viitegeneraattori.class);
        when(viite.uusi()).thenReturn(42);
        
        Varasto varasto = mock(Varasto.class);
        when(varasto.saldo(1)).thenReturn(10);
        when(varasto.saldo(2)).thenReturn(10);
        when(varasto.haeTuote(1)).thenReturn(new Tuote(1,"maito", 5));
        when(varasto.haeTuote(2)).thenReturn(new Tuote(2,"mehu",7));
        
        Kauppa k = new Kauppa(varasto,pankki,viite);
        
        k.aloitaAsiointi();
        k.lisaaKoriin(1);
        k.lisaaKoriin(2);
        k.poistaKorista(2);
        k.tilimaksu("kuha", "54321");
        
        verify(pankki).tilisiirto(eq("kuha"), anyInt(), eq("54321"), anyString(), eq(5));
    }
    
    

}
