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
import java.util.Set;

/**
 * A {@link TermMap} is an object that maps values of given type ({@code <V>})
 * to key terms {@link Term}.
 * 
 * @author Yassine Lassoued
 * @param <V> Type of the mapped values
 */
public class TermMap<V> {
    
    /**
     * A TermMap wraps a HashMap that associates with each term text value a multimap.
     * Each multimap stores the {@code V} values associated with the term language.
     */
    HashMap<String, Multimap<String,V>> entries;

    /**
     * Constructs an empty {@link TermMap} object
     */
    public TermMap() {
        this.entries = new HashMap<>();
    }
    
    /**
     * Inserts the provided {@code value} associated with the provided {@code term}
     * @param term Term with which the specified value is to be associated
     * @param value Value to be associated with the specified term
     */
    public void put(Term term, V value) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        if (!this.entries.containsKey(termString)) this.entries.put(termString, new Multimap<String,V>());
        this.entries.get(termString).put(lang, value);
    }
    
    /**
     * Inserts the provided {@code term-value} entry if it is not already in the term map
     * @param term Term with which the specified value is to be associated
     * @param value Value to be associated with the specified term
     * @return {@code true} if the entry was added, {@code false} otherwise
     */
    public boolean  putIfAbsent(Term term, V value) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        if (!this.entries.containsKey(termString)) this.entries.put(termString, new Multimap<String,V>());
        return this.entries.get(termString).putIfAbsent(lang, value);
    }
    
    /**
     * Inserts the provided {@code term-value} entry and makes sure that {@code value}
     * is the only value associated with {@code term}
     * @param term Key term with which the specified value is to be associated
     * @param value Value to be associated with the specified key term
     */
    public void putOnly(Term term, V value) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        if (!this.entries.containsKey(termString)) this.entries.put(termString, new Multimap<String,V>());
        this.entries.get(termString).putOnly(lang, value);
    }
    
    
    /**
     * Returns the list of values associated with the provided term
     * 
     * @param term Term for which values will be returned
     * 
     * @return {@code List<V>} containing the values associated with {@code term}.
     * If the {@link TermMap} does not contain any values for the term, then
     * an <i>empty</i> {@code List<V>} is returned.
     * 
     * <p>Changes to the returned list itself (e.g., adding or removing objects)
     * will not update the underlying term map. However changes to the objects
     * of the list will update those in the term map.
     */
    public List<V> getValues(Term term) {
        List<V> result = new ArrayList<>();
        
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> allValues = this.entries.get(termString);
        if (allValues!=null) result.addAll(allValues.getAll(lang));
        
        return result;
    }
    
    /**
     * Returns a value (the first one) associated with {@code term} if any
     * @param term {@link Term} whose associated value is to be returned
     * @return First value associated with {@code term}. If no such value exists
     * then {@code null} is returned.
     */
    public V getValue(Term term) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> allValues = this.entries.get(termString);
        if (allValues!=null) return allValues.get(lang);
        else return null;
    }
    
    /**
     * Returns the first non-null value associated with {@code term} if any
     * @param term {@link Term} whose associated value is to be returned
     * @return First non-null value associated with {@code term}. If no such
     * value exists then {@code null} is returned.
     */
    public V getNonNullValue(Term term) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> allValues = this.entries.get(termString);
        if (allValues != null) return allValues.getNonNull(lang);
        else return null;
    }
    
    /**
     * Returns the values associated with the provided {@code stringTerm}
     * in the form of a {@link ie.cmrc.util.Multimap}
     * @param stringTerm {@code String} value of the term whose associated values are to be returned
     * @return A {@link ie.cmrc.util.Multimap} associating with each language
     * the values of the provided string term. If no values exist then an empty
     * multimap is returned.
     */
    public Multimap<String,V> getValues(String stringTerm) {
        Multimap<String,V> values = this.entries.get(stringTerm);
        if (values != null) return values;
        else return new Multimap<>();
    }
    
    /**
     * Returns the list of languages available for a provided string term
     * 
     * @param stringTerm String term whose associated languages are to be returned
     * @return {@code List} of languages associated with the provided string term.
     * If the provided string term does not match any {@link Term} in the
     * {@link TermMap}, then an empty {@code List} is returned.
     * 
     * <p>Changes to the returned list of languages will not update the underlying term map.
     */
    public List<String> getKeyTermLanguages(String stringTerm) {
        List<String> result = new ArrayList<>();
        
        Multimap<String,V> allValues = this.entries.get(stringTerm);
        if (allValues!=null) {
            result.addAll(allValues.keySet());
        }
        
        return result;
    }
    
    /**
     * Returns the list of all the languages available in the {@link TermMap}.
     * 
     * @return {@code List} of all term languages in this term map.
     * If the term map is empty, then an empty list is returned.
     * 
     * <p>Changes to the returned list of languages will not update the underlying term map.
     */
    public List<String> getLanguages() {
        List<String> allLangs = new ArrayList<>();
        if (this.entries!=null) {
            Set<String> termStrings = this.entries.keySet();
            for (String termString: termStrings) {
                List<String> langs = this.getKeyTermLanguages(termString);
                if (langs != null) {
                    for (String lang: langs) {
                        if (!allLangs.contains(lang)) allLangs.add(lang);
                    }
                }
            }
        }
        return allLangs;
    }
    
    /**
     * Returns the list of terms contained in this {@link TermMap}.
     * 
     * @return {@code List} of key terms in this map. If the map is empty, then an
     * empty {@code List} is returned rather than a {@code null} value.
     * 
     * <p>Changes to the returned list of terms will not update the underlying term map.
     */
    public List<Term> getKeyTerms() {
        List<Term> terms = new ArrayList<>();
        if (this.entries!=null) {
            Set<String> termStrings = this.entries.keySet();
            for (String termString: termStrings) {
                List<String> langs = this.getKeyTermLanguages(termString);
                if (langs != null) {
                    for (String lang: langs) {
                        terms.add(new Term(termString, lang));
                    }
                }
            }
        }
        return terms;
    }
    
    /**
     * Returns the number of distinct key terms in the {@link TermMap}
     * @return Number of distinct terms in the {@link TermMap}. Each term
     * is counted only once. This is equivalent to {@code getTerms.getSize()}.
     */
    public int getNumKeyTerms() {
        return this.getKeyTerms().size();
    }
    
    /**
     * Indicates whether this {@link TermMap} is empty
     * @return {@code true} is this {@link TermMap} is empty. {@code false} otherwise.
     */
    public boolean isEmpty() {
        return this.entries.isEmpty();
    }
    
    /**
     * Removes the provided {@code term-value} pair from the {@link TermMap}
     * @param term {@link Term} the associated value of which to remove from the {@link TermMap}
     * @param value Value associated with the term to remove
     * @return {@code true} if the {@link TermMap} has changed, {@code false} otherwise.
     */
    public boolean remove(Term term, V value) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> allValues = this.getValues(termString);
        if (allValues!=null) return allValues.remove(lang, value);
        else return false;
    }
    
    /**
     * Removes all the values associated with the provided {@code term}.
     * At the end of this operation, {@link #containsKeyTerm(ie.cmrc.util.Term)}
     * will return {@code false}.
     * 
     * @param term {@link Term} whose values are to be removed
     * @return The {@code List} of values that were actually removed from the {@link TermMap}.
     * If no values were removed, then {@code null} is returned.
     * 
     * The returned List is modifiable, but updating it will have no
     * effect on the term map.
     */
    public List<V> removeAll(Term term) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> allValues = this.getValues(termString);
        if (allValues!=null) return allValues.removeAll(lang);
        else return null;
    }
    
    /**
     * Removes all the languages and values associated with the provided string term.
     * At the end of this operation, {@link #containsKeyTerm(java.lang.String)}
     * will return {@code false}.
     * 
     * @param stringTerm Term string whose values are to be removed
     * @return {@code Multimap<String,V>} containing all the removed languages
     * with their associated values for the specified term string. This may be {@code null}.
     */
    public Multimap<String,V> removeAll(String stringTerm) {
        return this.entries.remove(stringTerm);
    }

    /**
     * Returns the list of term strings that appear in this {@link TermMap}
     * @return {@code List<String>} containing the term strings that exist in the {@link TermMap}.
     * 
     * If the {@link TermMap} is empty, then empty {@code List} is returned
     * rather than a {@code null} value.
     */
    public List<String> getKeyTermStrings() {
        return new ArrayList<>(this.entries.keySet()); //this.entries.keyset() is not null
    }

    /**
     * Indicates whether the {@link TermMap} contains the provided term
     * @param term Term to check
     * @return {@code true} is the term exists in the {@link TermMap}, {@code false} otherwise.
     */
    public boolean containsKeyTerm(Term term) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> map = this.entries.get(termString);
        return (map != null && map.containsKey(lang));
    }
    
    /**
     * Indicates whether this {@link TermMap} contains the term specified by
     * its string value {@code stringTerm}
     * @param stringTerm String value of a term to check
     * @return {@code true} if the term exists in the {@link TermMap}, {@code false} otherwise.
     */
    public boolean containsKeyTerm(String stringTerm) {
        Multimap<String,V> map = this.entries.get(stringTerm);
        return (map != null && !map.isEmpty());
    }
    
    /**
     * Checks whether the {@link TermMap} contains the {@code term-value} pair
     * @param term {@link Term} to check
     * @param value Value associated with {@code term}
     * @return {@code true} if the {@link TermMap} contains the pair {@code term-value},
     * {@code false} otherwise
     */
    public boolean containsEntry(Term term, V value) {
        String termString = null;
        String lang = null;
        if (term != null) {
            termString = term.getString();
            lang = term.getLanguage();
        }
        Multimap<String,V> map = this.entries.get(termString);
        return (map != null && map.containsEntry(lang, value));
    }
    
    /**
     * Checks whether the {@link TermMap} contains the {@code stringTerm-value} pair
     * @param stringTerm String value of a term to check
     * @param value Value associated with the string term
     * @return {@code true} if the {@link TermMap} contains the pair {@code stringTerm-value},
     * {@code false} otherwise
     */
    public boolean containsEntry(String stringTerm, V value) {
        Multimap<String,V> map = this.entries.get(stringTerm);
        return (map != null && map.containsValue(value));
    }
    
    /**
     * Returns the size of the {@link TermMap}. This is obtained by counting all the
     * values associated with all the terms contained in the {@link TermMap}.
     * @return Number of values contained in this {@link TermMap}. The result of this method
     * may be different than {@link #getNumKeyTerms()}.
     */
    public int getSize() {
        int size = 0;
        if (this.entries!=null) {
            Collection<Multimap<String,V>> maps = this.entries.values();
            if (maps != null) for (Multimap<String,V> map: maps) {
                size += map.size();
            }
        }
        return size;
    }
}
