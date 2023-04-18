package tools.files;

import java.io.File;

interface AbstractFile {
    File createWithPermissions(String pathName);
}
