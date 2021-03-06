package org.aim42.filesystem

import groovy.io.FileType

// see end-of-file for license information

// TODO: add exclude-patterns

class FileCollector {

    // (?i): ignore-case
    // htm(l): either htm or html
    // at least one character left of the dot
    public final static def HTML_FILE_EXTENSION_PATTERN = ~/(?i).+\.htm(l)?$/

    public final static Closure<Boolean> INCLUDE_HTML_FILES = { File file ->
        // allow only html or htm files
        (file.name ==~ HTML_FILE_EXTENSION_PATTERN)
    }


    public static Boolean isHtmlFile(File file) {
        return (isHtmlFile(file.absolutePath)
                && file.isFile())
    }

    /**
     * checks if @param fileName represents a valid html file,
     * ignoring the files' content!
     * @param fileName
     * @return
     */
    public static Boolean isHtmlFile(String fileName) {
        return (fileName ==~ HTML_FILE_EXTENSION_PATTERN)
    }

    /**
     * returns all configured html files as Set<File>
     *
     */
    public static Set<File> getConfiguredHtmlFiles(
            File srcDir,
            Set<String> sourceDocs) {
        // first case: no document names given -> return all html files
        // in directory tree
        if ((sourceDocs == null) || (sourceDocs?.empty)) {
            return getAllHtmlFilesFromDirectory(srcDir)
        } else return getAllConfiguredHtmlFiles(srcDir, sourceDocs)
    }

    /**
     * returns all html files in a given directory.
     * (recursively looks in subdirectories)
     * @param dir where to look for matching files
     * @return all files with appropriate extension
     */
    public static Set<File> getAllHtmlFilesFromDirectory(File dir) {
        Set<File> files = new HashSet<File>()

        dir.eachFileRecurse(FileType.FILES) { file ->
            if (isHtmlFile(file)) {
                // add only files, not directories
                files.add(file)
            }
        }

        return files
    }

    /**
     * returns all configured html files from @param srcDocs
     * which really exist below @param srcDocs
     */
    public static Set<File> getAllConfiguredHtmlFiles(File srcDir, Set<String> srcDocs) {
        Set<File> files = new HashSet<File>()

        srcDocs.each { configuredFileName ->
            File file = new File( srcDir, configuredFileName)
            if (file.exists()) {
                files.add( file )
            }
        }

        return files

    }

}

/*========================================================================
 Copyright 2014 Gernot Starke and aim42 contributors

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an
 "AS IS" BASIS,WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 ========================================================================*/

