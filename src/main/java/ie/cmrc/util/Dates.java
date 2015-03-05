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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class for rendering and parsing dates
 * @author yassine
 */
public class Dates {
    /** The date format in iso. */
    public static String FORMAT_DATE_ISO="yyyy-MM-dd'T'HH:mm:ssZ";

    /**
     * Takes in an ISO date string of the following format:
     * yyyy-mm-ddThh:mm:ss.ms+HoMo
     *
     * @param isoDateString the iso date string
     * @return the date
     * @throws Exception the exception
     */
    public static Date fromISODateString(String isoDateString) throws Exception {
            DateFormat f = new SimpleDateFormat(FORMAT_DATE_ISO);
            return f.parse(isoDateString);
    }

    /**
     * Render date
     *
     * @param date the date object
     * @param format - if not specified, will use FORMAT_DATE_ISO
     * @param tz - tz to set to, if not specified uses local timezone
     * @return the iso-formatted date string
     */
    public static String toISOString(Date date, String format, TimeZone tz) {
            if( format == null ) format = FORMAT_DATE_ISO;
            if( tz == null ) tz = TimeZone.getDefault();
            DateFormat f = new SimpleDateFormat(format);
            f.setTimeZone(tz);
            return f.format(date);
    }

    /**
     * Render date using the FORMAT_DATE_ISO format and the local time zone
     *
     * @param date the date object
     * @return the iso-formatted date string
     */
    public static String toISOString(Date date) {
        return toISOString(date,FORMAT_DATE_ISO,TimeZone.getDefault());
    }
}
