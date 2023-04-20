package tools.files;

interface FileAbstractFactory {
    FullPermissionsFile createFullPermissionFile(String fileName);

    ReadOnlyFile createReadOnlyFile(String fileName);
}
