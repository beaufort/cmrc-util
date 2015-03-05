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


/**
 * Enumeration of standard two-letter languages codes
 * 
 * @author yassine
 */
public enum Language {

    ABKHAZIAN("ab"),
    AFAR("aa"),
    AFRIKAANS("af"),
    ALBANIAN("sq"),
    AMHARIC("am"),
    ARABIC("ar"),
    ARMENIAN("hy"),
    ASSAMESE("as"),
    AYMARA("ay"),
    AZERBAIJANI("az"),
    BASHKIR("ba"),
    BASQUE("eu"),
    BENGALI("bn"),
    BHUTANI("dz"),
    BIHARI("bh"),
    BISLAMA("bi"),
    BRETON("br"),
    BULGARIAN("bg"),
    BURMESE("my"),
    BYELORUSSIAN("be"),
    CAMBODIAN("km"),
    CATALAN("ca"),
    CHINESE("zh"),
    CORSICAN("co"),
    CROATIAN("hr"),
    CZECH("cs"),
    DANISH("da"),
    DUTCH("nl"),
    ENGLISH("en"),
    ESPERANTO("eo"),
    ESTONIAN("et"),
    FAEROESE("fo"),
    FIJI("fj"),
    FINNISH("fi"),
    FRENCH("fr"),
    FRISIAN("fy"),
    GAELIC("gd"),
    GALICIAN("gl"),
    GEORGIAN("ka"),
    GERMAN("de"),
    GREEK("el"),
    GREENLANDIC("kl"),
    GUARANI("gn"),
    GUJARATI("gu"),
    HAUSA("ha"),
    HEBREW("iw"),
    HINDI("hi"),
    HUNGARIAN("hu"),
    ICELANDIC("is"),
    INDONESIAN("in"),
    INTERLINGUA("ia"),
    INTERLINGUE("ie"),
    INUPIAK("ik"),
    IRISH("ga"),
    ITALIAN("it"),
    JAPANESE("ja"),
    JAVANESE("jw"),
    KANNADA("kn"),
    KASHMIRI("ks"),
    KAZAKH("kk"),
    KINYARWANDA("rw"),
    KIRGHIZ("ky"),
    KIRUNDI("rn"),
    KOREAN("ko"),
    KURDISH("ku"),
    LAOTHIAN("lo"),
    LATIN("la"),
    LATVIAN("lv"),
    LINGALA("ln"),
    LITHUANIAN("lt"),
    MACEDONIAN("mk"),
    MALAGASY("mg"),
    MALAY("ms"),
    MALAYALAM("ml"),
    MALTESE("mt"),
    MAORI("mi"),
    MARATHI("mr"),
    MOLDAVIAN("mo"),
    MONGOLIAN("mn"),
    NAURU("na"),
    NEPALI("ne"),
    NORWEGIAN("no"),
    OCCITAN("oc"),
    ORIYA("or"),
    OROMO("om"),
    PASHTO("ps"),
    PERSIAN("fa"),
    POLISH("pl"),
    PORTUGUESE("pt"),
    PUNJABI("pa"),
    QUECHUA("qu"),
    RHAETO_ROMANCE("rm"),
    ROMANIAN("ro"),
    RUSSIAN("ru"),
    SAMOAN("sm"),
    SANGRO("sg"),
    SANSKRIT("sa"),
    SERBIAN("sr"),
    SERBO_CROATIAN("sh"),
    SESOTHO("st"),
    SETSWANA("tn"),
    SHONA("sn"),
    SINDHI("sd"),
    SINGHALESE("si"),
    SISWATI("ss"),
    SLOVAK("sk"),
    SLOVENIAN("sl"),
    SOMALI("so"),
    SPANISH("es"),
    SUDANESE("su"),
    SWAHILI("sw"),
    SWEDISH("sv"),
    TAGALOG("tl"),
    TAJIK("tg"),
    TAMIL("ta"),
    TATAR("tt"),
    TEGULU("te"),
    THAI("th"),
    TIBETAN("bo"),
    TIGRINYA("ti"),
    TONGA("to"),
    TSONGA("ts"),
    TURKISH("tr"),
    TURKMEN("tk"),
    TWI("tw"),
    UKRAINIAN("uk"),
    URDU("ur"),
    UZBEK("uz"),
    VIETNAMESE("vi"),
    VOLAPUK("vo"),
    WELSH("cy"),
    WOLOF("wo"),
    XHOSA("xh"),
    YIDDISH("ji"),
    YORUBA("yo"),
    ZULU("zu");
    
    /**
     * ISO two-letter code of the language
     */
    private final String twoLetterCode;
    
    /**
     * Constructs a language provided its two letter code
     * @param twoLetterCode 
     */
    private Language(String twoLetterCode) {
        this.twoLetterCode = twoLetterCode;
    }

    /**
     * Two-letter code of the language
     * @return Two-letter code of the language
     */
    public String twoLetterCode() {
        return twoLetterCode;
    }
    
    /**
     * {@inheritDoc}
     * @return English name of the language
     */
    @Override
    public String toString() {
        String[] tokens = this.name().split("_");
        String label = " ";
        for (int i=0; i<tokens.length; i++) {
            String token = tokens[i];
            String word = token;
            if (word.length()>1) {
                word = word.substring(0,1) + word.substring(1).toLowerCase();
            }
            if (!word.isEmpty()) label += word;
            if (i<tokens.length-1) label += "";
        }
        return label;
    }
    
    /**
     * Parses a two-letter language code and returns the matching {@code Language}
     * @param twoLetterLanguageCode Two-letter language code to parse
     * @return {@code Language} matching the parsed two-letter code if
     * possible, otherwise {@code null}
     */
    public static Language fromString(String twoLetterLanguageCode) {
        if (twoLetterLanguageCode != null) {
            for (Language language: Language.values()) {
                if (twoLetterLanguageCode.equals(language.twoLetterCode)) return language;
            }
        }
        return null;
    }

}
