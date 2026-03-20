package HashMap;

public class HashMap<K, V> {
    private Object[] table; //之后转成Node类型
    private int size = 0;
    private int capacity = 8;
    private float threshold = 0.75f;

    static class Node<K, V>{
        K key;
        V value;
        Node<K, V> next;

        Node(K key, V value){
            this.key = key;
            this.value = value;
        }
    }

    public HashMap(){}

    public HashMap(int capacity){
        this.capacity = capacity;
    }

    public HashMap(int capacity, float threshold){
        this.capacity = capacity;
        this.threshold = threshold;
    }

    public void print(){
        Node<K, V> cur;
        for(int i = 0; i < capacity; i++){
            cur = (Node<K, V>)table[i];
            if(cur != null){
                System.out.println();
            }
            while(cur != null){
                System.out.print(cur.value + " ");
                cur = cur.next;
            }
        }
    }

    private void resize(){
        System.out.println("[Debug]: 触发扩容");
        if(table == null){
            this.table = new Object[capacity];
        }
        else{
            Object[] newTab  = new Object[capacity * 2];
            /*****rehash******/
            // Node<K, V> cur;
            // 
            // for(int i = 0; i < capacity; i++){
            //     cur = (Node<K, V>)table[i]
            //     if(cur != null){
            //         while(cur != null){
            //             if(cur.key.hashCode() & (capacity*2) <= )
            //             cur = cur.next;
            //         }
            //     }
            // }
            
            /******************/
            capacity *= 2;
            table = newTab;
        }
    }

    public void put(K key, V value, boolean isOverwritten){
        if(table == null){
            resize();
        }

        int hash = (key == null ? 0 : key.hashCode());
        int idx = hash & (capacity - 1);

        Node<K, V> newNode = new Node<K, V>(key, value);
        @SuppressWarnings("unchecked")
        Node<K, V> cur = (Node<K, V>)table[idx];

        if(cur != null){
            if(cur.key == key || (key != null && cur.key != null && cur.key.equals(key))){
                if(isOverwritten){
                    cur.value = value;
                }
                return;
            }else{
                while(cur.next != null){
                    if(cur.next.key == key || (key != null && cur.next.key != null && cur.next.key.equals(key))){
                        if(isOverwritten){
                            cur.next.value = value;
                        }
                        return;
                    }
                    cur = cur.next;
                }
                cur.next = newNode;
            }
        }else{
            table[idx] = newNode;
        }
        size++;
        if(size > threshold * capacity){
            // resize();
            System.out.println("[Debug]: 请求扩容");
        }
    }

    public V get(K key){
        int hash = (key == null ? 0 : key.hashCode());
        int idx = hash & (capacity - 1);
        @SuppressWarnings("unchecked")
        Node<K, V> cur = (Node<K, V>)table[idx];
        if(cur != null){
            while(cur != null){
                if(cur.key == key || (key != null && cur.key != null && cur.key.equals(key))){
                    break;
                }
                cur = cur.next;
            }
        }
        return (V)(cur == null ? null : cur.value);
    }
}
