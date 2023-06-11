import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void main(String[] args) {
        // Создать три экземпляра класса GameProgress
        GameProgress gp1 = new GameProgress(100, 100, 1, 1.0);
        GameProgress gp2 = new GameProgress(90, 90, 2, 10.0);
        GameProgress gp3 = new GameProgress(80, 50, 5, 15.0);
        String path1 = "D://Games//savegames//save1.data";
        String path2 = "D://Games//savegames//save2.data";
        String path3 = "D://Games//savegames//save3.data";

        // Сохранить сериализованные объекты GameProgress в папку savegames из предыдущей задачи.
        saveGame(path1, gp1);
        saveGame(path2, gp2);
        saveGame(path3, gp3);

        // Созданные файлы сохранений из папки savegames запаковать в архив zip.
        List<String> toZip = List.of(path1, path2, path3);
        zipFiles("D://Games//savegames//zip.zip", toZip);

        // Удалить файлы сохранений, лежащие вне архива.
        File file1 = new File(path1);
        File file2 = new File(path2);
        File file3 = new File(path3);
        deleteFile(file1);
        deleteFile(file2);
        deleteFile(file3);
    }

    public static void saveGame(String path, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
            System.out.println("Создан файл: " + path);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles(String zipPath, List<String> toZip) {
        try (ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipPath))) {

            for (String path : toZip) {
                File file = new File(path);
                FileInputStream fis = new FileInputStream(file);

                ZipEntry entry = new ZipEntry(file.getName());
                zout.putNextEntry(entry);

                byte[] buffer = new byte[fis.available()];
                int bytes;
                while ((bytes = fis.read(buffer)) != -1) {
                    zout.write(buffer, 0, bytes);
                }
                fis.close();
                zout.closeEntry();
            }
            System.out.println("Создан Архив: " + zipPath);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void deleteFile(File file) {
        if (file.delete()) {
            System.out.println("Удален файл: " + file);
        } else System.out.println("Ошибка удаления");
    }
}