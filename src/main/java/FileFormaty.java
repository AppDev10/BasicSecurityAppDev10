import java.io.File;

/**
 * Created by Niels on 27/03/2015.
 */
public class FileFormaty {
    private File file;
    private String extention;
    private String fileSize;
    private String fileName;
    private String filePath;


    public FileFormaty (File file) {
        this.file = file;
        this.fileName = getFileName(file);
        this.extention = getFileExtension(file);
        this.fileSize = getFileSizeKb(file);
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(fileName.lastIndexOf(".")+1);
        else return "";
    }

    private String getFileName(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
            return fileName.substring(0,fileName.lastIndexOf("."));
        else return "";
    }

    private String getFileSizeKb (File file) {
        double fileSizeInKB = ((int)(file.length() / 1024.0 * 100))/100.0;
        return fileSizeInKB + " kB";
    }

    @Override
    public String toString() {
        return file + "  fileName: " + fileName + "  fileSize: " + fileSize + "  fileType: " + extention;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getExtention() {
        return extention;
    }

    public void setExtention(String extention) {
        this.extention = extention;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}

