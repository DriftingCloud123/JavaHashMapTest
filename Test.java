import HashMap.HashMap;

public class Test{
    public static void main(String[] args){
        HashMap<String, Integer> map = new HashMap<String,Integer>();

        map.put("kkk", 2, true);
        map.put("vvv", 5, true);
        map.put("nnn", 7, true);
        map.put("ppp", 9, true);

        map.put("nnn1", 7, true);
        map.put("ppp1", 9, true);

        map.print();


        map.put("kkk2", 2, true);
        map.put("vvv2", 5, true);
        map.put("nnn2", 7, true);
        map.put("ppp2", 9, true);

        map.put("nnn3", 7, true);
        map.put("ppp3", 9, true);

        int val = map.get("kkk");
        System.out.println(val);
        val = map.get("vvv");
        System.out.println(val);
        val = map.get("nnn");
        System.out.println(val);
        val = map.get("ppp");
        System.out.println(val);

        System.out.println();
        map.print();
    }
}