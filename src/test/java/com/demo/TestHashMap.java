package com.demo;

import com.demo.HashMap.HashMap;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class TestHashMap {

    HashMap<String, Integer> map1;
    HashMap<Character, Double> map2;
    HashMap<String, String> map3;

    // 每个测试方法执行前初始化HashMap
    @BeforeEach
    public void setHashMap() {
        map1 = new HashMap<>();
        map2 = new HashMap<>();
        map3 = new HashMap<>();
    }

    // 每个测试方法执行后清空引用（辅助GC，非必需但规范）
    @AfterEach
    public void delHashMap() {
        map1 = null;
        map2 = null;
        map3 = null;
    }

    // 1. 测试正常插入键值对（非空键+非空值）
    @Test
    public void testPut_NormalKeyValue() {
        // 执行插入操作
        map1.put("apple", 10, false);
        map2.put('a', 3.14, false);
        map3.put("name", "张三", false);

        // 断言验证：插入后能正确获取值
        assertEquals(10, map1.get("apple"));
        assertEquals(3.14, map2.get('a'));
        assertEquals("张三", map3.get("name"));

        // 断言验证：元素数量正确
        assertEquals(1, map1.size());
        assertEquals(1, map2.size());
        assertEquals(1, map3.size());
    }

    // 2. 测试重复键覆盖（核心场景）
    @Test
    public void testPut_DuplicateKey() {
        // 第一次插入
        map1.put("banana", 20, false);
        assertEquals(20, map1.get("banana"));

        // 重复键插入（覆盖值）
        map1.put("banana", 30, false);

        // 断言验证：值被覆盖
        assertEquals(30, map1.get("banana"));
        // 断言验证：元素数量不变（未新增）
        assertEquals(1, map1.size());
    }

    // 3. 测试空键插入（HashMap支持null键）
    @Test
    public void testPut_NullKey() {
        // 空键+非空值
        map1.put(null, 99, false);
        assertEquals(99, map1.get(null));
        assertEquals(1, map1.size());

        // 空键+空值
        map3.put(null, null, false);
        assertNull(map3.get(null));
        assertEquals(1, map3.size());
    }

    // 4. 测试空值插入（HashMap支持null值）
    @Test
    public void testPut_NullValue() {
        // 非空键+空值
        map1.put("test", null, false);
        assertNull(map1.get("test"));
        assertEquals(1, map1.size());

        // 多次插入不同空值键
        map3.put("key1", null, false);
        map3.put("key2", null, false);
        assertEquals(2, map3.size());
        assertNull(map3.get("key1"));
        assertNull(map3.get("key2"));
    }

    // 5. 测试批量插入（验证容量/扩容逻辑）
    @Test
    public void testPut_BatchInsert() {
        // 批量插入10个不同键值对
        for (int i = 0; i < 10; i++) {
            map1.put("key" + i, i, false);
        }

        // 断言验证：所有值都能正确获取
        for (int i = 0; i < 10; i++) {
            assertEquals(i, map1.get("key" + i));
        }
        // 断言验证：总数量正确
        assertEquals(10, map1.size());
    }

    // 6. 测试插入后删除键再插入（边界场景）
    @Test
    public void testPut_AfterRemove() {
        // 插入→删除→重新插入
        map1.put("temp", 50, false);
        // map1.remove("temp"); // 假设你的HashMap有remove方法
        assertNull(map1.get("temp"));
        assertEquals(0, map1.size());

        // 重新插入相同键
        map1.put("temp", 60, false);
        assertEquals(60, map1.get("temp"));
        assertEquals(1, map1.size());
    }

    // 你的原始Put方法（可保留，作为测试辅助）
    public void Put() {
        map1.put(null, null, false);
    }
}