package generics;

import java.util.Arrays;
import java.util.Iterator;

public class MyGenericClass<T> implements Iterable<T> {
    //It cannot be T[] because while using NoArgsConstructor we cannot write new T[].
    //T is just a type without any actual class. So you cannot create an instance of it;
    private Object[] elems;
    private int elemCount;

    public MyGenericClass() {
        elems = new Object[5];
    }

    public MyGenericClass(T[] elems) {
        this.elems = elems;
        elemCount = elems.length;
    }

    public Object[] getElems() {
        return elems;
    }

    public T get(int index) {
        if(index >= elemCount) throw new IndexOutOfBoundsException();
        return (T) elems[index];
    }

    public void add(T elem) {
        if(++elemCount >= elems.length) {
            Object[] newElems = new Object[elemCount*2];
            System.arraycopy(elems, 0, newElems, 0, elemCount - 1);
            elems = newElems;
        }
        elems[elemCount - 1] = elem;
    }

    public void add(T elem, int index) {
        if(index > elemCount) {
            throw new IndexOutOfBoundsException("Index "+ index + " out of bound");
        }
        if(index == elemCount) {
            add(elem);
            return;
        }
        shiftRight(index);
        elems[index] = elem;
    }

    public void delete(T elem) {
        if(elemCount == 0) throw new RuntimeException("elems length is 0");
        for(int i = 0; i < elemCount; i++) {
            if(elem.equals(elems[i])) {
                shiftLeft(i);
                elemCount--;
                return;
            }
        }
        throw new ElemNotFoundException();
    }

    public void delete(int index) {
        if(elemCount == 0) throw new RuntimeException("elems length is 0");
        if(index >= elemCount) throw new IndexOutOfBoundsException("elem index out of bound");
        shiftLeft(index);
        elemCount--;
    }

    private void shiftLeft(int index) {
        //I don't check bounds because it's fine if it throws indexoutofboundexception
        //that's how it should behave anyways
        if (elemCount - index >= 0) System.arraycopy(elems, index + 1, elems, index, elemCount - index);
    }

    private void shiftRight(int index) {
        //I don't check bounds because it's fine if it throws indexoutofboundexception
        //that's how it should behave anyways
        if (elemCount - index >= 0) System.arraycopy(elems, index, elems, index + 1, elemCount - index);
    }

    @Override
    public Iterator<T> iterator() {

        return new Iterator<>() {

            private int count = elemCount;
            private int index;

            @Override
            public boolean hasNext() {
                if(count != 0) return true;
                return false;
            }

            @Override
            public T next() {
                count--;
                index++;
                return (T) elems[index-1];
            }
        };
    }
}
