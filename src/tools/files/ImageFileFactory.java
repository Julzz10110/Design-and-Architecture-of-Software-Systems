package tools.files;

public class ImageFileFactory implements FileAbstractFactory {

    @Override
    public FullPermissionsFile createFullPermissionFile(String fileName) {
        System.out.println(ImageFileFactory.class.getName() + " : Файл " + fileName + " создан со всеми правами.");
        return new FullPermissionsImageFile();
    }

    @Override
    public ReadOnlyFile createReadOnlyFile(String fileName) {
        System.out.println(ImageFileFactory.class.getName() + " : Файл " + fileName + " создан с правами только для чтения.");
        return new ReadOnlyImageFile();
    }
}
