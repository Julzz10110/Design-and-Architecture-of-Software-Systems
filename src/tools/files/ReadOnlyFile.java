package tools.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

class ReadOnlyFile implements AbstractFile {
    @Override
    public File createWithPermissions(String pathName) {
        File file = null;
        Path path = Paths.get(pathName);
        Set<PosixFilePermission> ownerWritable = PosixFilePermissions.fromString("r--r--r--");
        FileAttribute<?> permissions = PosixFilePermissions.asFileAttribute(ownerWritable);
        try {
            file = Files.createFile(path, permissions).toFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return file;
    }
}
