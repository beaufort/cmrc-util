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

import java.util.Objects;

/**
 * Represents a term in a given language. A term has a value (text of the term,
 * e.g., label, name, etc.) and an associated language. It can be viewed as a
 * language-value pair.
 * 
 * @author Yassine Lassoued
 */
public class Term implements Comparable<Term> {
    
    /**
     * String content of the term, this may be a name, a label, etc.
     */
    private final String string;
    
    /**
     * Term language
     */
    private final String language;
    
    /**
     * Separator used to parse a term from a {@code String} qualified name.
     */
    public static final String LANG_SEPARATOR = "@";
    
    /**
     * Constructs a term by parsing the provided qualified term string. 
     * @param qualifiedTermString A qualified term string in one of the following formats:<br/>
     * {@code value@langCode}, e.g., {@code label@en}, {@code earth@en}, etc.<br/>
     * {@code value}, e.g., {@code earth}, {@code identifier}, etc.<br/>
     */
    public Term(String qualifiedTermString) {
        if (qualifiedTermString!=null) {
            if (qualifiedTermString.matches("^.*@[^@]*$")) {
                this.string = qualifiedTermString.replaceAll("^(.*)@[^@]*$", "$1");
                this.language = qualifiedTermString.replaceAll("^.*@([^@]*)$", "$1");
            }
            else {
                this.string = qualifiedTermString;
                this.language = null;
            }
        }
        else {
            this.string = "";
            this.language = null;
        }
    }

    /**
     * Constructs a Term using the provided value and language
     * @param stringContent {@code String} content of the term. If this is {@code null} then it will
     * be replaces with an empty String ({@code ""}).
     * @param language Language of the term (may be {@code null})
     */
    public Term(String stringContent, String language) {
        if (stringContent!=null) this.string = stringContent;
        else this.string = "";
        this.language = language;
    }

    /**
     * {@code String} String content of the term
     * @return String content of the term. This is never {@code null}, but may be <i>empty</i>.
     */
    public String getString() {
        return string;
    }

    /**
     * Language of the term
     * @return Language of the term. This may be {@code null}.
     */
    public String getLanguage() {
        return language;
    }
    
    /**
     * Returns the qualified string value of the term as per the {@linkplain #Term(java.lang.String)} documentation
     * @return Qualified string value of the term. This is never null, but may be <i>empty</i>.
     */
    public String getQualifiedString() {
        if (this.language != null) {
            return this.string + "@" + this.language;
        }
        else return this.string;
    }

    /**
     * Tests if this term is equal to the provided object
     * @param object Object to compare to this term
     * @return {@code true} if {@code object} is a {@code Term} and has the same value and
     * language as this term, {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        else {
            if (object == this) return true;
            else {
                if (!(object instanceof Term)) return false;
                else {
                    Term field = (Term) object;
                    return ((field.getString() == null ? this.getString() == null : field.getString().equals(this.getString())) && (field.getLanguage() == null ? this.getLanguage() == null : field.getLanguage().equals(this.getLanguage())));
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     * 
     * @return Hash code value for this Term
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.string);
        hash = 53 * hash + Objects.hashCode(this.language);
        return hash;
    }

    /**
     * {@inheritDoc}
     * 
     * @return Qualified value of this {@code Term}. This is the same as {@link #getQualifiedString()}.
     */
    @Override
    public String toString() {
        return this.getQualifiedString();
    }

    /**
     * {@inheritDoc}
     * 
     * @param otherTerm Term to compare to this instance
     * @return A negative integer, zero, or a positive integer as this term is
     * less than, equal to, or greater than the specified {@code otherTerm}.
     * Comparison is based on comparing the term languages first, then the values.
     */
    @Override
    public int compareTo(Term otherTerm) {
        if (otherTerm!=null) {
            String myLang = this.language;
            if (myLang==null) myLang="";

            String avLang = otherTerm.language;
            if (avLang==null) avLang="";

            if (!myLang.equals(avLang)) return myLang.compareTo(avLang);
            else {

                String myName = this.string;
                if (myName==null) myName="";

                String objectName = otherTerm.string;
                if (objectName==null) objectName = "";

                return (myName.compareTo(objectName));
            }
        }
        else return 1;
    }
}
