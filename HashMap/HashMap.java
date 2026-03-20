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
        System.out.println("========桶的内容========");
        Node<K, V> cur;
        for(int i = 0; i < capacity; i++){
            cur = (Node<K, V>)table[i];
            if(cur == null){
                System.out.println("[空桶]");
                continue;
            }
            else{
                while(cur != null){
                    System.out.print("[" + cur.key + "=" + cur.value + "] ");
                    cur = cur.next;
                }
                System.out.println();
            }
        }
        System.out.println("========================");
    }

    private void resize(){
        if(table == null){
            this.table = new Object[capacity];
            return;
        }
        
        Object[] oldTab  = table;
        Object[] newTab  = new Object[capacity * 2];
        /*****rehash******/
        int old_cap = capacity;
        int new_cap = old_cap * 2;
        Node<K, V> cur;
        Node<K, V> curNext;
        
        for(int i = 0; i < old_cap; i++){
            cur = (Node<K, V>)oldTab[i];
            if(cur == null){
                continue;
            }
            
            Node<K, V> nextNode;
            while(cur != null){
                nextNode = cur.next;
                int hash = (cur.key == null ? 0 : cur.key.hashCode());
                int newIdx = hash & (new_cap - 1);
                
                curNext = (Node<K, V>) newTab[newIdx];
                if(curNext == null){
                    newTab[newIdx] = cur;
                }else{
                    while(curNext.next != null){
                        curNext = curNext.next;
                    }
                    curNext.next = cur;
                }
                
                cur.next = null;    //断开原连接防止循环引用A->B->A
                cur = nextNode;
            }
        }
        
        /******************/
        capacity = new_cap;
        table = newTab;

        System.out.printf("[Debug]: 触发扩容，大小：%d->%d\n", old_cap, new_cap);
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
            System.out.println("[Debug]: 请求扩容");
            resize();
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
