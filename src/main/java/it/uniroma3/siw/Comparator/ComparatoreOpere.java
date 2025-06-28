package it.uniroma3.siw.Comparator;

import java.util.Comparator;



import it.uniroma3.siw.model.Opera;

public class ComparatoreOpere implements Comparator<Opera> {
    @Override
    public int compare(Opera o1, Opera o2) {
        return (int) (o1.getId() - o2.getId());
    }
}
