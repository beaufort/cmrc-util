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

import java.io.File;

/**
 * Utility class for file manipulation
 * 
 * @author Yassine Lassoued <y.lassoued@ucc.ie>
 */
public class FileUtil {
    /**
     * Deletes the provided file. If the file is a direcotry then all of its content
     * will be deleted recursively.
     * @param file {@code java.io.File} to delete
     * @return {@code true} if the provided file was successfully deleted or does
     * not exist, {@code false} otherwise.
     */
    public static boolean delete(File file) {
        boolean allDeleted = true;
        if (file.exists()) {
            if (file.isFile()) {
                allDeleted = file.delete();
            }
            else if (file.isDirectory()) {
                File[] children = file.listFiles();
                if (children != null) {
                    for (File child: children) {
                        if (!delete(child)) allDeleted = false;
                    }
                }
                if (!file.delete()) allDeleted = false;
            }
        }
        return allDeleted;
    }
}
