import java.io.Serializable;

public class ListaZakupow implements Serializable {

    private ProduktNaLiscie[] lista;
    private int index = 0;
    private int sizeIncrease = 8;
    private String opis;
    ListaZakupow(int productAmount){
        this.lista = new ProduktNaLiscie[productAmount];
    }

    ListaZakupow(int productAmount, String opis){
        this.lista = new ProduktNaLiscie[productAmount];
        this.opis = opis;
    }

    public void setDescripion(String desc){
        this.opis=desc;
    }

    public String getDescription(){
        return this.opis;
    }

    public void addToList(ProduktNaLiscie obj){
        //TODO: Sprawdzanie czy obiekt już jest na liście
        boolean flagaDuplikatu = false;

        for(int i=0; i<getLength(); i++){
            if(lista[i] != null){
                if(lista[i].produkt.getName().equals(obj.produkt.getName())){
                    flagaDuplikatu = true;
                    lista[i].setAmount(lista[i].getAmount() + obj.getAmount());
                    i=getLength();
                }
            }
        }

        if(!flagaDuplikatu){
            if(index < lista.length){
                lista[index] = obj;
                index++;
            }else{
                increaseListSize();
                System.out.println("ZWIĘKSZONO ROZMIAR LISTY! :"+lista.length);
                lista[index] = obj;
                index++;
            }
            System.out.println("Dopisano produkt do listy! "+obj.getDescription());
        }else{
            System.out.println("Produkt znajduje się już na liście!");
        }
    }
    public int getLength(){
        return lista.length;
    }

    public ProduktNaLiscie getItem(int ind){
        return lista[ind];
    }

    private void increaseListSize() {
        ProduktNaLiscie[] nowaLista = new ProduktNaLiscie[lista.length + sizeIncrease];

        //TODO: Replace manual array copy
        //Przepisanie listy
        for(int i=0; i<lista.length; i++){
            nowaLista[i] = lista[i];
        }
        this.lista = nowaLista;
    }
    public  void addPrzepis(Przepis obj){
        boolean flagaDuplikatu = false;


        for (int j=0; j<obj.getLength();j++) {
            if(obj.lista[j] != null) {


                for (int i = 0; i < getLength(); i++) {
                    if ((lista[i] != null)) {

                        if (lista[i].produkt.getName().equals(obj.lista[j].getName())) {
                            flagaDuplikatu = true;
                            lista[i].setAmount(lista[i].getAmount() + obj.lista[j].getAmount());
                            i = getLength();
                        }
                    }
                }

                if (!flagaDuplikatu) {
                    ProduktNaLiscie obj2 = new ProduktNaLiscie(obj.lista[j].getProdukt(), obj.lista[j].getAmount());
                    if (index < lista.length) {
                        lista[index] = obj2;
                        index++;
                    } else {
                        increaseListSize();
                        System.out.println("ZWIĘKSZONO ROZMIAR LISTY ZAKUPÓW! :" + lista.length);
                        lista[index] = obj2;
                        index++;
                    }
                    System.out.println("Dopisano produkt do listy ZAKUPÓW! " + getDescription());
                } else {
                    System.out.println("Produkt znajduje się już na liście ZAKUPÓW!");
                }
            }

        }
    }
    private  void removeElement(int index){
        ProduktNaLiscie[] nowaLista = new ProduktNaLiscie[lista.length-1];
        //Przepisanie listy
        for(int i=0,k=0; i<lista.length; i++){
            if (i==index){
                continue;
            }
            nowaLista[k++] = lista[i];
        }
        this.lista = nowaLista;
    }
    public void koniecZakupów(){
        for (int i=0;i<getLength();i++){
            if (lista[i].wybrane){
                removeElement(i);
            }
        }
    }

    public void printOut(){
        System.out.println("\nLista zakupów "+opis);
        for(int i=0; i<lista.length;i++){
            if(lista[i] != null)
                System.out.println("Element ["+i+"] :"+lista[i]);
        }
    }

}
