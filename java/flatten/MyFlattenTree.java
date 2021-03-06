package flatten;


import java.util.List;
import java.util.ArrayList;


public class MyFlattenTree<T> implements FlattenTree<T>
{

    static final class myfunc<T> implements Function<T, List<T>>
    {
        public myfunc(MyFlattenTree<T> t)
        {
            mft = t;
        }
        
        public List<T> apply(T p) 
        {
            List<T> r = new ArrayList<T>();
            r.add(p);
            return r;
        }

        private MyFlattenTree<T> mft;
        
    }

    static final class myfunc2<T> implements Function<Triple<Tree<T>>, List<T>>
    {
        public myfunc2(MyFlattenTree<T> t) 
        {
            mft = t;
        }
        
        public List<T> apply(Triple<Tree<T>> p) 
        {
            List<T> r = new ArrayList<T>();

            r.addAll(mft.flattenInOrder(p.left()));
            r.addAll(mft.flattenInOrder(p.middle()));
            r.addAll(mft.flattenInOrder(p.right()));
            return r;
        }
        private MyFlattenTree<T> mft;
    }
    
    public List<T> flattenInOrder(Tree<T> tree) 
    {
        if (tree == null)
            throw new java.lang.IllegalArgumentException();
        
        Either<T, Triple<Tree<T>>> n =  tree.get();

        if (n.isLeft()) {
            List<T> l = n.ifLeft(new myfunc<T>(this)); // I have to use singletone
            return l;
        } else {

            List<T> l = n.ifRight(new myfunc2<T>(this)); // I have to use singletone
            return l;
        }

    }

}
