package tools.files;

public class TableFileFactory implements FileAbstractFactory {

    @Override
    public FullPermissionFile createFullPermissionFile(String fileName) {
        System.out.println(TableFileFactory.class.getName() + " : Файл " + fileName + " создан со всеми правами.");
        return new FullPermissionsTableFile();
    }

    @Override
    public ReadOnlyFile createReadOnlyFile(String fileName) {
        System.out.println(TableFileFactory.class.getName() + " : Файл " + fileName + " создан с правами только для чтения.");
        return new ReadOnlyTableFile();
    }
}
