package aisd.list;

import java.util.Iterator;
import java.util.ListIterator;

// metody z parametrem index rzucaj� wyj�tkiem IndexOutOfBoundException
// w przypadku b��du zakresu jego warto�ci

public interface IList<E> extends Iterable<E> {
	// dodanie do kolekcji, gdzie - zale�y od typu kolekcji
	// zwraca true, je�li element zosta� dodany
	boolean add(E value);
	// dodanie do kolekcji na okre�lon� pozycj�
	// zwraca wynika jak dla poprzedniego add
	boolean add(int index, E value);
	// czy�ci kolekcj�
	void clear();
	// zwraca, czy kolekcja zawiera podan� warto��
	boolean contains(E value);
	// pobiera (bez usuwania) warto�c spod podanej pozycji
	E get(int index);
	// ustawia now� warto�c na podanej pozycji, zwraca star� warto��
	E set(int index, E value);
	// wzraca pozycj� podanej warto�ci lub -1
	int	indexOf(E value);
	// zwraca, czy kolekcja jest pusta
	boolean	isEmpty();
	// zwraca zwyk�y iterator dla tej kolekcji
	Iterator<E>	iterator();
	// zwraca dwukierunkowy iterator dla listy 
	ListIterator<E>	listIterator();
	// usuwa element z podaje pozycji, zwracaj�c jego warto��
	// lub null je�li go nie ma
	E remove(int index);
	// usuwa warto�� oraz zwraca true lub zwraca false
	boolean	remove(E value);
	// zwraca liczb� element�w kolekcji
	int size();
}
