package aisd.list;

import java.util.Iterator;
import java.util.ListIterator;

// metody z parametrem index rzucaj¹ wyj¹tkiem IndexOutOfBoundException
// w przypadku b³êdu zakresu jego wartoœci

public interface IList<E> extends Iterable<E> {
	// dodanie do kolekcji, gdzie - zale¿y od typu kolekcji
	// zwraca true, jeœli element zosta³ dodany
	boolean add(E value);
	// dodanie do kolekcji na okreœlon¹ pozycjê
	// zwraca wynika jak dla poprzedniego add
	boolean add(int index, E value);
	// czyœci kolekcjê
	void clear();
	// zwraca, czy kolekcja zawiera podan¹ wartoœæ
	boolean contains(E value);
	// pobiera (bez usuwania) wartoœc spod podanej pozycji
	E get(int index);
	// ustawia now¹ wartoœc na podanej pozycji, zwraca star¹ wartoœæ
	E set(int index, E value);
	// wzraca pozycjê podanej wartoœci lub -1
	int	indexOf(E value);
	// zwraca, czy kolekcja jest pusta
	boolean	isEmpty();
	// zwraca zwyk³y iterator dla tej kolekcji
	Iterator<E>	iterator();
	// zwraca dwukierunkowy iterator dla listy 
	ListIterator<E>	listIterator();
	// usuwa element z podaje pozycji, zwracaj¹c jego wartoœæ
	// lub null jeœli go nie ma
	E remove(int index);
	// usuwa wartoœæ oraz zwraca true lub zwraca false
	boolean	remove(E value);
	// zwraca liczbê elementów kolekcji
	int size();
}
