package vlb.ide.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileHashMap<K, V> implements Map<K, V>{
	private static File file = null;

	static{
		try {
			file = File.createTempFile("map", ".tmp");
		} catch (IOException e) {
		}
	}

	public FileHashMap(){
		Map<K,V> map = new HashMap<K,V>(); 	
		writeMap(map);
	}

	@Override
	public void clear() {
		Map<K,V> map = readMap();
		map.clear();
		writeMap(map);
	}

	@Override
	public boolean containsKey(Object key) {
		Map<K,V> map = readMap();
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		Map<K,V> map = readMap();
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Map<K,V> map = readMap();
		return map.entrySet();
	}

	@Override
	public V get(Object key) {
		Map<K,V> map = readMap();
		V v = map.get(key);
		return v;
	}

	@Override
	public boolean isEmpty() {
		Map<K,V> map = readMap();
		boolean ret = map.isEmpty();
		return ret;
	}

	@Override
	public Set<K> keySet() {
		Map<K,V> map = readMap();
		Set<K> set = map.keySet();
		return set;
	}

	@Override
	public V put(K key, V value) {
		Map<K,V> map = readMap();
		map.put(key, value);
		writeMap(map);
		return value;
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> map2) {
		Map<K,V> map = readMap();
		map.putAll(map2);
		writeMap(map);
	}

	@Override
	public V remove(Object key) {
		Map<K,V> map = readMap();
		V value = map.remove(key);
		writeMap(map);
		return value;
	}

	@Override
	public int size() {
		Map<K,V> map = readMap();		
		return map.size();
	}

	@Override
	public Collection<V> values() {
		Map<K,V> map = readMap();
		return map.values();
	}
	
	private void writeMap(Map<K,V> map){
		try {
			FileOutputStream fout = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(fout);
			oos.writeObject(map);
			oos.close();
			fout.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}
	
	private Map<K,V> readMap(){
		try {
		    FileInputStream fin = new FileInputStream(file);
		    ObjectInputStream ois = new ObjectInputStream(fin);
		    Object o = ois.readObject();
			@SuppressWarnings("unchecked")
			Map<K,V> map = (Map<K,V>)o;
		    ois.close();
		    fin.close();
		    return map;
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (ClassNotFoundException e) {
		}
		return null;
	}

}
