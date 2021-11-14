package org.openengine.reflection;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.jar.JarFile;

public abstract class CLTools {
    private CLTools() {}


    public static List<String> getAllKnownClassesName() {
        final var classFiles = new ArrayList<String>();
        getClassLocationsForCurrentClasspath()
                .forEach(file -> classFiles.addAll(getClassesFromPath(file)));
        return classFiles;
    }

    private static Collection<String> getClassesFromPath(File path) {
        if (path.isDirectory()) {
            return getClassesFromDirectory(path);
        } else {
            return getClassesFromJarFile(path);
        }
    }

    private static String fromFileToClassName(final String fileName) {
        return fileName.substring(0, fileName.length() - 6).replaceAll("/|\\\\", "\\.");
    }

    private static List<String> getClassesFromJarFile(File path) {
        final var classes = new ArrayList<String>();

        try {
            if (path.canRead()) {
                final var jar = new JarFile(path);
                final var en = jar.entries();
                while (en.hasMoreElements()) {
                    final var entry = en.nextElement();
                    if (entry.getName().endsWith("class")) {
                        final var className = fromFileToClassName(entry.getName());
                        classes.add(className);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read classes from jar file: " + path, e);
        }

        return classes;
    }

    private static List<String> getClassesFromDirectory(File path) {
        final var classes = new ArrayList<String>();
        // get jar files from top-level directory
        final var jarFiles = listFiles(path, (dir, name) -> name.endsWith(".jar"), false);
        for (final var file : jarFiles) {
            classes.addAll(getClassesFromJarFile(file));
        }

        // get all class-files
        final var classFiles = listFiles(path, (dir, name) -> name.endsWith(".class"), true);

        int substringBeginIndex = path.getAbsolutePath().length() + 1;
        for (File classfile : classFiles) {
            var className = classfile.getAbsolutePath().substring(substringBeginIndex);
            className = fromFileToClassName(className);
            classes.add(className);

        }

        return classes;
    }

    private static List<File> listFiles(File directory, FilenameFilter filter, boolean recurse) {
        final var files = new ArrayList<File>();
        final var entries = directory.listFiles();

        // Go over entries
        for (final var entry : entries) {
            // If there is no filter or the filter accepts the
            // file / directory, add it to the list
            if (filter == null || filter.accept(directory, entry.getName())) {
                files.add(entry);
            }

            // If the file is a directory and the recurse flag
            // is set, recurse into the directory
            if (recurse && entry.isDirectory()) {
                files.addAll(listFiles(entry, filter, recurse));
            }
        }

        // Return collection of files
        return files;
    }

    public static List<File> getClassLocationsForCurrentClasspath() {
        final var urls = new ArrayList<File>();
        final var javaClassPath = System.getProperty("java.class.path");
        if (javaClassPath != null) {
            for (final var path : javaClassPath.split(File.pathSeparator)) {
                urls.add(new File(path));
            }
        }
        return urls;
    }

    // todo: this is only partial, probably
    public static URL normalize(URL url) throws MalformedURLException {
        var spec = url.getFile();

        // get url base - remove everything after ".jar!/??" , if exists
        final int i = spec.indexOf("!/");
        if (i != -1) {
            spec = spec.substring(0, spec.indexOf("!/"));
        }

        // uppercase windows drive
        url = new URL(url, spec);
        final var file = url.getFile();
        final var i1 = file.indexOf(':');
        if (i1 != -1) {
            String drive = file.substring(i1 - 1, 2).toUpperCase();
            url = new URL(url, file.substring(0, i1 - 1) + drive + file.substring(i1));
        }

        return url;
    }

}
