/* 
 * Copyright 2015 Coastal and Marine Research Centre (CMRC), Beaufort,
 * Environmental Research Institute (ERI), University College Cork (UCC).
 * Yassine Lassoued <y.lassoued@gmail.com, y.lassoued@ucc.ie>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ie.cmrc.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A multimap is a map where 0 or many values may be associated with a key
 * @author Yassine Lassoued
 * @param <K> Type for the keys maintained by this multimap
 * @param <V> Type of the mapped values
 */
public class Multimap<K,V> {
    
    /**
     * Key-value pairs are stored in a {@code HashMap<K,List<V>>} object that
     * maps each key to the {@code List} of values associated with it.
     */
    HashMap<K,List<V>> entries;
    
    /**
     * Constructs an empty multimap
     */
    public Multimap() {
        this.entries = new HashMap<>();
    }
    
    /**
     * Returns the number of key-value pairs in this multimap.
     *
     * <p><b>Note:</b> this method does not return the number of <i>distinct
     * keys</i> in the multimap, which is given by {@code keySet().size()} or
     * {@code asMap().size()}.
     * 
     * @return number of key-value pairs in this multimap. If the multimap is
     * empty then 0 is returned.
     */
    public int size() {
        int n = 0;
        Collection<List<V>> collection = this.entries.values();
        for (List<V> values: collection) {
            if (values != null) n += values.size();
        }
        return n;
    }

    /**
     * Indicates whether the multimap is empty
     * 
     * @return {@code true} if this multimap contains no key-value pairs.
     */
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }

    /**
     * Returns Checks whether the multimap contains at least one key-value pair
     * with the key {@code key}.
     * 
     * @param key Key to search in the multimap
     * 
     * @return {@code true} if this multimap contains at least one key-value pair
     * with the key {@code key}.
     */
    public boolean containsKey(K key) {
        return this.entries.containsKey(key);
    }

    /**
     * Checks whether the multimap contains at least one  key-value pair
     * with the value {@code value}.
     * 
     * @param value Value to search in the multimap
     * 
     * @return {@code true} if this multimap contains at least one key-value pair
     * with the value {@code value}.
     */
    public boolean containsValue(V value) {
        for (List<V> values: this.entries.values()) {
            if (values != null && values.contains(value)) return true;
        }
        return false;
    }

    /**
     * Indicates whether the multimap contains the provided {@code key-value}
     * 
     * @param key Key with which the specified value is associated
     * @param value Value to be associated with the specified key
     * 
     * @return {@code true} if this multimap contains at least one key-value pair
     * with the key {@code key} and the value {@code value}.
     */
    public boolean containsEntry(K key, V value) {
        List<V> values = this.entries.get(key);
        if (values != null) {
            return values.contains(value);
        }
        else return false;
    }

    // Modification Operations

    /**
     * Stores a key-value pair in this multimap.
     * @param key Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public void put(K key, V value) {
        if (!this.entries.containsKey(key)) this.entries.put(key, new ArrayList<V>());
        this.entries.get(key).add(value);
    }
    
    /**
     * Stores a key-value pair if it is not already contained in the multimap
     * @param key Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     * @return {@code true} if the entry was added, {@code false} otherwise
     */
    public boolean putIfAbsent(K key, V value) {
        if (!this.containsEntry(key, value)) {
            if (!this.entries.containsKey(key)) this.entries.put(key, new ArrayList<V>());
            this.entries.get(key).add(value);
            return true;
        }
        else return false;
    }
    
    /**
     * Stores a key-value entry and makes sure that {@code value} is the only
     * value associated with {@code key}. Practically speaking, this method removes
     * the current values associated with {@code key} and inserts {@code value} instead.
     * @param key Key with which the specified value is to be associated
     * @param value Value to be associated with the specified key
     */
    public void putOnly(K key, V value) {
        this.removeAll(key);
        this.put(key, value);
    }

    /**
     * Removes a single key-value pair with the key {@code key} and the value
     * {@code value} from this multimap, if such exists. If multiple key-value
     * pairs in the multimap fit this description, the first one is removed.
     * If no values are left for the provided {@code key}, then the {@code key}
     * is deleted from the set of keys and {@code containsKey} will return {@code false}.
     *
     * @param key Key of the element to remove
     * @param value Value to delete
     * @return {@code true} if the multimap has changed, false otherwise
     */

    public boolean remove(K key, V value) {
        List<V> values = this.entries.get(key);
        if (values != null) {
            boolean changed =  values.remove(value);
            if (values.isEmpty()) this.entries.remove(key);
            return changed;
        }
        else return false;
    }

    // Bulk Operations

    /**
     * Stores a key-value pair in this multimap for each of {@code values}, all
     * using the same {@code key}.
     *
     * @param key Key for the values to add
     * @param values Values to add
     * @return {@code true} if the multimap changed
     */
    public boolean putAll(K key, Collection<? extends V> values) {
        if (values != null && !values.isEmpty()) {
            if (!this.entries.containsKey(key)) this.entries.put(key, new ArrayList<V>());
            return this.entries.get(key).addAll(values);
        }
        else return false;
    }

    /**
     * Removes all values associated with the key {@code key}.
     * 
     * <p>Once this method returns, {@code key} will not be mapped to any values,
     * so it will be deleted from the list of keys {@code keySet()}.
     *
     * @param key Key the values of which to be removed
     * 
     * @return the values that were removed. If no values were removed then an
     * <i>empty</i> {@code List} is returned.
     * The returned List <i>may</i> be modifiable, but updating it will have no
     * effect on the multimap.
     */
    public List<V> removeAll(K key) {
        List<V> result = new ArrayList<>();
        List<V> removed = this.entries.remove(key);
        if (removed != null) result.addAll(removed);
        return result;
    }

    /**
     * Removes all key-value pairs from the multimap, leaving it {@linkplain
     * #isEmpty empty}.
     */
    public void clear() {
        this.entries.clear();
    }

    // Views

    /**
     * Returns a {@code List} of the values associated with {@code key} in this
     * multimap, if any. Note that when {@code containsKey(key)} is false, this 
     * returns an <i>empty</i> {@code List}.
     *
     * <p>Changes to the returned List itself (e.g., adding or removing objects)
     * will <i>not</i> update the underlying multimap. However, changes to the
     * objects themselves will be reflected in the multimap.
     * 
     * @param key Key the values associated with to be returned
     * 
     * @return {@code List} of the values associated with {@code key} in this
     * multimap, if any; otherwise an empty List.
     */
    public List<V> getAll(K key) {
        List<V> result = new ArrayList<>();
        List<V> values = this.entries.get(key);
        if (values != null) result.addAll(values);
        return result;
    }
    
    /**
     * Returns the first value associated with {@code key} in this multimap,
     * if any. Note that when {@code containsKey(key)} is false, or no value
     * is associated with the {@code key} then this returns {@code null}.
     * 
     * <p>A return value of {@code null} does not necessarily indicate that the
     * multimap contains no mapping for the {@code key}; it's also possible that
     * the first value associated with {@code key} be null. The {@code containsKey}
     * operation may be used to distinguish these two cases.
     * 
     * @param key Key the value associated with to be returned
     * 
     * @return A value ("first found") associated with the provided key, if any; otherwise null
     */
    public V get(K key) {
        List<V> values  = this.entries.get(key);
        if (values!= null && !values.isEmpty()) {
            return values.get(0);
        }
        else return null;
    }
    
    /**
     * Returns the first non null value associated with {@code key} in this multimap,
     * if any. Note that when {@code containsKey(key)} is false, or no non-null value
     * is associated with the {@code key} then this returns {@code null}.
     * 
     * @param key Key the value associated with to be returned
     * 
     * @return First non-null value associated with the provided key, if any; otherwise null
     */
    public V getNonNull(K key) {
        List<V> values  = this.entries.get(key);
        if (values!= null && !values.isEmpty()) {
            for (V value: values) {
                if (value != null) return value;
            }
        }
        return null;
    }

    /**
     * Returns a view collection of all <i>distinct</i> keys contained in this
     * multimap. Note that the key set contains a key if and only if this multimap
     * maps that key to at least one value. If the multimap is empty then an <i>empty</i>
     * {@code Set} is returned.
     *
     * <p>Changes to the returned set will update the underlying multimap, and
     * vice versa. However, <i>adding</i> to, and <i>removing</i> from, the
     * returned set are not possible.
     * 
     * @return {@code Set} of keys contained in this multimap. If the multimap
     * is empty then an <i>empty</i> {@code Set} is returned.
     */
    public Set<K> keySet() {
        return this.entries.keySet();
    }

    /**
     * Returns a {@code List} containing the <i>value</i> from each key-value
     * pair contained in this multimap, without collapsing duplicates (so {@code
     * values().size() == size()}).
     *
     * <p>Changes to the returned List itself (e.g., adding or removing objects)
     * will <i>not</i> update the underlying multimap. However, changes to the
     * objects themselves will be reflected in the multimap.
     * 
     * @return {@code List} containing the <i>value</i> from each key-value
     * pair contained in this multimap. If the multimap is empty then an <i>empty</i>
     * {@code List} is returned.
     */
    public List<V> values() {
        List<V> allValues = new ArrayList<>();
        Collection<List<V>> collection = this.entries.values();
        for (List<V> values: collection) {
            if (values != null) allValues.addAll(values);
        }
        return allValues;
    }

    /**
     * Returns a view of this multimap as a {@code Map} from each distinct key
     * to the nonempty collection of that key's associated values. Note that
     * {@code this.asMap().get(k)} is equivalent to {@code this.get(k)}.
     *
     * <p>Changes to the returned map or the collections that serve as its values
     * will update the underlying multimap, and vice versa.
     * 
     * @return {@code Map<K, List<V>>} matching each key with the associated {@code List}
     * of values. If the multimap is empty, then an <i>empty</i> {@code Map} is returned.
     */
    public Map<K, List<V>> asMap() {
        return this.entries;
    }
    
}
