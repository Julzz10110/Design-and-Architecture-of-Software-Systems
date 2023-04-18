package tools.files;

interface FileAbstractFactory {
    FullPermissionFile createFullPermissionFile(String fileName);

    ReadOnlyFile createReadOnlyFile(String fileName);
}
