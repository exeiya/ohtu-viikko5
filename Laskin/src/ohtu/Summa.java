
package ohtu;

import javax.swing.JTextField;

public class Summa implements Komento{
    private Sovelluslogiikka sovellus;
    private JTextField syotekentta;
    private JTextField tuloskentta;
    private int addedValue;
    
    public Summa(Sovelluslogiikka sovellus, JTextField tuloskentta, JTextField syotekentta){
        this.sovellus = sovellus;
        this.syotekentta = syotekentta;
        this.tuloskentta = tuloskentta;
        this.addedValue = 0;
    }
    
    @Override
    public void suorita(){
        int arvo = 0;
        try {
            arvo = Integer.parseInt(syotekentta.getText());
        } catch (Exception e) {
        }
        addedValue = arvo;
        
        sovellus.plus(arvo);
        int tulos = sovellus.tulos();
        
        syotekentta.setText("");
        tuloskentta.setText("" + tulos);
        
    }

    @Override
    public void peru() {
        sovellus.miinus(addedValue);
        int tulos = sovellus.tulos();
        
        syotekentta.setText("");
        tuloskentta.setText("" + tulos);
    }
    
}
